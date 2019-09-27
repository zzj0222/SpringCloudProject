package com.demo.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import com.demo.member.model.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * https://blog.csdn.net/qq_18603599/article/details/81172866
 * 消息生产者
 * @author zzj
 * @create 2019-05-28 12:24
 **/
public class Producer {
    DefaultMQProducer producer = new DefaultMQProducer("test-group");


    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("192.168.184.128:9876");
        producer.setInstanceName("rmq-instance-a");
        producer.start();
        try {
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setUserName("abc" + i);
                user.setPassword(String.valueOf(i));
                Message message = new Message("log-topic", "user-tag", JSON.toJSONString(user).getBytes());

                System.out.println("生产者发送消息:" + JSON.toJSONString(user));
                SendResult result = producer.send(message);
                System.out.println("响应结果："+result);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }




}
