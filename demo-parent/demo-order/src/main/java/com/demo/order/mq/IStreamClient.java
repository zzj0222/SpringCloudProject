package com.demo.order.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author zzj
 * @create 2018-11-12 15:15
 **/
public interface IStreamClient {
    /**
     * 接收消息、入口
     * @return
     */
    @Input("myMessageInput")
    SubscribableChannel input();


    /**
     * 发送消息
     * @return
     */
    @Output("myMessageOutput")
    MessageChannel output();

    /**
     * 发送消息-减库存
     * @return
     */
    @Output("inventoryReductionOutPut")
    MessageChannel inventoryReductionOutPut();

}
