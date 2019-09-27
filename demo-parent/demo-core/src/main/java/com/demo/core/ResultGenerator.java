package com.demo.core;

import com.demo.core.enums.ResultCode;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    public static Result genSuccessResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(message);
        return result;
    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static Result genSuccessResult(Object data,String message) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(message);
        result.setSuccess(false);
        return result;
    }

    public static Result genFailResult(String errorCode,String message,Object data) {
        Result result = new Result();
        result.setCode(errorCode);
        result.setMsg(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
}
