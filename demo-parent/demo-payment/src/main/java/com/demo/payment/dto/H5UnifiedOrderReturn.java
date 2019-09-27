package com.demo.payment.dto;

import com.demo.payment.wx.WxPayResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zzj
 * @create 2019-04-24 18:56
 *  https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 *
 **/
@Data
public class H5UnifiedOrderReturn {
//    /**
//     * 返回状态码
//     */
//    @ApiModelProperty(value = "返回状态码")
//    private String returnCode;
//    /**
//     * 返回信息
//     */
//    @ApiModelProperty(value = "返回信息")
//    private String returnMsg;
//
//    /**
//     * 以下字段在return_code为SUCCESS的时候有返回
//     */
//    @ApiModelProperty(value = "公众账号ID")
//    private String appId;
//
//    @ApiModelProperty(value = "商户号")
//    private String mchId;
//
//    @ApiModelProperty(value = "设备号")
//    private String deviceInfo;
//
//    @ApiModelProperty(value = "随机字符串")
//    private String nonceStr;
//
//    @ApiModelProperty(value = "签名")
//    private String sign;
//
//    @ApiModelProperty(value = "业务结果")
//    private String resultCode;
//    /**
//     * 以下字段在return_code为FAIL的时候有返回
//     */
//    @ApiModelProperty(value = "错误代码")
//    private  String errCode;
//
//    @ApiModelProperty(value = "错误代码描述")
//    private  String errCodeDes;
//
//    /**
//     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
//     */
//    @ApiModelProperty(value="交易类型")
//    private String tradeType;
//
//    @ApiModelProperty(value="预支付交易会话标识")
//    private String prepayId;
//
//    /**
//     * code_url 字段在 trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
//     * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
//     */
//    @ApiModelProperty(value="二维码链接rade_type=NATIVE时有返回")
//    private String codeUrl;

    /**
     * h5支付时返回 mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
     */
    @ApiModelProperty(value="支付跳转链接")
    private String mwebUrl;

    private WxPayResponse wxPayResponse;

}
