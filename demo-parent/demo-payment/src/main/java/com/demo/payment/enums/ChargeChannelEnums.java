package com.demo.payment.enums;
/**
 * @author zzj
 * @create 2019-04-26 12:13
 **/
public enum  ChargeChannelEnums {
    ALIPAY("alipay", "支付宝 App 支付"),
    ALIPAY_WAP("alipay_wap", "支付宝手机网站支付"),
    ALIPAY_QR("alipay_qr", "支付宝扫码支付"),
    ALIPAY_SCAN("alipay_scan", "支付宝条码支付"),
    ALIPAY_LITE("alipay_lite", "支付宝小程序支付"),
    ALIPAY_PC_DIRECT("alipay_pc_direct", "支付宝电脑网站支付"),
    WX("wx", "微信 App 支付"),
    WX_PUB("wx_pub", "微信 JSAPI 支付"),
    WX_PUB_QR("wx_pub_qr", "微信 Native 支付"),
    WX_PUB_SCAN("wx_pub_scan", "微信付款码支付"),
    WX_WAP("wx_wap", "微信 H5 支付"),
    WX_LITE("wx_lite", "微信小程序支付");

    private String code;
    private String msg;


    public static String getMsg(String code) {
        ChargeChannelEnums[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ChargeChannelEnums fr = var1[var3];
            if (fr.getCode().equals(code)) {
                return fr.getMsg();
            }
        }
        return null;
    }

    private ChargeChannelEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
