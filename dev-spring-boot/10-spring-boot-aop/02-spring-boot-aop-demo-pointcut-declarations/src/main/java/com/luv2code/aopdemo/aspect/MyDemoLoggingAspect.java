package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

// @Order determines the order the aspects run
// can be negative numbers
@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
//    @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
//    private void forDaoPackage() {}
//
//    // create a pointcut for getter methods
//    @Pointcut("execution(* com.luv2code.aopdemo.dao.*.get*(..))")
//    private void getter() {}
//
//    // create a pointcut for setter methods
//    @Pointcut("execution(* com.luv2code.aopdemo.dao.*.set*(..))")
//    private void setter() {}
//
//    // create pointcut: include package ... exclude getter/setter
//    @Pointcut("forDaoPackage() && !(getter() || setter())")
//    public void forDaoPackageNoGetterSetter() {}

    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {

        // print out which method we are advising on
        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @Around on method: " + method);

        // get begin timestamp
        long begin = System.currentTimeMillis();

        // now, let's execute the method
        Object result = null;


        try {
            result = theProceedingJoinPoint.proceed();
        } catch (Exception exc) {
            // log the exception
            System.out.println(exc.getMessage());

            // handle the exception and give user a custom message
            // result = "Major accident! But no worries, your private AOP helicopter is ib the way!";

            // rethrow exception to main app
            throw exc;
        }

        // get end timestamp
        long end = System.currentTimeMillis();

        // compute duration and display it
        long duration = end - begin;
        System.out.println("\n======> Duration: " + duration /1000.0 + " seconds");

        return result;
    }

    // @After runs after the method no matter whether an exception is thrown or not
    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After (finally) on method: " + method);
    }

    @AfterThrowing(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc"
    )
    public void afterThrowingFindAccountsAdvice(
            JoinPoint theJoinPoint, Throwable theExc)
    {
        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterThrowing on method: " + method);

        // log the exception
        System.out.println("\n=====>>> The exception is: " + theExc);
    }

    // add a new advice for @AfterReturning on the findAccounts method
    // returning must have same name as the param i.e. "result"
    @AfterReturning(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result" )
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

        // print out the results
        System.out.println("\n=====>>> result is: " + result);

        // let's post-process the data ... let's modify it :-)

        // convert the account names to uppercase
        convertAccountNamesToUpperCase(result);

        System.out.println("\n=====>>> result is: " + result);
    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        // loop through the accounts
        for(Account tempAccount: result) {
            // get uppercase version of name
            String theUpperName = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperName);
        }
    }

    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {

        System.out.println("\n======>>> Executing @Before advice on method");

        // JoinPoint
        // display the method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();

        System.out.println("Method: " + methodSignature);

        // display method arguments
        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop through args
        for(Object tempArg: args) {
            System.out.println(tempArg);

            if(tempArg instanceof Account) {

                // downcast and print Account specific stuff
                Account theAccount = (Account) tempArg;

                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }
        }
    }

    // matches on package name
//    @Before("forDaoPackage()")
//    public void performApiAnalytics() {
//        System.out.println("\n=====>> Performing API Analytics");
//    }

//    @Before("forDaoPackageNoGetterSetter()")
//    public void logToCloudAsync() {
//
//        System.out.println("\n=====>> Logging to Cloud in async fashion");
//    }

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
