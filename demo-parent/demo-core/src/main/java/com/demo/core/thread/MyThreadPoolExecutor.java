package com.demo.core.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor {

    /**
     * 核心线程数设置成10
     */
    private static int CORE_POOL_SIZE = 10;

    /**
     * 线程池最大线程数为30
     */
    private static int MAXIMUM_POOL_SIZE = 30;

    /**
     * 超过核心线程数量的线程idle 30秒之后会退出
     */
    private static int KEEP_ALIVE_TIME = 30;

    /**
     * 队列长度为100
     */
    private static int QUEUE_LENGTH = 100;


    private static ThreadPoolExecutor threadPoolExecutor = null;

    public static synchronized ThreadPoolExecutor getThreadPoolExecutor(){
        if(null==threadPoolExecutor){
            threadPoolExecutor = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUE_LENGTH),
                    new MyRejectedExecutionHandler()
            );
        }
        return  threadPoolExecutor;
    }


}
