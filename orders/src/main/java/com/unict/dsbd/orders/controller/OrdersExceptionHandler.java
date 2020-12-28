package com.unict.dsbd.orders.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrdersExceptionHandler extends ResponseEntityExceptionHandler {

	public static final Logger log = LoggerFactory.getLogger(OrdersExceptionHandler.class);
	
	@ExceptionHandler(value= { NullPointerException.class })
	protected ResponseEntity<Object> internalServerErrorExceptionHandler(
			RuntimeException ex, WebRequest request){
		String errMsg = ex.getClass().toString();
		log.error("OrdersExceptionHandler: {} Error: {}", ex.getClass(), ex.getMessage());
		ex.printStackTrace();
		return handleExceptionInternal(ex, errMsg, 
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
