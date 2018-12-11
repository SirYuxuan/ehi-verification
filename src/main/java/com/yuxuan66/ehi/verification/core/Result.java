package com.yuxuan66.ehi.verification.core;

/**
 * @author Sir丶雨轩
 * @date 2018/12/11 14:02
 */
public class Result {
    /**
     * 是否通过
     */
    private boolean check;
    /**
     * 如果校验失败 提示信息
     */
    private String msg;

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}