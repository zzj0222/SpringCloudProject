package com.demo.utils.jwt;/**
 * @Description
 * @author zhangzhenjiang
 * @date 2019/3/14 9:45
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzj
 * @create 2019-03-14 9:45
 **/
public class SelfDefinedParameterUtils {

    public  static List<SelfDefinedParameter> setSelfDefinedParameter(Object obj){
        List<SelfDefinedParameter> selfDefinedParameterList=new ArrayList<>();
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            try {
                SelfDefinedParameter selfDefinedParameter=new SelfDefinedParameter();
                selfDefinedParameter.setKey(f.getName());
                selfDefinedParameter.setValue(f.get(obj));
                selfDefinedParameterList.add(selfDefinedParameter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return   selfDefinedParameterList;

    }
}
