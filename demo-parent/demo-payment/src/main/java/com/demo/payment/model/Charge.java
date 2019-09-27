package com.demo.payment.model;

import lombok.Data;

/**
 * 支付对象
 * @author zzj
 * @create 2019-04-26 9:25
 **/
@Data
public class Charge  {

    /**
     * 支付渠道
     */
    private String channel;

    /**
     * 订单号
     */
    private  String orderNo;






}
