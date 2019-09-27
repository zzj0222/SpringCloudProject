package com.demo.payment.dto;


import lombok.Data;

/**
 * 静默授权返回的参数
 * @author zzj
 * @create 2019-04-08 10:47
 **/
@Data
public class WxAuthCallBackDTO {

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
