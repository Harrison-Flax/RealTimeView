package com.realtimeview.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

// Enables scheduling for updating stocks
@EnableScheduling
@SpringBootApplication
public class AppApplication {
	// For debugging/logging
	public static final Logger logger = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	// ObjectMapper for StockService
	// Needs to be defined here aside from the pom.xml
	// Since Spring needs an instance of it to hand over to the utilized classes
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}