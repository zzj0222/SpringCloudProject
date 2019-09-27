package com.demo.payment.model;

import lombok.Data;


@Data
public class User {

    private Long userId;

    /**
     * 微信公众号openId
     */
    private  String wxAuthOpenId;



}
