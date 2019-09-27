package com.demo.mq.rocketmq.service;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author zzj
 * @create 2019-05-29 14:25
 **/
public interface Producer {

    boolean isStarted();

    boolean isClosed();

    void start();

    void shutdown();

    SendResult send(Message var1);

    void sendOneway(Message var1);

    void sendAsync(Message var1, SendCallback var2);
}
