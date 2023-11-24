package com.itmy.handler.sentinel;

import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.exception.ParamException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-31 14:35
 */
@Slf4j
public class UserFallback {

    public static List<User> selectUserAllFallback(Throwable throwable) {
        log.error("添加订单|添加用户信息异常，触发熔断兜底操作。");
        throw new ParamException(600,"添加用户信息异常，触发熔断兜底操作");
    }
}
