package com.purang.requried12;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComponentDefinitionWithAnnotationTest {
	
	private static String configLocation = "com/purang/requried12/componentDefinitionWithAnnotation.xml";  
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);  
	@Test
	public void test() {
		TestCompoment testCon = ctx.getBean("component",TestCompoment.class);
		System.out.println(testCon.getCtx());
		Assert.assertNotNull(testCon.getCtx());  
	}
	@Test
	public void test1() {
		TestHibernateDaoImpl testCon = ctx.getBean("repositoryDao",TestHibernateDaoImpl.class);
		testCon.say();
		Assert.assertNotNull(testCon);  
	}
	@Test
	public void testDao() {
		TestServiceImpl testCon = ctx.getBean("testServiceImplff",TestServiceImpl.class);
		testCon.say();
		Assert.assertNotNull(testCon.getDao());  
	}
	@Test
	public void testWeb() {
		TestAction testCon = ctx.getBean("testActiont",TestAction.class);
		testCon.say();
		Assert.assertNotNull(testCon);
	}
}
