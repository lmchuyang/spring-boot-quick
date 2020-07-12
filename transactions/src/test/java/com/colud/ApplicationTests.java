/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package com.colud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    //@Resource
    // private RedisTemplate redisTemplate;

    // @Resource
    // private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        //redisTemplate.opsForValue().set("springboot2.1", "druid0.16");
    }

    @Test
    public void test1() {
        //  redisTemplate.opsForValue().set("c", "c");
        //  redisTemplate.opsForValue().set("springboot2.1", "druid0.16");
        System.out.println("redis ");
        List st= new ArrayList();
        st.add("1");
        st.add("2");
        System.out.println(st.size());
    }
}
