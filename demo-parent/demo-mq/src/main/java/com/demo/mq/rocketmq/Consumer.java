package com.demo.mq.rocketmq;


import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息消费者
 * @author zzj
 * @create 2019-05-28 17:10
 * 原文：https://blog.csdn.net/qq_18603599/article/details/81172866
 * DefaultMQPushConsumer 和 DefaultMQPullConsumer
 **/
public class Consumer {
    private static final Map<MessageQueue,Long> OFFSE_TABLE = new HashMap<MessageQueue,Long>();
    public static void main(String[] args) throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-group");
//        consumer.setNamesrvAddr("192.168.184.128:9876");
//        consumer.setInstanceName("rmq-instance-a");
//        consumer.subscribe("log-topic", "user-tag");
//      设置一个Listener，主要进行消息的逻辑处理
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(
//                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                for (MessageExt msg : msgs) {
//                    System.out.println("消费者消费数据:"+new String(msg.getBody()));
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        consumer.start();


        DefaultMQPullConsumer consumer=new DefaultMQPullConsumer("order-group");
        consumer.setNamesrvAddr("192.168.184.128:9876");
        consumer.start();
        Set<MessageQueue> mqs=consumer.fetchSubscribeMessageQueues("order-topic");
       for(MessageQueue mq:mqs){
           try {
               // 获取消息的offset，指定从store中获取
               long offset = consumer.fetchConsumeOffset(mq,true);
               System.out.println("consumer from the queue:"+mq+":"+offset);
               while(true){
                   PullResult pullResult = consumer.pullBlockIfNotFound(mq, null,
                           getMessageQueueOffset(mq), 32);
                   putMessageQueueOffset(mq,pullResult.getNextBeginOffset());
                   switch(pullResult.getPullStatus()){
                       case FOUND:
                           List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                           for (MessageExt m : messageExtList) {
                               System.out.println(new String(m.getBody()));
                           }
                           break;
                       case NO_MATCHED_MSG:
                           break;
                       case NO_NEW_MSG:
                           break;
                       case OFFSET_ILLEGAL:
                           break;
                   }
               }
           } catch (Exception e) {
               e.printStackTrace();
           }


       }



    }



    // 保存上次消费的消息下标
    private static void putMessageQueueOffset(MessageQueue mq,
                                              long nextBeginOffset) {
        OFFSE_TABLE.put(mq, nextBeginOffset);
    }

    // 获取上次消费的消息的下标
    private static Long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if(offset != null){
            return offset;
        }
        return 0L;
    }







}
