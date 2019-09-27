package com.demo.gateway.filter;

import com.demo.gateway.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zzj
 * @create 2019-03-19 9:22
 **/
@Component
@Slf4j
public class PostFilter {
    @Resource
    RedisUtil redisUtil;

}
