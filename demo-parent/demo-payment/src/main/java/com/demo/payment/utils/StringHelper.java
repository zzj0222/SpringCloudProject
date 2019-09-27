package com.demo.payment.utils;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StringHelper {

    /**
     * 设置默认字符串
     *
     * @param
     * @param defaultVal 请求为空的时候设置的默认值
     * @return
     */
    public static String setDefaultString(Object str, String defaultVal) {
        if (str == null) {
            return defaultVal;
        }
        return StringHelper.isNullOrEmpty(str.toString()) ? defaultVal : str.toString();
    }

    /**
     * 对字符串进行md5加密
     *
     * @param s 将要加密的字符串
     * @return 加密后的字符串
     */
    public final static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 进行判断的字符串
     * @return 如果为null 或者"" 则返回true代表为空 如果字符串不为空则返回false
     */
    public final static boolean isNullOrEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }
        return false;
    }

    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Integer Yuan2Fen(Double yuan) {
        return new BigDecimal(yuan).movePointRight(2).intValue();
    }

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static Double Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }




}


