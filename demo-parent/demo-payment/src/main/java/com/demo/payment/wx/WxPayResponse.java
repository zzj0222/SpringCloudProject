package com.demo.payment.wx;

import lombok.Data;

@Data
public class WxPayResponse {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String signType = "MD5";
    private String returnUrl;
    private String nopayReturnUrl;
    private String paySign;
}
