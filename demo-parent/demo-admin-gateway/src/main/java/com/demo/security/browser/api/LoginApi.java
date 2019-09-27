package com.demo.security.browser.api;

import com.laiai.core.BaseController;
import com.laiai.core.Result;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzj
 * @create 2018-12-11 15:36
 **/
@RestController
public class LoginApi extends BaseController {

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result ajaxLogin(String username, String password, Boolean rememberMe)
    {
       return  success();
    }


}
