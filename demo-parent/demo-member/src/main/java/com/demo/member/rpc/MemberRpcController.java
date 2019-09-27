//package com.demo.member.rpc;
//
//
////import com.demo.member.grpc.service.GrpcClientService;
////import com.alipay.sofa.rpc.config.ConsumerConfig;
//import com.demo.member.model.User;
//import com.demo.member.service.UserService;
//import com.demo.product.model.Product;
//import com.laiai.core.BaseController;
//import com.laiai.core.Result;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 提供给其它内部模块调用的接口
// * 命名规范:模块名称＋Rpc+Controller结尾(模块名称要跟数据库表名一致)
// * @author zzj
// * @create 2018-11-06 15:28
// **/
//@RestController
//@RequestMapping("/api/memberRpc")
//public class MemberRpcController extends BaseController {
//
////    @Resource
////    GrpcClientService grpcClientService;
////
////    @RequestMapping("/hello")
////    public String  printMessage(@RequestParam(defaultValue = "Spring Cloud") String name){
////        return  grpcClientService.sendMessage(name);
////    }
//
//    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
//    Result findById(@PathVariable("id") Long id){
//        Map<String,Object> map=new HashMap();
//        User user=new User();
//        user.setId(id);
////        user.setLaiaiNumber("150094");
//        user.setMobile("13692280466");
////        user.setNickName("足球");
//
//        map.put("user",user);
//
////        ConsumerConfig<UserService> consumerConfig=new ConsumerConfig<UserService>()
////                .setInterfaceId(UserService.class.getName()).setProtocol("bolt").setDirectUrl("bolt:localhost:9012").setDisconnectTimeout(10*1000);
////        UserService userService=consumerConfig.refer();
////        System.out.println(userService.findAll());
//        return success(map);
//
//    }
//
//}
