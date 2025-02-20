package org.senla.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeLoggerAspect {

    @Around("execution(* org.senla.service.*.*(..)) || execution(* org.senla.repository.*.*(..)) || execution(* org.senla.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long execution = System.currentTimeMillis() - startTime;

        log.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), execution);

        return result;
    }
}
