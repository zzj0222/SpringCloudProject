//package com.demo.member.swagger;
//import com.laiai.core.Result;
//import com.demo.member.model.User;
//import io.swagger.annotations.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.validation.BindingResult;
//import java.io.IOException;
//
//
//
///**
// * Created by CodeGenerator on 2019-03-11 10:59:37.
// */
//@Api(tags = "用户表管理")
//public interface UserSwagger {
//
//     @ApiOperation(value="新增", notes="新增User")
//     public Result add(User user, BindingResult result) throws IOException;
//
//
//     @ApiOperation(value="删除", notes="以主键id删除User")
//     public Result delete(@PathVariable Long id);
//
//
//     @ApiOperation(value="更新", notes="更新user")
//     public Result update(@RequestBody User user);
//
//
//     @ApiOperation(value="详情", notes="获取详情")
//     public Result view(@PathVariable Long id);
//
//
//     @ApiOperation(value="按条件及分页查找", notes="按条件及分页查找")
//     public Result pageList(@RequestBody User obj, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize);
//}
