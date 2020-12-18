package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.product.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository repo;

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id){
    	
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
    public ResponseEntity getOrdersPagination(@RequestParam(value = "per_page", required = false, defaultValue = "-1" ) final int per_page, @RequestParam(value = "page",required = false,defaultValue = "-1") final int page){
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


    @RequestMapping(path="/insert")
    public String testInsert(){
        int i=1;
        List<Product> l1= new ArrayList<Product>();
        Product p1 = new Product(2,3);
        l1.add(p1);
        while(i<20) {
            repo.save(new Order(i, i*2, l1, "via milano", "milano", 1, "n/d"));;
            i++;
        }
        return "salvataggio effetuato con successo";
    }
}
