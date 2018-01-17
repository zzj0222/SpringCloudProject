package com.laiai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description : 测试客户端负载均衡的接口API
 */
@RestController
@RequestMapping(value = "/test")
public class TestRibbonApi {
	
    /**
     * 注入RestTemplate
     */
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value = "/name" ,method = RequestMethod.GET)
    public String testGetNameOfBlog(){
        String url="http://cloud-service/ribbon/name";
        return restTemplate.getForObject(url,String.class);
    }
}
