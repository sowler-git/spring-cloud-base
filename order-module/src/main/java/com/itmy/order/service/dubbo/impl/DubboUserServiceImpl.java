package com.itmy.order.service.dubbo.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.itmy.entity.Blog;
import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.exception.ParamException;
import com.itmy.handler.sentinel.CustomerBlockHandler;
import com.itmy.handler.sentinel.UserFallback;
import com.itmy.order.service.IBlogService;
import com.itmy.order.service.dubbo.IDubboUserService;
import com.itmy.user.service.IUserExternalService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.metadata.IIOInvalidTreeException;
import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: niusaibo
 * @date: 2023-10-17 11:39
 */
@Service
@Slf4j
public class DubboUserServiceImpl implements IDubboUserService {

    @DubboReference(group = "userGroup",version = "2.0")
    private IUserExternalService userExternalService;

    @Autowired
    private IBlogService blogService;

//    @PostConstruct
//    private void initFlowRules(){
//        System.out.println("Sentinel initFlowRules start===");
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource(IDubboUserService.class.getName());
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        // Set limit QPS to 20.
//        rule.setCount(20);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//        System.out.println("Sentinel initFlowRules end====");
//    }


    @Override
    @SentinelResource(value = "com.itmy.user.service.IUserExternalService:selectUserAll()",
            blockHandler = "selectUserAll",
            blockHandlerClass = CustomerBlockHandler.class,
            fallback = "selectUserAllFallback",
            fallbackClass = UserFallback.class,
            exceptionsToIgnore = {IllegalArgumentException.class})
    //fallback 负责业务异常 blockHandler配置异常 exceptionsToIgnore 报该异常fallback不处理
    @GlobalTransactional(rollbackFor = Exception.class,timeoutMills = 30000,name = "order_tx_group")
    public List<User> selectUserAll() {
        //添加blog
        Blog blog = new Blog();
        blog.setUid(UUID.randomUUID().toString());
        blog.setTitle("dubbo事务测试Test");
        blog.setContent("dubbo事务测试Test啊的服务器打");
        blog.setSummary("12");
        blog.setTagUid("3c16b9093e9b1bfddbdfcb599b23d835");
        blogService.insert(blog);
        //处理相关逻辑
        Response<List<User>> response = userExternalService.selectUserAll();
//        boolean flag = true;
//        if (flag == true){
//            throw new ParamException(500,"用户模块出现错误，需要回滚");
//        }
        User user = new User();
        user.setUserName("dubbo事务");
        user.setAccount("system");
        user.setEmail("dubbo@gemail.com");
        Response insert = userExternalService.insert(user);
        System.out.println(insert);
        return response.getModel();
    }




}
