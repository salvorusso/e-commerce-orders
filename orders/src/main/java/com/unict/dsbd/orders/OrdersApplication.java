package com.unict.dsbd.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdersApplication  {
	
	public static String version;
	public static final Logger log = LoggerFactory.getLogger(OrdersApplication.class);

	
	public OrdersApplication(@Value("${appVersion}") String v) {
		version=v;
	}

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
		log.info("OrdersApplication up and running, version: "+ version);


	}

}
