package com.purang.requried12;

import org.springframework.beans.factory.annotation.Autowired;

public class TestBean12 {

	@Autowired//(required=false)//设置false 就是注解的时候，可以不用构造方法，默认为true，必须有构造方法， 配置文件里<constructor-arg index="0" value="hello app"/>
	private String message;
	@Autowired
	private String messeed;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMesseed() {
		return messeed;
	}

	public void setMesseed(String messeed) {
		this.messeed = messeed;
	}
	
}
