package com.supermarkets.service;

import java.util.List;


public interface CrudService <T>{

     List<T> getAll();
     T getById(Integer id);
     String save(T model);
     String update(String id, T model);
     String delete(Integer id);
}
