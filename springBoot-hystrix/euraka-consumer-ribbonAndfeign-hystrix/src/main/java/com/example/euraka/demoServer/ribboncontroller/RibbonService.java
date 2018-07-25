package com.example.euraka.demoServer.ribboncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class RibbonService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String consumer() {
        return restTemplate.getForObject("http://springcloud-euraka-client/hello", String.class);
    }

    public String hiError() {
        return "fallbackCall";
    }

}
