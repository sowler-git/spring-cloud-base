package com.itmy.user.service.external.impl;

import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.exception.ParamException;
import com.itmy.user.service.IUserExternalService;
import com.itmy.user.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @Author: niusaibo
 * @date: 2023-10-23 14:10
 */
@Service
@DubboService(timeout = 1000 * 10,group = "userGroup",version = "2.0")
public class UserExternalServiceImpl implements IUserExternalService {

    @Autowired
    private IUserService userService;


    @Override
    public Response selectUserAll() {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000*5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return Response.success(userService.selectUserAll());
    }

    @Override
    public Response insert(User user) {
//        boolean flag = true;
//        if (flag == true){
//            throw new ParamException(500,"用户模块出现错误，需要回滚");
//        }
//        try {
//            TimeUnit.MILLISECONDS.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        boolean save = userService.save(user);
        if (save){
            return Response.success();
        }else {
            return Response.fail();
        }
    }
}
