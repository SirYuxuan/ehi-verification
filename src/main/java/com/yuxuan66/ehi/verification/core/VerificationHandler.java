package com.yuxuan66.ehi.verification.core;


import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器用处理过程
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 23:04
 */
public interface VerificationHandler {

    /**
     * ajax请求处理 输出错误信息
     *
     * @param verificationResult 校验结果
     * @return 输出的JSON
     */

    String ajax(VerificationResult verificationResult);

    /**
     * 页面请求处理 转发到指定地址
     *
     * @param verificationResult 校验结果
     * @param request            request操作对象
     * @return 访问的请求地址
     */
    String page(VerificationResult verificationResult, HttpServletRequest request);
}
