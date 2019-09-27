//package com.demo.member.api;
//
//
//import com.alibaba.fastjson.JSONObject;
//
//import com.demo.member.service.FeignService;
//
//import com.demo.utils.jwt.*;
//import com.laiai.core.BaseController;
//import com.laiai.core.Result;
//
//import org.springframework.web.bind.annotation.*;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 提供给app 和 web 端的接口
// * 命名规范：模块名称＋Api结尾(模块名称要跟数据库表名一致)
// * @author zzj
// * @create 2018-11-06 16:54
// **/
//@RestController
//@RequestMapping("/api/user")
//public class UserApi extends BaseController {
//
//    @Resource
//    FeignService feignService;
//
//    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
//    Result findById(@PathVariable("id") Long id, HttpServletRequest request){
//        Map<String,Object> map=new HashMap();
//        User user=new User();
//        user.setId(id);
////        user.setLaiaiNumber("150094");
//        user.setMobile("13692280466");
////        user.setNickName("足球");
//        List<Product> productList=feignService.getProductList();
//        map.put("user",user);
//        map.put("productList",productList);
//        map.put("cookies",request.getCookies());
//        return success(map);
//
//    }
//
//    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
//    Result login(@RequestParam(value = "userName") String userName,@RequestParam(value = "passWord") String password)   {
//        if(userName.equals("admin") && password.equals("123456")){
////            User user=new User();
////            user.setId(1L);
////            user.setMobile("13692280466");
////            user.setEmail("619374235@qq.com");
////            user.setUserName("zzj");
////            JwtClaimsParameter jwtClaimsParameter=new JwtClaimsParameter();
////            // 自定义参数
////            List<SelfDefinedParameter> selfDefinedParameterList= new ArrayList<>();
////            SelfDefinedParameter selfDefinedParameter=new SelfDefinedParameter();
////            selfDefinedParameter.setKey("user");
////            selfDefinedParameter.setValue(JSONObject.toJSONString(user));
////            selfDefinedParameterList.add(selfDefinedParameter);
////            jwtClaimsParameter.setSelfDefinedParameterList(selfDefinedParameterList);
////            JwtClaims claims= JwtClaimsUtils.getJwtClaims(jwtClaimsParameter);
////           String token= TokenUtils.jwtsign(claims,TokenUtils.rsaJsonWebKey.getPrivateKey());
//
//            return success();
//        }else{
//            return  success("LoginFail","用户名密码错误");
//        }
//
//    }
//
//    @RequestMapping(value = "/checkToken" ,method = RequestMethod.GET)
//    Result checkToken(@RequestParam(value = "token") String token){
//        JwtClaimsParameter jwtClaimsParameter=new JwtClaimsParameter();
//        String jwt=  TokenUtils.refresh(token,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey(),TokenUtils.rsaJsonWebKey.getPrivateKey());
//        JwtClaims claims=  TokenUtils.checkJwt(jwt,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey());
//        return  success(claims);
//    }
//
//    @RequestMapping(value = "/refreshToken" ,method = RequestMethod.GET)
//    Result refreshToken(@RequestParam(value = "token") String token){
//        JwtClaimsParameter jwtClaimsParameter=new JwtClaimsParameter();
//        String jwt=  TokenUtils.refresh(token,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey(),TokenUtils.rsaJsonWebKey.getPrivateKey());
//        return  success(jwt);
//    }
//
//    @RequestMapping(value = "/getJwtUserInfo" ,method = RequestMethod.GET)
//    Result getJwtUserInfo(@RequestParam(value = "token") String token){
//        JwtClaimsParameter jwtClaimsParameter=new JwtClaimsParameter();
//        String jwt=  TokenUtils.refresh(token,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey(),TokenUtils.rsaJsonWebKey.getPrivateKey());
//        JwtClaims claims=  TokenUtils.checkJwt(jwt,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey());
//        if(claims!=null) {
//            Object obj=claims.getClaimValue("user");
//            // 刷新token
////             jwt=  TokenUtils.refresh(token,jwtClaimsParameter.getIssuer(),jwtClaimsParameter.getAudience(),TokenUtils.rsaJsonWebKey.getPublicKey(),TokenUtils.rsaJsonWebKey.getPrivateKey());
//            Map<String,Object> map=new HashMap<>();
//            map.put("user", JSONObject.parseObject(obj.toString(), User.class));
//            map.put("jwt",jwt);
//            map.put("token",token);
//            map.put("b",token.equals(token));
//            return success(map);
//        }else{
//            return  success("token过期");
//        }
//    }
//
//
//    public static void main(String[] args) {
//
//    }
//
//
//
//}
