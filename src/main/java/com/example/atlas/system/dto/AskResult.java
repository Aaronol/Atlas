package com.example.atlas.system.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class AskResult {
    private static final int SUCCESS_CODE = 0;
    private static final int FAILED_CODE = 104;

    private int code;
    private String msg;
    private Integer count;
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
        ret.setCount(getDataLength(data));
        return ret;
    }

    private static Integer getDataLength(Object data) {
        if (Collection.class.isAssignableFrom(data.getClass())) {
            ArrayList<Object> arrayListData = (ArrayList<Object>) data;
            return arrayListData.size();
        } else if (data.getClass().isArray()) {
            Object[] obj = (Object[]) data;
            return obj.length;
        } else {
            return 0;
        }
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
