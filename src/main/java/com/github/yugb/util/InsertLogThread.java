package com.github.yugb.util;

import com.github.yugb.bean.RequestLog;
import com.github.yugb.dao.RequestLogDao;


/**
 * 插入日志线程
 *
 * @author xiaoyuge
 * @date 2022/05/20
 */
public class InsertLogThread extends Thread {

    private RequestLog requestLog;

    private RequestLogDao logService;

    public InsertLogThread(RequestLog requestLog, RequestLogDao logService) {
        this.requestLog = requestLog;
        this.logService = logService;
    }

    @Override
    public void run() {
        logService.save(requestLog);
    }
}
