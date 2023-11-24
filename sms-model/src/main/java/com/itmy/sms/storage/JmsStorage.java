package com.itmy.sms.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsMessagingTemplate;

@Slf4j
public class JmsStorage implements IDataStorage {

    private String topic;

    private JmsMessagingTemplate template;

    public JmsStorage(JmsMessagingTemplate template, String topic) {
        this.template = template;
        this.topic = topic;
        log.warn("***** Jms Storage Activated");
    }

    @Override
    public void store(long timestamp, String gatewaySn, String sensorDeviceId, String sensorGroupAddr, String val) {
        String message = String.format("%d,%s,%s,%s,%s", timestamp, gatewaySn, sensorDeviceId, sensorGroupAddr, val);
        this.template.convertAndSend(topic, message);
        log.info("JMS|data sent, {}, {}, {}, {}, {}", timestamp, gatewaySn, sensorDeviceId, sensorGroupAddr, val);
    }

    @Override
    public String getType() {
        return "JMS";
    }
}
