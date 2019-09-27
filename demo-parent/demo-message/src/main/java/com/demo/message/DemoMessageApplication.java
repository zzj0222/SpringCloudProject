package com.demo.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 通知服务 短信、邮件通知
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class DemoMessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoMessageApplication.class, args);
	}
}
