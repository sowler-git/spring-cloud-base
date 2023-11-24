/*******************************************************************************
 * Copyright (C) DFocus, Inc - All Rights Reserved
 * This file is part of the project gateway-server
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by huhaonan@dfocuspace.com, 2018/3
 ******************************************************************************/

package com.itmy.sms.persist;

/**
 * 把原始数据从Kafka取出之后, 保存到DB (包括Redis)
 */
public interface IDataPersist {

    /**
     * 写入动作
     *
     * @param timestamp
     * @param sn
     * @param groupAddr
     * @param val
     */
    void put(long timestamp, String sn, String groupAddr, String val);
}
