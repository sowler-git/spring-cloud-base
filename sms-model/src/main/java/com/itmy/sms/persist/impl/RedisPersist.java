/*******************************************************************************
 * Copyright (C) DFocus, Inc - All Rights Reserved
 * This file is part of the project gateway-server
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by huhaonan@dfocuspace.com, 2018/3
 ******************************************************************************/

package com.itmy.sms.persist.impl;

import com.itmy.sms.persist.IDataPersist;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("redisPersist")
public class RedisPersist implements IDataPersist {

    private final static Logger logger = LogManager.getLogger("Aggr.Redis");

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 数据格式:
     *
     * 对于某个传感器来说, 在Redis中它的原始数据存储格式为:
     *
     * {hashKey} => {
     *
     *     id  :=> 传感器在jm系统中的主键(如果有)
     *     g   :=> 组地址,
     *     v   :=> 最新值,
     *     vt  :=> 最新值的时间,
     *     pt  :=> 最新一条有人的时间,
     *     at  :=> 最新一条无人的时间
     * }
     *
     *
     * @param timestamp
     * @param sn
     * @param groupAddr
     * @param val
     */
    @Override
    public void put(long timestamp, String sn, String groupAddr, String val) {

        String msg = StringUtils.join(timestamp, sn, groupAddr, val);
        try {
            if (!isLatest(timestamp, groupAddr)) {
                return;
            }

            Map<String, String> origin = createOrigin(timestamp, groupAddr, val);

            RMap<String, String> rMap = redissonClient.getMap(groupAddr);

            rMap.putAll(origin);

            logger.info("AGGR|{}|{}", groupAddr, val);
        } catch (Exception ex) {
            logger.error("get redis conn failed, msg={}", msg, ex);
        }
    }

    /**
     * 是否最新的传感器数据
     *
     * @param timestamp
     * @param groupAddr
     * @return
     */
    private boolean isLatest(long timestamp, String groupAddr) {

        RMap<String, String> rMap = redissonClient.getMap(groupAddr);

        Map<String, String> prev = rMap.readAllMap();

        // 判断一下Redis里面存储的这条记录的时间是不是更新一点 防止新数据被覆盖
        if (null != prev && StringUtils.isNotEmpty("prev.get(OriginFields.lastUpdateTime)")) {
            try {
                Long pvt = Long.valueOf("prev.get(OriginFields.lastUpdateTime)");

                // 如果本次数据并不是最新的 忽略本次
                if (pvt >= timestamp) {
                    return false;
                }

            } catch (Exception ex) {
                logger.error("Invalid prev vt, {}", prev);
                // 之前的数据有问题 准备覆盖
                return true;
            }
        }

        // redis原来无数据
        return true;
    }

    /**
     *
     * 组织需要插入Redis的数据格式
     *
     * @param timestamp 上报时间
     * @param groupAddr 组地址
     * @param val 值
     * @return map
     */
    private Map<String, String> createOrigin(long timestamp, String groupAddr, String val) {

        Map<String, String> origin = new HashMap<>();

//        origin.put(OriginFields.groupAddress, groupAddr);
//
//        // 区分新老网关的数据, 0 -> 新网关, 1 -> 老网关
//        origin.put("type", "0");
//
//        // 最后一条数据
//        origin.put(OriginFields.lastValue, val);
//
//        // 最后一条数据的时间
//        String strTime = String.valueOf(timestamp);
//        origin.put(OriginFields.lastUpdateTime, strTime);
//
//        if (GatewayUtils.isPresent(val)) {
//            // 最后一条有人的时间
//            origin.put(OriginFields.lastPresentTime, strTime);
//        } else {
//            // 最后一条无人的数据
//            origin.put(OriginFields.lastAbsenceTime, strTime);
//        }

        return origin;
    }
}
