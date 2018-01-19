package com.purang.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AoppTest {

	@Test
	public void testHelloworld() {
		@SuppressWarnings("resource")
		ApplicationContext context  = new ClassPathXmlApplicationContext("resourceAop.xml");//resourceAop
		HelloWorldService hello = context.getBean("helloWorldService",HelloWorldService.class);
				hello.sayHelloWorld();
	}

}
