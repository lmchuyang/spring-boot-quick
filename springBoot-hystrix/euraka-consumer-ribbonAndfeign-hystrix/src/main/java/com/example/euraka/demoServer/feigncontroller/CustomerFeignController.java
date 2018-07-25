package com.example.euraka.demoServer.feigncontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.euraka.demoServer.infterServer.GreetingClient;

/**
 * @author 李名才
 * @create 2018/7/13.
 * @blog https://blog.csdn.net/limingcai168
 */
@RestController
public class CustomerFeignController {
	
	@Autowired
	EurekaClientFeign eurekaclient;
	//超时调用fallback 的方法，如果禁止重试，也会调用两次，如果配置文件有调用其它实例，至少会执行3次以上
	//所以在写接口的时候，需要对接口增加幂等性
    @GetMapping("/hi")
    public String sayHi(@RequestParam(defaultValue = "cralor",required = false)String name){
        return eurekaclient.sayHiFromClientEureka(name);
    }
    //正常执行调用
    @GetMapping("/hi2")
    public String sayHi2(@RequestParam(defaultValue = "lmc",required = false)String name){
        return eurekaclient.sayHiFromClientEureka2(name);
    }
    @Autowired
    private GreetingClient greetingClient;
    @RequestMapping("/get-greeting")
    public String greeting(Model model) {
	   Map<String, String> parpm= new HashMap<String, String>();
		  parpm.put("name", "lmc");
		  parpm.put("test", "demo");
		String message =  greetingClient.greeting(parpm.toString()); //以字符串方式传参数
        return message+":传参数";
    }
	   
}
