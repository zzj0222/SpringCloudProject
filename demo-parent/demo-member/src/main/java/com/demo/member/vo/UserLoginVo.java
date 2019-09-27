package com.demo.member.vo;


import lombok.Data;

/**
 * @author zzj
 * @create 2019-03-15 10:37
 * 手机验证码登录
 **/
@Data
public class UserLoginVo {
    /**
     * 国家编码
     */
    private String mobileArea;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 登录密码
     */
    private String userPass;

    /**
     * 手机验证码
     */
    private String verifyCode;

}
