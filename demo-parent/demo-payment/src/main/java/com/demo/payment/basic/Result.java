package com.demo.payment.basic;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;
import com.demo.payment.enums.ResultCode;
import lombok.Data;
/**
 * 统一API响应结果封装
 * @author zzj
 * @create 2018-11-06 16:18
 **/
@Data
public class Result implements Serializable {
    private String code;
    private String msg;
    private Object data;
    public Result() {

    }
    public Result(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg =ResultCode.SUCCESS.getMessage();
        this.data = data;
    }
    public Result(String msg) {
        this.code = ResultCode.SUCCESS.getCode();
        this.data= JSON.parse("{}");
        this.msg =msg;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }
    public static Result sucess(String msg) {
        return new Result(msg);
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result fail(String message) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setData(JSON.parse("{}"));
        result.setMsg(message);
        return result;
    }
    public static Result build(String code, String msg,Object data) {
        return new Result(code, msg, data);
    }

    public static Result build(String code, String msg) {
        return new Result(code, msg,JSON.parse("{}"));
    }



}

