package com.demo.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dubbo.provider.service.DemoService;

/**
 * @author zzj
 * @create 2018-12-07 15:28
 **/
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "eureka-1"
)
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
