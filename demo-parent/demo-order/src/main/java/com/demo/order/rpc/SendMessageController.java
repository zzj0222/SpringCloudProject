package com.demo.order.rpc;

import com.demo.order.mq.IStreamClient;
import com.demo.product.model.Product;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzj
 * @create 2018-11-12 15:55
 **/

@RestController
public class SendMessageController {


    @Resource
    IStreamClient streamClient;

    @GetMapping("/send/msg")
    public void send() {
        for (int i = 0; i < 100; i++) {
            MessageBuilder<String> messageBuilder = MessageBuilder.withPayload("这是第" + i + "条消息");
            streamClient.output().send(messageBuilder.build());
        }
    }


    @GetMapping("/send/dto")
    public void sendDto() {
        Product product=new Product();
        product.setId(2);
        product.setName("商品名称");
            MessageBuilder<Product> messageBuilder = MessageBuilder.withPayload(product);
            streamClient.output().send(messageBuilder.build());

    }

    /**
     * 消息-减库存
     */
    @GetMapping("/send/inventoryReduction")
    public void inventoryReduction() {
        Product product=new Product();
        product.setId(2);
        product.setName("商品名称");
        MessageBuilder<Product> messageBuilder = MessageBuilder.withPayload(product);
        streamClient.inventoryReductionOutPut().send(messageBuilder.build());

    }


}
