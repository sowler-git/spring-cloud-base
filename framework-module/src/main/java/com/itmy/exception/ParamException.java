package com.itmy.exception;

import com.itmy.enums.ErrorEnum;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:09
 */
public class ParamException extends RuntimeException{

    private int code = 400;
    private String msg;

    public ParamException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ParamException(String msg) {
        this.msg = msg;
    }

    public ParamException(ErrorEnum errorEnums) {
        this.code = errorEnums.getCode();
        this.msg = errorEnums.getMsg();
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
}
