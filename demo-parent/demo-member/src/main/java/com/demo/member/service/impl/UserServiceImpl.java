//package com.demo.member.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.demo.common.CacheConstant;
//import com.demo.member.mapper.UserMapper;
//import com.demo.member.model.User;
//import com.demo.member.service.UserService;
//import com.demo.member.vo.UserLoginVo;
//import com.demo.utils.common.RedisUtils;
//import com.laiai.core.Result;
//import com.laiai.core.abstractbean.AbstractService;
//import com.laiai.core.exception.BusinessException;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import tk.mybatis.mapper.entity.Condition;
//import tk.mybatis.mapper.entity.Example;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//
///**
// * Created by CodeGenerator on 2019-03-11 10:59:37.
// */
//@Service
//public class UserServiceImpl extends AbstractService<User> implements UserService {
//    @Resource
//    private UserMapper userMapper;
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
//    @Override
//    public Result userLogin(UserLoginVo userLoginVo) {
//        User user = getUserInfo(userLoginVo.getMobile());
//        if (user == null) {
//            throw new BusinessException("-1", "无用户注册信息，请先进行注册");
//        }
//        if (user.getPassword() == null || !user.getPassword().equals(userLoginVo.getUserPass().toUpperCase())) {
//            throw new BusinessException("-1", "手机号或密码错误，请尝试用验证码登录");
//        }
//
//        if (user.getMobileArea() == null || user.getMobileArea().length() <= 0) {
//            user.setMobileArea("86");
//        }
////        Result result = authService.autoLogin(user, ParameterUtils.getSource());
//        return null;
//    }
//
//    /**
//     * 手机号码加验证码登录
//     *
//     * @param userLoginVo
//     * @return
//     */
//    @Override
//    public Result userLoginByMobile(UserLoginVo userLoginVo) {
//        return null;
//    }
//
//    @Override
//    public List<User> batchImport(MultipartFile file) {
//        // 打开Excel文件对象
//        Workbook workbook=null;
//        String fileName=file.getOriginalFilename();
//        if(fileName.endsWith("XLS")){
//            try {
//                workbook= new HSSFWorkbook(file.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else if(fileName.endsWith("XLSX")){
//            try {
//                workbook= new XSSFWorkbook(file.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else{
//            throw new  BusinessException("-1","文件不是Excel文件");
//        }
//
//        // 读取Sheet1
//        Sheet sheet=workbook.getSheet("Sheet1");
//        int rows=sheet.getLastRowNum();
//        if(rows==0){
//            throw new  BusinessException("-1","请填写数据");
//        }
//        List<User> users=new ArrayList<>();
//        for(int i=1;i<=rows+1;i++){
//            Row  row=sheet.getRow(i);
//            if(row!=null){
//
//                Iterator<Cell> cellIterator =  row.cellIterator();
//                if(cellIterator.hasNext()){
//                    Cell cell=cellIterator.next();
//
//                    System.out.println(cell.getStringCellValue());
//                }
//                // 新增用户
//                User user=new User();
//                user.setUserName("");
//                user.setEmail("");
//                user.setMobile("");
//                user.setMobileArea("");
//                user.setPassword("");
//                user.setSalt("");
//
//                // 查询用户是否存在
//
//
//                users.add(user);
//            }
//        }
//
//        return users;
//    }
//
//    private String getCellValue(Cell cell){
//        String value="";
//        if(cell!=null){
//            // 判断数据类型
//            switch(cell.getCellType()){
//                case HSSFCell.CELL_TYPE_NUMERIC:
//
//            }
//
//        }
//        return  value.trim();
//    }
//
//
//    /**
//     * 查询用户登录缓存
//     *
//     * @param mobile
//     * @return
//     */
//    private User getUserInfo(String mobile) {
//        Object object = redisUtils.hget(CacheConstant.MEMBER.USER_LOGIN_LIST, mobile);
//        if (object == null) {
//            User user = findBy("mobile",mobile);
//            if (user!=null) {
//                redisUtils.hset(CacheConstant.MEMBER.USER_LOGIN_LIST, mobile, JSONObject.toJSONString(user));
//                return user;
//            } else {
//                return null;
//            }
//        } else {
//            return JSONObject.parseObject(object.toString(), User.class);
//        }
//    }
//
//    /**
//     * 清理用户登录信息
//     * 手机号/邮箱/密码变更时清理缓存
//     * @param mobile
//     */
//    public void removeUserLoginCache(String mobile) {
//        redisUtils.hremove(CacheConstant.MEMBER.USER_LOGIN_LIST, mobile + "");
//    }
//}
