package com.demo.utils.platform.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: WebUtils
 * @Description: WEB工具类
 * @author zheng.shk
 * @date 2016年8月9日 下午2:11:07
 */
public class WebUtils {
	
	private static Logger log = LoggerFactory.getLogger(WebUtils.class);
	
	/**
	 * 获取上下文URL全路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		int prot = request.getServerPort();
		sb.append("http://").append(request.getServerName()).append(":").append(prot).append(request.getContextPath());
		String path = sb.toString();
		sb = null;
		return path;
	}
	
	
	/**
	 * 获取完整请求路径(含内容路径及请求参数)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}	
	
	
	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if(StrKit.notBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			log.info("request ip 地址：{}",XFor);
			int index = XFor.indexOf(",");
			if(index != -1){
				return XFor.substring(0,index);
			}else{
				return XFor;
			}
		}
		XFor = Xip;
		if(StrKit.notBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
			return XFor;
		}
		if (StrKit.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StrKit.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StrKit.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StrKit.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StrKit.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		log.info("request ip 地址：{}",XFor);
		return XFor;
	}

	
	/**
	 * 获取页面传送过来的参数值，并放到paramMap中，页面可以获取其原来的值
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			map.put(name, request.getParameter(name));
		}
		return map;
	}
	
	/**
	 * 
	 * @Description: 重新设置页面需要显示的值，放到paramMap中，页面可以获取其值
	 * @param request
	 * @param key
	 * @param value  void
	 */
	@SuppressWarnings("unchecked")
	public static void setParmMap(HttpServletRequest request,String key, String value){
		Map<String, String> map = (Map<String, String>) request.getAttribute("paramMap");
		map.put(key, value);
		request.setAttribute("paramMap", map);
	}
	
	/**
	 * 判断ajax请求
	 * @param request
	 * @return  true:是ajax请求， false：非ajax请求
	 */
	public static boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}
	
	
	
	/**
	 * 
	 * @Title: getReqBody
	 * @Description: 获取http+json的请求参数
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getReqBody(HttpServletRequest request) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
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
	 * 
	 * @Title: getReqBodyToObject
	 * @Description: 获取http+json的请求参数，并返回对应的object
	 * @param request
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T getReqBodyToObject(HttpServletRequest request, Class<T> clazz) throws Exception {
		String reqJsonParam = getReqBody(request);
		try {
			T t = JSON.parseObject(reqJsonParam, clazz);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			String eMsg = e.getMessage();
			if (StrKit.isBlank(eMsg)) {
				eMsg = e.getCause().toString();
			}
			throw new Exception("json invalid,message:" + eMsg);
		}
	}


}
