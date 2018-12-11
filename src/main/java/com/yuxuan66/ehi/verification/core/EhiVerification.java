package com.yuxuan66.ehi.verification.core;

import com.yuxuan66.ehi.verification.annotation.Param;
import com.yuxuan66.ehi.verification.annotation.Verification;
import com.yuxuan66.ehi.verification.utils.ReflectUtil;
import com.yuxuan66.ehi.verification.utils.StrUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 核心校验工具
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 13:04
 */
public class EhiVerification {
    /**
     * 全局错误提示-空内容提示
     */
    private String nullMsg = "{FIELD}不能为空";

    /**
     * 全局错误提示-校验失败提示
     */
    private String errorMsg = "{FIELD}填写错误";
    /**
     * 拦截器用 错误处理器
     */
    private VerificationHandler verificationHandler = new DefaultVerificationHandler();

    public VerificationResult verification(Object object) {
        return verification(object, null);
    }

    public VerificationResult verification(Object object, Verification verificationP) {
        VerificationResult verificationResult = new VerificationResult();
        //所有要被校验的参数
        List<Param> params = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Verification verification = verificationP == null ? clazz.getAnnotation(Verification.class) : verificationP;
        if (verification == null) {
            return verificationResult;
        }
        params.addAll(Arrays.asList(verification.params()));
        for (Field field : clazz.getDeclaredFields()) {
            Param param = field.getAnnotation(Param.class);
            if (param == null) {
                continue;
            }
            params.add(param);
        }
        //开始校验每一个字段
        for (Param param : params) {
            String fieldName = param.value();
            //TODO 如果字段为空 这里不做处理,直接跳过
            if (StrUtil.isBlank(fieldName)) {
                continue;
            }
            verificationResult.addField(fieldName);
            Object valueObj = ReflectUtil.getVal(object, fieldName);
            String value = valueObj == null ? "" : valueObj.toString();
            //非空校验
            if (param.require()) {
                if (StrUtil.isBlank(value)) {
                    //校验失败
                    verificationResult.setVerification(false);
                    verificationResult.addField(fieldName, geNullField(param));
                    continue;
                }
            }
            //正则校验
            if (!StrUtil.isBlank(value) && !StrUtil.isBlank(param.pattern())) {
                Pattern pattern = Pattern.compile(param.pattern());
                if (!pattern.matcher(value).matches()) {
                    //校验失败
                    verificationResult.setVerification(false);
                    verificationResult.addField(fieldName, getErrorField(param));
                    continue;
                }
            }
            //AsFor 校验
            if (!StrUtil.isBlank(value) && !StrUtil.isBlank(param.asFor())) {
                String asField = param.asFor();
                Object asValObj = ReflectUtil.getVal(object, asField);
                String asVal = asValObj == null ? "" : asValObj.toString();
                if (!asVal.equals(value)) {
                    //校验失败
                    verificationResult.setVerification(false);
                    verificationResult.addField(fieldName, getErrorField(param));
                    continue;
                }
            }
            //nullFor 校验
            if (!StrUtil.isBlank(value) && !StrUtil.isBlank(param.nullFor())) {
                String nullField = param.nullFor();
                Object nullValObj = ReflectUtil.getVal(object, nullField);
                String nullVal = nullValObj == null ? "" : nullValObj.toString();
                if (StrUtil.isBlank(nullVal) && StrUtil.isBlank(value)) {
                    //校验失败
                    verificationResult.setVerification(false);
                    verificationResult.addField(fieldName, getErrorField(param));
                    continue;
                }
            }

            if (!StrUtil.isBlank(value) && param.len().max() != -1 && param.len().min() != -1) {
                int min = param.len().min();
                int max = param.len().max();
                if (value.length() > max || value.length() < min) {
                    //校验失败
                    verificationResult.setVerification(false);
                    verificationResult.addField(fieldName, getErrorField(param));
                    continue;
                }
            }
            //校验成功
            Result result = new Result();
            result.setCheck(true);
            verificationResult.addField(fieldName, result);
        }
        return verificationResult;
    }

    public String getNullMsg() {
        return nullMsg;
    }

    public void setNullMsg(String nullMsg) {
        this.nullMsg = nullMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public VerificationHandler getVerificationHandler() {
        return verificationHandler;
    }

    public void setVerificationHandler(VerificationHandler verificationHandler) {
        this.verificationHandler = verificationHandler;
    }

    /**
     * 内部使用 构建Result实例
     *
     * @param param
     * @return
     */
    private Result geNullField(Param param) {
        Result result = new Result();
        result.setCheck(false);
        String msg = StrUtil.isBlank(param.nullMsg()) ? nullMsg : param.nullMsg();
        result.setMsg(msg.replace("{FIELD}", param.value()));
        return result;
    }

    /**
     * 内部使用 构建Resule实例
     *
     * @param param
     * @return
     */
    private Result getErrorField(Param param) {
        Result result = new Result();
        result.setCheck(false);
        String msg = StrUtil.isBlank(param.errorMsg()) ? errorMsg : param.errorMsg();
        result.setMsg(msg.replace("{FIELD}", param.value()));
        return result;
    }
}
