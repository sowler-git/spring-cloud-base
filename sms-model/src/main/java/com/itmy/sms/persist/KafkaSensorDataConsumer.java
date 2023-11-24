/*******************************************************************************
 * Copyright (C) DFocus, Inc - All Rights Reserved
 * This file is part of the project gateway-server
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by huhaonan@dfocuspace.com, 2018/3
 ******************************************************************************/

package com.itmy.sms.persist;

import com.itmy.sms.config.KafkaConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 *  从Kafka读取传感器数据 调用{@link IDataPersist} 存到Redis或者SQLServer
 */
@Service
@ConditionalOnBean(KafkaConfig.class)
@Log4j2
public class KafkaSensorDataConsumer{

    @PostConstruct
    public void post() {
        log.warn("***** Kafka Sensor Data Consumer Activated");
    }

    @Value("${gateway_kafka_topic_origin:sensor.data.origin}")
    private String topicOfOriginData;

    @Autowired
    @Qualifier("redisPersist")
    private IDataPersist dataPersist;

    @KafkaListener(topics = {"sensor.data"},
            groupId = "gateway.consumer", clientIdPrefix = "gateway-data")
    public void consumer(ConsumerRecord<String, String> data) {
        String originRecord = data.value();
        String[] datas = originRecord.split(",");
        Long timestamp = Long.valueOf(datas[0]);
        String sn = datas[1];
        String deviceId = datas[2];
        String groupAddr = datas[3];
        String val = datas[4];
        dataPersist.put(timestamp, sn, groupAddr, val);
    }
}
