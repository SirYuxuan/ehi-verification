package com.yuxuan66.ehi.verification.interceptor;

import com.yuxuan66.ehi.verification.annotation.Verification;
import com.yuxuan66.ehi.verification.core.EhiVerification;
import com.yuxuan66.ehi.verification.core.VerificationResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 参数校验拦截器
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 23:04
 */
public class ParamInterceptor implements HandlerInterceptor {

    /**
     * 核心校验器操作对象
     */
    private EhiVerification ehiVerification;
    public ParamInterceptor() {
    }

    public ParamInterceptor(EhiVerification ehiVerification) {
        this.ehiVerification = ehiVerification;
    }

    public EhiVerification getEhiVerification() {
        return ehiVerification;
    }

    public void setEhiVerification(EhiVerification ehiVerification) {
        this.ehiVerification = ehiVerification;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果请求的不是方法,不进行拦截 直接放过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //获取方法注解。
        Verification verification = handler.getClass().getAnnotation(Verification.class);
        if (((HandlerMethod) handler).getMethod().isAnnotationPresent(Verification.class)) {
            verification = ((HandlerMethod) handler).getMethod().getAnnotation(Verification.class);
        }
        //没有没有参数校验注解则不进行拦截，直接放过
        if (verification == null) {
            return true;
        }
        //遍历request所有参数,跟自定义注解参数校验做比较
        Map params = getParameterMap(request);
        VerificationResult verificationResult = ehiVerification.verification(params);
        //如果校验通过 直接放行
        if (verificationResult.isVerification()) {
            return true;
        }
        if (response.getHeader("x-requested-with") != null && response.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            PrintWriter out = response.getWriter();
            out.print(ehiVerification.getVerificationHandler().ajax(verificationResult));
            out.flush();
            return false;
        } else {
            request.getRequestDispatcher(ehiVerification.getVerificationHandler().page(verificationResult, request)).forward(request, response);
            return false;
        }
    }

    /**
     * 获取request内请求参数转换为Map对象
     *
     * @param request request请求对象
     * @return 转换后的对象
     */
    private Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
