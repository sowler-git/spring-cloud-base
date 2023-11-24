package com.itmy.user.service;

import com.itmy.entity.CurrentUser;
import com.itmy.entity.User;
import com.itmy.entity.dto.LoginDTO;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:24
 */
public interface IAccountService {

    /**
     * 本地登陆
     * @param dto
     * @return
     */
    User login(LoginDTO dto);

    void unlock(Long userId);

    /**
     * 修改密码
     * @param oldPassword
     * @param password
     * @param confirmPassword
     */
    Boolean changePassword(String oldPassword, String password, String confirmPassword, String email, CurrentUser currentUser);

    /**
     * 密码格式验证
     * @param password
     */
    void checkPassword(String password);

    /**
     * 账号的token失效
     * @param userId
     */
    void deleteToken(Long userId);
}
