package com.demo.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author zheng.shk
 * @Date 17:04 2018/3/19
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter
{

    private final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = null;
        if (handler instanceof HandlerMethod) {
            handlerMethod = (HandlerMethod) handler;
        } else {
            return true;
        }
        MDC.put("remoteAddr", "remoteAddr:" + getIpAddr(request));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTime =  format.format(new Date()).toUpperCase();
        String className = handlerMethod.getBean().getClass().getSimpleName();
        String method = handlerMethod.getMethod().getName();
        MDC.put("requestController", "requestController:" + className + "---methodName:" + method);
        String client =  request.getHeader("client");
        StringBuilder reqLog = new StringBuilder();
        if(notBlank(client)){
            reqLog.append("client:").append(client).append("--");
        }
        reqLog.append("requestController:").append(className).append("--method:").append(method);
        logger.info("request log:"+requestTime+"--"+reqLog.toString());
        return true;
    }


    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(notBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(this.notBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


    /**
     * 字符串不为 null 而且不为  "" 时返回 true
     */
    public boolean notBlank(String str) {
        return str != null && !"".equals(str.trim());
    }

    /**
     * 字符串为 null 或者为  "" 时返回 true
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }


    public static Date parseDate(String dateStr) {
        try {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = null;
            if (isBlank(dateStr)) {
                return null;
            }
            format = new SimpleDateFormat(pattern);
            return format.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    public static void main(String[] args) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String result =  format.format(new Date()).toUpperCase();
        System.out.println(result);
    }
}
