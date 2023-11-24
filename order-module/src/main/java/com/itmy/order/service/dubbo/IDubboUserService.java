package com.itmy.order.service.dubbo;

import com.itmy.entity.User;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-17 11:38
 */
public interface IDubboUserService {

    List<User> selectUserAll();

}
