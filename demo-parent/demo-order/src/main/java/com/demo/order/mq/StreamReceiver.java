package com.demo.order.mq;

import com.demo.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author zzj
 * @create 2018-11-12 15:36
 **/
@Slf4j
@Component
@EnableBinding(IStreamClient.class)
public class StreamReceiver {
    @StreamListener("myMessageOutput")
    public void process(Product message) {
        log.info("message : {}", message);
    }



}
