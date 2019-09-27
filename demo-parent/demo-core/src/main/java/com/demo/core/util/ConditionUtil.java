package com.demo.core.util;

import java.lang.reflect.Field;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * 查询条件工具类 类的反射机制获取属性和值
 * @author zzj
 * @create 2018-11-08 10:02
 **/
public class ConditionUtil {

    public static <T> Condition getCondition(Object obj) {
        Class cls = obj.getClass();
        Condition condition = new Condition(cls);
        Example.Criteria criteria = condition.createCriteria();
        Field[] fields = cls.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            Field f = fields[i];
            f.setAccessible(true);

            try {
                String filedName = f.getName();
                Object value = f.get(obj);
                if (null != value && !"".equals(value)) {
                    criteria.andEqualTo(filedName, value);
                }
            } catch (Exception var10) {
                ;
            }
        }

        return condition;
    }
}
