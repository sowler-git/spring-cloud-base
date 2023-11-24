package com.itmy.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itmy.entity.Blog;
import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.handler.sentinel.CustomerBlockHandler;
import com.itmy.order.service.IBlogService;
import com.itmy.order.service.dubbo.IDubboUserService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * @Author: niusaibo
 * @date: 2023-10-10 13:41
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private IBlogService blogService;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IDubboUserService dubboUserService;

    @GetMapping
    public Response findAll(){
        List<Blog> blogs = blogService.selectList();
        System.out.println(JSONObject.toJSONString(blogs));
       //redisTemplate.opsForValue().set("blogs_key",JSONObject.toJSONString(blogs));
        RBucket<String> blogsKey = redissonClient.getBucket("blogs_key");
        if (!blogsKey.isExists()){
            blogsKey.set(JSONObject.toJSONString(blogs));
        }
        return Response.success(blogs);
    }

    @GetMapping("/redis")
    @SentinelResource(value = "redis",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "redisFindBlockException")
    public Response redisFind(){
        String name = Thread.currentThread().getName();
        System.out.println("当前线程名称：" + name);
        //ValueOperations valueOperations = redisTemplate.opsForValue();
      //  Object blogsKey = valueOperations.get("blogs_key");
        RBucket<String> blogsKey = redissonClient.getBucket("blogs_key");
        List<Blog> blogs = null;
        if (blogsKey.isExists()){
            blogs = JSONArray.parseArray(blogsKey.get(), Blog.class);
            System.out.println("redis ..........");
        } else {
            blogs = blogService.selectList();
            blogsKey.set(JSONObject.toJSONString(blogs));
            System.out.println("Mysql ..........");
        }
        List<User> userList = dubboUserService.selectUserAll();
        Map<String,Object> listMap = new HashMap<>();
        listMap.put("blogList",blogs);
        listMap.put("userList",userList);
        return Response.success(listMap);
    }

    @GetMapping("/sentinelTest")
    @SentinelResource(value = "sentinelTest",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "selectUserBlockException")
    public Response sentinelFind(@RequestParam(value = "name",required = false) String name,
                                 @RequestParam(value = "email",required = false) String email){
        System.out.println("用户名称："+name+"、\t 邮箱：" + email);
        //int a = 10/0;
        List<User> userList = dubboUserService.selectUserAll();
        return Response.success(userList);
    }
}
