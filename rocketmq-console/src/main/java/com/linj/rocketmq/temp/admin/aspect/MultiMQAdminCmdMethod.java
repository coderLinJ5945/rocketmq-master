package com.linj.rocketmq.temp.admin.aspect;

import java.lang.annotation.*;


//spring的同一日志管理
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiMQAdminCmdMethod {
    long timeoutMillis() default 0;
}
