package com.purang.transaction.project;


import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestTransactionDaoSerice {
private final String CREATE_TABLE_USER=" CREATE TABLE user( " +  
    " id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,  " +  
    " name varchar(100))";

private final String DROP_TABLE_USER="drop table user";

private final String CREATE_ADDRESS_TABLE_SQL ="CREATE TABLE address( id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,  province varchar(100), city varchar(100), street varchar(100), user_id int)";
private final String DROP_TABLE_ADDRESS=" drop table address ";
@Test
	public void testServiceTransaction() {
		String[] location = new String[]{"resourceJdbc.xml","com/purang/transaction/project/applicationContext-jdbc.xml","com/purang/transaction/project/applicationContext-service.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(location);
		DataSource dataSource =ctx.getBean(DataSource.class);
		JdbcTemplate template = new JdbcTemplate(dataSource);
		//template.update(CREATE_TABLE_USER);
		//template.update(CREATE_ADDRESS_TABLE_SQL);
		
		IUserService userService = ctx.getBean("userService",IUserService.class);
		IAddressService addressService = ctx.getBean("addressService",IAddressService.class);
		UserModel user = createDefaultUserModel();
		try {
			userService.save(user);
			 Assert.fail(); 
		} catch (RuntimeException e) {
		}
		System.out.println("====="+userService.countAll());
		System.out.println("====="+addressService.countAll());
		
		//template.update(DROP_TABLE_USER);  
		//template.update(DROP_TABLE_ADDRESS);  
	
	}
	private UserModel createDefaultUserModel() { 
		UserModel user = new UserModel();
		user.setName("testName_Project");
		AddressModel address = new AddressModel();
		address.setProvince("上海");
		address.setCity("shanghai");
		address.setStreet("唐镇 ");
		user.setAddress(address);
		return user;
	}

}
