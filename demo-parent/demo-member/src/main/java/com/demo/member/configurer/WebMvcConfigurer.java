package com.demo.member.configurer;

import java.util.List;

import com.laiai.core.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.laiai.core.handler.ExceptionHander;

	
/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    //private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
    

    // 统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    	exceptionResolvers.add(new ExceptionHander());
    }


    // 解决跨域问题,注意：但使用gateway时，只需要在gateway中设置跨域,应用服务中不需要设置
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
//    }

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor());

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("doc.html")
    .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("swagger-ui.html")
    .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
    .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
