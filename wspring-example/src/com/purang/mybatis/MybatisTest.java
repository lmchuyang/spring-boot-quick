package com.purang.mybatis;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.purang.ibatis.IUserDao;
import com.purang.ibatis.UserModel;

public class MybatisTest {

	@Test
	public void test() {
		String[] configLaction = {"resourceJdbc.xml","com/purang/mybatis/applicationContext-mybatis.xml"};
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(configLaction);
		IUserDao userDao = context.getBean(UserMybatisDaoImpl.class);
		UserModel userModel = new UserModel();
		userModel.setName("hello world5");
		//userDao.save(userModel);
		System.out.println("=============: "+userDao.countAll());
	}

}
