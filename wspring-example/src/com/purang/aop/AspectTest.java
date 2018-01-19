package com.purang.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectTest {

	@Test
	public void test() {
		   System.out.println("======================================");  
		    ApplicationContext ctx = new ClassPathXmlApplicationContext("resourceAspect.xml");  
		    IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);  
		   // helloworldService.sayBefore("before"); 
		    helloworldService.sayAfterReturning("after");
		    //helloworldService.sayAfterThrowing(new Exception());
		    System.out.println("======================================");  
	}

}
