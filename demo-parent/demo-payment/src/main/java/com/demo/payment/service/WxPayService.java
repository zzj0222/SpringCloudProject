package com.demo.payment.service;

import com.demo.payment.basic.Result;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/8 9:48
 */
public interface WxPayService {


    /**
     * 通过用户Id绑定微信
     * @param userId
     * @param code
     * @param scope
     * @param request
     * @param response
     * @return
     */
    Result bindWeiXinByUserId(Long userId, String code, String scope, HttpServletRequest request, HttpServletResponse response);

    /**
     * 创建预支付订单-公众号支付
     * @param orderNo
     * @param request
     * @return
     */
    Result createPreServiceOrder(String orderNo,HttpServletRequest request);

    /**
     * 微信支付异步通知
     * @param notifyData
     * @param map
     * @return
     */
    Result notify(String notifyData,ModelMap map);
    /**
     * 创建预支付订单-h5支付
     * @param orderNo
     * @param request
     * @return
     */
    Result createH5PreOrder(String orderNo,HttpServletRequest request);
}
