package com.amit.SecurityApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityAppApplication.class, args);
		log.info("Application is starting by Amit Lal");
	}

}
