package com.common.aspect;

import com.by.byconfig.dynamicDS.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class YouzanAspect {
    @Pointcut("execution(* com.youzan.youzanweb.controller.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint point) {
        DynamicDataSourceContextHolder.setDataSourceKey("main");
    }

}
