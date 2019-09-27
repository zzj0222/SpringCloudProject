package com.demo.mq.rocketmq.service.impl;

import com.demo.mq.rocketmq.service.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zzj
 * @create 2019-05-29 14:30
 **/
@Service
public class ProducerImpl implements Producer{

    private  Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private  final DefaultMQProducer defaultMQProducer;
    public ProducerImpl() {
        this.defaultMQProducer = new DefaultMQProducer(  this.getProperties().getProperty("producerGroup"));
    }


    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public SendResult send(Message var1) {
        SendResult sendResult =null;
        try {
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        try {
             sendResult = this.defaultMQProducer.send(var1);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sendResult;
    }

    /**
     * 同步发送消息
     * @param var1
     */
    @Override
    public void sendOneway(Message var1) {

    }

    /**
     * 异步发送消息
     * @param var1
     * @param var2
     */
    @Override
    public void sendAsync(Message var1, SendCallback var2) {

    }
}
