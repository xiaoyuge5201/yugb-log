package com.github.yugb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.yugb.util.JdbcClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志连接汽车配置
 *
 * @author xiaoyuge
 * @author: 小余哥
 * @description: 自动装配
 * @create: 2022-05-18 09:27
 * @date 2022/05/20
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({LogConnectionProperties.class, LogConfigProperties.class})
@EnableAsync
public class LogConnectionAutoConfiguration {
}
