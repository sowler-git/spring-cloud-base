package com.itmy.entity.base;

import com.itmy.enums.ErrorEnum;
import com.itmy.enums.FallbackErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 11:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 接口数据
     */
    private T model;

    /**
     * 成功请求
     *
     */
    public static Response success() {
        return Response.builder()
                .code(ErrorEnum.OK.getCode())
                .msg(ErrorEnum.OK.getMsg())
                .build();
    }

    /**
     * 成功请求
     *
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(ErrorEnum.OK.getCode());
        response.setMsg(ErrorEnum.OK.getMsg());
        response.setModel(data);
        return response;
    }


    /**
     * 请求失败
     */
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setCode(ErrorEnum.ERROR.getCode());
        response.setMsg(ErrorEnum.ERROR.getMsg());
        return response;
    }

    /**
     * 请求失败
     */
    public static <T> Response<T> fail(T data) {
        Response<T> response = new Response<>();
        response.setCode(ErrorEnum.ERROR.getCode());
        response.setMsg(ErrorEnum.ERROR.getMsg());
        response.setModel(data);
        return response;
    }

    /**
     * 请求失败
     *
     * @param errorEnum
     * @return
     */
    public static Response fail(ErrorEnum errorEnum) {
        Response response = new Response<>();
        response.setCode(errorEnum.getCode());
        response.setMsg(errorEnum.getMsg());
        return response;
    }
    public static Response fail(FallbackErrorEnum errorEnum) {
        Response response = new Response<>();
        response.setCode(errorEnum.getCode());
        response.setMsg(errorEnum.getMsg());
        return response;
    }
    /**
     * 请求失败
     *
     * @param msg
     * @return
     */
    public static Response fail(int code, String msg) {
        Response response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }



}
