package com.purang.requried12;

import org.springframework.stereotype.Repository;

@Repository("repositoryDao")
public class TestHibernateDaoImpl {

	public void say(){
		System.out.println("TestHibernateDaoImpl");
	}
}
