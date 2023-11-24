package com.itmy.picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: niusaibo
 * @date: 2023-06-28 10:16
 */
@SpringBootApplication
@EnableTransactionManagement
//@EnableOpenApi
//@EnableDiscoveryClient
//@EnableFeignClients("com.moxi.mogublog.commons.feign")
//@ComponentScan(basePackages = {
//        "com.itmy.picture"})
public class PictureApplication {


    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class,args);
    }
}
