package com.supermarkets.v1.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.supermarkets.exception.CustomParameterizedException;
import com.supermarkets.exception.CustomizedException;
import com.supermarkets.service.Response;
import com.supermarkets.service.Supermarket.SupermarketService;
import com.supermarkets.v1.model.Department;
import com.supermarkets.v1.model.POJO;
import com.supermarkets.v1.model.Product;
import com.supermarkets.v1.model.Supermarket;

@Component("supermarketHandlerV1")
public class SupermarketHandler extends CrudHandler<POJO> {
	@Autowired
	private SupermarketService supermarketService;

	@Override
	public List<POJO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POJO getById(Integer id) {
		return supermarketService.getById(id);
	}

	@Override
	public Response save(POJO model) {
		if (validatePojo((Supermarket) model)) {
			String result = supermarketService.save((Supermarket) model);
			return new Response(result, ((Supermarket) model).getId());
		} else {
			return new Response("error in validation", ((Supermarket) model).getId());
		}

	}

	@Override
	public Response update(String id, POJO model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(Integer id) {
		String result=supermarketService.delete(id);
		return new Response(result, id);
	}

	public boolean validatePojo(Supermarket supermarket) throws CustomizedException {

		Errors errors = new BeanPropertyBindingResult(supermarket, "supermarketHandlerV1");
		ValidationUtils.invokeValidator(getValidator(), supermarket, errors);
		String paramName ="";
		if (supermarket.getName() == null) {
			throw new CustomParameterizedException("Malformed or Missing request body", "name");
		}

		if (errors.hasErrors()) {
			String errorMessages = errors.getAllErrors().get(0).getDefaultMessage();
			if (errorMessages.contains("name")) {
				paramName = "name";
			}
			throw new CustomParameterizedException(errorMessages, paramName);
		}

		for (Department d : supermarket.getDepartments()) {
			errors = new BeanPropertyBindingResult(d, "department");
			ValidationUtils.invokeValidator(getValidator(), d, errors);
			if (errors.hasErrors()) {
				String errorMessages = errors.getAllErrors().get(0).getDefaultMessage();
				if (errorMessages.contains("category")) {
					paramName = "category";
				}
				
				throw new CustomParameterizedException(errorMessages, paramName);
			}
			for (Product p : d.getProducts()) {
				errors = new BeanPropertyBindingResult(p, "product");
				ValidationUtils.invokeValidator(getValidator(), p, errors);
				if (errors.hasErrors()) {
					String errorMessages = errors.getAllErrors().get(0).getDefaultMessage();
					if (errorMessages.contains("name")) {
						paramName = "name";
					} else if (errorMessages.contains("quantity")) {
						paramName = "quantity";
					} else if (errorMessages.contains("unit price")) {
						paramName = "price";

						throw new CustomParameterizedException(errorMessages, paramName);
					}
				}
			}

		}

		return true;
	}

}
