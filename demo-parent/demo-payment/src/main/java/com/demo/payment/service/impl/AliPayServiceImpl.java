package com.demo.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.demo.payment.config.AlipayConfig;
import com.demo.payment.service.AliPayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 支付宝支付
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/10 13:27
 */
@Service
public class AliPayServiceImpl implements AliPayService {
    @Override
    public void goPayPage(HttpServletResponse response,String outTradeNo,String subject,String totalAmount,String body,String productCode,String returnUrl,String notifyUrl) throws AlipayApiException, IOException {
        //实例化客户端 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGNTYPE);
        // 订单模型
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        // body可空
        model.setBody(body);
        if(productCode.equals("QUICK_WAP_PAY")) {
            // 如果是手机网站支付
            model.setTimeExpire("15m");
        }
        model.setProductCode(productCode);
        AlipayTradePagePayRequest pagePayRequest =new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(returnUrl);
        pagePayRequest.setNotifyUrl(notifyUrl);
        pagePayRequest.setBizModel(model);
        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        String form = alipayClient.pageExecute(pagePayRequest).getBody();
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 校验签名
     * @param request
     * @return
     */
    @Override
    public boolean rsaCheckV1(HttpServletRequest request){
        // https://docs.open.alipay.com/54/106370
        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
                    AlipayConfig.ALIPAY_PUBLIC_KEY,
                    AlipayConfig.CHARSET,
                    AlipayConfig.SIGNTYPE);

            return verifyResult;
        } catch (AlipayApiException e) {
//            log.debug("verify sigin error, exception is:{}", e);
            return false;
        }
    }
}
