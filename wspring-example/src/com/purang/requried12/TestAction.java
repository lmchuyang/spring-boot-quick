package com.purang.requried12;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Controller
public class TestAction {
	@Autowired
	TestServiceImpl testService;
	
	public void say() {
		System.out.println("Not yet implemented");
	}

}
