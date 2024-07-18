package com.supermarkets.service.Supermarket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarkets.dao.SupermarketDB;
import com.supermarkets.service.CrudService;
import com.supermarkets.v1.model.Supermarket;

@Service("supermarketService")
public class SupermarketService implements CrudService<Supermarket>{
	@Autowired
	private SupermarketDB dao;

	@Override
	public List<Supermarket> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supermarket getById(Integer id) {
		return dao.getSupermarketById(id);
	}

	@Override
	public String save(Supermarket model) {		
		return dao.saveSuperMarket(model);
	}

	@Override
	public String update(String id, Supermarket model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteSuperMarket(id);
	}

}
