package com.unict.dsbd.orders.controller;


import com.mongodb.client.MongoClient;
import com.unict.dsbd.orders.heartBeater.HeartBeat;
import com.google.gson.Gson;
import com.unict.dsbd.orders.order.ExtraArgs;
import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderPaymentRequest;
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
import org.springframework.web.server.MediaTypeNotSupportedStatusException;
import org.springframework.web.server.ResponseStatusException;

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
    
    @Value("${kafkaInvoicingTopic}")
    private String invoicingTopic;
    
    @Value("${kafkaLogTopic}")
    private String logTopic; 
    
    private final String ORDER_COMPLETED = "order_completed";
    private final String ORDER_VALIDATION = "order_validation";
    private final String ORDER_PAID = "order_paid";
    private final String ORDER_VALIDATION_FAILURE = "order_paid_validation_failure";
    private final String ABORT_STATUS = "Abort";
    private final String PAID_STATUS = "Paid";
    private final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";
    private final String WRONG_AMOUNT = "WRONG_AMOUNT_PAID";
    
    private void sendMessage(String topicName, String key, String msg) {
    	log.info("Publishing new message in topic: {}, key: {}, msg: {}", topicName, key, msg);
        kafkaTemplate.send(topicName, key, msg);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {

        int tmpUserIdGet = 0;
        Order tmpOrder;
        String userInfoGet = headers.getFirst("X-User-ID");

        log.info("getOrderById: {}", id);
        log.debug("userInfo: {}", userInfoGet);

        try {
            tmpUserIdGet = Integer.parseInt(userInfoGet);

        } catch (NumberFormatException e) {
            String msg = "X-User-ID malformed: " + userInfoGet;
            log.error(msg);
            //return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, msg, e);
              
        }

        if (tmpUserIdGet != 0)
            tmpOrder = repo.findByIdAndUserId(id, tmpUserIdGet);
        else
            tmpOrder = repo.findById(id);

        if (tmpOrder != null)
            return ResponseEntity.ok(tmpOrder);
  	
        Order o1 = repo.findById(id);
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else {
            log.error("getOrderById: Order {} Not Found", id);
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getOrdersPagination(@RequestParam(value = "per_page", required = false, defaultValue = "-1") final int per_page,
                                                           @RequestParam(value = "page", required = false, defaultValue = "-1") final int page,
                                                           @RequestHeader HttpHeaders headers) {
        ArrayList<Order> tmpOder = null;
        int tmpUserId = 0;
        String userInfoGet = headers.getFirst("X-User-ID");

        log.debug("userInfo: {}", userInfoGet);

        if (userInfoGet == null) {
            String msg = "Missing X-User-ID Header";
            log.error(msg);
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

        try {
            tmpUserId = Integer.parseInt(userInfoGet);

        } catch (NumberFormatException e) {
            String msg = "X-User-ID malformed: " + userInfoGet;
            log.error(msg);
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

        if (per_page == -1 && page == -1)
            if (tmpUserId != 0)
                tmpOder = repo.findAllByUserId(tmpUserId);
            else
                tmpOder = (ArrayList<Order>) repo.findAll();
        else {
            Pageable p1 = PageRequest.of(page, per_page);
            if (tmpUserId != 0)
                tmpOder = repo.findAllByUserId(tmpUserId, p1);
            else
                tmpOder = new ArrayList<Order>(repo.findAll(p1).getContent());
        }

        if (tmpOder != null)
            return ResponseEntity.ok(tmpOder);
        else
            return ResponseEntity.notFound().build();
    }

/*
    Decommentare questo metodo se si vuole testare il meccanismo di heartBeat!
*/
/*    @PostMapping(path = "/ping",produces = MediaType.APPLICATION_JSON_VALUE)
    public String PostHeartBeatTest(@RequestBody String h1){
        return h1;
    }*/

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
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
    
    @KafkaListener(topics = "${kafkaMainTopic}")
    public void orderValidation(
    		@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
    		@Payload String message) {
    	
    	log.info("Received Message: key:{}, message:{} ", key, message);
    	if(key.equals(ORDER_VALIDATION)) {
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
    	}else if(key.equals(ORDER_PAID)){
    		OrderPaymentRequest paymentRequest = new Gson().fromJson(message, OrderPaymentRequest.class);
    		
    		Order order = repositoryServices.findOrderByIdAndUser(paymentRequest.getOrderId(), paymentRequest.getUserId());
    		String msg = "";
    		if(order == null) {
    			log.error("Order {} not found for UserId {}", paymentRequest.getOrderId(), paymentRequest.getUserId());
    			msg = ORDER_NOT_FOUND;
    			ExtraArgs extraArgs = new ExtraArgs(msg);
    			paymentRequest.setExtraArgs(extraArgs);
    	    	String errorMessage = new Gson().toJson(paymentRequest);
    	    	sendMessage(logTopic, ORDER_VALIDATION_FAILURE, errorMessage);
    	    	return;
    		}
    		
    		if(paymentRequest.getAmountPaid() != order.getTotal()) {
    			log.error("Order total {}, amount paid {}", order.getTotal(), paymentRequest.getAmountPaid());
    			msg = WRONG_AMOUNT;
    			ExtraArgs extraArgs = new ExtraArgs(msg);
    			paymentRequest.setExtraArgs(extraArgs);
    			String errorMessage = new Gson().toJson(paymentRequest);
    			sendMessage(logTopic, ORDER_VALIDATION_FAILURE, errorMessage);
    			
    			order.setStatus(ABORT_STATUS);
    			order.setExtraArgs(extraArgs);
    			order = repositoryServices.updateOrder(order);
    			return;
    		}
    		
    		
    		log.info("Setting Order {} status to Paid", order.getId());
    		order.setStatus(PAID_STATUS);
    		if(order.getExtraArgs()!=null)
    			order.getExtraArgs().setError("");
    		
			order = repositoryServices.updateOrder(order);
			log.debug("{}", order);
			String successMessage = new Gson().toJson(paymentRequest);
			sendMessage(notificationTopic, ORDER_PAID, successMessage);
			sendMessage(invoicingTopic, ORDER_PAID, successMessage);
    	}
        
    }
}
