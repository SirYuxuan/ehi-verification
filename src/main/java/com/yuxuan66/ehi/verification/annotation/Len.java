package com.yuxuan66.ehi.verification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 长度校验
 * @author Sir丶雨轩
 * @date 2018/12/11 13:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER})
public @interface Len {
    /**
     * 最小长度
     * @return
     */
    int min() default 0;

    /**
     * 最大长度
     * @return
     */
    int max() default 0;
}
