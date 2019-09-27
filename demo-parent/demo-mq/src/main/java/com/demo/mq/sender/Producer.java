package com.demo.mq.sender;

import com.demo.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 *  生产者-发送消息
 * @author zzj
 * @create 2018-11-14 15:20
 **/
@Slf4j
@Component
@EnableBinding(CustomSource.class)
public class Producer {


    @Resource
    CustomSource customSource;

    @Scheduled(fixedRate = 3000)
    public  void sendSecondsKillMessage(){
        Product product=new Product();
        Date date=new Date();
        product.setId(date.getTime());
        product.setName("商品名称:"+new Date());
        MessageBuilder<Product> messageBuilder = MessageBuilder.withPayload(product);
        customSource.secondsKillOutput().send(messageBuilder.build());
    }
}
