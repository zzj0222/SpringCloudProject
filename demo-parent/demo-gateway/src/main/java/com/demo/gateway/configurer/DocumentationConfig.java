package com.demo.gateway.configurer;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *  配置swagger文档资源
 * @author zzj
 * @create 2019-03-12 15:13
 **/
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
//        resources.add(swaggerResource("网关API系统", "/v2/api-docs", "2.0"));
        resources.add(swaggerResource("用户系统", "/demo-member/v2/api-docs", "2.0"));
        resources.add(swaggerResource("订单系统", "/demo-order/v2/api-docs", "2.0"));
        resources.add(swaggerResource("支付系统", "/demo-pay/v2/api-docs", "2.0"));
        resources.add(swaggerResource("商品系统", "/demo-product/v2/api-docs", "2.0"));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
