package com.github.yugb.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 记录用户的操作记录
 *
 * @author xiaoyuge
 */
public class RequestLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 操作时间
     */
    private String createDate;
    /**
     * 操作人
     */
    private String username;
    /**
     * 操作类型：0，代表添加，1代表删除，2代表修改，3代表查看
     */
    private String operateType;

    /**
     * 日志级别
     */
    private String logType;
    /**
     * 模块名称：例如数据上架，数据库下架等比较重要的步骤名称
     */
    private String module;
    /**
     * 描述
     */
    private String description;

    /**
     * 电脑ip
     */
    private String remoteAddr;

    /**
     * URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 提交参数
     */
    private String params;

    /**
     * 异常
     */
    private String exception;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", username='" + username + '\'' +
                ", operateType='" + operateType + '\'' +
                ", logType='" + logType + '\'' +
                ", module='" + module + '\'' +
                ", description='" + description + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }

    /**
     * 设置请求参数
     *
     * @param paramMap 参数
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue);
        }
        this.params = params.toString();
    }
}
