package com.taoge.vertxspring.utils.resultvo;

import io.vertx.core.json.Json;

public class ResultBean {
    private String code;
    private String msg;
    private Object data;

    public ResultBean() {
    }

    public String getCode() {
        return code;
    }

    public ResultBean setCode(String code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultBean setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResultBean setResultConstant(ResultConstant resultConstant) {
        this.code = resultConstant.getCode();
        this.msg = resultConstant.getMsg();
        return this;
    }

    public ResultBean setResultConstant(ResultConstant resultConstant, Object data) {
        this.code = resultConstant.getCode();
        this.msg = resultConstant.getMsg();
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }

    public static ResultBean build() {
        return new ResultBean();
    }
}
