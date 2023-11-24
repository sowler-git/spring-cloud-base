package com.itmy.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class AppConfiguration {

    /**
     * 调度提出配置,可以开关控制开启
     */
    @ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true)
    @Configuration
    @EnableScheduling
    public static class SchedulingConfiguration implements SchedulingConfigurer {
        @Override
        public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
            scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
        }
    }
}
