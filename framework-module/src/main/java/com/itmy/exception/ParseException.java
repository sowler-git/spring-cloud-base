package com.itmy.exception;

import com.itmy.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  解析异常
 * @Author: niusaibo
 * @date: 2023-10-13 14:17
 */
@Getter
@Setter
@AllArgsConstructor
public class ParseException extends RuntimeException {

    private int code = 400;
    private String msg;


    public ParseException(String msg) {
        this.msg = msg;
    }

    public ParseException(ErrorEnum errorEnums) {
        this.code = errorEnums.getCode();
        this.msg = errorEnums.getMsg();
    }
}
