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

    public void addField(String name) {
        fields.add(name);
    }

    public void addField(String name, Result result) {
        datas.put(name, result);
        if (!result.getCheck()) {
            errorDatas.put(name, result);
        }
    }
}
