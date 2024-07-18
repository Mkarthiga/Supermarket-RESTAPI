package com.supermarkets.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.supermarkets.ProxyFactory.ProxyFactoryClass;
import com.supermarkets.service.Response;
import com.supermarkets.v1.handler.SupermarketHandler;
import com.supermarkets.v1.model.POJO;

@RestController
@Validated
@RequestMapping("/api")
public class MainController {

	private final ApplicationContext context;
	private final Gson gson = new Gson();
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	public MainController(ApplicationContext context) {
		this.context = context;
	}
	@PostMapping("/{version}/supermarket")
	public ResponseEntity<?> createVendor(@PathVariable String version, @RequestBody String jsonString) {
		SupermarketHandler supermarketHandler;
		try {
			supermarketHandler = (SupermarketHandler) ProxyFactoryClass.createProxy(context, version, "supermarket");
			POJO vendor = (POJO) objectMapper.readValue(jsonString, ProxyFactoryClass.clazz);
			Response response = supermarketHandler.save(vendor);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (BeansException | JsonProcessingException e) {

			e.printStackTrace();
			return new ResponseEntity<>(new Response(e.getMessage(), (StringBuilder) null), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/{version}/supermarket/{supermarketId}")
	public ResponseEntity<?> getVendorById(@PathVariable String version, @PathVariable Integer supermarketId) {
		SupermarketHandler supermarketHandler;
		try {
			supermarketHandler= (SupermarketHandler) ProxyFactoryClass.createProxy(context, version, "supermarket");
			POJO supermarket = supermarketHandler.getById(supermarketId);
			String jsonOutput = objectMapper.writeValueAsString(supermarket);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			if (supermarket != null) {
				return new ResponseEntity<>(jsonOutput,headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Response("Id doesn't exist",new StringBuilder("id")),HttpStatus.NOT_FOUND);
			}
		} catch (BeansException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	@DeleteMapping("/{version}/supermarket")
	public ResponseEntity<?> deleteSale(@PathVariable String version, @RequestParam Integer id) {
		SupermarketHandler supermarketHandler;
		try {
			supermarketHandler= (SupermarketHandler) ProxyFactoryClass.createProxy(context, version, "supermarket");
			Response response =supermarketHandler.delete(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(new Response(e.getMessage(), id), HttpStatus.NOT_FOUND);
		}

	}

	
}
