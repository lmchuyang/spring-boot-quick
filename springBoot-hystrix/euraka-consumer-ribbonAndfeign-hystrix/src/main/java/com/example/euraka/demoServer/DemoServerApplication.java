package com.example.euraka.demoServer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import feign.Retryer;

@SpringBootApplication
@Configuration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
//@EnableCircuitBreaker
public class DemoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServerApplication.class, args);
	}
	
/*	SpringCloud重试机制配置
	首先声明一点，这里的重试并不是报错以后的重试，而是负载均衡客户端发现远程请求实例不可到达后，去重试其他实例。
	其实也是Ribbon+RestTemplate的重试机制，因为是SpringCloud默认方式 
         对于整合了Ribbon的RestTemplate，例如一个RestTemplate添加了@LoadBalanced 注解
	*/
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		/*HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		  httpRequestFactory.setReadTimeout(3000);
		  httpRequestFactory.setConnectTimeout(2000);
		return new RestTemplate(httpRequestFactory);*/
	    return new RestTemplate();
	}
	//feign重试机制，feign默认是通过自己包下的Retryer进行重试配置,默认是5次，优先配置文件配置的重试策略
	//如果没有配置文件，下面这样设置至少也会重试两次，本实例之外的一次调用
    @Bean
    public Retryer feignRetryer(){
       // return new Retryer.Default(100,TimeUnit.SECONDS.toMillis(1),5);//默认是5次
        return Retryer.NEVER_RETRY; //feign取消重试
    }
    
}
