package com.info.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.info.order.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String count;
	private String invoice;
	
	@Column(name = "shipping_information")
	private String shippingInformation;
	
	@JsonIgnore
	@Column(name = "product_id")
	private String productId;
	
	@Transient
	private Product product;
	
}
