package com.demo.payment.api;

import com.demo.payment.basic.BaseController;
import com.demo.payment.basic.Result;
import com.demo.payment.config.WxPayConfig;
import com.demo.payment.service.WxShareService;
import com.demo.payment.utils.SHA1;
import com.demo.payment.utils.StrKit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * 微信分享接口
 * 参考 ：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
 */
@RestController
@RequestMapping("/api/wxShare")
public class WxShareApi extends BaseController {


    @Resource
    private WxShareService wxShareService;

    /**
     * 获取openId-前端调用
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/wxOpenId",method = RequestMethod.GET)
    public Result getOpenId(@RequestParam String code) throws UnsupportedEncodingException {
        String wxOpenId = wxShareService.getWxOpenId(code);
        return success(wxOpenId);
    }
    /**
     * Js分享接口签名 -前端调用
     * @param url
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wxConfig",method = RequestMethod.GET)
    public Result wxConfig(@RequestParam String url) throws Exception {
        String jsapi_ticket = wxShareService.getJsApiTicket();
        String noncestr = StrKit.getStringRandom(8);
        String timestamp = System.currentTimeMillis() / 1000 + "";
        HashMap<String, Object> map = new HashMap<>();
        map.put("jsapi_ticket", jsapi_ticket);
        map.put("noncestr", noncestr);
        map.put("timestamp", timestamp);
        map.put("url", URLDecoder.decode(url, "utf-8"));
        String stringLink = StrKit.createLinkString(map);
        String signature = SHA1.encode(stringLink);
        map.put("signature", signature);
        map.put("appId", WxPayConfig.mpAppId);
        return success(map);
    }

}
