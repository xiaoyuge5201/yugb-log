package com.github.yugb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.yugb.util.JdbcClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: 小余哥
 * @description:
 * @create: 2022-05-18 09:27
 **/
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({LogConnectionProperties.class, LogConfigProperties.class})
@EnableAsync
public class LogConnectionAutoConfiguration {
}
