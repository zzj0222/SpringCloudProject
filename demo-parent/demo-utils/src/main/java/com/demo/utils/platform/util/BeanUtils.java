package com.demo.utils.platform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.PropertyAccessor;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author zheng.shk
 */
public class BeanUtils {

    private static final Log logger = LogFactory.getLog(BeanUtils.class);
    private static Mapper mapper = new DozerBeanMapper();
    private static final String JAVA_CLASS_PROPERTY = "class";


    /**
     * @param sourceObj 源对象 obj 目标对象
     * @throws {异常描述}
     * @description {对象转换,如mapToObject}
     */
    public static <T> T mapper(Object sourceObj, Class<T> obj) {
        if (null == sourceObj) {
            return null;
        } else {
            return mapper.map(sourceObj, obj);
        }
    }

    /**
     * @param object
     * @return
     * @throws {异常描述}
     * @authordescription {entity to type to map}
     */
    public static Map<String, Object> entityToMap(Object object, Class<?> obj) {
        return objToMap(mapper(object, obj));
    }

    /**
     * Convert a Object to a tree {@link Map} will erase class information.
     * 转换包含复杂类型对象
     */
    public static final Map<String, Object> toMap(Object object) {
        return toMap(object, JAVA_CLASS_PROPERTY);
    }

    /**
     * @param object
     * @param ignoreProperties
     * @return
     * @throws {异常描述}
     * @description {转换对象，并过滤掉不需要转换属性}
     */
    public static final Map<String, Object> toMap(Object object, String... ignoreProperties) {
        return toMap(object, new HashSet<Object>(), false, ignoreProperties);
    }

    /**
     * Convert a java bean to flat map(JUMP data map structure) will erase class
     * information. 转换不包含复杂类型对象
     */
    @SuppressWarnings("unused")
    private static final Map<String, Object> toFlatMap(Object object) {
        return toFlatMap(object, JAVA_CLASS_PROPERTY);
    }

    /**
     * Convert a java bean to flat map(JUMP data map structure).
     */
    public static final Map<String, Object> toFlatMap(Object object, String... ignoreProperties) {
        return toMap(object, new HashSet<Object>(), true, ignoreProperties);
    }

