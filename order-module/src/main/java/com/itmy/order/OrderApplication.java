package com.itmy.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: niusaibo
 * @date: 2023-10-10 13:19
 */
@SpringBootApplication
@MapperScan("com.itmy.order.dao")
@ComponentScan("com.itmy.*")
@EnableDiscoveryClient
@EnableDubbo
public class OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class,args);
    }
}
