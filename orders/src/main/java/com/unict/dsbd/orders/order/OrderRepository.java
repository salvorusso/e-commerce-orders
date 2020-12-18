package com.unict.dsbd.orders.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findById(int id);
    ArrayList<Order> findAllByUserId(int userId);
}
