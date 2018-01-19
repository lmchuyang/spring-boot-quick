package com.purang.aop;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
	private static BeanFactory factory;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//factory 
		
		ApplicationContext context  = new ClassPathXmlApplicationContext("resourceAop.xml");
		//IHelloWorldService hello = context.getBean("helloWorldService",IHelloWorldService.class);
		HelloWorldService hello = (HelloWorldService) context.getBean("helloWorldService",HelloWorldService.class);
		
		hello.sayHelloWorld();
	}

}
