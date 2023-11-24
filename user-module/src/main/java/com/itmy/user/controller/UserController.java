package com.itmy.user.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itmy.controller.BaseController;
import com.itmy.entity.CurrentUser;
import com.itmy.entity.User;
import com.itmy.entity.base.PageResModel;
import com.itmy.entity.base.Response;
import com.itmy.entity.dto.UserAdd;
import com.itmy.entity.dto.UserSearchDTO;
import com.itmy.entity.dto.UserUpdate;
import com.itmy.user.service.IUserService;
import com.itmy.utils.EncryptUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 10:50
 */
@RestController
@RequestMapping("/user")
@RefreshScope
@Api(tags = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Value("${user.config.attribute}")
    private String config;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping
    public Response findAll(HttpServletRequest request){
//        String login_user = request.getHeader("LOGIN_USER");
//        System.out.println(login_user);
//        String authorization = request.getHeader("Authorization");
//        System.out.println(authorization);
//        CurrentUser currentUser1 = JSONObject.parseObject(login_user, CurrentUser.class);
//        UserHolder.setCurrentUser(currentUser1);
//        CurrentUser currentUser = UserHolder.getCurrentUser();
//        System.out.println("Local："+currentUser);
        CurrentUser currentUser = getCurrentUser(request);
        System.out.println(currentUser);
        List<User> userList = null;
        RBucket<String> userBucket = redissonClient.getBucket("user_list");
        if (userBucket.isExists()){
            System.out.println("reidssion get values ......");
            String userListStr = userBucket.get();
            userList = JSONArray.parseArray(userListStr, User.class);
        }else {
            userList = userService.selectUserAll();
            System.out.println("mysql values .....");
            userBucket.set(JSONObject.toJSONString(userList));
        }
        return Response.success(userList);
    }

    @GetMapping("/nacos")
    public Response findNacosConfig(){
        return Response.success(config);
    }

    @GetMapping("/list")
    public Response selectUser(UserSearchDTO userSearchDTO){
        //List<User> userList = userService.list();
        PageResModel<User> resModel = userService.searchUserByPage(userSearchDTO);
        return Response.success(resModel);
    }

    @ApiOperation("添加用户")
    @PostMapping
    public Response saveUser(@RequestBody UserAdd userAdd){
        User user = new User();
        BeanUtils.copyProperties(userAdd,user);
        if (!StringUtils.isEmpty(userAdd.getPassword())){
            user.setPassword(EncryptUtils.encodeAES(userAdd.getPassword()));
        }
        userService.save(user);
        return Response.success();
    }

    @ApiOperation("修改用户")
    @PutMapping
    public Response updateUser(@RequestBody UserUpdate userUpdate){
        if (userUpdate.getId() == null){
            return Response.fail("用户ID为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdate,user);
        if (!StringUtils.isEmpty(userUpdate.getPassword())){
            user.setPassword(EncryptUtils.encodeAES(userUpdate.getPassword()));
        }
        userService.updateById(user);
        return Response.success();
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable("id") Long userId){
        userService.removeById(userId);
        return Response.success();
    }
}
