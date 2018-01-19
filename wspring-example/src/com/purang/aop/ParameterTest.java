package com.purang.aop;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ParameterTest {

	@Test
	public void test() {
		 System.out.println("======================================");
		   ApplicationContext ctx = new ClassPathXmlApplicationContext("resourceparameter.xml");  
		    IHelloWorldService hello = ctx.getBean("helloWorldService", IHelloWorldService.class);  
		    IPointcutService ipo = ctx.getBean("pointcutService",IPointcutService.class);
		//使用JoinPoint获取参数
		 hello.sayBefore("parameter");
	     System.out.println("======================================");
	     
	     //自动获取
	     ipo.test("parameter_test");
	     System.out.println("======================================");
	}

}
