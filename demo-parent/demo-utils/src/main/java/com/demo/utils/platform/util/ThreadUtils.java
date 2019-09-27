package com.demo.utils.platform.util;

import com.demo.utils.thread.ThreadPoolExecutor;

/**
 * Created by makuangwen on 2018/04/28.
 *
 * 线程工具类
 */
public class ThreadUtils {


    /**
     *
     * @param runnable:需要运行的线程
     *
     * @throws Exception
     * */
    public static synchronized void getSortThread(Runnable runnable){
        ThreadPoolExecutor.getThreadPoolExecutor().execute(runnable);
    }

    /**
     *
     * @param runnable:需要运行的线程
     *
     * @throws Exception
     * */
    public static synchronized void getSortTimeOutThread(Runnable runnable){
        ThreadPoolExecutor.getThreadPoolExecutor().execute(runnable);
    }


}
