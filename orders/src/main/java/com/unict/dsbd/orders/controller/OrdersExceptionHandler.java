package com.unict.dsbd.orders.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;
import com.unict.dsbd.orders.order.CustomErrorMessage;

@ControllerAdvice
public class OrdersExceptionHandler extends ResponseEntityExceptionHandler {

	public static final Logger log = LoggerFactory.getLogger(OrdersExceptionHandler.class);
	
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${spring.application.name}")
	private String serviceName;
	
    @Value("${kafkaLogTopic}")
    private String logTopic; 
    
    private final String HTTP_ERRORS = "http_errors";
	
    private void sendMessage(String topicName, String key, String msg) {
    	log.info("Publishing new message in topic: {}, key: {}, msg: {}", topicName, key, msg);
        kafkaTemplate.send(topicName, key, msg);
    }
	
	@ExceptionHandler(value= { NullPointerException.class })
	protected ResponseEntity<Object> internalServerErrorExceptionHandler(
			RuntimeException ex, 
			HttpServletRequest servletRequest, 
			WebRequest webRequest){
		
		
		CustomErrorMessage error = new CustomErrorMessage();
		error.setTimestamp(Instant.now().getEpochSecond());
		error.setSourceIp(servletRequest.getRemoteAddr());
		error.setService(serviceName);
		error.setRequest(servletRequest.getRequestURI().concat(" ").concat(servletRequest.getMethod()));
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		error.setError(sw.toString());
		
    	String errorMessage = new Gson().toJson(error);
    	sendMessage(logTopic, HTTP_ERRORS, errorMessage);
		
		log.error("OrdersExceptionHandler: {} Error: {}", ex.getClass(), ex.getMessage());
		ex.printStackTrace();
		return handleExceptionInternal(ex, error, 
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
	}
}
