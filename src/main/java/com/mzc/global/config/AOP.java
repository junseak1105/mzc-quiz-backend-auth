package com.mzc.global.config;

import com.mzc.global.Response.DefaultRes;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class AOP {

    @Before("execution(* com.mzc.quiz..controller..*.*(..))")
    public void logBefore() {
        log.info("=================Request Start==================");
    }

    @Around("execution(* com.mzc.quiz..controller..*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        log.info("Target: " + pjp.getTarget());
        log.info("Param: " + Arrays.toString(pjp.getArgs()));


        //invoke method
        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        log.info("TIME: "  + (end - start));

        return result;
    }

    @AfterReturning(pointcut = "execution(* com.mzc.quiz..controller..*.*(..)))", returning = "result")
    public void logReturn(DefaultRes result) {
        log.info("----------------------");
        log.info("statuscode: " + result.getStatusCode());
        log.info("responsemessage: " + result.getResponseMessage());
        log.info("data: " + result.getData());
        log.info("=================Request End==================");
    }
}
