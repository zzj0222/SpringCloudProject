package com.demo.auth.api;

import com.laiai.core.BaseController;
import com.laiai.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zzj
 * @create 2019-03-05 9:09
 **/
@RestController
public class Authorize extends BaseController {


    @RequestMapping("/connect/oauth2/authorize")
    Result getAuthorizeCode(HttpServletRequest request){
       String appId= request.getAttribute("appId").toString();
       String redirectUrl= request.getAttribute("redirectUrl").toString();
        return  success(appId);
    }
}
