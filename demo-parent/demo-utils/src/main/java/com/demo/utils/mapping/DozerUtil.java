//package com.demo.utils.mapping;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
////import org.dozer.DozerBeanMapper;
////import org.dozer.Mapper;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * 对象映射工具类
// * @author zzj
// * @create 2018-11-29 17:16
// **/
//public class DozerUtil {
////    private static final Log logger = LogFactory.getLog(DozerUtil.class);
////    private static Mapper mapper = new DozerBeanMapper();
////    private static final String JAVA_CLASS_PROPERTY = "class";
//
//    public DozerUtil() {
//    }
//
//
//    public static <T> T objectToObj(Object srcObject, Class<T> obj)  {
//        Object destObj = null;
//        try {
//            destObj = obj.cast(obj.newInstance());
//        } catch (InstantiationException var5) {
//            var5.printStackTrace();
//        } catch (IllegalAccessException var6) {
//            var6.printStackTrace();
//        }
//
//        Field[] fs = obj.getDeclaredFields();
//
//        for(int i = 0; i < fs.length; ++i) {
//            setFieldValue(destObj, fs[i].getName(), obj, getFieldValue(srcObject, fs[i].getName(), Object.class));
//        }
//
//        return null;
//    }
//
//    public static <T> T mapToObject(Map<String, Object> m, Class<T> obj) {
//        if (null == m) {
//            return null;
//        } else {
//            try {
//                T destObj = obj.cast(obj.newInstance());
//                Iterator iterator = m.entrySet().iterator();
//
//                while(iterator.hasNext()) {
//                    Map.Entry<?, ?> o = (Map.Entry)iterator.next();
//                    destObj.getClass();
//                    setFieldValue(destObj, (String)o.getKey(), obj, o.getValue());
//                }
//
//                return destObj;
//            } catch (InstantiationException var5) {
//                var5.printStackTrace();
//            } catch (IllegalAccessException var6) {
//                var6.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//    public static <T> Object setFieldValue(Object target, String fname, Class<T> ftype, Object fvalue) {
//        if (target != null && fname != null && !"".equals(fname) && fvalue != null) {
//            Class clazz = target.getClass();
//
//            try {
//                Field field = clazz.getDeclaredField(fname);
//                Class<?> type = field.getType();
//                Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), type);
//                if (!Modifier.isPublic(method.getModifiers())) {
//                    method.setAccessible(true);
//                }
//
//                if (field.getType().equals(Date.class)) {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = dateFormat.parse((String)fvalue);
//                    fvalue = date;
//                } else if (field.getType().equals(Byte.class)) {
//                    fvalue = ((Integer)fvalue).byteValue();
//                } else if (field.getType().equals(Long.class)) {
//                    fvalue = ((Integer)fvalue).longValue();
//                } else if (field.getType().equals(Integer.class)) {
//                    fvalue = (Integer)fvalue;
//                } else if (field.getType().equals(BigDecimal.class)) {
//                    fvalue = new BigDecimal(fvalue.toString());
//                }
//
//                method.invoke(target, fvalue);
//            } catch (Exception var17) {
//                try {
//                    Field field = clazz.getDeclaredField(fname);
//                    if (!Modifier.isPublic(field.getModifiers())) {
//                        field.setAccessible(true);
//                    }
//
//                    field.set(target, fvalue);
//                } catch (Exception var16) {
//                    Class parentClass = clazz.getSuperclass();
//
//                    try {
//                        Field field = parentClass.getDeclaredField(fname);
//                        if (!Modifier.isPublic(field.getModifiers())) {
//                            field.setAccessible(true);
//                        }
//
//                        Object parentObj = parentClass.newInstance();
//                        field.set(parentObj, fvalue);
//                        Object obj = field.get(parentObj);
//                        field.set(target, obj);
//                    } catch (SecurityException var11) {
//                        var11.printStackTrace();
//                    } catch (NoSuchFieldException var12) {
//                        logger.debug("NoSuchFieldException:" + var16.getMessage());
//                    } catch (InstantiationException var13) {
//                        var13.printStackTrace();
//                    } catch (IllegalAccessException var14) {
//                        var14.printStackTrace();
//                    } catch (IllegalArgumentException var15) {
//                        var15.printStackTrace();
//                    }
//                }
//            }
//
//            return target;
//        } else {
//            return target;
//        }
//    }
//    public static <T> Object getFieldValue(Object target, String fname, Class<T> ftype) {
//        if (target != null && fname != null && !"".equals(fname)) {
//            Class clazz = target.getClass();
//
//            try {
//                Method method = clazz.getDeclaredMethod("get" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), ftype);
//                if (!Modifier.isPublic(method.getModifiers())) {
//                    method.setAccessible(true);
//                }
//
//                return method.invoke(target);
//            } catch (Exception var14) {
//                try {
//                    Field field = clazz.getDeclaredField(fname);
//                    if (!Modifier.isPublic(field.getModifiers())) {
//                        field.setAccessible(true);
//                    }
//
//                    return field.get(target);
//                } catch (Exception var13) {
//                    Class parentClass = clazz.getSuperclass();
//
//                    try {
//                        Field field = parentClass.getDeclaredField(fname);
//                        if (!Modifier.isPublic(field.getModifiers())) {
//                            field.setAccessible(true);
//                        }
//
//                        return field.get(target);
//                    } catch (SecurityException var9) {
//                        var9.printStackTrace();
//                    } catch (NoSuchFieldException var10) {
//                        logger.debug("NoSuchFieldException:" + var13.getMessage());
//                    } catch (IllegalArgumentException var11) {
//                        var11.printStackTrace();
//                    } catch (IllegalAccessException var12) {
//                        logger.debug("IllegalAccessException:" + var12.getMessage());
//                    }
//
//                    return null;
//                }
//            }
//        } else {
//            return null;
//        }
//    }
//
//}
