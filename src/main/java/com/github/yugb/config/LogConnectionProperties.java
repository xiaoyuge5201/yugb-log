package com.github.yugb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志连接属性
 *
 * @author xiaoyuge
 * @author: 小余哥
 * @description: log链接数据信息
 * @create: 2022-05-18 09:27
 * @date 2022/05/20
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class LogConnectionProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;
    /**
     * 初始化大小
     */
    private int initialSize = 5;
    /**
     * 最小连接数
     */
    private int minIdle = 10;
    /**
     * 最大连接数
     */
    private int maxActive = 20;
    /**
     * 获取连接时的最大等待时间
     */
    private int maxWait = 60000;
    /**
     * 多久才进行一次检测需要关闭的空闲连接，单位是毫秒
     */
    private int timeBetweenEvictionRunsMillis = 60000;
    /**
     * 一个连接在池中最小生存的时间，单位是毫秒
     */
    private int minEvictableIdleTimeMillis = 300000;
    /**
     * # 检测连接是否有效的 SQL语句
     */
    private String validationQuery = "SELECT 1";
    /**
     * 申请连接时如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效，默认false，建议开启，不影响性能
     */
    private boolean testWhileIdle = true;
    /**
     * 申请连接时执行validationQuery检测连接是否有效，默认true，开启后会降低性能
     */
    private boolean testOnBorrow = true;
    /**
     * # 归还连接时执行validationQuery检测连接是否有效，默认false，开启后会降低性能
     */
    private boolean testOnReturn = true;

    private boolean poolPreparedStatements = true;

    private int maxPoolPreparedStatementPerConnectionSize = 20;
    /**
     * 配置扩展插件：stat-监控统计，log4j-日志，wall-防火墙（防止SQL注入），去掉后，监控界面的sql无法统计
     */
    private String filters = "stat,wall";
    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    private String connectionProperties = "connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
