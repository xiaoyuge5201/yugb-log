package com.github.yugb.listener;

import com.github.yugb.dao.RequestLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RequestLogDao logDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //容器启动时，检测日志表是否存在
        logger.debug("spring context init...");
        boolean flag = logDao.validateTableExist();
        if (!flag){
            logger.debug("创建日志表...");
            logDao.createLogTable();
        }
    }
}
