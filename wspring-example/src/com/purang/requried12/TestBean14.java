package com.purang.requried12;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TestBean14 {
	   private String message;  
	   private String mess;
	    private List<String> list;  
	    @Autowired(required = true) //任意一个或多个参数方法注入  
	    private void initMessage(String message,String mess, ArrayList<String> list) {  
	        this.message = message;  
	        this.mess = mess;
	        this.list = list;  
	    }
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List<String> getList() {
			return list;
		}
		public void setList(List<String> list) {
			this.list = list;
		}
		public String getMess() {
			return mess;
		}
		public void setMess(String mess) {
			this.mess = mess;
		}  
	    
}
