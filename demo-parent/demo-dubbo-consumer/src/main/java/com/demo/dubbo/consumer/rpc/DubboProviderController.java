package com.demo.dubbo.consumer.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.core.BaseController;
import com.demo.core.Result;
import com.demo.dubbo.provider.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzj
 * @create 2018-12-06 13:31
 **/
@RestController
@RequestMapping("/rpcDubboProvider")
public class DubboProviderController extends BaseController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880")
    private DemoService demoService;


    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

    @RequestMapping("/test")
    public Result test() {
        return success("test");
    }
}
