package com.demo.payment.dto;

import lombok.Data;

@Data
public class UpdateOrderDTO {

    /**
     * 交易号
     */
    private  String tradeNo;

    /**
     * 订单状态 0-未支付 1-已支付
     */
    private  Integer orderStatus;

    /**
     * 支付方式
     */
    private  Integer payMethod;

    private String orderNo;
}
