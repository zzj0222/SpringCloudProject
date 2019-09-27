package com.demo.utils.jwt;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/2/20 14:44
 */

import com.demo.utils.jwt.JwtClaimsParameter;
import com.demo.utils.jwt.SelfDefinedParameter;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;

/**
 * @author zzj
 * @create 2019-02-20 14:44
 **/
public class JwtClaimsUtils {
    /**
     * 通过 OIDC 协议中定义的 Claims 属性(aud, sub, exp, iat, iss)与其属性值，生成 Claims(全称 JwtClaims)
     * @return
     */
    public static JwtClaims getJwtClaims(JwtClaimsParameter jwtClaimsParameter){
        JwtClaims claims = new JwtClaims();
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        //expire time
        NumericDate date = NumericDate.now();
        date.addSeconds(jwtClaimsParameter.getSecond());
        claims.setExpirationTime(date);
        claims.setNotBeforeMinutesInThePast(jwtClaimsParameter.getNotBeforeMinutesInThePast());
        claims.setSubject(jwtClaimsParameter.getSubject());
        claims.setAudience(jwtClaimsParameter.getAudience());
        claims.setIssuer(jwtClaimsParameter.getIssuer());
        claims.setGeneratedJwtId(); // 令牌的唯一标识符
        claims.setIssuedAtToNow(); // 当令牌被发布/创建时（现在）
        // // 在此之前，令牌无效（2分钟前）
        claims.setNotBeforeMinutesInThePast(jwtClaimsParameter.getNotBeforeMinutesInThePast());
        //添加自定义参数
        for(SelfDefinedParameter selfDefinedParameter:jwtClaimsParameter.getSelfDefinedParameterList()) {
            claims.setClaim(selfDefinedParameter.getKey(), selfDefinedParameter.getValue());
        }
        return claims;
    }
    public static JwtClaims getJwtClaims2(JwtClaimsParameter jwtClaimsParameter){

        JwtClaims claims =new JwtClaims();
        claims.setIssuer(jwtClaimsParameter.getIssuer());
        claims.setAudience(jwtClaimsParameter.getAudience());
        // 令牌失效的时间长（从现在开始10分钟）
        claims.setExpirationTimeMinutesInTheFuture(jwtClaimsParameter.getExpirationTimeMinutesInTheFuture());
        claims.setGeneratedJwtId(); // 令牌的唯一标识符
        claims.setIssuedAtToNow(); // 当令牌被发布/创建时（现在）
        // // 在此之前，令牌无效（2分钟前）
        claims.setNotBeforeMinutesInThePast(jwtClaimsParameter.getNotBeforeMinutesInThePast());
//        NumericDate numericDate=NumericDate.now();
//        numericDate.addSeconds(jwtClaimsParameter.getSecond());
//        claims.setExpirationTime(numericDate);
        claims.setSubject(jwtClaimsParameter.getSubject());
//        List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
//        //  // 多个属性/声明 也会起作用，最终会成为一个JSON数组
//        claims.setStringListClaim("groups", groups);
        //添加自定义参数
        for(SelfDefinedParameter selfDefinedParameter:jwtClaimsParameter.getSelfDefinedParameterList()) {
            claims.setClaim(selfDefinedParameter.getKey(), selfDefinedParameter.getValue());
        }
        return  claims;
    }


}
