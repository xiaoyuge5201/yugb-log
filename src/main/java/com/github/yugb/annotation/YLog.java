package com.github.yugb.annotation;

import com.github.yugb.bean.enums.OperatorType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 日志拦截注解
 *
 * @author xiaoyuge
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface YLog {

    /**
     * @return 模块名称
     */
    String module() default "";

    /**
     * @return 描述
     */
    String desc() default "";

    /**
     * @return 操作类型，增删改查
     */
    OperatorType type();
}
