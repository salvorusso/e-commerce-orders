package com.unict.dsbd.orders.controller;


import com.google.gson.Gson;
import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.order.OrderValidationRequest;
import com.unict.dsbd.orders.services.RepositoryServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;
    
    @Autowired
    private RepositoryServices repositoryServices;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    @Value("${kafkaMainTopic}")
    private String mainTopic;
    
    @Value("${kafkaNotificationTopic}")
    private String notificationTopic;
    
    private final String ORDER_COMPLETED = "order_completed";
    private final String ORDER_VALIDATION = "order_validation";
    private final String ABORT_STATUS = "Abort";
    
    private void sendMessage(String topicName, String key, String msg) {
    	log.info("Publishing new message in topic: {}, key: {}, msg: {}", topicName, key, msg);
        kafkaTemplate.send(topicName, key, msg);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id){
    	
    	log.info("getOrderById: {}", id);
    	
        Order o1 = repo.findById(id);
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else {
        	log.error("getOrderById: Order {} Not Found", id);
        	 return ResponseEntity.notFound().build();
        }
           
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Order>> getOrdersPagination(@RequestParam(value = "per_page", required = false, defaultValue = "-1" ) final int per_page, @RequestParam(value = "page",required = false,defaultValue = "-1") final int page){
        ArrayList<Order> o1;
        if(per_page == -1 && page == -1)
            o1 = repo.findAllByUserId(1);
        else{
            Pageable p1 = PageRequest.of(page,per_page);
            o1 = repo.findAllByUserId(1, p1);
        }
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }


    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> newOrder(
    		@RequestBody Order order,
    		@RequestHeader HttpHeaders headers){
    	
		String userInfo = headers.getFirst("X-User-ID");
		log.debug("userInfo: {}", userInfo);
		
		if(userInfo == null) {
			String msg = "Missing X-User-ID Header";
			log.error(msg);
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);	
		}
		
		try {
			order.setUserId(Integer.parseInt(userInfo));
		} catch (NumberFormatException e) {
			String msg = "X-User-ID malformed: " + userInfo;
			log.error(msg);
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		
    	log.info("newOrder {}", order);
    	order = repositoryServices.insertOrder(order);
    	String message = new Gson().toJson(order);
    	sendMessage(mainTopic, ORDER_COMPLETED, message);
    	sendMessage(notificationTopic, ORDER_COMPLETED, message);
    	log.debug("order successfully saved: {}", order);
    	return ResponseEntity.ok(order);
    }
    
    @KafkaListener(topics = "orders", groupId = "ordermanager")
    public void orderValidation(
    		@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
    		@Payload String message) {
    	
    	if(key.equals(ORDER_VALIDATION)) {
    		log.info("Received Message: key:{}, message:{} ", key, message);
    		OrderValidationRequest validationRequest = new Gson().fromJson(message, OrderValidationRequest.class);
    		
    		Order order = repo.findById(validationRequest.getOrderId());
    		if (order == null) {
    			log.error("Order not found");
    			return;
    		}
    		
    		if(validationRequest.getStatus() != 0) {
    			log.info("Status code: {} ; Setting Order {} status to Abort", 
    					validationRequest.getStatus(), validationRequest.getOrderId());
    			order.setStatus(ABORT_STATUS);
    			order = repositoryServices.updateOrder(order);
    			log.debug("{}", order);
    		}
    	}  
        
    }
}
