package com.common.aspect;

import com.by.byconfig.dynamicDS.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class YouzanAspect {
    private static final Logger LOG = LoggerFactory.getLogger(YouzanAspect.class);
    @Pointcut("execution(* com.youzan.youzanweb.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint point) {
        LOG.info("--设置数据源--");
        DynamicDataSourceContextHolder.setDataSourceKey("main");
    }

}
