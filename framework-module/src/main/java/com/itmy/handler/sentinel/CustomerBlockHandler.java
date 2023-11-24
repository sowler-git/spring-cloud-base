package com.itmy.handler.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.enums.FallbackErrorEnum;
import com.itmy.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-25 16:59
 * 自定义限流处理逻辑
 */
@Slf4j
public class CustomerBlockHandler {

    /**
     * 查询用户热点限流测试
     * @param name
     * @param email
     * @param exception
     * @return
     */
    public static Response selectUserBlockException(@RequestParam(value = "name",required = false) String name,
                                                    @RequestParam(value = "email",required = false) String email,
                                                    BlockException exception){
        log.error("CustomerBlockHandler|selectUserBlockException is fail");
        return Response.fail(FallbackErrorEnum.USER_MODULE_FALL);
    }

    /**
     * 查询限流
     * @return
     */
    public static Response redisFindBlockException(BlockException exception){
        log.error("添加订单 redis|添加用户 redis信息，调用接口被限流。。。。。");
        return Response.fail(FallbackErrorEnum.REDIS_FIND_FALL);
    }


    public List<User> selectUserAll(BlockException exception){
        log.error("添加订单|添加用户信息，触发限流控制。。。。。");
        throw new ParamException(600,"添加用户信息异常："+exception.getMessage());
    }

}
