package com.demo.mq.receiver;
import com.demo.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author zzj
 * @create 2018-11-14 17:06
 **/
@Slf4j
@EnableBinding(ICustomSink.class)
public class Customer {

    @StreamListener(ICustomSink.secondsKillInput)
    public  synchronized  void secondsKillReceive(Product killMessage){
        // 秒杀业务处理
        log.info("秒杀业务处理message : {}", killMessage);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
