package com.demo.product.grpc.config;
import com.demo.product.grpc.interceptor.LogGrpcInterceptor;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author zzj
 * @create 2018-11-21 14:21
 **/
@Configuration
public class GlobalClientInterceptorConfiguration {
    @Bean
    public GlobalServerInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {
        return new GlobalServerInterceptorConfigurerAdapter() {
            @Override
            public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
                registry.addServerInterceptors(new LogGrpcInterceptor());
            }
        };
    }
}
