package com.supermarkets.v1.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import com.supermarkets.service.Response;
import com.supermarkets.v1.model.POJO;


public abstract class CrudHandler <T>{

	@Autowired
	private Validator validator;

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
    abstract List<T> getAll();
    abstract T getById(Integer id);
    abstract Response save(POJO model);
    //abstract Response saveAll(List<Customer> models);
    abstract Response update(String id, T model);
    abstract Response delete(Integer id);
}