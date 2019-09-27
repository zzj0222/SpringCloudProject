package com.demo.payment.utils;

import com.demo.payment.config.WxPayConfig;
import com.demo.payment.wx.H5PayReturnDTO;
import com.github.wxpay.sdk.Sign;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeiXinUtils {
    private static final Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

    /** 微信开发平台应用appid */
    public static final String APP_ID = WxPayConfig.mpAppId;

    /**
     *
     */
    //public static final String noncestr="***";
    /** 微信开发平台应用appsecret */
    public static final String APP_SECRET = WxPayConfig.mpAppSecret;
    /**
     * 随机字符串
     */
    public static final String nonceStr= WXPayUtil.generateNonceStr();

    /** 商户号 */
    public static final String MCH_ID = WxPayConfig.mchId;

    // 应用对应的密钥
    public static final String APP_KEY = WxPayConfig.mchKey;

    // 微信公众号api
    public static final String REDIRECT_URI = WxPayConfig.REDIRECT_URI; // 授权回调
    public static final String PAY_URI = WxPayConfig.notifyUrl;// 支付回调
    public static String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 预支付订单
     * @param openid
     * @param total_fee
     * @param spbill_create_ip
     * @param notify_url
     * @param body
     * @param out_trade_no
     * @return
     */
    public static String getPayOrderByWeiXin(String openid, String total_fee, String spbill_create_ip, String notify_url, String body, String out_trade_no) {
        String result="";
//        total_fee=1+"";
        try {
            Map<String, String> data=new HashMap<String,String>();
            data.put("appid", APP_ID);
            data.put("mch_id", MCH_ID);
            data.put("device_info", "WEB");
            data.put("nonce_str", nonceStr);
            data.put("body", body);
            data.put("trade_type", "JSAPI");
            data.put("sign_type", "MD5");
            data.put("fee_type", "CNY");
            data.put("out_trade_no", out_trade_no);
            data.put("total_fee", total_fee);
            System.out.println("total_fee"+ total_fee);
            data.put("notify_url", notify_url);
            data.put("openid", openid);
            data.put("spbill_create_ip", spbill_create_ip);
            String key=WxPayConfig.mchKey;

            String UTF8 = "UTF-8";
            String appid=APP_ID;
            String mch_id=MCH_ID;
            String device_info="WEB";
            String nonce_str=nonceStr;
            String sign=WXPayUtil.generateSignature(data,key,SignType.MD5);
            String trade_type="JSAPI";
            String sign_type="MD5";
            String fee_type="CNY";
            String reqBody = "<xml><body>"+body+"</body><trade_type>"+trade_type+"</trade_type><mch_id>"+mch_id+"</mch_id><sign_type>"+sign_type+"</sign_type><nonce_str>"+nonce_str+"</nonce_str><detail /><fee_type>"+fee_type+"</fee_type><device_info>"+device_info+"</device_info><out_trade_no>"+out_trade_no+"</out_trade_no><total_fee>"+total_fee+"</total_fee><appid>"+appid+"</appid><notify_url>"+notify_url+"</notify_url><openid>"+openid+"</openid><sign>"+sign+"</sign><spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip></xml>";
            System.out.println(reqBody);
            result = PostRequest.httpsRequest(unifiedOrder, "POST", reqBody);
//			String requestXML;
//		requestXML = WXPayUtil.generateSignature(parameters,WxPayConfig.mchKey);
//		System.out.println(requestXML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("prepay_id="+result);
        return getPrepayId(result);
    }

    /**
     * xml 取出prepayId
     * @param xml
     * @return
     */
    private static String getPrepayId(String xml) {
        String prepay_id = "";
        try {
            Map<String,String> map = WXPayUtil.xmlToMap(xml);
            prepay_id = map.get("prepay_id").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prepay_id;
    }

    /*
     * paySign
     */
    public static String getPaySignByWeiXin(String timeStamp, String nonceStr, String _package) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appId", APP_ID);
        System.out.println("appId="+APP_ID);
        parameters.put("timeStamp", timeStamp);
        System.out.println("timeStamp="+timeStamp);
        parameters.put("nonceStr", nonceStr);
        System.out.println("nonceStr="+nonceStr);
        parameters.put("package", _package);
        System.out.println("package="+_package);
        parameters.put("signType", "MD5");
        System.out.println("signType=MD5");
        //parameters.put("key", APP_KEY);
        String sign = Sign.paySign("UTF-8", parameters);
        System.out.println("sign="+sign);
        return sign;
    }


    /**
     * 微信h5支付，统一下单
     * @param openid
     * @param total_fee
     * @param spbill_create_ip
     * @param notify_url
     * @param body
     * @param out_trade_no
     * @return
     */
    public static H5PayReturnDTO getH5PayOrderByWeiXin(String openid, String total_fee, String spbill_create_ip, String notify_url, String body, String out_trade_no) {
        String result="";
//        total_fee=1+"";
        try {
            Map<String, String> data=new HashMap<String,String>();
            data.put("appid", APP_ID);
            data.put("mch_id", MCH_ID);
            data.put("device_info", "WEB");
            data.put("nonce_str", nonceStr);
            data.put("body", body);
            data.put("trade_type", "MWEB");
            data.put("sign_type", "MD5");
            data.put("fee_type", "CNY");
            data.put("out_trade_no", out_trade_no);
            data.put("total_fee", total_fee);
            System.out.println("total_fee"+ total_fee);
            data.put("notify_url", notify_url);
            // data.put("openid", openid);
            data.put("spbill_create_ip", spbill_create_ip);
            String key=WxPayConfig.mchKey;

            String UTF8 = "UTF-8";
            String appid=APP_ID;
            String mch_id=MCH_ID;
            String device_info="WEB";
            String nonce_str=nonceStr;
            String sign=WXPayUtil.generateSignature(data,key,SignType.MD5);
            String trade_type="MWEB"; // MWEB 表示h5支付
            String sign_type="MD5";
            String fee_type="CNY";
            //<openid>"+openid+"</openid>
            String reqBody = "<xml><body>"+body+"</body><trade_type>"+trade_type+"</trade_type><mch_id>"+mch_id+"</mch_id><sign_type>"+sign_type+"</sign_type><nonce_str>"+nonce_str+"</nonce_str><detail /><fee_type>"+fee_type+"</fee_type><device_info>"+device_info+"</device_info><out_trade_no>"+out_trade_no+"</out_trade_no><total_fee>"+total_fee+"</total_fee><appid>"+appid+"</appid><notify_url>"+notify_url+"</notify_url><sign>"+sign+"</sign><spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip></xml>";
            System.out.println(reqBody);
            // 微信统一下单
            result = PostRequest.httpsRequest(unifiedOrder, "POST", reqBody);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("h5PayResult="+result);


//        <xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxa6106814c195430d]]></appid><mch_id><![CDATA[1387955702]]></mch_id><device_info><![CDATA[WEB]]></device_info><nonce_str><![CDATA[GIveDYzbPrzyVd90]]></nonce_str><sign><![CDATA[AC90B3A65CAC600749BA23BF42823264]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201803271137441a3e111e3c0288956735]]></prepay_id><trade_type><![CDATA[MWEB]]></trade_type><mweb_url><![CDATA[https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx201803271137441a3e111e3c0288956735&package=573385060]]></mweb_url></xml>
        H5PayReturnDTO h5PayReturnDTO=WeiXinUtils.getH5PayReturnDTO(result);
        System.out.println("mweb_url="+h5PayReturnDTO.getMweb_url());
        return h5PayReturnDTO;
    }

    public  static  H5PayReturnDTO  getH5PayReturnDTO(String xml) {
        H5PayReturnDTO h5PayReturnDTO=new H5PayReturnDTO();
        String return_code= "";
        String return_msg= "";
        String appid="";
        String mch_id="";
        String device_info="";
        String nonce_str="";
        String sign="";
        String result_code="";
        String trade_type="";
        String prepay_id ="";
        String mweb_url="";
        try {
            Map<String,String> map = WXPayUtil.xmlToMap(xml);
            System.out.println(map);
            //<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxa6106814c195430d]]></appid><mch_id><![CDATA[1387955702]]></mch_id><device_info><![CDATA[WEB]]></device_info><nonce_str><![CDATA[GIveDYzbPrzyVd90]]></nonce_str><sign><![CDATA[AC90B3A65CAC600749BA23BF42823264]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201803271137441a3e111e3c0288956735]]></prepay_id><trade_type><![CDATA[MWEB]]></trade_type><mweb_url><![CDATA[https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx201803271137441a3e111e3c0288956735&package=573385060]]></mweb_url></xml>
            return_code = map.get("return_code").toString();
            return_msg = map.get("return_msg").toString();
            appid = map.get("appid").toString();
            mch_id = map.get("mch_id").toString();
            device_info = map.get("device_info").toString();
            nonce_str = map.get("nonce_str").toString();
            sign = map.get("sign").toString();
            result_code = map.get("result_code").toString();
            prepay_id = map.get("prepay_id").toString();
            trade_type = map.get("trade_type").toString();
            mweb_url = map.get("mweb_url").toString();

            h5PayReturnDTO.setReturn_code(return_code);
            h5PayReturnDTO.setReturn_msg(return_msg);
            h5PayReturnDTO.setAppid(appid);
            h5PayReturnDTO.setMch_id(mch_id);
            h5PayReturnDTO.setDevice_info(device_info);
            h5PayReturnDTO.setNonce_str(nonce_str);
            h5PayReturnDTO.setSign(sign);
            h5PayReturnDTO.setResult_code(result_code);
            h5PayReturnDTO.setTrade_type(trade_type);
            h5PayReturnDTO.setPrepay_id(prepay_id);
            h5PayReturnDTO.setMweb_url(mweb_url);
            h5PayReturnDTO.setAppid(appid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return h5PayReturnDTO;
    }

}
