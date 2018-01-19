package com.purang.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@org.aspectj.lang.annotation.Aspect
public class Aspect {
	
	@Pointcut(value="execution(* com.purang..*.sayBefore(..)) && args(param)", argNames = "param")  
	public void beforePointcut(String param) {}  
	
	@Before(value = "beforePointcut(param)", argNames = "param")  
	public void beforeAdvice(String param) {  
	    System.out.println("===========before advice param:" + param);  
	}  
	@After(value="execution(* com.purang..*.sayAfterReturning(..)) && args(param)",argNames="param")
	public void afterReturningAdvice(Object retVal){
		 System.out.println("===========after returning advice retVal:" + retVal);  
	}
	public void afere(String param) {  
	    System.out.println("===========before advice param:" + param);  
	} 
	@AfterThrowing(value="execution(* com.purang..*.sayAfterThrowing(..))",argNames="exception", throwing="exception")
	public void afterThrowingAdvice(Exception exception){
		 System.out.println("===========after throwing advice exception:" + exception); 
		}
	
}
