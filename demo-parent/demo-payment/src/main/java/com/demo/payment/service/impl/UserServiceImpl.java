package com.demo.payment.service.impl;

import com.demo.payment.model.User;
import com.demo.payment.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/4/8 16:30
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public int updateWxAuthOpenIdByUserId(Long userId, String openId) {
        return 0;
    }

    @Override
    public User findByUserId(Long userId) {
        return null;
    }
}
