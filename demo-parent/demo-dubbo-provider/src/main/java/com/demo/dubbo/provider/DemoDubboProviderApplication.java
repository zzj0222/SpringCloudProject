package com.demo.dubbo.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class DemoDubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDubboProviderApplication.class, args);

	}

//	public static void main(String[] args) {
//		new SpringApplicationBuilder(DemoDubboProviderApplication.class)
//				.web(false)
//				.run(args);
//	}
}
