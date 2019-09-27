package com.demo.member.configurer;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mkw
 * @className FeignHttpClientConfigurer
 * @description TODO
 * @date 2018/8/20 13:37
 */
@Configuration
public class FeignHttpClientConfigurer {
    @Bean
    public HttpClient httpClient(HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager connectionManager) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000).setSocketTimeout(3000).setConnectionRequestTimeout(3000).build();
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(connectionManager);
        builder.setDefaultRequestConfig(requestConfig);
        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        return builder;
    }

    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(250);
        return connectionManager;
    }
}
