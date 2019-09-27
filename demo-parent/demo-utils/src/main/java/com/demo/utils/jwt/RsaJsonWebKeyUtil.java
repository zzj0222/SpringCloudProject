package com.demo.utils.jwt;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/3/13 14:46
 */

import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

/**
 * @author zzj
 * @create 2019-03-13 14:46
 **/
public class RsaJsonWebKeyUtil {
    public static RsaJsonWebKey rsaJsonWebKey = null;

    private RsaJsonWebKeyUtil() {
    }
    public static RsaJsonWebKey getInstance(String keyId) {
        // 生成一个RSA密钥对，用于签署和验证JWT，包装在JWK中
        if (rsaJsonWebKey == null) {
            try {
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                rsaJsonWebKey.setKeyId(keyId);
            } catch (JoseException e) {
                e.printStackTrace();
            }
        }
        // 给JWK一个关键ID（kid），这是礼貌的做法
        return rsaJsonWebKey;
    }

    public  static JsonWebKeySet getJsonWebKey(String keyId){
        RsaJsonWebKey rsaJsonWebKey=getInstance(keyId);
        JsonWebKeySet jsonWebKeySet=new JsonWebKeySet(rsaJsonWebKey);
        return  jsonWebKeySet;
    }

}
