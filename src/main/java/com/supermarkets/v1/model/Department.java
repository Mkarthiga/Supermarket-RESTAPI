package com.supermarkets.v1.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department implements POJO {

	private Integer id;
	@NotNull(message = "Department category should not be null")
	private String category;

	private List<Product> products;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(Integer id, String category, List<Product> products) {
		super();
		this.id = id;
		this.category = category;
		this.products = products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
