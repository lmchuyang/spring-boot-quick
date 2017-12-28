package org.spring.springboot.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot HelloWorld 案例
 *
 * Created by bysocket on 16/4/26.
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String sayHello() {
        return "这个世界很完美,Hello,World!";
    }
    @RequestMapping("/hello/{name}")  //@PathVariable 必需加修饰属性
    public String sayHelloName(@PathVariable String name) {
        return "这个世界很完美,Hello,World!"+name;
    }
}
