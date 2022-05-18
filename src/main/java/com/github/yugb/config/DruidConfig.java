package com.github.yugb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DruidDBConfig类被@Configuration标注，用作配置信息；
 * DataSource对象被@Bean声明，为Spring容器所管理，
 */
@Component
public class DruidConfig {

    @Autowired
    private LogConnectionProperties properties;

    private final static DruidDataSource datasource = new DruidDataSource();

    @Bean
    @Primary
    public DataSource dataSource() {
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());

        // configuration
        datasource.setInitialSize(properties.getInitialSize());
        datasource.setMinIdle(properties.getMinIdle());
        datasource.setMaxActive(properties.getMaxActive());
        datasource.setMaxWait(properties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(properties.getValidationQuery());
        datasource.setTestWhileIdle(properties.isTestWhileIdle());
        datasource.setTestOnBorrow(properties.isTestOnBorrow());
        datasource.setTestOnReturn(properties.isTestOnReturn());
        datasource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        datasource.setRemoveAbandoned(true);
        datasource.setRemoveAbandonedTimeout(1800);
        datasource.setLogAbandoned(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(properties.getConnectionProperties());

        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        return servletRegistrationBean;
    }

    /**
     * 获取连接
     * @return 获取连接
     */
    public static Connection getConnection() {
        try {
            return datasource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
