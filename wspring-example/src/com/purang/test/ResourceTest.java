package com.purang.test;

import java.io.IOException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResourceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("resource.xml");
		//一定要注册销毁回调，否则我们定义的销毁方法不执行   
		((AbstractApplicationContext) beanFactory).registerShutdownHook();  
		DependentBean dBean = (DependentBean) beanFactory.getBean("dependentBean");
		try {
			dBean.write("aaaaaaaaaaaaaaaaaaaaaaas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
