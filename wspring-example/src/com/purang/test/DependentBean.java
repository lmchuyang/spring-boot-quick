package com.purang.test;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

public class DependentBean {

	@Autowired
	ResourceBean resource;
	public void write(String ss) throws IOException {
		System.out.println("DependentBean:=======写资源");  
		resource.getFos().write(ss.getBytes());
	}
	//初始化方法 
	public void init() throws IOException {
		System.out.println("DependentBean:=======初始化");
		resource.getFos().write("DependentBean:=======初始化=====".getBytes());
	}
	//销毁方法  
	public void destroy() throws IOException {
		 System.out.println("DependentBean:=======销毁");  
		 //在销毁之前需要往文件中写销毁内容  
		 resource.getFos().write("DependentBean:=======销毁=====".getBytes());  
	}
	
}
