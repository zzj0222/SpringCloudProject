package com.demo.admin.api;

import com.laiai.core.BaseController;
import com.laiai.core.Result;
import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzj
 * @create 2018-12-11 15:36
 **/
@RestController
public class LoginApi extends BaseController {

//    @PostMapping("/login")
//    @ResponseBody
//    public Result ajaxLogin(String username, String password, Boolean rememberMe)
//    {
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
//        Subject subject = SecurityUtils.getSubject();
//        try
//        {
//            subject.login(token);
//            return success();
//        }
//        catch (AuthenticationException e)
//        {
//            String msg = "用户或密码错误";
//            if (StringUtils.isNotEmpty(e.getMessage()))
//            {
//                msg = e.getMessage();
//            }
//            return fail(msg);
//        }
//    }


}
