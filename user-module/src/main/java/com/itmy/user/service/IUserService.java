package com.itmy.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itmy.entity.User;
import com.itmy.entity.base.PageResModel;
import com.itmy.entity.dto.UserSearchDTO;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 10:49
 */
public interface IUserService extends IService<User> {


    List<User> selectUserAll();

    PageResModel<User> searchUserByPage(UserSearchDTO userSearchDTO);

    User getByAccount(String account, Long tenantId);
}
