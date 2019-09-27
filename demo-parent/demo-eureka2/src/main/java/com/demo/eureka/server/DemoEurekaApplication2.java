package com.demo.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;



@SpringBootApplication
@EnableEurekaServer
public class DemoEurekaApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(DemoEurekaApplication2.class, args);
	}

}
