package com.unict.dsbd.orders.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unict.dsbd.orders.order.Order;
import com.unict.dsbd.orders.order.OrderRepository;

@Service
@Transactional
public class RepositoryServices {

	@Autowired
	private OrderRepository repository;
	
	public Order insertOrder(Order order) {
		order.setId(UUID.randomUUID());
		order = repository.save(order);
		return order;
		
	}
}
