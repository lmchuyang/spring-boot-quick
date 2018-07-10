package com.example.euraka.demoServer.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//模拟的时候，当作远程微服务
@RestController
public class HelloController {

	   @RequestMapping("/hello")
	    public String hello(){
		 System.out.println("***************springCloudEurakaProvider1************hello");
	        return "hello world";
	    }
	 
	   @RequestMapping("/world")
	    public String world(){
		   System.out.println("***************springCloudEurakaProvider1************world");
	        return "lmc world is beauful";
	    }
	   
	 //使用Spring Netflix Feign Client实现的REST消费Web应用，远程微服务的接口，本质的接口都 一样,微服务调用只跟URL有关，跟方法名无关
	   @ResponseBody
	   @RequestMapping("/greeting")
	    public String greetingTest(@RequestBody String str) {
	        System.out.println("************springCloudEurakaProvider1***********greetingTest********"+str);
	        return "view_lmc_123456"+str;
	    }
	   
	   
}
