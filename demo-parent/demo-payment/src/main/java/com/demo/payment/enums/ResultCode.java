package com.demo.payment.enums;

/**
 * @author zzj
 * @create 2018-11-08 9:40
 **/
public enum ResultCode {
     // 一切正常
     OK("200","OK"),
    //	一般由缺失参数，参数格式不正确等引起。
    BAD_REQUEST("400", "BAD_REQUEST"),
    // 没有提供正确的 API Key。
    UNAUTHORIZED("401", "UNAUTHORIZED"),
    // 参数格式正确但是请求失败，一般由业务错误引起。
    REQUEST_FAILED("402","REQUEST_FAILED"),
    FORBIDDEN("403","FORBIDDEN"),
    // 资源不存在
    NOT_FOUND("404","NOT_FOUND"),
    SERVER_ERRORS_500("500","服务器出错"),
    SERVER_ERRORS_502("502","服务器出错"),
    SERVER_ERRORS_503("503","服务器出错"),
    SERVER_ERRORS_504("504","服务器出错"),
    SUCCESS("0000", "SUCCESS"),
    NOT_AUTH("4001", "没有权限"),
    SING_FAIL("4002", "参数签名错误"),
    TOKEN_EXPIRE("4003", "TOKEN已过期"),
    TOKEN_IVALID("4004", "非法TOKEN或者TOKEN为空"),
    VALIDATE_FAIL("4009", "参数校验错误"),
    ERROR("5002", "系统内部错误"),
    BUSY("5003", "系统繁忙");

    private String code;
    private String message;

    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMsg(String code) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultCode fr = var1[var3];
            if (fr.getCode().equals(code)) {
                return fr.getMessage();
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
