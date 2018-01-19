package com.purang.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MethodInjectTest {

	private static BeanFactory factory;

	public static void main(String[] args) {
		factory = new ClassPathXmlApplicationContext("resourceLookup.xml");
		 System.out.println("=======singleton sayHello======");  
		 HelloApi helloApi = factory.getBean("helloApi1",HelloApi.class);
		 helloApi.sayHello();
		 helloApi = factory.getBean("helloApi1",HelloApi.class);
		 helloApi.sayHello();
		 System.out.println("=======prototype sayHello======");  
		 HelloApi helloApi2 = factory.getBean("helloApi2",HelloApi.class);
		 helloApi2.sayHello();
		 helloApi2 = factory.getBean("helloApi2",HelloApi.class);
		 helloApi2.sayHello();
	}

}
