package org.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ExempleRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ExempleRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExempleRestApplication.class, args);
		log.info("YOUSSEF AND ANAS AND YOSR YASMINE");
	}
}

