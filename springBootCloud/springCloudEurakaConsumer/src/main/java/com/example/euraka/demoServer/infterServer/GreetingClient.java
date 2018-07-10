package com.example.euraka.demoServer.infterServer;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//Feign Client 调用微服务程序，接口方式实现,可以很好的将微服务分开，松耦合
@FeignClient("springCloudEurakaProvider")
public interface GreetingClient {
	/**
     * 这里有两个坑需要注意：
     *
     * 1、这里需要设置请求的方式为 RequestMapping 注解，用 GetMapping 注解是运行不成功的，即 GetMapping 不支持。
     *
     * 2、注解 PathVariable 里面需要填充变量的名字，不然也是运行不成功的。
     * 3、这一方法主要就是为了绑定Controller里，访问url的方法，控制层通过方法去请求找到相应的URL方法
     * 通过接口方式绑定远程微服务
     */
	/* @RequestMapping(value="/greeting",method = RequestMethod.POST)
	 String greeting(JSONObject parpam);*/
	
	 @PostMapping(value="/greeting")//传参数的方式调用
	 String greeting(String str);
	 
}
