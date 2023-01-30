package com.info.order.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.info.order.entity.Order;
import com.info.order.model.Product;
import com.info.order.model.RestResponse;
import com.info.order.service.OrderService;
import com.info.order.util.RestHelper;

@RestController
@RequestMapping("/")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("orders")
	public ResponseEntity<RestResponse> getorders() {
		List<Order> orders = orderService.findAll();
		return RestHelper.responseSuccess("orders", orders);
	}
	
	@GetMapping("order/{id}")
	public ResponseEntity<RestResponse> getorder(@PathVariable Long id) {
		Order order = orderService.findById(id);
		if(!Objects.nonNull(order)) {
			return RestHelper.responseError("Order not found", HttpStatus.BAD_REQUEST);
		}
		Product product = restTemplate.getForObject("http://product-service/product/" + order.getProductId(), Product.class);
		if(Objects.nonNull(product)) {
			order.setProduct(product);
		}
		return RestHelper.responseSuccess("order", order);
	}
	
	@PostMapping("order")
	public ResponseEntity<RestResponse> saveOrder(@RequestBody Order order) {
		Product product = restTemplate.getForObject("http://product-service/product/" + order.getProductId(), Product.class);
		if(!Objects.nonNull(product)) {
			return RestHelper.responseError("Product not found", HttpStatus.BAD_REQUEST);
		}
		Order newOrder = new Order();
		newOrder.setCount(order.getCount());
		newOrder.setInvoice(order.getInvoice());
		newOrder.setShippingInformation(order.getShippingInformation());
		newOrder.setProductId(order.getProductId());
		orderService.save(newOrder);
		return RestHelper.responseMessage("Order saved successfully.", HttpStatus.OK);
	}
	
	@PutMapping("order/{id}")
	public ResponseEntity<RestResponse> editOrder(@PathVariable Long id, @RequestBody Order order) {
		Order oldOrder = orderService.findById(id);
		if(!Objects.nonNull(oldOrder)) {
			return RestHelper.responseError("Order not found", HttpStatus.BAD_REQUEST);
		}
		
		Product product = restTemplate.getForObject("http://product-service/product/" + order.getProductId(), Product.class);
		if(!Objects.nonNull(product)) {
			return RestHelper.responseError("Product not found", HttpStatus.BAD_REQUEST);
		}
		
		oldOrder.setCount(order.getCount());
		oldOrder.setInvoice(order.getInvoice());
		oldOrder.setShippingInformation(order.getShippingInformation());
		oldOrder.setProductId(order.getProductId());
		orderService.save(oldOrder);
		return RestHelper.responseMessage("Order updated successfully.", HttpStatus.BAD_REQUEST);
	}

}
