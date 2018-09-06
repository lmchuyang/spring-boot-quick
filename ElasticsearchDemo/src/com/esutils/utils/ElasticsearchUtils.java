package com.esutils.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

/**
 * @author lmc
 * @create 2018/7/13.
 * @blog https://blog.csdn.net/limingcai168
 */

public class ElasticsearchUtils {

	public static final String INDEX_NAME = "java_demo_index";
	public static final String TYPE_NAME = "java_demo_type";
	
	private static TransportClient  client;
	
	public static TransportClient getEsClient(){
		//的是没有安装x-pack插件连接方式
		Settings settings = Settings.builder().put("cluster.name","elasticsearch")//设置ES实例的名称
          .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
          .build();
/* 其它参数设置  client.transport.ignore_cluster_name  //设置 true ，忽略连接节点集群名验证
		client.transport.ping_timeout       //ping一个节点的响应时间 默认5秒
		client.transport.nodes_sampler_interval //sample/ping 节点的时间间隔，默认是5s
*/		
	    /**
         * 这里的连接方式指的是没有安装x-pack插件,如果安装了x-pack则参考{@link ElasticsearchXPackClient}
         * 1. java客户端的方式是以tcp协议在9300端口上进行通信
         * 2. http客户端的方式是以http协议在9200端口上进行通信
         */
		client = new PreBuiltTransportClient(settings);//连接方式不一样，与x-pack插件有关
		try {
		 //client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.11.237"),9300));
		   client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.11.237"),9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*这里的连接方式指的是服务器端安装x-pack插件
		 * Settings settings = Settings.builder().put("cluster_name","elasticsearch")// //设置ES实例的名称，单机就是自己的名字
				.put("client.transport.sniff",true)//探测集群中机器状态，自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
				.put("xpack.security.transport.ssl.enabled",false)
				.put("xpack.security.user","elastic:changeme")
				.build();
				client = new PreBuiltXPackTransportClient(settings);
				*/
				
		  System.out.println("ElasticsearchClient 连接成功");
		return client;
	}
	public static void closeClient(){
		if(client!=null){
			client.close();
		}
	}
	public static String getIndexName(){
		return INDEX_NAME;
	}
	public static String getTypeName(){
		return TYPE_NAME;
	}
}
