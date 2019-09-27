package com.demo.utils.common;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/3/7 14:12
 */

import java.util.Random;

/**
 * @author zzj
 * @create 2019-03-07 14:12
 **/
public class RandomUtils {
    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) { // length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        System.out.println();
        System.out.println(getRandomString(16));
        System.out.println(System.currentTimeMillis()-start);
    }

}
