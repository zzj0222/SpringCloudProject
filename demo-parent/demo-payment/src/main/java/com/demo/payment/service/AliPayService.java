package com.demo.payment.service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付宝支付
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/10 13:26
 */
public interface AliPayService {

    /**
     * 跳转支付页面
     */
    void goPayPage(HttpServletResponse response,String outTradeNo,String subject,String totalAmount,String body,String productCode,String returnUrl,String notifyUrl) throws AlipayApiException, IOException;

    /**
     * 校验签名
     * @param request
     * @return
     */
    boolean rsaCheckV1(HttpServletRequest request);
}
