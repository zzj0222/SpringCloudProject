package com.demo.core.enums;

/**
 * @author zzj
 * @create 2018-11-08 9:40
 **/
public enum ResultCode {
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
