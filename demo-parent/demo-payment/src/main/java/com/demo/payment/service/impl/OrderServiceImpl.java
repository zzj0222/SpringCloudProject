package com.demo.payment.service.impl;

import com.demo.payment.dto.OrderPayDTO;
import com.demo.payment.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl  implements OrderService{
    @Override
    public OrderPayDTO findOrderPayInfoByOrderNo(String orderNo) {
        // 查询数据库
        return null;
    }
}
