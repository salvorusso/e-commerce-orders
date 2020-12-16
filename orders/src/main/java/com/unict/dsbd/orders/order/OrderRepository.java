package com.unict.dsbd.orders.order;

import com.unict.dsbd.orders.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findByName(String name);
}
