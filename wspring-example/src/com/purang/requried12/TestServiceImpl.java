package com.purang.requried12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("testServiceImplff")//默认不写的话 是对象的小写作为bean (testServiceImpl),否则以定义的为对象bean
public class TestServiceImpl {

	@Autowired
	@Qualifier("repositoryDao")
	private TestHibernateDaoImpl dao;
	public TestHibernateDaoImpl getDao() {  
        return dao;  
    }
	public void say(){
		System.out.println("testServiceImplff");
	}
}
