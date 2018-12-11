package com.yuxuan66.ehi.verification.utils;

import com.yuxuan66.ehi.verification.exception.VerificationException;

import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 13:53
 */
public class ReflectUtil {


    public static Object getVal(Object object, String fieldName) {
        Method method = null;
        try {
            method = object.getClass().getMethod("get" + StrUtil.fristUp(fieldName));
        } catch (NoSuchMethodException e) {
            throw new VerificationException("Can't find field '" + fieldName + "' getter method.");
        }
        try {
            return method.invoke(object);
        } catch (ReflectiveOperationException e) {
            throw new VerificationException("invoke method get" + StrUtil.fristUp(fieldName) + " error." + e.getMessage());
        }
    }
}
