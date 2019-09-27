package com.demo.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zzj
 * @create 2018-11-29 18:59
 **/
public class JsonUtils {
    public  static  String getMessage(String message){
        if(isJsonObject(message)) {
            Object obj= JSONObject.parse(message);
            Map<String,Object> map=(Map<String, Object>) obj;
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                //  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                message=entry.getValue().toString();
//            }

//            for (String key : map.keySet()) {
//                System.out.println("Key = " + key);
//            }
            //遍历map中的值
            for (Object value : map.values()) {
                System.out.println("Value = " + value);
                message=value.toString();
            }
            return  message;
        }else{
            return  message;
        }




    }
    /**
     * 判断是JsonObject
     * @param obj
     * @return
     */
    public static boolean isJsonObject(Object obj){
        String content = obj.toString();
        try {
            JSONObject.parseObject(content);
            if (content.startsWith("{")) {
                return  true;
            }else {
                return  false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是JsonArray
     * @param obj
     * @return
     */
    public static boolean isJsonArray(Object obj){
        String content = obj.toString();
        try {
            JSONArray.parseArray(content);
            if (content.startsWith("[")) {
                return  true;
            }else {
                return  false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String jsonStr = "{\"key\":\"key\",\"value\":\"value\",\"productId\":\"1\"}";

        System.out.println("无序遍历结果："+System.currentTimeMillis());
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(System.currentTimeMillis());
        System.out.println("-------------------");
        System.out.println("有序遍历结果："+System.currentTimeMillis());
        LinkedHashMap<String, String> jsonMap = JSON.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, String>>() {
        });
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(System.currentTimeMillis());

    }


}
