package com.supermarkets.exception;


public class CustomParameterizedException extends RuntimeException {

	private String parameterName;
	 public CustomParameterizedException(String message,String parameterName){
	        super(message);
	        this.parameterName=parameterName;

	    }
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}

