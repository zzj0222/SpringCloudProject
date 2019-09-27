package com.demo.payment.api;

import com.alibaba.fastjson.JSONObject;
import com.demo.payment.basic.BaseController;
import com.demo.payment.basic.Result;
import com.demo.payment.config.WxPayConfig;
import com.demo.payment.enums.ResultCode;
import com.demo.payment.service.WxPayService;
import com.demo.payment.utils.CookieUtils;
import com.demo.payment.utils.StringHelper;
import com.demo.payment.wx.WxPayResponse;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 微信支付api
 * @author zhangzhenjiang
 * @date 2019/4/8 9:08
 *  相关文档地址：https://pay.weixin.qq.com/wiki/doc/api/index.html
 */
@RestController
@RequestMapping("/api/wxPay")
public class WxPayApi extends BaseController{

    @Resource
    private WxPayService wxPayService;



    /**
     * 微信网页授权
     * @param scope
     * @param userId
     * @param orderNo
     * @param request
     * @param response
     * @return
     * 参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
     */
    @RequestMapping(value="/auth",method= RequestMethod.GET)
    @ResponseBody
    public Result goToWxAuthPage(@RequestParam(value = "scope", required = true,defaultValue="snsapi_base") String scope, @RequestParam(value = "userId", required = true,defaultValue="") String userId, @RequestParam(value = "orderNo", required = true,defaultValue="") String orderNo, HttpServletRequest request, HttpServletResponse response){
        //回调地址  snsapi_base 静默授权    snsapi_userinfo; https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ WxPayConfig.mpAppId
                + "&redirect_uri="+ URLEncoder.encode(WxPayConfig.REDIRECT_URI)
                + "&response_type=code"
                + "&scope="+scope
                + "&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(url);
        CookieUtils.setCookie(request, response, "scope", scope);
        CookieUtils.setCookie(request, response, "authUserId", userId);
        CookieUtils.setCookie(request, response, "orderNo", orderNo);
        return Result.build("200", "网页授权连接",url);

    }

    /**
     * 微信授权回调页面-公众号支付
     * @param request
     * @param response
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_4
     */
    @RequestMapping(value="/callback",method=RequestMethod.GET)
    public String wxAuthCallback(HttpServletRequest request,HttpServletResponse response,ModelMap map){
        Result result=null;
        //直接从request获取code 微信访问这个回调地址的时候会把这个code传递过来 微信授权登陆
        String code=request.getParameter("code");
        System.out.println("code=="+ code);
        String userToken=CookieUtils.getCookieValue(request, "token");
        String userId= StringHelper.setDefaultString(CookieUtils.getCookieValue(request, "authUserId"), "0");
        String scope=StringHelper.setDefaultString(CookieUtils.getCookieValue(request, "scope"),"snsapi_base");
        String orderNo=StringHelper.setDefaultString(CookieUtils.getCookieValue(request, "orderNo"),"0");
        System.out.println(userId);
        // 拿到用户的openId ,并绑定
        result=wxPayService.bindWeiXinByUserId(Long.parseLong(userId), code,scope,request,response);
        if(result.getCode().equals(ResultCode.SUCCESS)){
            map.put("orderNo", orderNo);
            //创建微信预支付订单(服务) 公众号支付
            result=wxPayService.createPreServiceOrder(orderNo,request);
            if(result.getCode().equals(ResultCode.SUCCESS) && result.getData()!=null){
                WxPayResponse wxPayResponse=(WxPayResponse)result.getData();
                System.out.println(JSONObject.toJSONString(wxPayResponse));
                map.put("wxPayResponse", wxPayResponse);
            }
        }
        return "wxpay";
    }


    /**
     * 微信支付异步通知
     * @param notifyData
     * @param map
     * @return
     */
    @RequestMapping(value="/notify",method = RequestMethod.POST)
    public String notify(@RequestBody String notifyData,ModelMap map) {
       Result result= wxPayService.notify(notifyData,map);
        //返回给微信处理结果
        if(result!=null && result.getCode().equals(ResultCode.SUCCESS)){
            return "wxpaysuccess";
        }
        return  "";
    }

    /**
     * h5支付创建微信预支付订单
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping(value="/h5preorder",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Result createH5PreOrder(@RequestParam(value = "orderNo", required = true) String orderNo,HttpServletRequest request){
        Result result=null;
        //2. 发起支付
        StringBuffer url = request.getRequestURL();
        url.delete(url.length() - request.getRequestURI().length(),  url.length());
        System.out.println(url.toString());
        result=wxPayService.createH5PreOrder(orderNo,request);
        return result;

    }

//    /**
//     * 公司条目信息
//     */
//    @Override
//    @GetMapping("/getCompanyItemVoList")
//    public Result getCompanyItemVoList() {
//        List<CompanyItemVo> companyItemVoList= companyItemService.getCompanyItemVoList();
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("companyItemVoList", companyItemVoList);
//        Result result= success(resultMap);
//        result.setMsg("公司条目信息");
//        return  result;
//    }

}
