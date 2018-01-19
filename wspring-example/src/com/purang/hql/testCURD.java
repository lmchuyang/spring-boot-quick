package com.purang.hql;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class testCURD {

	@SuppressWarnings("resource")
	@Test
	public void test() {
		  String[] configLocations = new String[] {  
				  "classpath:resourceJdbc.xml",  
		            "classpath:applicationContext-hibernate2.xml"};  
		    ApplicationContext ctx =  new ClassPathXmlApplicationContext(configLocations);  
		    HibernateTemplate hibernateTemplate =  ctx.getBean(HibernateTemplate.class);  
		    UserModel2 model = new UserModel2();  
		   // model.setMyName("test");  
		    //insert(hibernateTemplate, model);  
		   // select(hibernateTemplate, model);  
		    update(hibernateTemplate, model);  
		    //delete(hibernateTemplate, model);  
	}
	private void insert(HibernateTemplate hibernateTemplate, UserModel2 model) {  
	    hibernateTemplate.save(model);  
	}  
	private void select(HibernateTemplate hibernateTemplate, UserModel2 model) {  
	    UserModel2 model2 = hibernateTemplate.get(UserModel2.class, 1);  
	    Assert.assertEquals(model2.getMyName(), model.getMyName());  
	    @SuppressWarnings("unchecked")
		List<UserModel2> list = hibernateTemplate.find(" from UserModel2 ");  
	    Assert.assertEquals(list.get(0).getMyName(), model.getMyName());  
	    System.out.println("size:"+list.size());
	}  
	private void update(HibernateTemplate hibernateTemplate, UserModel2 model) {  
		 model.setId(9);
	    model.setMyName("789456test");  
	    hibernateTemplate.saveOrUpdate(model);  
	}  
	private void delete(HibernateTemplate hibernateTemplate, UserModel2 model) {  
		model.setId(1);
	    hibernateTemplate.delete(model);  
	}  
}
