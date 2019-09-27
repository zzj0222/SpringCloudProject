package com.demo.core.validator;/**
 * Created by sprite on 4/30/14.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laiai.core.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValidatorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorHelper.class);


    /**
     * 自定义： 基础类型非空校验，例如：String,BigDecimal,Integer
     *
     * @param message
     * @param value
     * @throws IOException
     */
    public static void notEmptyMsg(Object value,String message) {
        if (null != value) {
            Class<?> type = value.getClass();
            if (type.equals(String.class)) {
                if (null == value.toString() || "".equals(value.toString())) {
                    throw new ValidatorException(message);
                }
            }
        } else {
            throw new ValidatorException(message);
        }
    }

    /**
     * 自定义： 基础类型非空校验，例如：String,BigDecimal,Integer
     *
     * @param filedName
     * @param value
     * @throws IOException
     */
    public static void notEmpty(String filedName, Object value) {
        if (null != value) {
            Class<?> type = value.getClass();
            if (type.equals(String.class)) {
                if (null == value.toString() || "".equals(value.toString())) {
                    throw new ValidatorException(filedName + ":" + "不能为空;");
                }
            }
        } else {
            throw new ValidatorException(filedName + ":" + "不能为空;");
        }
    }

    public static void notEmpty(String field, String[] values) throws IOException {
        if (null == values || values.length == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> validatorMsg = new HashMap<String, String>();
            validatorMsg.put(field, "不能为空");
            String msg = objectMapper.writeValueAsString(validatorMsg);
            throw new ValidatorException(msg);
        }
        if (null != values || values.length == 1) {
            if (values[0] == null || values[0].trim().length() == 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> validatorMsg = new HashMap<String, String>();
                validatorMsg.put(field, "不能为空");
                String msg = objectMapper.writeValueAsString(validatorMsg);
                throw new ValidatorException(msg);
            }
        }
    }


    public static void notEmpty(String[] fields, String[] values) throws IOException {
        if (null != fields && fields.length > 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> validatorMsg = new HashMap<String, String>();
            for (int i = 0; i < fields.length; i++) {
                if (values[i] == null || values[i].trim().length() == 0) {
                    validatorMsg.put(fields[i], "不能为空");
                }
            }
            if (validatorMsg.size() > 0) {
                String msg = objectMapper.writeValueAsString(validatorMsg);
                throw new ValidatorException(msg);
            }

        }
    }

    /**
     * nspaces校验帮助方法，在ctrl中调用，配合hibernate的valid注解，ctrl中需要
     *
     * @param result
     * @throws IOException
     */
    public static void validate(BindingResult result) throws IOException {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, String> validatorMsg = new HashMap<String, String>();
            for (FieldError error : errors) {
                String field = error.getField();

                // 同一字段可能多个约束校验失败，检测是否存在，用逗号连接
                if (validatorMsg.containsKey(field)) {
                    validatorMsg.put(field, validatorMsg.get(field) + " , " + error.getDefaultMessage());
                } else {
                    validatorMsg.put(error.getField(), error.getDefaultMessage());
                }
            }

            String msg = objectMapper.writeValueAsString(validatorMsg);
            if (logger.isDebugEnabled()) {
                logger.debug("校验失败:", msg);
            }

            throw new ValidatorException(msg);
        }
    }


    /**
     * nspaces校验帮助方法，在ctrl中调用，配合hibernate的valid注解，ctrl中需要
     *
     * @param result
     * @param ignoreFields 忽略字段列表，逗号分隔
     * @throws IOException
     * @throws IOException
     */
    public static void validate(BindingResult result, String ignoreFields) throws IOException {
        if (result.hasErrors()) {

            // 忽略列表
            String[] ignoreFieldArr = ignoreFields.split(",");
            Map<String, String> ignoreFieldMap = new HashMap<String, String>();
            for (String field : ignoreFieldArr) {
                ignoreFieldMap.put(field, field);
            }
            List<FieldError> errors = result.getFieldErrors();
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, String> validatorMsg = new HashMap<String, String>();
            for (FieldError error : errors) {
                String field = error.getField();
                if (ignoreFieldMap.containsKey(field)) {
                    continue;
                }
                // 同一字段可能多个约束校验失败，检测是否存在，用逗号连接
                if (validatorMsg.containsKey(field)) {
                    validatorMsg.put(field, validatorMsg.get(field) + " , " + error.getDefaultMessage());
                } else {
                    validatorMsg.put(error.getField(), error.getDefaultMessage());
                }
            }

            String msg = objectMapper.writeValueAsString(validatorMsg);
            logger.debug("校验失败:", msg);
            /**
             * 为空则表示校验出错的全是例外
             */
            if (!validatorMsg.isEmpty()) {
                throw new ValidatorException(msg);
            }


        }
    }

}
