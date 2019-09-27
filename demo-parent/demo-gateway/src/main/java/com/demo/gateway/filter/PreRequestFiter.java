package com.demo.gateway.filter;

import com.demo.gateway.constant.HttpHeader;
import com.demo.gateway.constant.SystemHeader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 路由之前调用的过滤器(身份验证等)
 * @author zzj
 * @create 2018-11-08 17:45
 **/
@Component
public class PreRequestFiter extends ZuulFilter{
    private final Logger log = LoggerFactory.getLogger(PreRequestFiter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 返回一个int值来指定过滤器的执行顺序，不同的过滤器允许返回相同的数字。
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 返回一个boolean值来判断该过滤器是否要执行，true表示执行，false表示不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /**
     * 过滤器的具体逻辑
     * 用户访问权限校验
     * 1、先获取用户token,从token中获取userId和username
     * 2、根据userId或者username获取用户的权限列表
     * 3、根据用户请求的uri和method，查看用户是否具有改uri权限
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();
        String method=request.getMethod();
        long dateHead=request.getDateHeader(HttpHeader.HTTP_HEADER_DATE);
        String  queryString=request.getQueryString();
       String appKey= request.getHeader(SystemHeader.X_CA_KEY);
       String sign=request.getHeader(SystemHeader.X_CA_SIGNATURE);
       String signHeader=request.getHeader(SystemHeader.X_CA_SIGNATURE_HEADERS);
       String timeStamp=request.getHeader(SystemHeader.X_CA_TIMESTAMP);
       // 登陆认证

        System.out.println(request.getRequestURI());
        System.out.println(method);
        System.out.println(dateHead);
        System.out.println(queryString);
        System.out.println(appKey);
        System.out.println(request.getContentType());
        System.out.println(request.getScheme());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteUser());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getRemotePort());
       boolean l= Arrays.asList("application/json,application/xml").contains(request.getContentType());
        System.out.println(l);



        return null;
    }

    public static void main(String[] args) {
        boolean l= Arrays.asList("application/json","application/xml").contains("application/json");
        List<String> list=  Arrays.asList("application/json","application/xml");
        System.out.println(l);
        System.out.println(list);
        String inStr="{\n" +
                "  \"code\": \"0000\",\n" +
                "  \"msg\": \"SUCCESS\",\n" +
                "  \"data\": {\n" +
                "    \"bannerList\": [\n" +
                "      {\n" +
                "        \"bannerPath\": \"http://jiayicollege.oss-cn-shenzhen.aliyuncs.com/upload/20181126/b1df50f4107f46b0ad25fc3d27b9cf46.jpg\",\n" +
                "        \"bannerLink\": \"http://www.testclass.net/appium_base/appium-base-sdk\",\n" +
                "        \"bannerName\": \"的\",\n" +
                "        \"productId\": 133,\n" +
                "        \"productType\": 3,\n" +
                "        \"courseType\": 3\n" +
                "      },\n" +
                "      {\n" +
                "        \"bannerPath\": \"http://jiayicollege.oss-cn-shenzhen.aliyuncs.com/upload/20190315/917a0636127b4466aab0a41ba4f6b90a.jpg\",\n" +
                "        \"bannerLink\": \"\",\n" +
                "        \"bannerName\": \"测试\",\n" +
                "        \"productId\": 213,\n" +
                "        \"productType\": 4,\n" +
                "        \"courseType\": 2\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        try
        {

            System.out.println(inStr.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
