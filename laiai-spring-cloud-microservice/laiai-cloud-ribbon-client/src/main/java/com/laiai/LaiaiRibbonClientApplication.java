package com.laiai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description :启动类，示范负载均衡服务器
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LaiaiRibbonClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(LaiaiRibbonClientApplication.class, args);
    }

    /**
     * Spring提供的用于访问Rest服务的客户端
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}