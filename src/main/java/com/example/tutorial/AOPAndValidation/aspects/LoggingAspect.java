package com.example.tutorial.AOPAndValidation.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("@annotation(com.example.tutorial.annotations.Log)")
    public void logPointcut(){
    }

    @Pointcut("execution(* com.example.tutorial.services.UserService.addUser(..))")
    public void addNamePointcut(){
    }

    @Pointcut("execution(* com.example.tutorial.services.UserService.updateUser(..))")
    public void updateNamePointcut(){
    }

    @Pointcut("execution(* com.example.tutorial.services.UserService.deleteUser(..))")
    public void deleteNamePointcut(){
    }

    @Before("logPointcut()")
    public void logAllMethodCallsAdvice(){
        System.out.println("In Aspect");
    }
    @Before("addNamePointcut()")
    public void logAddNameMethodCallAdvice(){
        System.out.println("Logging add name method call");
    }

    @After("updateNamePointcut()")
    public void logUpdateNameMethodCallAdvice(){
        System.out.println("Logging update name method call");
    }

    @Around("deleteNamePointcut()")
    public Object deleteNameAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            // Before the target method is executed
            System.out.println("Before deleting name");

            // Execute the target method
            result = joinPoint.proceed();

            // After the target method is executed
            System.out.println("After deleting name");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}