package com.demo.payment.wx;

import lombok.Data;

/**
 * 微信统一下单返回结果
 * 参考：https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_20&index=1
 * @author zzj
 *
 */
@Data
public class H5PayReturnDTO {

    private String return_code; // 返回状态码

    private String return_msg;//返回信息

    private String appid;// 公众账号ID

    private String mch_id;//商户号

    private String device_info;//设备号

    private String nonce_str;//随机字符串

    private String sign;//签名

    private String result_code;//业务结果


    private String trade_type; //交易类型

    private String prepay_id; // 预支付交易会话标识

    private String mweb_url; //支付跳转链接

    private String redirect_url;//支付结果回调页面


    private WxPayResponse payResponse;

}


