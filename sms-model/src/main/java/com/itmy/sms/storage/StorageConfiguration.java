package com.itmy.sms.storage;

import com.itmy.sms.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class StorageConfiguration implements ApplicationContextAware {

    @Value("${gateway_kafka_topic_origin:sensor.data.origin}")
    private String topicOfOriginData;

    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired(required = false)
    JmsMessagingTemplate jmsMessagingTemplate;

    @Bean
    @ConditionalOnMissingBean(KafkaConfig.class)
    public JmsStorage jmsStorage() {
        return new JmsStorage(jmsMessagingTemplate, topicOfOriginData);
    }

    @Bean
    @ConditionalOnBean(KafkaConfig.class)
    public KafkaStorage kafkaStorage() {
        return new KafkaStorage(kafkaTemplate, topicOfOriginData);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void post() {
        IDataStorage dataStorage = this.applicationContext.getBean(IDataStorage.class);
        log.warn("==========================================");
        log.warn(" Data Storage Type: {}", dataStorage.getType());
        log.warn("==========================================");
    }
}
