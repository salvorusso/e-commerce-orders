package com.unict.dsbd.orders.order;

import com.unict.dsbd.orders.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface OrderRepository extends MongoRepository<Order, Integer>, PagingAndSortingRepository<Order,Integer> {
    Order findById(int id);
    ArrayList<Order> findAllByUserId(int userId);
    ArrayList<Order> findAllByUserId(int userId, Pageable pageable);

}
