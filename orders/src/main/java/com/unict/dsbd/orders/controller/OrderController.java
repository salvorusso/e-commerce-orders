package com.unict.dsbd.orders.controller;


import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.stereotype.Controller
public class OrderController {

    @Autowired
    OrderRepository repo;

    @RequestMapping(path = "/insert")
    public @ResponseBody String test(){
        repo.save(new Order(2,"antonio",10));
        return "inserimento effettuato";
    }
}
