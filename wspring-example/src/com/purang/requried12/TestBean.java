package com.purang.requried12;

import org.springframework.beans.factory.annotation.Required;

public class TestBean {

	private String message;
	@Required
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
