package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.services.RepositoryServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Order> newOrder(
    		@RequestBody Order order){

    	log.info("newOrder {}", order);
    	order = repositoryServices.insertOrder(order);
    	log.debug("order successfully saved {}", order);
    	return ResponseEntity.ok(order);
    }
}
