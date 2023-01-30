package com.info.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private Long id;
	private String code;
	private String name;
	private Double price;
}
