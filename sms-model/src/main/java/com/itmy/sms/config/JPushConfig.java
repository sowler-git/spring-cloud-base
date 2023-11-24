package com.itmy.sms.config;


import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;



@Configuration
public class JPushConfig {

    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    private JPushClient jPushClient;

    /**
     * 推送客户端
     * @return
     */
    @PostConstruct
    public void initJPushClient() {
        jPushClient = new JPushClient(appKey, masterSecret);
    }

    /**
     * 获取推送客户端
     * @return
     */
    public JPushClient getJPushClient() {
        return jPushClient;
    }

}
