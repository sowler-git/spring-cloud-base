package com.itmy.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmy.entity.User;
import com.itmy.entity.base.PageResModel;
import com.itmy.entity.dto.UserSearchDTO;
import com.itmy.service.BaseServiceImpl;
import com.itmy.user.dao.UserDao;
import com.itmy.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 10:49
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao,User> implements IUserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<User> selectUserAll() {
        return userDao.selectUserAll();
    }

    @Override
    public PageResModel<User> searchUserByPage(UserSearchDTO userSearchDTO) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        IPage<User> userIPage =
                this.baseMapper.selectPage(new Page<>(userSearchDTO.getPageNum(),
                        userSearchDTO.getPageSize()),
                        queryWrapper);
        return new PageResModel<>(userIPage);
    }

    @Override
    public User getByAccount(String account, Long tenantId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.TENANT_ID, tenantId);
        // 账号或邮箱都可以
        wrapper.and(w -> w.eq(User.ACCOUNT, account).or().eq(User.EMAIL, account));
        List<User> userList = list(wrapper);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }
}
