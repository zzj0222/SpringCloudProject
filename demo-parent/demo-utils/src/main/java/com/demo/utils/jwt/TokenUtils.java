package com.demo.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import com.demo.utils.http.HttpClientUtil;
import com.sun.deploy.net.HttpUtils;
import org.jose4j.base64url.Base64Url;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.*;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.lang.JoseException;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zzj
 * @create 2019-02-20 15:02 UUIDUtils.getKeyId()
 * https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
 **/
public class TokenUtils {
    public static RsaJsonWebKey rsaJsonWebKey = RsaJsonWebKeyUtil.getInstance("123456");
    /**
     * URLEncoder charset
     */
    public static final String CHARSET = "UTF-8";
    /**
     * JWT格式的数据，包含用户身份认证的信息
     * @param keyId
     * @param claims
     * @param privateKeyText
     * @return
     * @throws JoseException
     *
     */
    public  static String  getIdToken(String keyId, JwtClaims claims,String privateKeyText) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setKeyIdHeaderValue(keyId);
        jws.setPayload(claims.toJson());
        PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(privateKeyText)).getPrivateKey();
        jws.setKey(privateKey);
        String idToken = jws.getCompactSerialization();
        return  idToken;
    }

    public  static boolean verifyToken(final String idToken,String issuer,String audience,String publicKeyText) throws MalformedClaimException, JoseException {
        boolean flag=false;
        /*
         * 使用JwtConsumer builder构建适当的JwtConsumer，它将 用于验证和处理JWT。 JWT的具体验证需求是上下文相关的， 然而,
         * 通常建议需要一个（合理的）过期时间，一个受信任的时间 发行人, 以及将你的系统定义为预期接收者的受众。
         * 如果JWT也被加密，您只需要提供一个解密密钥对构建器进行解密密钥解析器。
         */
        HttpsJwks httpsJkws = new HttpsJwks("http://localhost:5555");
        HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);

        PublicKey publicKey = new RsaJsonWebKey(JsonUtil.parseJson(publicKeyText)).getPublicKey();
        JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() //// JWT必须有一个有效期时间
                .setAllowedClockSkewInSeconds(30) // 允许在验证基于时间的令牌时留有一定的余地，以计算时钟偏差。单位/秒
                .setRequireSubject() // 主题声明
                .setVerificationKeyResolver(httpsJwksKeyResolver)
                .setExpectedIssuer(issuer) // JWT需要由谁来发布,用来验证 发布人
                .setExpectedAudience(audience) // JWT的目的是给谁, 用来验证观众
                .setVerificationKey(publicKey) // 用公钥验证签名 ,验证秘钥
                .setJwsAlgorithmConstraints( // 只允许在给定上下文中预期的签名算法,使用指定的算法验证
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, // 白名单
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build(); // 创建JwtConsumer实例

        try {
            // 验证JWT并将其处理为jwtClaims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(idToken);
//			如果JWT失败的处理或验证，将会抛出InvalidJwtException。
//			希望能有一些有意义的解释（s）关于哪里出了问题。
            System.out.println("JWT validation succeeded! " + jwtClaims);
            flag=true;
        } catch (InvalidJwtException e) {
            System.out.println("Invalid JWT! " + e);
            // 对JWT无效的（某些）特定原因的编程访问也是可能的
            // 在某些情况下，您是否需要不同的错误处理行为。
            // JWT是否已经过期是无效的一个常见原因
            if (e.hasExpired()) {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
            }
            // 或者观众是无效的
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
        }
        return  flag;
    }

    public static String  refresh(String jwt,String issuer,String audience,PublicKey publicKey,PrivateKey privateKey){
        JwtClaims claims=passJwt(jwt);
        if(claims!=null){
            // 过期时间每次都加30s
            NumericDate numericDate=NumericDate.now();
            numericDate.addSeconds(30);
            claims.setExpirationTime(numericDate);
            try {
                System.out.println(claims.getExpirationTime().getValue());
            } catch (MalformedClaimException e) {
                e.printStackTrace();
            }
            // JWT是一个JWS和/或一个带有JSON声明的JWE作为有效负载。
            // 在这个例子中，它是一个JWS，所以我们创建一个JsonWebSignature对象。
            JsonWebSignature jws = new JsonWebSignature();
            // JWS的有效负载是JWT声明的JSON内容
            jws.setPayload(claims.toJson());
            // JWT使用私钥签署
            jws.setKey(privateKey);
            /*
             * 设置关键ID（kid）头，因为这是一种礼貌的做法。 在这个例子中，我们只有一个键但是使用键ID可以帮助 促进平稳的关键滚动过程
             */
            jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

            // 在jw/jws上设置签名算法，该算法将完整性保护声明
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            /*
             * 签署JWS并生成紧凑的序列化或完整的jw/JWS 表示，它是由三个点（'.'）分隔的字符串
             * 在表单头.payload.签名中使用base64url编码的部件 如果你想对它进行加密，你可以简单地将这个jwt设置为有效负载
             * 在JsonWebEncryption对象中，并将cty（内容类型）头设置为“jwt”。
             */
            try {
                jwt = jws.getCompactSerialization();
            } catch (JoseException e) {
                e.printStackTrace();
            }
            return jwt;
        }
        return  jwt;


    }

    /**
     * 授权token
     * 资源服务器 RP使用Access Token访问时，返回授权用户的信息
     * @return
     * 用于访问资源
     * @param grantType 授权类型 client_credential-客户端认证
     * @param appId
     * @param appSecret
     * @return {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    public static  String getAccessToken(String grantType,String appId,String appSecret){
        return  "";
    }

    /**
     * 获取授权码
     * @return
     */
    /**
     * 用于获取授权code的URL地址，此地址用于用户身份鉴权，获取用户身份信息，同时重定向到$redirect_url
     *
     * @param appId       微信公众号应用唯一标识
     * @param redirectUrl 授权后重定向的回调链接地址，重定向后此地址将带有授权code参数，
     *                    该地址的域名需在微信公众号平台上进行设置，
     *                    步骤为：登陆微信公众号平台  开发者中心  网页授权获取用户基本信息 修改
     * @param moreInfo    FALSE 不弹出授权页面,直接跳转,这个只能拿到用户openid
     *                    TRUE 弹出授权页面,这个可以通过 openid 拿到昵称、性别、所在地，
     *                    重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节，该值会被微信原样返回，我们可以将其进行比对，防止别人的攻击
     * @return 用于获取授权code的URL地址
     * @throws UnsupportedEncodingException
     */
    public static String createOauthUrlForCode(String appId, String redirectUrl, boolean moreInfo)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        sb.append("appid=").append(appId);
        sb.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, CHARSET));
        sb.append("&response_type=code");
        sb.append("&scope=").append(moreInfo ? "snsapi_userinfo" : "snsapi_base");
        sb.append("&state=pingpp");
        sb.append("#wechat_redirect");



        return sb.toString();
    }

    // 生成一个RSA密钥对，用于签署和验证JWT，包装在JWK中


    public  static String jwtsign(JwtClaims claims,PrivateKey privateKey ) {

        // JWT是一个JWS和/或一个带有JSON声明的JWE作为有效负载。
        // 在这个例子中，它是一个JWS，所以我们创建一个JsonWebSignature对象。
        JsonWebSignature jws = new JsonWebSignature();
        // JWS的有效负载是JWT声明的JSON内容
        jws.setPayload(claims.toJson());
        // JWT使用私钥签署
        jws.setKey(privateKey);
        /*
         * 设置关键ID（kid）头，因为这是一种礼貌的做法。 在这个例子中，我们只有一个键但是使用键ID可以帮助 促进平稳的关键滚动过程
         */
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // 在jw/jws上设置签名算法，该算法将完整性保护声明
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        /*
         * 签署JWS并生成紧凑的序列化或完整的jw/JWS 表示，它是由三个点（'.'）分隔的字符串
         * 在表单头.payload.签名中使用base64url编码的部件 如果你想对它进行加密，你可以简单地将这个jwt设置为有效负载
         * 在JsonWebEncryption对象中，并将cty（内容类型）头设置为“jwt”。
         */
        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException e) {
            e.printStackTrace();
        }
        return jwt;
    }

    public  static  JwtClaims checkJwt(String jwt,String issuer,String audience,PublicKey publicKey )   {
        /*
         * 使用JwtConsumer builder构建适当的JwtConsumer，它将 用于验证和处理JWT。 JWT的具体验证需求是上下文相关的， 然而,
         * 通常建议需要一个（合理的）过期时间，一个受信任的时间 发行人, 以及将你的系统定义为预期接收者的受众。
         * 如果JWT也被加密，您只需要提供一个解密密钥对构建器进行解密密钥解析器。
         */


        JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() //// JWT必须有一个有效期时间
                .setAllowedClockSkewInSeconds(30) // 允许在验证基于时间的令牌时留有一定的余地，以计算时钟偏差。单位/秒
                .setRequireSubject() // 主题声明
                .setExpectedIssuer(issuer) // JWT需要由谁来发布,用来验证 发布人
                .setExpectedAudience(audience) // JWT的目的是给谁, 用来验证观众
                .setVerificationKey(publicKey) // 用公钥验证签名 ,验证秘钥
                .setJwsAlgorithmConstraints( // 只允许在给定上下文中预期的签名算法,使用指定的算法验证
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, // 白名单
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build(); // 创建JwtConsumer实例 RSA_USING_SHA256

        try {
            // 验证JWT并将其处理为jwtClaims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);

//			如果JWT失败的处理或验证，将会抛出InvalidJwtException。
//			希望能有一些有意义的解释（s）关于哪里出了问题。
            System.out.println("JWT validation succeeded! " + jwtClaims);
            return  jwtClaims;
        } catch (InvalidJwtException e) {
            System.out.println("Invalid JWT! " + e);
            // 对JWT无效的（某些）特定原因的编程访问也是可能的
            // 在某些情况下，您是否需要不同的错误处理行为。
            // JWT是否已经过期是无效的一个常见原因
            if (e.hasExpired()) {
                try {
                    System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
                } catch (MalformedClaimException e1) {
                    e1.printStackTrace();
                }
            }
            // 或者观众是无效的
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                try {
                    System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
                } catch (MalformedClaimException e1) {
                    e1.printStackTrace();
                }
            }
            return  null;
        }


    }

    public  static JwtClaims passJwt(String jwt){
        // Build a JwtConsumer that doesn't check signatures or do any validation
        JwtConsumer firstPassJwtConsumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();
        //The first JwtConsumer is basically just used to parse the JWT into a JwtContext object.
        try {
            JwtContext jwtContext = firstPassJwtConsumer.process(jwt);
            return  jwtContext.getJwtClaims();
//            String issuer = jwtContext.getJwtClaims().getIssuer();
//
//            // Just using the same key here but you might, for example, have a JWKS URIs configured for
//            // each issuer, which you'd use to set up a HttpsJwksVerificationKeyResolver
//            Key verificationKey = rsaJsonWebKey.getKey();
//            // And set up the allowed/expected algorithms
//            AlgorithmConstraints algorithmConstraints = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
//                    AlgorithmIdentifiers.RSA_USING_SHA256, AlgorithmIdentifiers.RSA_USING_SHA384);

        } catch (InvalidJwtException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static void main(String[] args) throws IOException, JoseException, MalformedClaimException, InterruptedException {
//        String keyId=UUIDUtils.getKeyId();
        JwtClaimsParameter jwtClaimsParameter=new JwtClaimsParameter();
        // ID Token的消费者
//        System.out.println(keyId);
        jwtClaimsParameter.setAudience("JiaYi");
        jwtClaimsParameter.setSubject("subject");
        jwtClaimsParameter.setNotBeforeMinutesInThePast(1);
        // 在有效时间过期之后，ID Token不得接受处理
        jwtClaimsParameter.setSecond(360L);
        // Subject标识符。由客户端使用本地唯一的从不在发行给终端用户期间重新分配的标识符
        jwtClaimsParameter.setSubject("subject");
        List<SelfDefinedParameter> selfDefinedParameterList=new ArrayList<>();
        SelfDefinedParameter selfDefinedParameter=new SelfDefinedParameter();
        selfDefinedParameter.setKey("userId");
        selfDefinedParameter.setValue(134393);
        selfDefinedParameterList.add(selfDefinedParameter);
        jwtClaimsParameter.setSelfDefinedParameterList(selfDefinedParameterList);
        JwtClaims claims=JwtClaimsUtils.getJwtClaims(jwtClaimsParameter);

//        System.out.println(claims);
//        String   keyId2="51be95bc30104212bd06623969f77f01";
//        String privateKeyText=KeyPairUtils.getPrivateKey(keyId2,null,null);
//        String publicKeyText=KeyPairUtils.getPublicKey(keyId2,null,null);
//        PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(privateKeyText)).getPrivateKey();
//        PublicKey publicKey = new RsaJsonWebKey(JsonUtil.parseJson(publicKeyText)).getPublicKey();

//        String sign= jwtsign(claims,rsaJsonWebKey.getPrivateKey());
//        System.out.println(sign);
//        JwtClaims jwtClaims=checkJwt(sign,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),keyId);
//        System.out.println(sign);
//        System.out.println(jwtClaimsParameter.getAudience());
//        System.out.println(jwtClaimsParameter.getIssuer());
////        System.out.println(keyId);
//        Thread.sleep(20*1000);



//        System.out.println(jwtClaims3.getClaimValue("userId"));
//        // pem转换成jwk格式 kty:密钥类型
//        // jwk格式 kty:算法是RSA n alg:指定算法ES256
//        // 第二个使用RSA算法。第一个指定关键是用于加密。第二个参数指定的关键是使用RS256算法。都提供一个键ID键匹配的目的。在这两种情况下,整数使用base64url编码表示的大端法表示。(换行符值仅用于显示目的
//        String privateKey="{\"kty\":\"RSA\",\"kid\":\"9a715977a075444bba3edf02d6847f73\",\"alg\":\"ES256\",\"n\":\"sEKqTZ3q2UVfX4Wu9FwPpzrIY9_uVhhx0GCm71QuUFFimFdAWDxZvt3mk5QcX0il85dFcgiktNeEO52GJ8FvxkxByMEyLme5Vta1HWfsFhVOljX8Dkb74uSomvBpuopkjcrXD3qaI_grygnwXxTO2mxYaDZslf9tn6iBfxVkEHkrTVKCCKlOz6HmfxlYwirF2bNZJWQG2NoJ14Rbsew-kj7kdn0KRqEsVKmOs1yrLOY5xz33g_0jdwOq41TYoQUYPgF383l1getgu3vU5G0Bw1_Gp9yuDGZ2ZCH2_S2IjbVqLG802Qs707BlLr6l6G1-FwUyVWv6iYXl7Wrs79eOMQ\",\"e\":\"AQAB\",\"d\":\"jd660QqAky0hXPNvJMRM3R-VV75QSgAW69A_zXcGaCN5poDkC6FX4ycchkC6rX7-BBkIDocie2TUzF84MhuiagvLD1lhmLRoG5tp4CdbgVLZFFKTdLJx3-iIRV2o3Dq0QOS5QX4_PyBrro1dakqHTIvUhNz5fapAhDfPM4j2J8LkBfw9EdMASlqUnhdWpCGjjWuNTv5TbdxZOsSKIToaZzqe54GzTj4h7dqixEQbRaGqPmccQRjWtOAD_0GtbFW23GBvC1Ln0KxcPK6fcOEVhRfKFXF6j4mYjVdzzSoO0V0DpzHQze1VKNkpn1xNOhC2rWQvfiNEP03FYYJFJDOVgQ\",\"p\":\"_whWVC1eWWwF9KSnJSqv6B06goB3yvAPRb3XUBhA56OfcMZqRlQNjbCGKJQ2X9CZHnrVG-VbgGoxo9H46cxg2nofCuESD23WQgURrb6yK1BoeLVBqZi0_YFiDWogtCLdFAA1yO1smW-MFMSKfPDWzEtkDexnF_YvOPkIYGufXSk\",\"q\":\"sO3VCeyRxi50OPAew4linyfMIhjnSqH3n2LcjXhHapwYmoMGKbKRNjPzpD8WnIjRnaHqECh5FeUPe5gDpu_hmQqd389BxaqxMMy-jp4S8Ea9CF4w1Pves9YfNi6hf8XSXMWdBDiUQ0yN57i_El6SQVRilOwnxGL5AgDl3EKQQck\",\"dp\":\"81W-IRUkt8elKxbLUGAZdSwmPoxbQGuq9s07GnX5v8rT_2Oh1Tavs-ihKSi5dNRwtK9YlQRTOQSaFofqqxFRvEPXSKcVd1lCnL1_b7rK_x76f2nam_pTfqorp0cCqz20mTfWGb_H5eKT0uDwjmOxXz3z2oEcoLVJu1-JyY4GgXk\",\"dq\":\"EqWOLVSUbHma4CFWbK8_CJ0OI43SWohFa7PkoLhLdo3q_eXPMXfTXA4PoIDRpj-5L87Pt1I8qLUSc6kqwV5IvXNj_iF-whFiSU1ay_cW8eHSrF4Y8Wl2KCfp9DDn7mpv6D0fdkFsRsd7sS9EW_BBo8b7-MwGyOjRa4eishsFzxk\",\"qi\":\"ErdwTp5EB3quX-blESJYFnAofer9UIbzchYRKKw2f0Hq5v34D5z9Br5yIcH0gQx-P9BmgbtPRTp1xkAOEmGnlt-qXID-SVjTrN-NohYqosRpzD1CY-giV611kt5YUiHI3z0lrRs9LcCW9ugdHr5L5ftaFuutWZ76Ncb96OKw3wE\"}";
//        String publicKey="{\"kty\":\"RSA\",\"kid\":\"9a715977a075444bba3edf02d6847f73\",\"alg\":\"ES256\",\"n\":\"svE5-2JRPAC_mDs_WCFqjJ0jCXu2dMZWvCAM4b9FS4Ozr7bjPg2WBYtCvMGyLk0OXEsUcVAlpOJVrjOMPt33DjlR51lY96l27YEBGrz_ZqhfatsaNBGRPm8-4NsjlO8XOtp9P2a6InFwn-iRzgkFpCtAo0s0XdWz-F3pBuk92wiY5f1U9zeflSmUhZBKdq0avqh5lBP5HOHklkrM-wUbYpXYS4NYB6K8N_0LoQLRBJtm18_XkJ03VmLwfBasSTfompmVI7uDI5IP6qlCWJx5ZkiBxzYHHihbFOl_-HVrScqYNPVcqJcfFm4x9jjMR4raDrn6X5YA18uYSu3bG92Kqw\",\"e\":\"AQAB\"}";
//        System.out.println(KeyPairUtils.getPrivateKey(keyId,null,null));
//        // 公钥 - 对jwt进行验签
//        System.out.println(KeyPairUtils.getPublicKey(keyId,null,null));
//        String key="c3eaf0ebe4db4bbaa9c644ced1b75fd1";
//        System.out.println(key.length());
//       String url= createOauthUrlForCode("wx12264858","www.baidu.com",false);
//        String result=HttpClientUtil.doGet(url);
//        System.out.println(result);
        RsaJsonWebKey rsaJsonWebKey = RsaJsonWebKeyUtil.getInstance("123456");
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey));
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey.getKey()));
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey.getPublicKey()));
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey.getRsaPublicKey()));
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey.getPrivateKey()));
        System.out.println(JSONObject.toJSONString(rsaJsonWebKey.getRsaPrivateKey()));
//       Key key= rsaJsonWebKey.getKey();
//       PrivateKey privateKey=rsaJsonWebKey.getPrivateKey();
//       PublicKey publicKey=rsaJsonWebKey.getPublicKey();
//        rsaJsonWebKey.getKeyType();
//        rsaJsonWebKey.getAlgorithm();


    }
}
