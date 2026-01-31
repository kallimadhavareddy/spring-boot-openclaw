package com.kalli.tech.openclaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootOpenclawApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOpenclawApplication.class, args);
	}

}
