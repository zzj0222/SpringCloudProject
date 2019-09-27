package com.demo.common;

/**
 * 缓存常量
 * @author zzj
 * @create 2019-03-15 11:58
 **/
public class CacheConstant {
    public static class MEMBER {

        /**
         * redis 获取验证码key前缀
         */
        public static final String VERIFICATION_CODE_LIST = "cache:member:verification_code:list:";

        /**
         * redis 用户登录列表缓存key
         */
        public static final String USER_LOGIN_LIST = "cache:member:user_login_list:";
        /**
         * redis 用户登录信息缓存key
         */
        public static final String USER_LIST = "cache:member:user_list:";
    }
}
