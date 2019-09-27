package com.demo.member.vo;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/3/15 10:11
 */

import lombok.Data;

/**
 * @author zzj
 * @create 2019-03-15 10:11
 * 手机号注册
 **/
@Data
public class UserRegisterVo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 确认密码
     */
    private String confirmUserPass;

    /**
     * 国家编码
     */
    private String mobileArea;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 手机验证码
     */
    private String verifyCode;



}
