package com.supermarkets.v1.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName(value = "supermarket")
public class Supermarket implements POJO {

	private Integer id;
	@NotNull(message = "SuperMarket name should not be null")
	private String name;
	private String address;
	private List<Department> departments;

	public Supermarket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Supermarket(Integer id, String name, String address, List<Department> departments) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.departments = departments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
