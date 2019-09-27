package com.demo.core.util;

import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ParameterUtils {


    public static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request;
    }

    public static String getAllinpayBizUserId(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String allinpayBizUserId = null;
        if (request != null) {
            allinpayBizUserId = request.getHeader("allinpayBizUserId");
        }
        return  allinpayBizUserId;
    }


    public static String getLaiaiNumber(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String laiaiNumber = null;
        if (request != null) {
            laiaiNumber = request.getHeader("laiaiNumber");
        }
        return  laiaiNumber;
    }


    public static Integer getCurrentUserId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Integer userId = null;
        if (request != null) {
            String object = request.getHeader("userId");
            if (null != object) {
                userId = Integer.valueOf(request.getHeader("userId"));
            }else{
                Object object1 = request.getAttribute("userId");
                if(null!=object1){
                    userId = Integer.valueOf(object1.toString());
                }
            }
        }
        return userId;
    }

    public static String getCurrentUserName() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userName = "";
        if (request != null) {
            String object = request.getHeader("userName");
            if(object!=null){
                userName = new String(Base64Utils.decodeFromString(userName));
            }else{
                Object object1 = request.getAttribute("userName");
                if(null!=object1){
                    userName = object1.toString();
                }
            }
        }
        return userName;
    }

    public static String getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null==requestAttributes){
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userObject = "";
        if (request != null) {
            userObject = request.getHeader("userObject");
            if(userObject!=null){
                userObject = new String(Base64Utils.decodeFromString(userObject));
            }else{
                Object object1 = request.getAttribute("userObject");
                if(null!=object1){
                    userObject = object1.toString();
                }
            }
        }
        return userObject;
    }
}
