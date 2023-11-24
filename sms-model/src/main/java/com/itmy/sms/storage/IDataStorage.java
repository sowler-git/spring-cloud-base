/*******************************************************************************
 * Copyright (C) DFocus, Inc - All Rights Reserved
 * This file is part of the project gateway-server
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by huhaonan@dfocuspace.com, 2018/2
 ******************************************************************************/

package com.itmy.sms.storage;

/**
 * 保存数据到 Kafka, Redis, ...
 */
public interface IDataStorage {

    /**
     * persistence sensor data
     *
     * @param gatewaySn
     * @param sensorDeviceId
     * @param sensorGroupAddr
     * @param val
     */
    void store(long timestamp,
               String gatewaySn,
               String sensorDeviceId,
               String sensorGroupAddr,
               String val);

    String getType();

}
