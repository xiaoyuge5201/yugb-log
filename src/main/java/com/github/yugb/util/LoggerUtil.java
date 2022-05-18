package com.github.yugb.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具類
 * 用于获取请求的ip信息
 *
 * @author com.github.yugb
 */
public class LoggerUtil {
    /**
     * 获取客户端ip地址
     *
     * @param request 請求
     * @return 返回结果 ip
     */
    public static String getClientIP(HttpServletRequest request) {
        String ips = request.getHeader("X-FORWARDED-FOR");
        if (ips != null && ips.length() != 0 && !"unknown".equalsIgnoreCase(ips)) {
            // 多次反向代理后会有多个ips值，第一个ips才是真实ips
            if (ips.contains(",")) {
                ips = ips.split(",")[0];
            }
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("Proxy-Client-IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("X-Real-IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getRemoteAddr();
        }
        if (ips != null && ips.contains(",")) {
            String[] ipWithMultiProxy = ips.split(",");
            for (String eachIpsegement : ipWithMultiProxy) {
                if (!"unknown".equalsIgnoreCase(eachIpsegement)) {
                    ips = eachIpsegement;
                    break;
                }
            }
        }
        return ips;
    }
}