package com.demo.product.mq;

import com.demo.product.model.Product;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author zzj
 * @create 2018-11-13 11:51
 **/
@Slf4j
@Component
//@EnableBinding(ICustomService.class)
public class StreamReceiver {

//    @StreamListener("inventoryReductionOutput")
//    public void process(Product message) {
//        log.info("减库存message : {}", message);
//    }
}
