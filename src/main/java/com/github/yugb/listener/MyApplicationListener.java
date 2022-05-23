package com.github.yugb.listener;

import com.github.yugb.dao.RequestLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.LockSupport;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RequestLogDao logDao;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            logger.info("容器即将关闭....");
            LockSupport.park(); //无期限暂停当前线程
        } else if (event instanceof ContextRefreshedEvent) {
            //容器启动时，检测日志表是否存在
            logger.info("容器开启中....");
            boolean flag = logDao.validateTableExist();
            if (!flag) {
                logger.info("创建日志表...");
                logDao.createLogTable();
            }
        }
    }
}
