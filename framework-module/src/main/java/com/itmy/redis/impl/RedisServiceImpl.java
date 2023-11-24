package com.itmy.redis.impl;

import com.itmy.redis.IRedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: niusaibo
 * @date: 2023-10-23 15:46
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedissonClient redissonClient;


    public void lock(String key, List<Long> idx) {
        if (CollectionUtils.isEmpty(idx)) {
            return;
        }
        idx.forEach(id -> {
            RLock lock = redissonClient.getLock(key + id);
            lock.lock(1, TimeUnit.MINUTES);
        });
    }

    public void unlock(String key, List<Long> idx) {
        if (CollectionUtils.isEmpty(idx)) {
            return;
        }
        idx.forEach(id -> {
            RLock lock = redissonClient.getLock(key + id);
            lock.unlock();
        });
    }
}
