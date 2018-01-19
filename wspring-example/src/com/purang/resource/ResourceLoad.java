package com.purang.resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;

public class ResourceLoad {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("resourceLoad.xml");
		ResourceBean bean =context.getBean(ResourceBean.class);
		ResourceLoader loader = (ResourceLoader) bean.getReLoad();
		 System.out.println(loader instanceof ApplicationContext);  
		 
		//ResourceLoad ld = new ResourceLoad();
		//ld.testResourceLoad();
	}
	
	
	public void testResourceLoad() {
	 ResourceLoader load = new DefaultResourceLoader();
	 Resource resource = load.getResource("classpath:com/purang/resource/text1.txt");
	 Assert.isAssignable(ClassPathResource.class, resource.getClass());  
	 
	 Resource resource2 = load.getResource("file:com/purang/resource/text1.txt");
	 Assert.isAssignable(UrlResource.class, resource2.getClass());  
	  
	 Resource resource3 = load.getResource("com/purang/resource/text1.txt");
	  Assert.isTrue(resource3 instanceof ClassPathResource);  
	}
	
}
