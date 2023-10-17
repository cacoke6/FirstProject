package com.example.basic.Member.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerAspect {
    long start = 0L;
    long end = 0L;

    @Before(value = "execution (* com.example.basic.Member.controller.*.*(..))")
    public void onBeforeHandler(
            JoinPoint joinPoint) {
        log.warn("@Before run");
        start = System.currentTimeMillis();
    }

    @After(value = "execution (* com.example.basic.Member.controller.*.*(..))")
    public void onAfterHandler(JoinPoint joinPoint) {
        log.warn("@After run");
        end = System.currentTimeMillis();
        log.warn(end - start + "");
    }

    @After(value = "execution (* com.example.basic.Member.util.*.*(..))")
    public void amugona(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.warn(  name  );
    }


    @AfterReturning(
            value = "execution (* com.example.basic.Member.util.*.*(..))",
            returning = "data")
    public void onAfterReturningHandler(JoinPoint joinPoint, Object data) {
        if (data != null) {
            log.warn(data.toString());
        }
        log.debug("@AfterReturning run");
    }

}
