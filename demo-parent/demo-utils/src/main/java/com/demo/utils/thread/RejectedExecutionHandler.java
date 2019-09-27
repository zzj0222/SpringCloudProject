package com.demo.utils.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionHandler implements java.util.concurrent.RejectedExecutionHandler {

    private final Logger log = LoggerFactory.getLogger(java.util.concurrent.RejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 处理任务被拒绝的情况，例如记录日志等
    }
}