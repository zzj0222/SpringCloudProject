package com.demo.utils.common;

import java.math.BigDecimal;

/**
 * 金额元分之间转换工具类
 * @author zzj
 * @create 2019-01-08 9:15
 **/
public class AmountUtils {
    /**
     * 将分为单位的转换为元 （除100）
     * @param amount
     * @return
     */
    public static BigDecimal changeF2Y(Long amount)  {

        return BigDecimal.valueOf(amount).divide(new BigDecimal(100));
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static Long changeY2F(BigDecimal amount){
        return amount.multiply(new BigDecimal(100)).longValue();
    }


    public static void main(String[] args) {
            System.out.println(changeF2Y(105L));
            System.out.println(changeY2F(new BigDecimal(3.25)));
    }

}
