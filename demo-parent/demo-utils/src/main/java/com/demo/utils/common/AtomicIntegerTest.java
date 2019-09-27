package com.demo.utils.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zzj
 * @create 2019-03-27 15:00
 **/
public class AtomicIntegerTest {
    private static final int THREADS_CONUT = 20;
    public static AtomicInteger count = new AtomicInteger(1000);

    public static void increase() {
        count.incrementAndGet();
        System.out.println(count);
    }

    public static void main(String[] args) {
//        Thread[] threads = new Thread[THREADS_CONUT];
//        for (int i = 0; i < THREADS_CONUT; i++) {
//            threads[i] = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 1000; i++) {
//                        increase();
//                    }
//                }
//            });
//            threads[i].start();
//        }
//
//        while (Thread.activeCount() > 1) {
//            Thread.yield();
//        }
        for (int i = 0; i < 1000; i++) {
            increase();
        }

//        System.out.println(count);
    }


}
