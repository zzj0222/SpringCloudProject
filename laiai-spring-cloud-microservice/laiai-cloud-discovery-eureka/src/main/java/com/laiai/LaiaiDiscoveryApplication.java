package com.laiai;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.SpringApplication;
@SpringBootApplication
@EnableEurekaServer
public class LaiaiDiscoveryApplication {

	public static void main(String[] args) {
		
		 SpringApplication.run(LaiaiDiscoveryApplication.class, args);

	}

}
