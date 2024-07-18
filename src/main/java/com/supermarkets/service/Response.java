package com.supermarkets.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Response {


	private String code;
	private String message;
	@JsonSerialize(using =com.supermarkets.utils.EmptyDetailsSerializer.class)
	private Details details;
	private String status;

	public Response(String message, Integer id) {

		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getResponse();

		int responseCode = response.getStatus();
		if (responseCode == 200) {
			setMessage(message);
			setStatus("success");

		} else {
			setMessage("Error in operations");
			setStatus(responseCode + " error");
		}
		setCode(getStatus().toUpperCase());
		details = new Details();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		details.setId(id);
		details.setModifiedOn(LocalDateTime.now().format(formatter));

	}
	public Response(String message,StringBuilder paramName)
	{
		details = new Details();
		if(message.equals("Required field not found") || paramName!=null)
		{
			if(paramName.toString().equals("id"))
			{
				setCode("BAD_REQUEST");
			}
			else
			setCode("MANDATORY_NOT_FOUND");

			details.setParam_name(paramName.toString());

		}
		else if(message.contains("Path variable"))
		{
			setCode("INVALID_URL");

		}
		else if(message.contains("Unable"))
		{
			setCode("INTERNAL_SERVER_ERROR");
		}
		else{
			setCode("BAD_REQUEST");
		}
		setStatus("error");
		setMessage(message);


	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Details {
		private Integer id;
		private String modifiedOn;
		private String param_name;
		public String getParam_name() {
			return param_name;
		}

		public void setParam_name(String param_name) {
			this.param_name = param_name;
		}

		public String getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(String modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}
}
