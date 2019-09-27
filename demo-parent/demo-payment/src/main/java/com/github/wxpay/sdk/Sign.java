package com.github.wxpay.sdk;
import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Formatter;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;  
import java.util.Set;  
import java.util.SortedMap;  
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.demo.payment.config.WxPayConfig;
import com.demo.payment.utils.HttpClientUtil;
import org.springframework.cache.annotation.Cacheable;


  

  
public class Sign {  
      
    @Cacheable(value="baseCache")  
    public static String getToken() {  
    	Map<String,String> params= new HashMap<String,String>();
    	params.put("grant_type", "client_credential");
    	params.put("appid", WxPayConfig.mpAppId);
    	params.put("secret", WxPayConfig.mpAppSecret);
        String s = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/token",params);
        HashMap<String, Object> json = JSONObject.parseObject(s, HashMap.class);
        String token = json.get("access_token").toString();
        return token;  
    }  
      
    @Cacheable(value="baseCache")  
    public static String getTicket() {  
        String token = Sign.getToken();  
        Map<String,String> params= new HashMap<String,String>();
        params.put("access_token", token);
    	params.put("type", "jsapi");
        String s1 =  HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket",params);  
        HashMap<String, Object> json1 = JSONObject.parseObject(s1, HashMap.class);
        return json1.get("ticket").toString();  
    }  
  
    public static String getNonceStr() {  
        return create_timestamp();  
    }  
  
    //chatSet  SHA-1 or MD5  
    public static Map<String, String> sign(String url, String chatSet) {  
        Map<String, String> ret = new HashMap<String, String>();  
        String nonce_str = create_nonce_str();  
        String timestamp = create_timestamp();  
        String string1;  
        String signature = "";  
        String jsapi_ticket = getTicket();  
  
        // 注意这里参数名必须全部小写，且必须有序  
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str  
                + "×tamp=" + timestamp + "&url=" + url;  
        // System.out.println(string1);  
  
        try {  
            MessageDigest crypt = MessageDigest.getInstance(chatSet);  
            crypt.reset();  
            crypt.update(string1.getBytes("UTF-8"));  
            signature = byteToHex(crypt.digest());  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        ret.put("url", url);  
        ret.put("jsapi_ticket", jsapi_ticket);  
        ret.put("nonceStr", nonce_str);  
        ret.put("timestamp", timestamp);  
        ret.put("signature", signature);  
        for (Map.Entry entry : ret.entrySet()) {  
            System.out.println(entry.getKey() + ", " + entry.getValue());  
        }  
        return ret;  
    }  
  
    private static String byteToHex(final byte[] hash) {  
        Formatter formatter = new Formatter();  
        for (byte b : hash) {  
            formatter.format("%02x", b);  
        }  
        String result = formatter.toString();  
        formatter.close();  
        return result;  
    }  
  
    private static String create_nonce_str() {  
        return UUID.randomUUID().toString();  
    }  
  
    private static String create_timestamp() {  
        return Long.toString(System.currentTimeMillis() / 1000);  
    }  
      
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Map.Entry) it.next();  
            String k = (String) entry.getKey();  
            Object v = entry.getValue();  
            if (null != v && !"".equals(v) && !"sign".equals(k)  
                    && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + WxPayConfig.mchKey);  
        String sign =WXPayUtil.MD5(sb.toString());  
        return sign;  
    }  
      
    public static String paySign(String characterEncoding, SortedMap<Object, Object> parameters) {  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Map.Entry) it.next();  
            String k = (String) entry.getKey();  
            Object v = entry.getValue();  
            if (null != v && !"".equals(v) && !"sign".equals(k)  
                    && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        //sb.replace(sb.length()-1, sb.length(), "?");  
        sb.append("key=" + WxPayConfig.mchKey);
        //sb.append("params=value");  
        System.out.println(sb);  
        String sign =WXPayUtil.MD5(sb.toString());  
        return sign;  
    }  
}  