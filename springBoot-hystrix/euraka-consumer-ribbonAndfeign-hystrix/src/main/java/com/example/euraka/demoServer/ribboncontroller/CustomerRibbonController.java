package com.example.euraka.demoServer.ribboncontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author 李名才
 * @param <R>
 * @create 2018/7/13.
 * @blog https://blog.csdn.net/limingcai168
 */
@RestController
public class CustomerRibbonController {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RibbonService  ribbonService;
	
	//这里写了一个service 层来表示业务逻辑，当然与下面worldCustomer的方式调用也是可以的，简洁一些
	// hellolmc 方法调用超时之后， 调用fallbackMethod = "hiError" 方法，自动熔断或降级
    @GetMapping("/hilmc")
    public String hellolmc(){
        return ribbonService.consumer();
    }
	
    //此方法可以正常调用，生产者并没有超时
	@RequestMapping(value = "/helloCustomer",method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod="fallback")
	public String worldCustomer(){
		return restTemplate.getForObject("http://springcloud-euraka-client/world", String.class);
	}
	
	public String fallback() {
		System.out.println("fallbackMethod worldCustomer");
        return "fallback Customer";
    }
	
	
	}
