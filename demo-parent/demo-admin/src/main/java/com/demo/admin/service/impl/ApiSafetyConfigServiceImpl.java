package com.demo.admin.service.impl;

import com.demo.admin.mapper.ApiSafetyConfigMapper;
import com.demo.admin.model.ApiSafetyConfig;
import com.demo.admin.service.ApiSafetyConfigService;
import com.demo.utils.common.*;
import com.google.common.base.Joiner;
import com.laiai.core.abstractbean.AbstractService;
import com.laiai.platform.util.IdWorker;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by CodeGenerator on 2019-03-07 09:50:56.
 */
@Service
public class ApiSafetyConfigServiceImpl extends AbstractService<ApiSafetyConfig> implements ApiSafetyConfigService {

    private static final FastDateFormat TIMESTAMP_FORMAT = FastDateFormat.getInstance("yyyyMMdd");
    private static final AtomicInteger counter = new AtomicInteger(new SecureRandom().nextInt());
    private static final Joiner KEY_JOINER = Joiner.on("-");
    private static final Joiner APPID_JOINER = Joiner.on("");

    @Resource
    private ApiSafetyConfigMapper apiSafetyConfigMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    @Transactional
    public void saveApiSafetyConfig(ApiSafetyConfig apiSafetyConfig) {
        apiSafetyConfig.setAppId("app_"+ UniqueKeyGeneratorUtils.generate());
        apiSafetyConfig.setAppKey("sk_live_"+ RandomUtils.getRandomString(24));
        apiSafetyConfig.setAppSecret( UUID.randomUUID().toString().replaceAll("-", ""));


        apiSafetyConfigMapper.insertSelective(apiSafetyConfig);
    }

    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//            System.out.println("app_"+UUID.randomUUID().toString().replaceAll("-", "").substring(0,16));
//            System.out.println("sk_live_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24));
        System.out.println(APPID_JOINER.join(TIMESTAMP_FORMAT.format(new Date()),RandomUtils.getRandomString(8),counter.incrementAndGet()));


    }
}
