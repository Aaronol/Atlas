package com.example.atlas.system.dto;

import java.util.Date;

public class AskResult {
    private static final int SUCCESS_CODE = 200;
    private static final int FAILED_CODE = 104;

    private int code;
    private String msg;
    private Object data;
    private Long stamp;

    public static AskResult success() {
        return success("", "");
    }

    public static AskResult success(Object obj) {
        return success("", obj);
    }

    public static AskResult success(String msg, Object data) {
        AskResult ret = new AskResult();
        ret.setCode(SUCCESS_CODE);
        ret.setMsg(msg);
        ret.setData(data);
        ret.setStamp(new Date().getTime());
        return ret;
    }

    public static AskResult failed(String msg) {
        return failed(FAILED_CODE, msg);
    }


    public static AskResult failed(int code, String msg) {
        AskResult ret = new AskResult();
        ret.setCode(code);
        ret.setMsg(msg);
        ret.setData("");
        ret.setStamp(new Date().getTime());
        return ret;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getStamp() {
        return stamp;
    }

    public void setStamp(Long stamp) {
        this.stamp = stamp;
    }
}
