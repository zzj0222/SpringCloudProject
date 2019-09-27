package com.demo.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.payment.basic.Result;
import com.demo.payment.config.WxPayConfig;
import com.demo.payment.dto.OrderPayDTO;
import com.demo.payment.dto.UpdateOrderDTO;
import com.demo.payment.dto.WxAuthCallBackDTO;
import com.demo.payment.enums.ResultCode;
import com.demo.payment.model.User;
import com.demo.payment.service.OrderService;
import com.demo.payment.service.UserService;
import com.demo.payment.service.WxPayService;
import com.demo.payment.utils.CookieUtils;
import com.demo.payment.utils.HttpClientUtil;
import com.demo.payment.utils.StringHelper;
import com.demo.payment.utils.WeiXinUtils;
import com.demo.payment.wx.H5PayReturnDTO;
import com.demo.payment.wx.WxPayResponse;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/8 10:00
 */
@Service
public class WxPayServiceImpl implements WxPayService {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    //  公众平台id(服务号)
    public  static  final  String mpAppId="";
    // 公众平台密钥(服务号)
    public static String mpAppSecret="";

    @Override
    public Result bindWeiXinByUserId(Long userId, String code, String scope,HttpServletRequest request,HttpServletResponse response) {
        Result result = null;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +mpAppId+ "&secret="
                +mpAppSecret + "&code=" + code + "&grant_type=authorization_code";
        String strJson = HttpClientUtil.doGet(url);
        System.out.println(strJson);
        if (scope.equals("snsapi_base")) {
            // 静默授权绑定
            try {
                WxAuthCallBackDTO wxAuthCallBackDTO = JSONObject.parseObject(strJson, WxAuthCallBackDTO.class);
                String openId = wxAuthCallBackDTO.getOpenid();
                System.out.println("userId=" + userId + " openId=" + openId);
                CookieUtils.setCookie(request, response, "openId", openId);
                // 绑定微信openId 到用户 存入数据库 -根据自己的业务需求来定
                int count= userService.updateWxAuthOpenIdByUserId(userId,openId);
                result = Result.success(count);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return result;
    }

    @Override
    public Result createPreServiceOrder(String orderNo, HttpServletRequest request) {
        // 订单
        OrderPayDTO orderPayDTO  = orderService.findOrderPayInfoByOrderNo(orderNo);
        User user=userService.findByUserId(orderPayDTO.getUserId());
        String openId =CookieUtils.getCookieValue(request, "openId");
        openId =user.getWxAuthOpenId();
        String userIp = StringHelper.getRemortIP(request);
        if (userIp.split(",").length > 0) {
            userIp = userIp.split(",")[0];
        }
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        int total_fee = StringHelper.Yuan2Fen(orderPayDTO.getOrderAmount().doubleValue());
        String body = "商品名称";
        // 预付单信息Id
        String prepayId = WeiXinUtils.getPayOrderByWeiXin(openId, total_fee + "", userIp, WxPayConfig.notifyUrl,
                body, orderPayDTO.getOrderNo());
        System.out.println("prepay_id=" + prepayId);
        String paySign = WeiXinUtils.getPaySignByWeiXin(timeStamp, WeiXinUtils.nonceStr, "prepay_id=" + prepayId);
        System.out.println("paySign=" + paySign);
        // 支付页面-前端页面
        String nopayReturnUrl="/shouyintai.html?ordid="+orderPayDTO.getOrderId();
        // 支付结果页面 前端页面
        String returnUrl="/zhifuresult.html?userId="+orderPayDTO.getUserId()+"&orderId="+orderPayDTO.getOrderId();
        WxPayResponse payResponse=new WxPayResponse();
        payResponse.setAppId(WxPayConfig.mpAppId);
        payResponse.setNonceStr(WeiXinUtils.nonceStr);
        payResponse.setPaySign(paySign);
        payResponse.setPrepayId(prepayId);
        payResponse.setReturnUrl(returnUrl);
        payResponse.setSignType("MD5");
        payResponse.setTimeStamp(timeStamp);
        payResponse.setNopayReturnUrl(nopayReturnUrl);
        Result result=Result.success(payResponse);
        return result;
    }

    @Override
    public Result notify(String notifyData, ModelMap map) {
        Result result=null;
        try {
            Map<String,String> wxmap= WXPayUtil.xmlToMap(notifyData);
            if(wxmap.get("result_code").equals("SUCCESS")){
                // 订单号
                String out_trade_no=wxmap.get("out_trade_no");
                // 交易号
                String trade_no=wxmap.get("transaction_id");
                    //订单
                    OrderPayDTO orderPayDTO = new OrderPayDTO();
                orderPayDTO = orderService.findOrderPayInfoByOrderNo(out_trade_no);
                    // //支付成功:修改库存修改订单状态
                    if (orderPayDTO.getOrderStatus() == 0) {
                        Result result2 = null;
                        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
                        updateOrderDTO.setTradeNo(trade_no);
                        updateOrderDTO.setOrderStatus(1);
                        updateOrderDTO.setPayMethod(2);// 微信支付
                        updateOrderDTO.setOrderNo(out_trade_no);
                        String json = JSONObject.toJSONString(updateOrderDTO);
                        String resultString2 = HttpClientUtil.doPostJson(WxPayConfig.API_ORDER_UPDATE_URL, json);
                        result2 = JSONObject.parseObject(resultString2, Result.class);
                        map.addAttribute("userId", orderPayDTO.getUserId());
                        map.addAttribute("orderId", orderPayDTO.getOrderId());
                        if(result2.getCode().equals(ResultCode.SUCCESS)){
                            result=Result.success("success");
                        }
                    } else {
                        map.addAttribute("userId", orderPayDTO.getUserId());
                        map.addAttribute("orderId", orderPayDTO.getOrderId());
                    }
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建预支付订单-H5微信支付
     * @param orderNo
     * @param request
     * @return
     */
    @Override
    public Result createH5PreOrder(String orderNo, HttpServletRequest request) {
        System.out.println("H5微信支付开始============");
        StringBuffer url = request.getRequestURL();
        url.delete(url.length() - request.getRequestURI().length(),  url.length());
        System.out.println(url.toString());
        try {
                // 订单
                OrderPayDTO orderPayDTO  = orderService.findOrderPayInfoByOrderNo(orderNo);
                User user=userService.findByUserId(orderPayDTO.getUserId());
                String openId =CookieUtils.getCookieValue(request, "openId");
                openId =user.getWxAuthOpenId();
                String userIp = StringHelper.getRemortIP(request);
                if (userIp.split(",").length > 0) {
                    userIp = userIp.split(",")[0];
                }
                String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
                int total_fee = StringHelper.Yuan2Fen(orderPayDTO.getOrderAmount().doubleValue());
                String body = "商品名称";
                H5PayReturnDTO h5PayReturnDTO = WeiXinUtils.getH5PayOrderByWeiXin(openId, total_fee + "", userIp, WxPayConfig.notifyUrl,
                        body, orderPayDTO.getOrderNo());
                String prepayId=h5PayReturnDTO.getPrepay_id();
                System.out.println("prepay_id=" +prepayId);

                String paySign = WeiXinUtils.getPaySignByWeiXin(timeStamp, WeiXinUtils.nonceStr, "prepay_id=" + prepayId);
                System.out.println("paySign=" + paySign);
                String nopayReturnUrl=url.toString()+ "/shouyintai.html?ordid="+orderPayDTO.getOrderId()+"&showState=1";
                String returnUrl=url.toString()+ "/zhifuresult.html?userId="+orderPayDTO.getUserId()+"&packageOrderId="+orderPayDTO.getOrderId();
                WxPayResponse payResponse=new WxPayResponse();
                payResponse.setAppId(WxPayConfig.mpAppId);
                payResponse.setNonceStr(WeiXinUtils.nonceStr);
                payResponse.setPaySign(paySign);
                payResponse.setPrepayId(prepayId);
                payResponse.setReturnUrl(returnUrl);
                payResponse.setSignType("MD5");
                payResponse.setTimeStamp(timeStamp);
                payResponse.setNopayReturnUrl(nopayReturnUrl);
                h5PayReturnDTO.setPayResponse(payResponse);
                h5PayReturnDTO.setRedirect_url(URLEncoder.encode(nopayReturnUrl));
                return  Result.success(orderPayDTO);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return  Result.fail("创建预支付订单失败");
    }
}
