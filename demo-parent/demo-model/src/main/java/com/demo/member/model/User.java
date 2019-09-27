package com.demo.member.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class User {
    /**
     * 自动编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码加盐后的值
     */
    private String password;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 加盐
     */
    private String salt;

    /**
     * 国家编码
     */
    private  String mobileArea;


}