package com.demo.utils.platform.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @Project : laiai_project
 * @Package Name : com.laiai.chat.common
 * @Description : 工具类
 * @Author gongtianyu
 * @Date 2018年04月03日 17:32
 * -------------------------------------------
 */
@Slf4j
public class CommonUtils {
    public static final String KEY = "yinxue_jwt_1";

    public static final int HMACSHA256_HASHSIZE = 256;

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * HMACSHA256加密
     *
     * @param message
     * @param secret
     * @return 加密后的字符串
     */
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            log.error("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static JSONObject getJwtHeaderByToken(String token) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        token = token.substring(0, token.indexOf("."));
        byte[] bytes = decoder.decode(token);
        String jsonStr = new String(bytes, "UTF-8");
        return JSONObject.parseObject(jsonStr);
    }

    /**
     * 根据传递的token判断
     * 认证成功返回json
     * 失败抛出异常
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static JSONObject getContentByToken(String token) throws Exception {

        final Base64.Decoder decoder = Base64.getDecoder();
        // 获取.后面的一段
        token = token.substring(token.indexOf(".") + 1, token.length());
        // 替换-和_
        token = token.replaceAll("-", "+").replaceAll("_", "/");
        switch (token.length() % 4) {
            case 0:
                break;
            case 2:
                token += "==";
                break;
            case 3:
                token += "=";
        }
        // 将token base64解码成字节数组
        byte[] bytes = decoder.decode(token);
        int byteSize = HMACSHA256_HASHSIZE >> 3;
        // 获取token中混淆的加密后的jwt内容   向右位移3
        byte[] jwtContent = new byte[byteSize];
        for (int i = 0; i < jwtContent.length; i++) {
            jwtContent[i] = bytes[i];
        }
        // 获取jwt内容HMACSHA256算法hash值
        String jwtContentHash = byteArrayToHexString(jwtContent);
        // 剩余的部分为接口入参
        byte[] data = new byte[bytes.length - byteSize];
        for (int i = 0; i + byteSize < bytes.length; i++) {
            data[i] = bytes[i + byteSize];
        }
        // 将data转换为字符串
        String jsonStr = new String(data, "UTF-8");
        // 根据key加密剩余部分
        String contentHash = sha256_HMAC(jsonStr, KEY);
        if (contentHash.equals(jwtContentHash)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            return json;
        } else {
            throw new Exception("非法token");
        }
    }
}
