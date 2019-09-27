package com.demo.utils.jwt;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/2/20 14:18
 */

import java.util.UUID;

/**
 * @author zzj
 * @create 2019-02-20 14:18
 **/
public class UUIDUtils {

    /**
     * 使用 UUID 生成的长度至少32位的随机字符串，可以全为数字或数字+字母
     */
    public  static  String getKeyId(){
        String keyId = UUID.randomUUID().toString().replaceAll("-", "");
        return  keyId;
    }

    public static void main(String[] args) {
        System.out.println(getKeyId());
        System.out.println(getKeyId().length());
    }

}
