package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.services.RepositoryServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository repo;
    
    @Autowired
    RepositoryServices repositoryServices;

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    @GetMapping(path = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id, @RequestHeader HttpHeaders headers){

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
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    	if(tmpUserIdGet != 0)
            tmpOrder = repo.findByIdAndUserId(id,tmpUserIdGet);
    	else
            tmpOrder = repo.findById(id);

        if(tmpOrder != null)
            return ResponseEntity.ok(tmpOrder);
        else {
        	log.error("getOrderById: Order {} Not Found", id);
        	 return ResponseEntity.notFound().build();
        }
           
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getOrdersPagination(@RequestParam(value = "per_page", required = false, defaultValue = "-1" ) final int per_page,
                                                           @RequestParam(value = "page",required = false,defaultValue = "-1") final int page,
                                                           @RequestHeader HttpHeaders headers){
        ArrayList<Order> tmpOder = null;
        int tmpUserId = 0;
        String userInfoGet = headers.getFirst("X-User-ID");

        log.debug("userInfo: {}", userInfoGet);

        if(userInfoGet == null) {
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

        if(per_page == -1 && page == -1)
            if(tmpUserId != 0)
                tmpOder = repo.findAllByUserId(tmpUserId);
            else
                tmpOder = (ArrayList<Order>) repo.findAll();
        else{
            Pageable p1 = PageRequest.of(page,per_page);
            if(tmpUserId != 0)
                tmpOder = repo.findAllByUserId(tmpUserId, p1);
            else
                tmpOder =  new ArrayList<Order>(repo.findAll(p1).getContent());
        }

        if(tmpOder != null)
            return ResponseEntity.ok(tmpOder);
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
    	log.debug("order successfully saved {}", order);
    	return ResponseEntity.ok(order);
    }
}
