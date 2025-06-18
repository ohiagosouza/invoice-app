package com.hiagosouza.api.quoted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@ComponentScan("com.hiagosouza")
@SpringBootApplication
public class QuotedApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotedApplication.class, args);
	}

}
