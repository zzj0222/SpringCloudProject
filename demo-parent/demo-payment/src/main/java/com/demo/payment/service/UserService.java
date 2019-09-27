package com.demo.payment.service;

import com.demo.payment.model.User;

public interface UserService {

    int updateWxAuthOpenIdByUserId(Long userId,String openId);

    User findByUserId(Long userId);
}
