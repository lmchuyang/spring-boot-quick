package com.purang.hql;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.purang.jdbc.IUserDao;
import com.purang.jdbc.UserModel;


public class testBestPractice {
	
	@Test
	public void testBasce() {
		  String[] configLocations = new String[] {  
	                "resourceJdbc.xml",  
	                "applicationContext-hibernate.xml"};  
	        //sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);  
		    @SuppressWarnings("resource")
			ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);  
		    IUserDao userDao = ctx.getBean(IUserDao.class);  
		    UserModel model = new UserModel();  
		    model.setName("test1");  
		    userDao.save(model);  
		    Assert.assertEquals(1, userDao.countAll()); 
	}
   
}
