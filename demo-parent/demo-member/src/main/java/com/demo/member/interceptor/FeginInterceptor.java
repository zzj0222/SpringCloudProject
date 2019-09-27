package com.demo.member.interceptor;


import com.laiai.core.util.ParameterUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author zheng.shk
 * @Date 14:14 2018/12/21
 */
@Component
public class FeginInterceptor implements RequestInterceptor{
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if(null==getHttpServletRequest()){
            return;
        }
        Integer userId = ParameterUtils.getCurrentUserId();
        if(userId!=null){
            requestTemplate.header("userId",userId.toString());
            String userObject = getHttpServletRequest().getHeader("userObject");
            requestTemplate.header("userObject",userObject);
            String userName = getHttpServletRequest().getHeader("username");
            requestTemplate.header("userName",userName);
        }
    }


    private HttpServletRequest getHttpServletRequest(){
        try {
            return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}
