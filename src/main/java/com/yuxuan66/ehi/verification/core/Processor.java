package com.yuxuan66.ehi.verification.core;

/**
 * 处理器接口
 * @author Sir丶雨轩
 * @date 2018/12/11 13:06
 */
public interface Processor {

    String page();

    String ajax(boolean check,String msg);
}
