package com.demo.member;



import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.laiai.core.util.ParameterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;



@SpringBootApplication(scanBasePackages={"com.demo.utils.common","com.demo.member.*"})
@EnableEurekaClient
@EnableFeignClients
@EnableApolloConfig
public class DemoMemberApplication {
	public static void main(String[] args) {
		ParameterUtils.setApolloEnv();
		SpringApplication.run(DemoMemberApplication.class, args);
	}
}
