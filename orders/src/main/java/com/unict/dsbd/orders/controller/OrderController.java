package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.product.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<Order>> getOrders(){
        /*TO DO::   - implementare passaggio userID al metodo findAllById().
        *           - Implementare meccanismo di impaginazione per_Page
        * */
        ArrayList<Order> o1 = repo.findAllByUserId(1);

        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }


    @RequestMapping(path="/insert")
    public String testInsert(){
        Product p1 = new Product(2,2);
        List<Product> l1= new ArrayList<Product>();
        l1.add(p1);

        Order o1 = new Order(2,1222.1,l1,"via milano","milano",1,"n/d");
        repo.save(o1);
        return "salvataggio effetuato con successo";
    }
}
