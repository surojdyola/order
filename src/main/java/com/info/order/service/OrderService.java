package com.info.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.order.entity.Order;
import com.info.order.repo.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	public List<Order> findAll() {
		return orderRepo.findAll();
	}
	
	public Order save(Order order) {
		return orderRepo.save(order);
	}
	
	public Order findById(Long id) {
		return orderRepo.findById(id).get();
	}

}
