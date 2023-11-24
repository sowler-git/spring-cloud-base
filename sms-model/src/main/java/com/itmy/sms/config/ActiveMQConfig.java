package com.itmy.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;

@ConditionalOnMissingBean(KafkaConfig.class)
@Configuration
@Slf4j
public class ActiveMQConfig {

    @PostConstruct
    public void post() {
        log.warn("===================================");
        log.warn("    ActiveMQConfig IS Enabled");
        log.warn("===================================");
    }

    /**
     * 内置的模拟ActiveMQ Broker端口, 默认 16161
     */
    @Value("${spring.activemq.broker-service.port:61616}")
    private String brokerServicePort;

    @Value("${spring.activemq.user:}")
    private String username;

    @Value("${spring.activemq.password:}")
    private String password;

    @Value("${spring.activemq.broker-url:tcp://localhost:61616}")
    private String brokerUrl;

    /**
     * 模拟一个内置的 Spring Embedded ActiveMQ Broker (Server)
     * @return
     * @throws Exception
     */
    @Bean
    BrokerService brokerService() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:" + brokerServicePort);
        brokerService.setPersistent(false);
        return brokerService;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        return new ActiveMQConnectionFactory(username, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return new JmsMessagingTemplate(jmsTemplate);
    }
}
