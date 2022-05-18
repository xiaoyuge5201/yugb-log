package com.github.yugb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 小余哥
 * @description:
 * @create: 2022-05-18 09:27
 **/
@ConfigurationProperties(prefix = "com.github.yugb.thread")
public class ThreadPoolProperties {
    /**
     * 核心线程池数
     */
    private int corePoolSize = 10;
    /**
     * 最大线程
     */
    private int maxPoolSize = 20;
    /**
     * 队列容量
     */
    private int queueCapacity = 1000;
    /**
     * 存货时间
     */
    private int keepAliveSeconds = 5000;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
