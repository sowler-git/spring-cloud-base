package com.itmy.exception;

import com.itmy.enums.ErrorEnum;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:48
 */
public class ModelException extends RuntimeException{

    private int code = 400;

    private String msg;

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    private Integer model;

    public ModelException(int code, String msg, Integer model) {
        this.code = code;
        this.msg = msg;
        this.model = model;
    }

    public ModelException(String msg) {
        this.msg = msg;
    }

    public ModelException(ErrorEnum errorEnums, Integer model) {
        this.code = errorEnums.getCode();
        this.msg = errorEnums.getMsg();
        this.model = model;
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
