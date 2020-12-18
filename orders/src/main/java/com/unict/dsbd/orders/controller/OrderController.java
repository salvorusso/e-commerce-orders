package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import com.unict.dsbd.orders.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class OrderController {

    @Autowired
    OrderRepository repo;

    @GetMapping(path = "/orders/{id}")
    public @ResponseBody ResponseEntity getOrderById(@PathVariable int id){
        Order o1 = repo.findById(id);
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(path = "/orders"/*,params = "!pagination"*/)
    public @ResponseBody ResponseEntity getOrders(){
/*
        TO DO:
                - implementare passaggio userID al metodo findAllById().
                
*/

        ArrayList<Order> o1 = repo.findAllByUserId(1);

        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }

/*

    To Do:
            -meotodo funzionante, cercare modo per fare funzionae entrambi i merodi

    @GetMapping(path = "/orders",params = "pagination")
    public @ResponseBody ResponseEntity getOrdersPagination(@RequestParam("per_page") final int per_page, @RequestParam("page") final int page){

        Pageable p1 = PageRequest.of(page,per_page);
        ArrayList<Order> o1 = repo.findAllByUserId(1, p1);
        if(o1 != null)
            return ResponseEntity.ok(o1);
        else
            return ResponseEntity.notFound().build();
    }
*/


    @RequestMapping(path="/insert")
    public @ResponseBody String testInsert(){
        int i = 3;
        List<Product> l1= new ArrayList<Product>();
        Product p1 = new Product(i*2,i*3);
        l1.add(p1);


        while(i<20) {
            repo.save(new Order(i, i*2, l1, "via milano", "milano", 1, "n/d"));;
            i++;
        }
        return "salvataggio effetuato con successo";
    }
}
