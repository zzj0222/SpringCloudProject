package com.demo.utils.http.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.utils.encrypt.util.RsaUtils;

/**
 * @author zheng.shk
 * @ClassName: HttpUtils
 * @Description: HTTP工具类
 * @date 2016年9月9日 下午4:37:15
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * @param url    路径
     * @param reqMap 参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, Object> reqMap) throws Exception {
        JSONObject jsonParam = JSONObject.parseObject(JSON.toJSONString(reqMap));
        return httpPost(url, jsonParam);
    }


    /**
     * post请求
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return HashMap<String   ,   Object>
     * @throws Exception
     */
    private static String httpPost(String url, JSONObject jsonParam) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpRequst = new HttpPost(url);
        StringEntity entity = null;
        if (null != jsonParam) {
            // 解决中文乱码问题
            entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpRequst.setEntity(entity);
            logger.info("------------------------request url:" + url);
            logger.info("------------------------send request data:" + jsonParam.toJSONString());

        }
        HashMap<String, String> responseMap = null;
        //https请求
        if (url.startsWith("https://")) {
            responseMap = SslUtils.post(url, null, null, entity);

        } else { //http请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpRequst);
            // 请求发送成功，并得到响应
            logger.info("★★★★★★★★★★★★★★★★★★★★★★★★http response code:" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseMap = new HashMap<String, String>();
                // 读取服务器返回过来的报文体
                String responseBody = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                responseMap.put("content", responseBody);
                logger.info("------------------------response data :" + responseBody);
            } else {
                throw new Exception("请求失败或者服务器错误");
            }
        }
        String result = responseMap.get("content");
        return result;
    }


    /**
     * post请求 并进行RSA加签，加签sign放到header中
     *
     * @param url        url地址
     * @param privateKey 私钥
     * @param reqMap     请求map
     * @return Map
     * @throws Exception
     */
    public static HashMap<String, String> httpPostAndSign(String url, String privateKey, Map<String, Object> reqMap) throws Exception {
        JSONObject jsonParam = JSONObject.parseObject(JSON.toJSONString(reqMap));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpRequst = new HttpPost(url);
        StringEntity entity = null;
        String sign = "";
        if (null != jsonParam) {
            // 解决中文乱码问题
            entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpRequst.setEntity(entity);
            sign = RsaUtils.sign(jsonParam.toString(), privateKey, "UTF-8");
            httpRequst.setHeader("sign", sign);
            logger.info("------------------------request url:" + url);
            logger.info("------------------------request header sign:" + sign);
            logger.info("------------------------send request data:" + jsonParam.toJSONString());

        }
        HashMap<String, String> responseMap = null;
        //https请求
        if (url.startsWith("https://")) {
            HashMap<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("sign", sign);
            responseMap = SslUtils.postSign(url, headerMap, null, entity);
        } else {
            //http请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpRequst);
            // 请求发送成功，并得到响应
            logger.info("★★★★★★★★★★★★★★★★★★★★★★★★http response code:" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseMap = new HashMap<String, String>();
                // 读取服务器返回过来的报文体
                String responseBody = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                responseMap.put("content", responseBody);
                Header signHeader = httpResponse.getFirstHeader("sign");
                if (null != signHeader) {
                    responseMap.put("sign", signHeader.getValue());
                    logger.info("------------------------response header sign :" + signHeader.getValue());
                }
                logger.info("------------------------response data :" + responseBody);
            } else {
                throw new Exception("请求失败或者服务器错误");
            }
        }

        return responseMap;

    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String httpGet(String url) throws Exception {
        // get请求返回结果
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        logger.info("------------------------request url:" + url);
        CloseableHttpResponse response = client.execute(request);
        // 请求发送成功，并得到响应
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★http response code:" + response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 读取服务器返回过来的json字符串数据
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            logger.info("------------------------response data :" + result);
        } else {
            logger.info("get请求提交失败:" + url);
        }
        return result;
    }

    /**
     * @param request
     * @return
     * @throws IOException
     * @Title: getHttpReqBody
     * @Description: 获取http的请求body
     */
    public static String getHttpReqBody(HttpServletRequest request) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param request
     * @param clazz
     * @return
     * @throws IOException
     * @Title: getReqParam
     * @Description: 获取http+json的请求参数，并返回对应的object
     */
    public static <T> T getReqParam(HttpServletRequest request, Class<T> clazz) throws Exception {
        String reqJsonParam = getHttpReqBody(request);
        try {
            T t = JSON.parseObject(reqJsonParam, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            String eMsg = e.getMessage();
            throw new Exception("json invalid,message:" + eMsg);
        }
    }


}
