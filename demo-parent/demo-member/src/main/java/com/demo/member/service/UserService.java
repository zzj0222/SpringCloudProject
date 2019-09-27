//package com.demo.member.service;
//import com.demo.member.model.User;
//import com.demo.member.vo.UserLoginVo;
//import com.laiai.core.Result;
//import com.laiai.core.abstractbean.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//
///**
// * Created by CodeGenerator on 2019-03-11 10:59:37.
// */
//public interface UserService extends Service<User> {
//
//    /**
//     * 用户登录
//     *
//     * @param userLoginVo
//     * @return
//     */
//    Result userLogin(UserLoginVo userLoginVo);
//
//    /**
//     * 手机号码加验证码登录 验证来艾账户
//     *
//     * @param userLoginVo
//     * @return
//     */
//    Result userLoginByMobile(UserLoginVo userLoginVo);
//
//    /**
//     * 批量导入
//     */
//    List<User> batchImport(MultipartFile file);
//
//}
