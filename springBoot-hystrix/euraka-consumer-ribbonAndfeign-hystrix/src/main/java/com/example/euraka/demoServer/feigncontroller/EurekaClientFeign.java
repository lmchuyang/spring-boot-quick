package com.example.euraka.demoServer.feigncontroller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "springcloud-euraka-client",fallback = HiHystrix.class)
public interface EurekaClientFeign {

    /**
     * 绑定 springcloud-euraka-client 服务的 /hi/ 接口
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name")String name);
    
    @GetMapping(value = "/hi2")
    String sayHiFromClientEureka2(@RequestParam(value = "name")String name);
    
}