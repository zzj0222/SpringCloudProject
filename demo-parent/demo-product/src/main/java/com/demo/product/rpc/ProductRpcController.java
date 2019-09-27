package com.demo.product.rpc;


import com.demo.product.model.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;


/**
 * 提供给其它内部模块调用的接口
 * 命名规范:模块名称＋Rpc+Controller结尾(模块名称要跟数据库表名一致)
 * @author zzj
 * @create 2018-11-06 15:25
 **/
@RestController
@RequestMapping("/api/productRpc")
public class ProductRpcController {

//    @Resource
//    MemberGrpcClientService memberGrpcClientService;

    @RequestMapping(value = "/getProductList",method = RequestMethod.GET)
    List<Product> getProductList(){
        List<Product> productList=new ArrayList<Product>();
        Product product=new Product();
        product.setId(1);
        product.setName("iphone5");
//        memberGrpcClientService.sendMessage("iphone6")
        productList.add(product);
//        int i=5/0;
        return productList;
    }


}
