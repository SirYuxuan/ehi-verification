package com.yuxuan66.ehi.verification.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校验结果
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 13:08
 */
public class VerificationResult {

    /**
     * 是否校验通过
     */
    private boolean verification = true;


    /**
     * 所有被校验的字段名称列表
     */
    private List<String> fields = new ArrayList<>();

    /**
     * 所有校验失败的字段
     */
    private Map<String, Result> errorDatas = new HashMap<>();
    /**
     * 所有参与校验的字段
     */
    public Map<String, Result> datas = new HashMap<>();

    /**
     * 添加字段到被校验字段名称立列表
     *
     * @param name
     */
    public void addField(String name) {
        fields.add(name);
    }

    /**
     * 添加字段到参与校验的字段校验结果列表
     *
     * @param name   字段名称
     * @param result 校验结果
     */
    public void addField(String name, Result result) {
        datas.put(name, result);
        if (!result.getCheck()) {
            errorDatas.put(name, result);
        }
    }

    /**
     * 获取校验失败的提示信息
     *
     * @return 提示信息
     */
    public String getErrorMsg() {
        StringBuilder errorMsg = new StringBuilder();
        for (String name : errorDatas.keySet()) {
            errorMsg.append("【");
            errorMsg.append(errorDatas.get(name).getMsg());
            errorMsg.append("】,");
        }
        return errorMsg.length() > 1 ? errorMsg.delete(errorMsg.length() - 1, errorMsg.length()).toString() : errorMsg.toString();
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }
}
