package com.demo.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付订单信息
 * @author zzj
 * @create 2019-04-08 11:15
 **/
@Data
public class OrderPayDTO {
    private  Long userId;

    private BigDecimal orderAmount;

    private  String orderNo;

    private String orderId;

    /**
     *  订单状态 0-未支付
     */
    private  Integer orderStatus;


}
