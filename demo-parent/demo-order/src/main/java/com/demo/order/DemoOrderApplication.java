package com.demo.order;

import com.demo.order.mq.IStreamClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class DemoOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoOrderApplication.class, args);
	}

}
