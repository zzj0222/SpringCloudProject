package com.demo.mq.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 *  生产者-发送消息接口(即消息发布者)
 * @author zzj
 * @create 2018-11-13 11:48
 **/
public interface CustomSource {

    /**
     * 绑定通道
     * 秒杀通道名称：secondsKillOutput
     * @return
     */
    @Output("secondsKillOutput")
    MessageChannel secondsKillOutput();
}
