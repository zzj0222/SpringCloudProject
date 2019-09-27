package com.demo.utils.jwt;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/3/14 10:41
 */

import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.lang.JoseException;

/**
 * @author zzj
 * @create 2019-03-14 10:41
 **/
public class EllipticCurveJsonWebKeyUtil {
    public  static EllipticCurveJsonWebKey ellipticCurveJsonWebKey=null;
    private  EllipticCurveJsonWebKeyUtil(){}
    public  static  EllipticCurveJsonWebKey getInstance(String keyId){
        if(ellipticCurveJsonWebKey==null){
            try {
                EllipticCurveJsonWebKey senderJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);
                senderJwk.setKeyId(keyId);
            } catch (JoseException e) {
                e.printStackTrace();
            }


        }

        return  ellipticCurveJsonWebKey;

    }
}
