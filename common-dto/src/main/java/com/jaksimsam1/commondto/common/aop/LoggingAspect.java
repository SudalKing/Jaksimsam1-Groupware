package com.jaksimsam1.commondto.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("Before Clas: {}, Before Method: {}", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
    }

    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        log.info("After Clas: {}, After Method: {}", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
    }

    @Before("execution(* com.jaksimsam1.userservice..*Controller.*(..)) && args(..,bindingResult)")
    public void validate(JoinPoint joinPoint, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            throw
        }
    }
}
