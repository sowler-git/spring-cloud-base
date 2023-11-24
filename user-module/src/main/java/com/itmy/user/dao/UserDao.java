package com.itmy.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 10:48
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from tb_user")
    List<User> selectUserAll();
}
