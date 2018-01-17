package com.laiai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 配置中心启动类-客户端
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LaiaiConfigClientApplication {

	public static void main(String[] args) {

		SpringApplication.run(LaiaiConfigClientApplication.class, args);
	}

}
