package com.example.euraka.demoServer.controller;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.euraka.demoServer.infterServer.GreetingClient;

@RestController
public class CustomerController {

	//微服务之间调用的方式有两种，RestTemplate+ribbon 实现负载均衡，第二种是Feign Client
	//spring-cloud-starter-ribbon的依赖用于客户端负载均衡，默认是轮询的方式。
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/helloCustomer")
	public String helloCustomer(){
		return restTemplate.getForEntity("http://springCloudEurakaProvider/hello", String.class).getBody();
	}
	
	@GetMapping("/customerWorld")
	public String worldCustomer(){
		
		return restTemplate.getForEntity("http://springCloudEurakaProvider/world", String.class).getBody();
	}
	
	//使用Spring Netflix Feign Client实现的REST消费Web应用，本地服务调用远程微服务的接口
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
	   
	   //微服务之间调用有三种方式 1,restTemplate+Ribbon, 2,Feign(以接口方式调用)  3,Http 前两种有负载均衡，第三种没有负载均衡
	   public class SalesService {
	   	@Autowired
	   	RestTemplate restTemplate;
	   //	@Autowired
	   //	UserFeignClient userFeignClient;
	   	private static final String RIBBON_URL = "http://user:8082/user/getUserInfo";
	   	private static final String HTTP_URL = "http://127.0.0.1:8082/user/getUserInfo";
	   	private static final String IP =""; //IpUtil.getIp();
	   	public String queryGoodsListByRibbon() {
	   		String sales_result = "queryGoodsListByRibbon success : [sales_ip:" + IP + "] ";
	   		String result = restTemplate.getForObject(RIBBON_URL, String.class);
	   		return sales_result + result;
	   	}

	   	public String queryGoodsListByFeign() {
	   		String sales_result = "queryGoodsListByFeign success : [sales_ip:" + IP + "] ";
	   		//String result = (String) userFeignClient.getUserInfo();
	   		return sales_result;// + result;
	   	}

	   	public String queryGoodsListByHttp() {
	   		String sales_result = "queryGoodsListByHttp success : [sales_ip:" + IP + "] ";
	   		//String result = HttpClientUtil.doGet(HTTP_URL);
	   		return sales_result;// + result;
	   	}
	   }
	   
}
