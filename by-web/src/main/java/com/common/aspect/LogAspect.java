package com.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Order(1)
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 第一个*表示包下的所有类，第二个*表示类下的所有方法
     */
    @Pointcut("execution(* com.by.byservice.impl.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void before(JoinPoint point) {
        LOG.info("--开始执行类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"--");
    }

    @After("webLog()")
    public void after(JoinPoint point) {
        LOG.info("--类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"执行结束--");
    }


    /**
     * 第一个*表示包下的所有类，第二个*表示类下的所有方法
     */
    @Pointcut("execution(* com.youzan.youzanweb.controller.YouzanController.by*(..))")
    public void youzanLog(){}

    @Before("youzanLog()")
    public void youzanBefore(JoinPoint point) {
        LOG.info("--开始执行类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"--");
        LOG.info("----开始打印参数列表:"+ Arrays.toString(point.getArgs()));
    }

    @AfterReturning(value = "youzanLog()",returning = "rtv")
    public void youzanAfter(JoinPoint point,Object rtv) {
        try{
            LOG.info("----取得目标返回值:"+rtv);
            LOG.info("--类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"执行结束--");
        }catch (Exception e){
            LOG.error("====错误信息："+e.getMessage());
        }
    }
}
