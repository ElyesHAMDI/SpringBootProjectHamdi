package org.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ExempleRestApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(ExempleRestApplication.class, args);
//	}
	private static final Logger log = LoggerFactory.getLogger(ExempleRestApplication.class);
	public static void main(String... args) {
		ConfigurableApplicationContext context =SpringApplication.run(ExempleRestApplication.class, args);
		log.info("YOUSSEF HAMDI ANS ANAS");
	}

}
