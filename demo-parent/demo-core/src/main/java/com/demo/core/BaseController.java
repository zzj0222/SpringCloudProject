package com.demo.core;

/**
 * @author zzj
 * @create 2018-11-08 9:51
 **/
public abstract class BaseController {

    public BaseController() {
    }

    protected Result success(Object objs) {
        return Result.success(objs);
    }

    protected Result success(Object... objs) {
        return Result.success(objs);
    }

    protected Result success() {
        return Result.success();
    }

    protected Result success(String msg) {
        return Result.sucess(msg);
    }


    protected Result fail(String message) {
        return Result.fail(message);
    }

    protected Result build(String code,String msg,Object objs) {
        return Result.build(code,msg,objs);
    }

    protected Result build(String code,String msg) {
        return Result.build(code,msg);
    }

}