    /**
     * @param object 源对象
     * @return
     * @throws {异常描述}
     * @description {ObjToMap}
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objToMap(Object object) {
        return mapper(object, Map.class);
    }

    /**
     * 转换Collection中对象的类型.
     */

    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();
        if (null != sourceList) {
            for (Object sourceObject : sourceList) {
                T destinationObject = mapper.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }

    /**
     * 转换maplist 为 objectList
     *
     * @param mapList
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> List<T> mapListToObjectList(List<Map<String, Object>> mapList, Class<T> destinationClass) {
        List<T> list = new ArrayList<T>();
        for (Map<String, Object> map : mapList) {
            list.add(mapToObject(map, destinationClass));
        }
        return list;
    }

    /**
     * 转换Collection中对象的类型为Map.
     */

    public static List<Map<String, Object>> mapperListToMap(Collection<?> sourceList) {
        List<Map<String, Object>> destinationList = new ArrayList<Map<String, Object>>();
        if (null != sourceList) {
            for (Object sourceObject : sourceList) {
                Map<String, Object> map = toMap(sourceObject);
                destinationList.add(map);
            }
        }

        return destinationList;
    }


    /**
     * 将对象类型的数据结构变更为对象类型的数据结构
     *
     * @param srcObject
     * @return Map
     */
    public static <T> T objectToObj(Object srcObject, Class<T> obj) {
        T destObj = null;
        try {
            destObj = obj.cast(obj.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // //映射子类的属性
        Field[] fs = obj.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            setFieldValue(destObj, fs[i].getName(), obj, getFieldValue(srcObject, fs[i].getName(), Object.class));
        }
        return destObj;
    }


    /**
     * 将map类型的数据结构变更为object类型的数据结构
     *
     * @param m
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> m, Class<T> obj) {
        if(null==m){
            return  null;
        }
        try {
            T destObj = obj.cast(obj.newInstance());
            for (Iterator<?> iterator = m.entrySet().iterator(); iterator
                    .hasNext(); ) {
                Entry<?, ?> o = (Entry<?, ?>) iterator.next();
                destObj.getClass();
                setFieldValue(destObj, (String) o.getKey(), obj, o.getValue());
            }

            return destObj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isSimpleValueType(Class<?> type) {
        if (type.equals(String.class)) {
            return true;
        } else if (type.equals(BigDecimal.class)) {
            return true;
        } else if (type.equals(Timestamp.class)) {
            return true;
        } else if (type.equals(Date.class)) {
            return true;
        } else if (type.equals(java.sql.Date.class)) {
            return true;
        } else if (type.equals(java.sql.Time.class)) {
            return true;
        } else if (type.equals(Integer.class)) {
            return true;
        } else if (type.equals(Long.class)) {
            return true;
        } else if (type.equals(Float.class)) {
            return true;
        } else if (type.equals(Double.class)) {
            return true;
        } else if (type.equals(Short.class)) {
            return true;
        } else if (type.equals(Byte.class)) {
            return true;
        } else if (type.equals(Boolean.class)) {
            return true;
        } else {

            return false;
        }
    }

    private static final Map<String, Object> toMap(Object object,
                                                   Set<Object> convertContext, boolean flat, String[] ignoreProperties) {
        return toMap(null, object, convertContext, flat, ignoreProperties);
    }

    private static final Map<String, Object> toMap(String key, Object object,
                                                   Set<Object> convertContext, boolean flat, String[] ignoreProperties) {

        if (null == object) {
            return null;
        }

        if (convertContext.contains(object)) {
            return null;
        }
        Class<?> type = object.getClass();
        if (isSimpleValueType(type)) {
            throw new RuntimeException("Simple type[" + type.getName()
                    + "] can not to map!");
        }
        convertContext.add(object);
        Map<String, Object> desc = new HashMap<String, Object>();
        PropertyDescriptor[] pds = org.springframework.beans.BeanUtils
                .getPropertyDescriptors(type);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays
                .asList(ignoreProperties) : null;

        for (PropertyDescriptor pd : pds) {
            // ignore property.
            if (null != ignoreProperties && ignoreList.contains(pd.getName())) {
                continue;
            }
            Method readMethod = pd.getReadMethod();
            if (readMethod != null) {
                desc.put(pd.getName(),
                        ReflectionUtils.invokeMethod(readMethod, object));
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();

        for (String innerKey : desc.keySet()) {
            Object value = desc.get(innerKey);
            if (flat) {
                String subKey = (key == null) ? innerKey
                        : (key + PropertyAccessor.NESTED_PROPERTY_SEPARATOR + innerKey);
                Map<String, Object> flatMap = descFlat(subKey, value,
                        convertContext, ignoreProperties);
                if (null != flatMap) {
                    resultMap.putAll(flatMap);
                }

            } else {
                resultMap.put(innerKey,
                        desc(value, convertContext, ignoreProperties));
            }
        }
        return resultMap;
    }

    private static Map<String, Object> descFlat(String key, Object object,
                                                Set<Object> context, String[] ignoreProperties) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (null == object || context.contains(object)) {
            result.put(key, null);
            return result;
        }
        Class<?> type = object.getClass();
        // if the target property type is simple value or array.
        if (isSimpleValueType(type) || type.isArray()) {
            result.put(key, object);
            return result;
        }
        if (object instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> coll = (Collection<Object>) object;
            List<Object> resultList = new ArrayList<Object>(coll.size());
            for (Object o : coll) {
                Object value = descFlat(null, o, context, ignoreProperties);
                resultList.add(value);
            }
            result.put(key, resultList);
            return result;

        } else if (object instanceof Map) {
            Map<?, ?> mapValue = (Map<?, ?>) object;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (Object innerKey : mapValue.keySet()) {
                Object value = mapValue.get(innerKey);
                String nextKey = key
                        + PropertyAccessor.NESTED_PROPERTY_SEPARATOR + innerKey;

                Map<String, Object> nestedDesc = descFlat(nextKey, value,
                        context, ignoreProperties);
                resultMap.putAll(nestedDesc);
            }
            return resultMap;
        }// other type.
        else {
            return toMap(key, object, context, true, ignoreProperties);
        }
    }

    private static Object desc(Object object, Set<Object> context,
                               String[] ignoreProperties) {
        if (null == object || context.contains(object)) {
            return null;
        }
        Class<?> type = object.getClass();
        if (isSimpleValueType(type) || type.isArray()) {
            return object;
        }

        if (object instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> coll = (Collection<Object>) object;
            List<Object> resultList = new ArrayList<Object>(coll.size());
            for (Object o : coll) {
                Object value = desc(o, context, ignoreProperties);
                resultList.add(value);
            }
            return resultList;

        } else if (object instanceof Map) {
            Map<?, ?> mapValue = (Map<?, ?>) object;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (Object key : mapValue.keySet()) {
                Object value = mapValue.get(key);
                resultMap.put(key.toString(),
                        desc(value, context, ignoreProperties));
            }
            return resultMap;
        }// other type.
        else {
            return toMap(object, context, false, ignoreProperties);
        }
    }

    public static <T> Object setFieldValueDefault(Object target, String fname,
                                                  Class<T> ftype, Object fvalue) {
        if (target == null
                || fname == null
                || "".equals(fname)
                || (fvalue != null && !ftype
                .isAssignableFrom(fvalue.getClass()))) {
            return target;
        }
        Class<? extends Object> clazz = target.getClass();
        Field[] fs = clazz.getDeclaredFields();
        try {
            for (int i = 0; i < fs.length; i++) {
                if (fname.toLowerCase().equals(fs[i].getName().toLowerCase())) {
                    Method method = clazz.getDeclaredMethod(
                            "set"
                                    + Character.toUpperCase(fs[i].getName()
                                    .charAt(0))
                                    + fs[i].getName().substring(1),
                            String.class);
                    if (!Modifier.isPublic(method.getModifiers())) {
                        method.setAccessible(true);
                    }
                    method.invoke(target, fvalue);
                }
            }
        } catch (Exception me) {
            try {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                field.set(target, fvalue);
            } catch (SecurityException e) {
//                logger.debug("SecurityException:" + e.getMessage());
            } catch (NoSuchFieldException e) {
//                logger.debug("NoSuchFieldException:" + e.getMessage());
            } catch (IllegalArgumentException e) {
//                logger.debug("IllegalArgumentException:" + e.getMessage());
            } catch (IllegalAccessException e) {
//                logger.debug("IllegalAccessException:" + e.getMessage());
            }
        }
        return target;
    }

    // 区分大小写

    public static <T> Object setFieldValue(Object target, String fname,
                                           Class<T> ftype, Object fvalue) {
        if (target == null || fname == null || "".equals(fname)
                || (fvalue == null)) {
            return target;
        }
        Class<? extends Object> clazz = target.getClass();
        try {
            Field field = clazz.getDeclaredField(fname);
            Class<?> type = field.getType();
            Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), type);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            if (field.getType().equals(Date.class)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse((String) fvalue);
                fvalue = date;
            } else if (field.getType().equals(Byte.class)) {
                fvalue = ((Integer) fvalue).byteValue();
            } else if (field.getType().equals(Long.class)) {
                fvalue = ((Integer)fvalue).longValue();
            } else if (field.getType().equals(Integer.class)) {
                fvalue = (Integer)fvalue;
            } else if (field.getType().equals(BigDecimal.class)) {
                fvalue = new BigDecimal(fvalue.toString());
            }
            method.invoke(target, fvalue);
        } catch (Exception me) {
            try {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                field.set(target, fvalue);
            } catch (Exception e) {
                // 映射父类属性
                Class<? extends Object> parentClass = clazz.getSuperclass();
                Field field;
                try {
                    field = parentClass.getDeclaredField(fname);
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    Object parentObj = parentClass.newInstance();
                    field.set(parentObj, fvalue);
                    Object obj = field.get(parentObj);
                    field.set(target, obj);

                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (NoSuchFieldException e1) {
                    logger.debug("NoSuchFieldException:" + e.getMessage());
                } catch (InstantiationException e2) {
                    e2.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                }
            }

        }
        return target;
    }

    public static <T> Object getFieldValue(Object target, String fname,
                                           Class<T> ftype) {
        if (target == null || fname == null || "".equals(fname)) {
            return null;
        }
        Class<? extends Object> clazz = target.getClass();
        try {
            Method method = clazz.getDeclaredMethod(
                    "get" + Character.toUpperCase(fname.charAt(0))
                            + fname.substring(1), ftype);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            return method.invoke(target);
        } catch (Exception me) {
            try {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                return field.get(target);
            } catch (Exception e) {
                // 映射父类属性
                Class<? extends Object> parentClass = clazz.getSuperclass();
                Field field;
                try {
                    field = parentClass.getDeclaredField(fname);
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    return field.get(target);

                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (NoSuchFieldException e1) {
                    logger.debug("NoSuchFieldException:" + e.getMessage());
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    logger.debug("IllegalAccessException:" + e1.getMessage());
                }
            }

        }
        return null;
    }


}
