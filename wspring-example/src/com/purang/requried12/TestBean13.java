package com.purang.requried12;

import org.springframework.beans.factory.annotation.Autowired;

public class TestBean13 {

	
	private String message;

	public String getMessage() {
		return message;
	}
	@Autowired
	public void setMessage(String message) {
		this.message = message;
	}
}
