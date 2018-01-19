package com.purang.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testBestPractice {

	@Test
	public void test() {
		 String[] configLocations = new String[] {  
		            "resourceJdbc.xml",  
		            "applicationContext-jdbc.xml"};  
			ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);  
		    IUserDao userDao = (IUserDao) ctx.getBean("userDao");  
		    UserModel model = new UserModel();  
		    
		    Map<String, Object> paramMap = new HashMap<String, Object>();  
		    paramMap.put("id", "100252"); 
		    paramMap.put("name", "testname");
		    
		    userDao.save(paramMap);
		      
		    model.setId(10501);
		    model.setName("test01"); 
		    //userDao.save(model);
		  
		    System.out.println("========: "+userDao.countAll());
		     Assert.assertEquals(2, userDao.countAll());  
	}

}
