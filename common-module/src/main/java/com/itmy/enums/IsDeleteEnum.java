package com.itmy.enums;

/**
 * @author nsbo
 * @Date 2023/10/7 15:38
 */
public enum IsDeleteEnum {

    NOT_DELETE(0,"未删除"),

    DELETE(1,"删除");

    private Integer code;

    private String msg;

    IsDeleteEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
