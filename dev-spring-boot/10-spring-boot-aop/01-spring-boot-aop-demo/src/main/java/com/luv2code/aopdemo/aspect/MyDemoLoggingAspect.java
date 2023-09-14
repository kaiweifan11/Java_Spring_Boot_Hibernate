package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    // this is where we add all of our related advices for logging

    // let's start with an @Before advice

    // matches on package name
    @Before("execution(* com.luv2code.aopdemo.dao.*.*(..))")
    public void beforeAddAccountAdvice() {
        System.out.println("\n======>>> Executing @Before advice on addAccount()");
    }

    // matches on params
    // .. means any number of params
//    @Before("execution(* add*(..))")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }
//    @Before("execution(* add*(com.luv2code.aopdemo.Account, ..))")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }

    // matches on return type
    // return types are optional,
    // so not adding the return type, matches on any type
//    @Before("execution(void add*())")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }

    // matches on any add*()
//    @Before("execution(public void add*())")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }

    // matches on class AccountDAO only
//    @Before("execution(public void com.luv2code.aopdemo.dao.AccountDAO.addAccount())")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }

    // matches on any function call addAccount()
//    @Before("execution(public void addAccount())")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on addAccount()");
//    }

    // Pointcut expressions
    // eg. "execution(public void addAccount())"
//    @Before("execution(public void updateAccount())")
//    public void beforeAddAccountAdvice() {
//        System.out.println("\n======>>> Executing @Before advice on updateAccount()");
//    }
}
