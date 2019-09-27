package com.demo.mq.receiver;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author zzj
 * @create 2018-11-14 17:00
 **/
public interface ICustomSink {

    String secondsKillInput="secondsKillInput";
    /**
     * 绑定通道
     * 秒杀通道名称：secondsKillInput
     * @return
     */
    @Input(secondsKillInput)
    SubscribableChannel secondsKillInput();
}
