package com.itmy.sms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置文件【可用于自动生成队列和交换机】
 */
@Configuration
public class RabbitMqConfig {

    public static final String EMAIL = "itmy.email";
    public static final String SMS = "itmy.sms";
    public static final String ROUTING_KEY_EMAIL = "itmy.email";
    public static final String ROUTING_KEY_SMS = "itmy.sms";

    public static final String EXCHANGE_DIRECT = "exchange.direct";

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(true).build();
    }

    /**
     * 声明Email队列
     *
     * @return
     */
    @Bean(EMAIL)
    public Queue MOGU_EMAIL() {
        return new Queue(EMAIL);
    }

    /**
     * 声明SMS队列
     *
     * @return
     */
    @Bean(SMS)
    public Queue MOGU_SMS() {
        return new Queue(SMS);
    }

    /**
     * email 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(EMAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    /**
     * sms 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(SMS) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
