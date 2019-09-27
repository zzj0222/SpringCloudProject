package com.demo.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.payment.config.WxPayConfig;
import com.demo.payment.exception.BusinessException;
import com.demo.payment.service.WxShareService;
import com.demo.payment.utils.HttpClientUtil;
import com.demo.payment.wx.WxpubOAuth;

import java.io.UnsupportedEncodingException;

/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/8 15:52
 */
public class WxShareServiceImpl implements WxShareService{

   // 缓存key
    private final String WX_PUB_JSTICKET = "LAIAISZ_WX_PUB_JSTICKET";

    @Override
    public String getWxOpenId(String code) throws UnsupportedEncodingException {
        return WxpubOAuth.getOpenId(WxPayConfig.mpAppId, WxPayConfig.mpAppSecret, code);
    }

    @Override
    public String getJsApiTicket() throws Exception {
        // 如果需要 存redis缓存
//        String redisJsTicket = redisUtils.get(WX_PUB_JSTICKET);
//        if(null!=redisJsTicket){
//            return redisJsTicket;
//        }
        String accessToken = getAccessToken();
        StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/cgi-bin/ticket/getticket?");
        requestUrl.append("access_token=").append(accessToken).append("&type=jsapi");
        String result = HttpClientUtil.doGet(requestUrl.toString());
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object ticket = jsonObject.get("ticket");
        if(ticket!=null){
//            redisUtils.set(WX_PUB_JSTICKET,ticket.toString(),7000);
            return  ticket.toString();
        }else{

            throw new BusinessException("WX0002","获取jsTicket异常");
        }
    }

    @Override
    public String getAccessToken() throws Exception {
        return WxpubOAuth.getAccessToken(WxPayConfig.mpAppId,WxPayConfig.mpAppSecret);
    }
}
