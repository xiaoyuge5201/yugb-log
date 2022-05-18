package com.github.yugb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author: 小余哥
 **/
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ExcutePoolAutoConfiguration {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPoolProperties poolProperties) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(poolProperties.getKeepAliveSeconds());
        pool.setCorePoolSize(poolProperties.getCorePoolSize());
        pool.setMaxPoolSize(poolProperties.getMaxPoolSize());
        pool.setQueueCapacity(poolProperties.getQueueCapacity());
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }
}
