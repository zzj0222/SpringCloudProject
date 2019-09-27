package com.demo.gateway;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.laiai.core.util.ParameterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableApolloConfig
public class DemoGatewayApplication  {

	public static void main(String[] args) {
		ParameterUtils.setApolloEnv();
		SpringApplication.run(DemoGatewayApplication.class, args);

	}

}
