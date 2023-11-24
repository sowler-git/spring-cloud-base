package com.itmy.handler;

import com.itmy.entity.base.Response;
import com.itmy.exception.ModelException;
import com.itmy.exception.ParamException;
import com.itmy.exception.ParseException;
import com.itmy.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: niusaibo
 * @date: 2023-10-16 09:54
 */
@RestControllerAdvice
public class ErrorHandler {



    @ExceptionHandler(ParamException.class)
    public Response handleParamException(ParamException ex) {
        return Response.builder().code(ex.getCode()).msg(ex.getMsg()).build();
    }

    @ExceptionHandler(ModelException.class)
    public Response handleParamException(ModelException ex) {
        return Response.builder().code(ex.getCode()).msg(ex.getMsg()).model(ex.getModel()).build();
    }

    @ExceptionHandler(ParseException.class)
    public Response handleParseException(ParseException ex) {
        return Response.builder().code(ex.getCode()).msg(ex.getMsg()).build();
    }

    @ExceptionHandler(SystemException.class)
    public Response handleParseException(SystemException ex) {
        return Response.builder().code(500).msg(ex.getMessage()).build();
    }

}
