//package com.demo.member.api;/**
// * @Description
// * @author zhangzhenjiang
// * @date 2019/3/15 11:12
// */
//
//
////import com.demo.member.service.UserService;
//import com.demo.member.vo.UserLoginVo;
//
//import com.jiayi.common.utils.RedisUtils;
//import com.laiai.core.BaseController;
//import com.laiai.core.Result;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zzj
// * @create 2019-03-15 11:12
// **/
//@RestController
//@RequestMapping("/api/login")
//public class LoginApi extends BaseController {
//
//    @Resource
//    private  UserService userService;
//
//
//
//    @Resource
//    private RedisUtils redisUtils;
//
//    /**
//     * 用户登录
//     *
//     * @param userLoginVo
//     * @return
//     */
//    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
//    public Result userLogin(@RequestBody UserLoginVo userLoginVo) {
//        return userService.userLogin(userLoginVo);
//    }
//
//
//    /**
//     * 手机号码加验证码登录
//     *
//     * @param userLoginVo
//     * @return
//     */
//    @RequestMapping(value = "/userLoginByMobile", method = RequestMethod.POST)
//    public Result userLoginByMobile(@RequestBody UserLoginVo userLoginVo) {
//        return userService.userLoginByMobile(userLoginVo);
//    }
//
//    /**
//     * test apollo
//     */
//    @RequestMapping(value = "/testapollo", method = RequestMethod.GET)
//    public Result testApollo() {
//        Map<String,Object> map=new HashMap<>();
//        redisUtils.set("mobile","13692280466");
//
//     return  success(map);
//    }
//}
