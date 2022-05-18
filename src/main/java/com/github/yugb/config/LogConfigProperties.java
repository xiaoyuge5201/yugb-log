package com.github.yugb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 小余哥
 * @description: 日志
 * @create: 2022-05-18 09:27
 **/
@ConfigurationProperties(prefix = "yugb.log")
public class LogConfigProperties {
    /**
     * 织入类型
     * 1. before:前置通知(应用：各种校验)：在方法执行前执行，如果通知抛出异常，阻止方法运行
     * 2. afterReturning:后置通知(应用：常规数据处理)：方法正常返回后执行，如果方法中抛出异常，通知无法执行，必须在方法执行后才执行，所以可以获得方法的返回值。
     * 3. around:环绕通知(应用：十分强大，可以做任何事情)：方法执行前后分别执行，可以阻止方法的执行，必须手动执行目标方法
     * 4. afterThrowing:抛出异常通知(应用：包装异常信息)：方法抛出异常后执行，如果方法没有抛出异常，无法执行
     * 5. after:最终通知(应用：清理现场)：方法执行完毕后执行，无论方法中是否出现异常
     */
    private String weavingType="afterReturning,afterThrowing";
    /**
     * 日志表名
     */
    private String logTableName = "sys_operate_log";
    /**
     * 操作人用户名字段
     * 需要将该字段放到session中，后续可以从session中获取该字段信息
     */
    private String operateUsernameOfSession = "username";

    public String getWeavingType() {
        return weavingType;
    }

    public void setWeavingType(String weavingType) {
        this.weavingType = weavingType;
    }

    public String getLogTableName() {
        return logTableName;
    }

    public void setLogTableName(String logTableName) {
        this.logTableName = logTableName;
    }

    public String getOperateUsernameOfSession() {
        return operateUsernameOfSession;
    }

    public void setOperateUsernameOfSession(String operateUsernameOfSession) {
        this.operateUsernameOfSession = operateUsernameOfSession;
    }

    @Override
    public String toString() {
        return "LogConfigProperties{" +
                "weavingType='" + weavingType + '\'' +
                ", logTableName='" + logTableName + '\'' +
                ", operateUsernameOfSession='" + operateUsernameOfSession + '\'' +
                '}';
    }
}
