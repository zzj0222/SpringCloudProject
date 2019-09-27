//package com.demo.member.feign.hystrix;
//
//import com.demo.member.feign.IProductService;
//
//import com.demo.product.model.Product;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author zzj
// * @create 2018-11-08 15:01
// **/
//@Service
//public class ProductHyStrix implements IProductService {
//
//    @Override
//    public List<Product> getProductList() {
//        List<Product> productList=new ArrayList<Product>();
//        Product product=new Product();
//        product.setId(1);
//        product.setName("fallback：Iphone");
//        productList.add(product);
//        return productList;
//    }
//}
