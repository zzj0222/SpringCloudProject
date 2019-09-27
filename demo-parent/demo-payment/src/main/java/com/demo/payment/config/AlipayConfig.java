package com.demo.payment.config;

/**
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {

    /**
     * 接口请求域名
     */
    public  static  String API_DOMAIN="http://home.yixue2015.com";


    /**
     * 支付宝商户appId
     */
    public static String APPID="2016082600314927";

    /**
     * 应用私钥  pkcs8格式的
     */
    public static String RSA_PRIVATE_KEY="";

    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY="";

    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String	NOTIFY_URL			=API_DOMAIN+"/api/aliPay/notify";

    /**
     *  手机端页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String	WAP_RETURN_URL			= API_DOMAIN+"/api/aliPay/wapReturnUrl";
    public static String	PC_RETURN_URL			= API_DOMAIN+"/api/aliPay/pcReturnUrl";

    /**
     * 请求网关地址
     *
     */
    public static String	URL					= "https://openapi.alipay.com/gateway.do";

    /**
     * 编码
     */
    public static String	CHARSET				= "UTF-8";

    /**
     * 返回格式
     */
    public static String	FORMAT				= "json";

    public static String	log_path			= "/log";
    // RSA2
    public static String	SIGNTYPE			= "RSA2";
}
