package com.purang.requried12;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class DependencyInjectWithAnnotationTest {
	
    private static String configLocation1 = "com/purang/requried12/dependecyInjectWithAnnotation.xml";
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation1);

	//private static String str = "classpath:com/purang/requried12/dependecyInjectWithAnnotation.xml";
	@Test
	public void test() {
		TestBean testBean = ctx.getBean("testBean",TestBean.class);
		testBean.setMessage("testBean");
		System.out.println("==========="+testBean.getMessage());
		 Assert.assertEquals("testBean", testBean.getMessage());  
	}
	@Test
	public void testBean11() {
	    System.out.println("************************");
		TestBean11 testBean11 = ctx.getBean("testBean11",TestBean11.class);
		System.out.println("==========="+testBean11.getMessage());
		 Assert.assertEquals("testBean11", testBean11.getMessage());  
	}
	@Test
	public void testBean12() {
		TestBean12 testBean12 = ctx.getBean("testBean12",TestBean12.class);
		testBean12.setMesseed("789456");
		System.out.println("==========="+testBean12.getMessage());
		System.out.println("==========="+testBean12.getMesseed());
		 Assert.assertEquals("hello app", testBean12.getMessage());  
	}
	@Test
	public void testBean13() {
		TestBean13 testBean13 = ctx.getBean("testBean13",TestBean13.class);
		System.out.println("******testBean13*****"+testBean13.getMessage());
		 Assert.assertEquals("hello app", testBean13.getMessage());  
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testBean14() {
		TestBean14 testBean14 = ctx.getBean("testBean14",TestBean14.class);
		List<String> lst = ctx.getBean("list", List.class);
		lst.add("45612");lst.add("888888");
		System.out.println("***testBean14*******"+ctx.getBean("list", List.class));
		 Assert.assertEquals("hello app", testBean14.getMessage());  
		 Assert.assertEquals(ctx.getBean("list", List.class), testBean14.getList());
	}
}
