package com.example.euraka.demoServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李名才
 * @create 2018/7/13.
 * @blog https://blog.csdn.net/limingcai168
 */
@RestController
public class HelloController {

	 @Autowired
	 DiscoveryClient discoveryClient;
	 
	   @RequestMapping("/hello")
	    public String hello() throws InterruptedException{
			Thread.sleep(9000L);
			 System.out.println("***************springcloud-euraka-client************hello:"+discoveryClient.getServices());
	        return "hello world";
	    }
	 
	   @RequestMapping("/world")
	    public String world(){
		   System.out.println("***************springcloud-euraka-client************world");
	        return "lmc world is beauful";
	    }
	   
	   @ResponseBody
	   @RequestMapping("/greeting")
	    public String greetingTest(@RequestBody String str) {
	        System.out.println("************springcloud-euraka-client***********greetingTest********"+str);
	        return "view_lmc_123456"+str;
	    }
	   
	   @ResponseBody
	   @RequestMapping("/hi")
	    public String hiTest(@RequestParam(value = "name")String name) throws InterruptedException {
		   Thread.sleep(6000L);
	        System.out.println("************hiTest****************"+name);
	        return "view_lmc_123456"+name;
	    }
	   @ResponseBody
	   @RequestMapping("/hi2")
	    public String hiTest2(@RequestParam(value = "name")String name) throws InterruptedException {
	        System.out.println("************hiTest2 no sleep****************"+name);
	        return "view_lmc_123456"+name;
	    }
}
