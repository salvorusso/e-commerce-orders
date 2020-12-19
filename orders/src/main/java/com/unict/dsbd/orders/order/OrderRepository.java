package com.unict.dsbd.orders.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, Integer>, PagingAndSortingRepository<Order,Integer> {
    ArrayList<Order> findAllByUserId(int userId);
    Order findById(UUID id);
    ArrayList<Order> findAllByUserId(int userId, Pageable pageable);

}
