package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class ElasticDemoApplication {

	
	//@PostConstruct  被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
	//PostConstruct在构造函数之后执行,init()方法之前执行
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticDemoApplication.class, args);
	}

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.cluster}")
	private String clusterName;
	
/*	@Value("${elasticsearch.nodename}")
	private String nodeName;*/

	@Value("${elasticsearch.port}")
	private int port;

	@Bean
	public Client client() throws UnknownHostException {
		System.out.println("\n\n\t\t\t ---------- init elastic client ----------\n\n\n");
		
		Settings settings = Settings.builder().put("cluster.name", clusterName)//.put("node.name", nodeName)
				.put("client.transport.sniff", true).build();

		@SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
		return client;
	}

	@PostConstruct
	public void initFrist(){
		System.out.println("这是服务启动后， 自动加载的方法");
	}
}
