package com.itmy.enums;

/**
 * @Author: niusaibo
 * @date: 2023-10-25 13:50
 */
public enum FallbackErrorEnum {

    USER_MODULE_FALL(500,"用户查询漫长，启用熔断"),
    REDIS_FIND_FALL(500,"调用接口被限流")

    ;
    private Integer code;

    private String msg;

    FallbackErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "FallbackErrorEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
