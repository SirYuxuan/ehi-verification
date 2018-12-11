package com.yuxuan66.ehi.verification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sir丶雨轩
 * @date 2018/12/11 13:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Verification {

    /**
     * 需要校验的字段列表
     *
     * @return
     */
    Param[] params() default {};
}
