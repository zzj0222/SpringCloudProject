package com.demo.payment.config;
public class WxPayConfig {


    /**
     * 公众平台id(服务号)
     */
    public static String mpAppId="";

    /**
     * 公众平台密钥(服务号)
     */
    public static String mpAppSecret="";

    /**
     * 商户号
     */
    public static String mchId="";

    /**
     * 商户密钥
     */
    public static String mchKey="";

    /**
     * 微信支付异步通知地址
     */
    public static String notifyUrl="http://www.huaszh.com/api/wxPay/notify";

    /**
     * 授权回调地址
     */
    public static String REDIRECT_URI="http://www.test.com/api/wxPay/callback";


    /**
     * 更新订单接口url
     */
    public static String API_ORDER_UPDATE_URL="http://www.test/api/order/update";


}
