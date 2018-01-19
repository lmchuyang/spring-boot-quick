package com.purang.ibatis;



import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testSqlMapClientTemplate {

	@Test
	public void test() {
		String[] configLocations = new String[] {  
            "resourceJdbc.xml",
            "applicationContext-ibatis.xml"};  
    ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);  
    IUserDao userDao = ctx.getBean(IUserDao.class);  
    UserModel model = new UserModel();  
    model.setName("test888");  
    userDao.save(model);  
    Assert.assertEquals(1, userDao.countAll());  }

}
