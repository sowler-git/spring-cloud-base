package com.itmy.redis;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-23 15:46
 */
public interface IRedisService {


    /**
     * 加锁
     * @param key
     * @param idx
     */
    void lock(String key, List<Long> idx);

    /**
     * 解锁
     * @param key
     * @param idx
     */
    void unlock(String key, List<Long> idx);
}
