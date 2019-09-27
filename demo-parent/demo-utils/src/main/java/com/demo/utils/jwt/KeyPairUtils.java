package com.demo.utils.jwt;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jose4j.base64url.Base64;
import org.jose4j.base64url.Base64Url;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;

/**
 * 公私钥 工具类
 * @author zzj
 * @create 2019-02-20 14:25
 **/
public class KeyPairUtils {
    /**
     *
     * @param keyId
     * @param use sig (signature)-用于签名   enc (encryption)-用于加密
     * @param  alg   算法：RS256 可选参数
     * @return
     * @throws JoseException
     */
    public  static String getPublicKey(String keyId,String use,String alg) throws JoseException {
        RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(2048);
        jwk.setKeyId(keyId);
        if(use!=null) {
            jwk.setUse(use);
        }

        List<String> keyOps=new ArrayList<>();
        jwk.setKeyOps(keyOps);

        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);

        String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        return  publicKey;
    }

    public  static String getPrivateKey(String keyId,String use,String alg) throws JoseException {
        RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(2048);
        jwk.setKeyId(keyId);
        if(use!=null) {
            jwk.setUse(use);
        }

        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);


        String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
        return  privateKey;
    }

    public static void main(String[] args) {
        System.out.println(Base64Url.encode("1".getBytes()));
        System.out.println(Base64.encode("1".getBytes()));
        System.out.println(Base64Url.decodeToUtf8String("MQ"));

    }

}
