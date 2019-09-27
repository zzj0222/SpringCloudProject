package com.demo.utils.common;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzj
 * @create 2019-03-08 9:34
 **/
public class LiftOff implements  Runnable {
    protected  int countDown=10;
    private static   int taskCount=0;
    private  final   int id=taskCount++;
    public  LiftOff(){}
    public  LiftOff(int countDown){
        this.countDown=countDown;
    }
    public  String status(){
        return  "#"+id+"("+(countDown>0?countDown:"Liftoff!")+").";
    }

    @Override
    public void run() {
        while (countDown-->0){
            System.out.println(status());
            Thread.yield();
        }
    }

    public static void main(String[] args) {
//        LiftOff launch=new LiftOff();
//        launch.run();

//        Thread t=new Thread(new LiftOff());
//        t.start();
//        System.out.println("Waiting for Liftoff");

//        for(int i=0;i<3;i++){
//            new Thread(new LiftOff()).start();
//        }
//        System.out.println("Waiting for Liftoff");

//        ExecutorService exec= Executors.newCachedThreadPool();
    }

}
