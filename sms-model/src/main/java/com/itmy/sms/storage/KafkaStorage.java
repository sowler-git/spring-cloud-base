/*******************************************************************************
 * Copyright (C) DFocus, Inc - All Rights Reserved
 * This file is part of the project gateway-server
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by huhaonan@dfocuspace.com, 2018/2
 ******************************************************************************/

package com.itmy.sms.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 把发过来的原始数据push到Kafka里
 */
@Slf4j
public class KafkaStorage implements IDataStorage {

    private KafkaTemplate<String, String> kafkaTemplate;

    private String topic;

    public KafkaStorage(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        log.warn("***** Kafka Storage Activated");
    }

    @Override
    public void store(long timestamp, String gatewaySn, String sensorDeviceId, String sensorGroupAddr, String val) {
        String message = String.format("%d,%s,%s,%s,%s", timestamp, gatewaySn, sensorDeviceId, sensorGroupAddr, val);
        // TODO partitions
        // TODO exception
        kafkaTemplate.send(topic, message);
        log.info("Kafka|data sent, {}, {}, {}, {}, {}", timestamp, gatewaySn, sensorDeviceId, sensorGroupAddr, val);
    }

    @Override
    public String getType() {
        return "Kafka";
    }
}
