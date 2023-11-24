package com.itmy.user.service;

import com.itmy.entity.User;
import com.itmy.entity.base.Response;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-23 14:10
 */
public interface IUserExternalService {

    Response<List<User>> selectUserAll();

    Response insert(User user);
}
