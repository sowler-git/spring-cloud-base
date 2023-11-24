package com.itmy.gateway.config;

import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public Config config() {
        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        String redisHost = "redis://" + host + ":" + port;

        config.useSingleServer()
                .setAddress(redisHost)
                .setPassword(password)
                .setTimeout(5000)
                .setConnectTimeout(5000);
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create(config());
    }

    @PostConstruct
    public void post() {
        log.info("=========================");
        log.info("Redisson start");
        log.info("=========================");
    }
}
