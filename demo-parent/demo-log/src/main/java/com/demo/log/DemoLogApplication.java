package com.demo.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class DemoLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLogApplication.class, args);
	}
}
