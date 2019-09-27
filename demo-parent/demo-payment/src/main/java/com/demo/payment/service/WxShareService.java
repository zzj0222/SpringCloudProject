package com.demo.payment.service;


import java.io.UnsupportedEncodingException;

/**
 * 微信分享
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/8 15:51
 */
public interface WxShareService {



    /**
     * 获取openID
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    String getWxOpenId(String code) throws UnsupportedEncodingException;

    /**
     * 获取JsApiTicket
     * @return
     * @throws Exception
     */
    String getJsApiTicket() throws Exception;

    /**
     * 获取AccessToken
     * @return
     * @throws Exception
     */
    String getAccessToken() throws Exception;
}
