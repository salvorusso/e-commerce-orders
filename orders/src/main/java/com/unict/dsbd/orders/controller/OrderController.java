package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository repo;

    @GetMapping(path = "/{id}")
    public ResponseEntity getOrderById(@PathVariable int id){
        Order o1 = repo.findById(id);
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "")
    public ResponseEntity getOrders(){
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
