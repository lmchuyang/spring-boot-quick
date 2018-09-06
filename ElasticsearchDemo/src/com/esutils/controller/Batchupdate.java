package com.esutils.controller;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.action.bulk.BulkProcessor;


import com.esutils.utils.ElasticsearchUtils;

//批量操作， 处理大数量的时候，尽量用批处理
public class Batchupdate {

	public void MultiGetResponeTest(){
		//批量查询条件可以封装成动态数据，
		Client client = ElasticsearchUtils.getEsClient();
		MultiGetResponse multiGetResponse = client.prepareMultiGet()
				.add("multiGet_index","multiget_type","1")//一个id的方式
				.add("multiGet_index","multiget_type","1","2","3")//多个id的方式
				.add("another_index","another_type","foo")//可以从另外一个索引获取
				.get();
		
		for(MultiGetItemResponse multRespone:multiGetResponse){//迭代返回值
			GetResponse response  = multRespone.getResponse();
			if(response.isExists()){//判断是否存在       
				String json = response.getSourceAsString();  //_source 字段
				System.out.println(json);
			}
			
		}
	}
	
	//Bulk API，批量插入
	public void BulkInsertTest() throws IOException{
		
		Client client = ElasticsearchUtils.getEsClient();
		XContentBuilder jsonbuilder = XContentFactory.jsonBuilder();
		//批量创建索引 再插入数据,同一个对象实例
		BulkRequestBuilder bulk = client.prepareBulk();
		
		bulk.add(client.prepareIndex("bulk_index", "bulk_type", "1")
				.setSource(jsonbuilder.startObject().
						field("user","lmcname")
						.field("postDate",new Date())
						.field("message","trying out ElasticSearch")
						.endObject()
						)
				);
		//批量创建索引，再插入数据(2)
		XContentBuilder jsonbuilder2 = XContentFactory.jsonBuilder();
		bulk.add(client.prepareIndex("bulk_index","bulk_type","2")//索引ID，进行插入数据
				.setSource(jsonbuilder2.startObject()
						.field("user","lmcname")
						.field("postDate",new Date())
						.field("message","try out ElastiSearch again")
						.endObject())
				);
		BulkResponse  bulkrespone = bulk.get(); //统一提交 数据
		if(bulkrespone.hasFailures()){
			System.out.println("处理有异常，请仔细检查");
		}
		
	}
	//批量更新操作
	public void batchUpdateIndex() throws IOException{
		Client client = ElasticsearchUtils.getEsClient();
		XContentBuilder jsonbuilder = XContentFactory.jsonBuilder();
		BulkRequestBuilder updateBulk = client.prepareBulk();  //批量提交的同一实例对象
		updateBulk.add(client.prepareUpdate("bulk_index", "bulk_type", "1").setDoc(jsonbuilder.startObject()
				.field("user","修改 updatelmc")
				.field("postDate",new Date())
				.field("message","update ElasticSerch again 被我修改了")
				.endObject())
				);
		XContentBuilder jsonbuilder2 = XContentFactory.jsonBuilder();
		updateBulk.add(client.prepareUpdate("bulk_index","bulk_type","2").setDoc(jsonbuilder2.startObject()
				.field("user","修改后的 user")
				.field("postDate",new Date())
				.field("message","修改后的message信息")
				.endObject())
				);
		BulkResponse bulkresponse = updateBulk.get();
		if(bulkresponse.hasFailures()){
			System.out.println("数据处理异常，请仔细检查");
		}
	}
	//批量删除操作
		public void batchDeleteIndex() throws IOException{
			Client client = ElasticsearchUtils.getEsClient();
			BulkRequestBuilder deleteBulk = client.prepareBulk();
			deleteBulk.add(client.prepareDelete("bulk_index","bulk_type","1"));
			deleteBulk.add(client.prepareDelete("bulk_index", "bulk_type", "2"));
			BulkResponse bulkRespone = deleteBulk.get(); //提交 批量删除
			if(bulkRespone.hasFailures()){
				System.out.println("批量插入数据错误");
			}
			
		}
		
		//使用 Bulk Processor
		//BulkProcessor 提供了一个简单的接口，在给定的大小数量上定时批量自动请求
		public void BulkProcessor() throws IOException{
			Client client = ElasticsearchUtils.getEsClient();
			BulkProcessor bulkProcessor = BulkProcessor.builder(
			        client,  //增加elasticsearch客户端
			        new BulkProcessor.Listener() {
			            @Override
			            public void beforeBulk(long executionId,
			                                   BulkRequest request) { System.out.println("执行前调用方法"); } //调用bulk之前执行 ，例如你可以通过request.numberOfActions()方法知道numberOfActions
			            @Override
			            public void afterBulk(long executionId,
			                                  BulkRequest request,
			                                  BulkResponse response) { System.out.println("执行后调用方法");  } //调用bulk之后执行 ，例如你可以通过request.hasFailures()方法知道是否执行失败
			            @Override
			            public void afterBulk(long executionId,
			                                  BulkRequest request,
			                                  Throwable failure) {  System.out.println("执行失败抛Throwable调用方法"); } //调用失败抛 Throwable
			        })
			        .setBulkActions(10000) //每次10000请求
			        .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) //拆成5mb一块
			        .setFlushInterval(TimeValue.timeValueSeconds(5)) //无论请求数量多少，每5秒钟请求一次。
			        .setConcurrentRequests(1) //设置并发请求的数量。值为0意味着只允许执行一个请求。值为1意味着允许1并发请求。
			        .setBackoffPolicy(
			            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))//设置自定义重复请求机制，最开始等待100毫秒，之后成倍更加，重试3次，当一次或多次重复请求失败后因为计算资源不够抛出 EsRejectedExecutionException 异常，可以通过BackoffPolicy.noBackoff()方法关闭重试机制
			        .build();
         /* BulkProcessor 默认设置
  			bulkActions  1000 
			bulkSize 5mb
			不设置flushInterval
			concurrentRequests 为 1 ，异步执行
			backoffPolicy 重试 8次，等待50毫秒*/
			bulkProcessor.add(new IndexRequest("twitter", "tweet", "1").source("your doc here 需要插入的文件和资源"));
			bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));//删除一个索引 
			bulkProcessor.close();  //关闭操作
		}
}
