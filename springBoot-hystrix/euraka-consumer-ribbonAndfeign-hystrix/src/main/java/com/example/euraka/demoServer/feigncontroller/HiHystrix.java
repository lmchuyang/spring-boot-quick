package com.example.euraka.demoServer.feigncontroller;

import org.springframework.stereotype.Component;

@Component
public class HiHystrix implements EurekaClientFeign {

    @Override
    public String sayHiFromClientEureka(String name) {
        return "hi,"+name+",sorry.error!";
    }

	@Override
	public String sayHiFromClientEureka2(String name) {
		  return "hi2,"+name+",sorry.error! Hi2";
	}
}