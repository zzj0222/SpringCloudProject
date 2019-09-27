package com.demo.grpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * emil:miles02@613.com
 * Created by forezp on 2018/8/11.
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( GrpcServerApplication.class, args );
    }
}
