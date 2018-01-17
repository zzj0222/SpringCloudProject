package com.laiai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
/**
 * 配置中心启动类-服务端
 */
//@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigServer
public class LaiaiConfigApplication {
	public static void main(String[] args) {
        SpringApplication.run(LaiaiConfigApplication.class, args);
    }
}
