package com.purang.test;


import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

public class ResourceBean {

	private FileOutputStream fos;
	private File file;
	//初始化方法
	public void init() {
		System.out.println("ResourceBean:==============初始化");
		System.out.println("resourcebena===========加载资源，执行一些预操作");
		try {
			this.fos = new FileOutputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//销毁资源方法
	public void destroy(){
		System.out.println("ResourceBean:==================开始销毁");
		System.out.println("ResourceBean:==================释放资源，执行一些预操作");
		try {
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FileOutputStream getFos() {
		return fos;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}

