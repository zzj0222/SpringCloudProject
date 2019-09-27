package com.demo.utils.jwt;

import lombok.Data;

import java.util.List;

/**
 * @author zzj
 * @create 2019-02-20 14:51
 **/
@Data
public class JwtClaimsParameter {

    public List<SelfDefinedParameter> selfDefinedParameterList;

    /**
     * 主题 ,是令牌的对象
     */
    public String subject="JWT";

    /**
     * 令牌将被发送给谁
     */
    public  String audience="JiaYi";

    public  Long second=30L;

    /**
     * 在此之前，令牌无效（1分钟前）
     */
    public  Integer notBeforeMinutesInThePast=1;

    /**
     * 令牌发行人：谁创建了令牌并签署了它
     */
    public  String issuer="LaiAi";

    /**
     * 令牌失效的时间长（从现在开始10分钟）
     */
    public  Integer expirationTimeMinutesInTheFuture=10;

}
