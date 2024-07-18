package com.supermarkets.exception;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.supermarkets.service.Response;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({CustomizedException.class })
	public ResponseEntity<?> handleCustomException(Exception exception) {

		return new ResponseEntity<>(new Response(exception.getMessage(), (StringBuilder) null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ CustomParameterizedException.class })
	public ResponseEntity<?> handleCustomParameterException(CustomParameterizedException exception) {

		return new ResponseEntity<>(
				new Response(exception.getMessage(), new StringBuilder(exception.getParameterName())),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {

		return new ResponseEntity<>(new Response("Path variable not found", (StringBuilder) null),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Response> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException exception) {

		return new ResponseEntity<>(
				new Response("Required field not found", new StringBuilder(exception.getParameterName())),
				HttpStatus.BAD_REQUEST);
	}


	  @ExceptionHandler({ HttpMessageNotReadableException.class,
	  HttpMediaTypeNotSupportedException.class, ValidationException.class }) public
	  ResponseEntity<Response>
	  handleMissingOrMalformedRequestBodyException(Exception exception) {

	  return new ResponseEntity<>(new Response("Missing or malformed request body",
	  (StringBuilder) null), HttpStatus.BAD_REQUEST); }


	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {

		exception.printStackTrace();
		return new ResponseEntity<>(new Response(exception.getMessage(), (StringBuilder) null), HttpStatus.BAD_REQUEST);
	}
}
