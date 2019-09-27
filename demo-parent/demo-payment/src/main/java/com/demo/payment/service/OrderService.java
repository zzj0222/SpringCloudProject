package com.demo.payment.service;


import com.demo.payment.dto.OrderPayDTO;

public interface OrderService {
    OrderPayDTO findOrderPayInfoByOrderNo(String orderNo);
}
