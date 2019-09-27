package com.demo.zinpkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import zipkin.server.EnableZipkinServer;


@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class DemoZinpkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoZinpkinApplication.class, args);
	}
}
