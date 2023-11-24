package com.itmy.sms.persist;

import com.itmy.sms.config.ActiveMQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 从ActiveMQ拿到传感器原始数据交给Persist去落地
 */
@Component
@ConditionalOnBean(ActiveMQConfig.class)
@Log4j2
public class JmsSensorDataConsumer {

    @PostConstruct
    public void post() {
        log.warn("***** Jms Sensor Data Consumer Activated");
    }

    @Autowired
    @Qualifier("redisPersist")
    private IDataPersist dataPersist;

    @JmsListener(destination = "sensor.data.origin")
    public void onMessage(String originRecord) {
        String[] datas = originRecord.split(",");
        Long timestamp = Long.valueOf(datas[0]);
        String sn = datas[1];
        String deviceId = datas[2];
        String groupAddr = datas[3];
        String val = datas[4];
        dataPersist.put(timestamp, sn, groupAddr, val);
        log.info("JMS|persisted");
    }

}
