package com.demo.product.feign;


import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zzj
 * @create 2018-11-06 15:07
 **/
@FeignClient(value = "demo-member")
public interface IMemberService {
}
