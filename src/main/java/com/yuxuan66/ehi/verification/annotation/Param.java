package com.yuxuan66.ehi.verification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验规则,如果配置多个规则
 * 顺序校验 第一个不通过则不会校验第二个,默认校验全部配置规则
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 13:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Param {
    /**
     * 字段名称
     *
     * @return
     */
    String value();

    /**
     * 是否需要 默认为需要
     *
     * @return
     */
    boolean require() default true;

    /**
     * asFor 另一个字段名称,如果两个字段内容不一样则不通过
     *
     * @return
     */
    String asFor() default "";

    /**
     * NullFor 另一个字段名称,如果两个字段内容都为空则不通过
     *
     * @return
     */
    String nullFor() default "";

    /**
     * 字段为空的错误提示,默认使用全局提示
     *
     * @return
     */
    String nullMsg() default "";

    /**
     * 字段校验不通过的错误提示,默认使用全局提示
     *
     * @return
     */
    String errorMsg() default "";

    /**
     * 用来校验的正则
     *
     * @return
     */
    String pattern() default "";


    /**
     * 长度校验,默认不会进行校验
     *
     * @return
     */
    Len len() default @Len(min = -1, max = -1);



}
