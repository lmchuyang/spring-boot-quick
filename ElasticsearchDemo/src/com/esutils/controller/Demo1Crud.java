package com.esutils.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import com.esutils.utils.ElasticsearchUtils;
import com.esutils.utils.JsonUtils;


public class Demo1Crud {
	
	private  TransportClient  client;
	//测试的时候，

	//增加
		public  void insert(){
			Map<String, String> map= new HashMap<String,String>();
			map.put("prodId", "8");
			map.put("prodName", "iphone8s");
			map.put("prodDesc", "智能手机3");
			client = ElasticsearchUtils.getEsClient();
			//索引值不能有大写,同一个索引，类型必须相同, 要在同一个索引名称和类型下增加多数据，索引id必须不同，否则会覆盖
			IndexResponse indexrespone = client.prepareIndex().setIndex(ElasticsearchUtils.getIndexName())//demo_index
			.setType(ElasticsearchUtils.getTypeName())//同一个索引，类型必须相同
			.setSource(map)
			.setId("8")//相同索引类型，ID必须不一样
			.execute()
			.actionGet();
			System.out.println("插入成功, isCreated="+indexrespone.getResult().toString());
			ElasticsearchUtils.closeClient();
		}
		
	//增加
	public  void insert2(){
		Map<String, String> map= new HashMap<String,String>();
		map.put("name", "Jon someth_test");
		map.put("gender", "male_test");
		//获取服务器实态
		client = ElasticsearchUtils.getEsClient();
		//索引值不能有大写,同一个索引，类型必须相同, 要在同一个索引名称和类型下增加多数据，索引id必须不同，否则会覆盖
		IndexResponse indexrespone = client.prepareIndex("index","type","2")//有不同参数构造函数
		.setSource(map).get();//相同索引类型，ID必须不一样
		System.out.println("插入成功, isCreated="+indexrespone.getResult().toString());
		ElasticsearchUtils.closeClient();
	}
	
	
	//查询
	public void query(){
		client = ElasticsearchUtils.getEsClient();
		//搜索数据
        GetResponse response = client.prepareGet("blog", "article", "2").execute().actionGet();
        System.out.println("blog get="+response.getSourceAsString());
        //GetResponse responssse = client.prepareGet("java_demo_index", "java_demo_type", "1").execute().actionGet();
		GetResponse getResponse = client.prepareGet().setIndex(ElasticsearchUtils.getIndexName())
				.setType(ElasticsearchUtils.getTypeName())
				.setId("1").execute().actionGet();
		System.out.println("java_demo_index get="+getResponse.getSourceAsString());
		ElasticsearchUtils.closeClient();
	}
	//搜索
		public void search(){
			client = ElasticsearchUtils.getEsClient();
			QueryBuilder query = QueryBuilders.queryStringQuery("iphone8s");
			SearchResponse searchResponse = client.prepareSearch("demo_index")//ElasticsearchUtils.getIndexName()
					.setQuery(query)
				  //.setOperationThreaded(false) operationThreaded 设置为 true 是在不同的线程里执行此次操作,配置线程
					.setFrom(0).setSize(10)//相当于limit 
					.execute()
					.actionGet();
			//operationThreaded 设置为 true 是在不同的线程里执行此次操作
			//SearchHits是SearchHit的复数形式，表示这个是一个列表
			SearchHits shs = searchResponse.getHits();
			for(SearchHit hit : shs){
				System.out.println(hit.getSourceAsString());
			}
		   ElasticsearchUtils.closeClient();
		}
		
		//搜索2  必须包含指定条件搜索，元素里面的筛选
		public void search2(){
			client = ElasticsearchUtils.getEsClient();
			QueryBuilder query2 = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("catId", "6"))
					.must(QueryBuilders.termsQuery("catId", "6","7","8"))// 包含查询  第一个参数是字段后面是包含的数据  相当于  sql里的  in(6,7,8)
					.must(QueryBuilders.rangeQuery("prodId").gte(5));//lte(5)小于等于5，gte(5)大于等于5的数据
			SearchResponse searchRespone2 = client.prepareSearch(ElasticsearchUtils.getIndexName())
					.setQuery(query2)
					.setFrom(0).setSize(10)
					.execute()
					.actionGet();
			SearchHits shs = searchRespone2.getHits();
			for(SearchHit hit :shs){
				System.out.println(hit.getSourceAsString());
			}
			ElasticsearchUtils.closeClient();
		}
		
		//过滤 和排序
		public void filter(){
			client = ElasticsearchUtils.getEsClient();
			//设置查询参数，年龄条件，要求是from，大于50 to 小于60  返回的列表
			//排序 multi_field 
			QueryBuilder  postfilter = QueryBuilders.rangeQuery("prodId").gte(5).lte(8);
			SearchResponse searchrespone = client.prepareSearch(ElasticsearchUtils.getIndexName())
					.setPostFilter(postfilter)
					.setFrom(0).setSize(10)
					.addSort("prodId.keyword",SortOrder.DESC)
					.execute()
					.actionGet();  
			//SearchHits是SearchHit的复数形式，表示这个是一个列表
			SearchHits shs = searchrespone.getHits();
			for(SearchHit hit:shs){
				System.out.println(hit.getSourceAsString());
			}
			ElasticsearchUtils.closeClient();
		}
		
		//删除
		public void delete(){
			client = ElasticsearchUtils.getEsClient();
			DeleteResponse  del = client.prepareDelete()
					.setIndex(ElasticsearchUtils.getIndexName())
					.setType(ElasticsearchUtils.getTypeName())
					.setId("1")
					.execute()
					.actionGet();
			System.out.println("del id found="+del.getResult());
			ElasticsearchUtils.closeClient();
		}
		//通过查询条件删除
		public void deleteSearch(){
			BulkByScrollResponse response =
			    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
			        .filter(QueryBuilders.matchQuery("gender", "male")) //查询条件
			        .source("persons") //index(索引名)
			        .get();  //执行
			long deleted = response.getDeleted(); //删除文档的数量
		 }
		//如果需要执行的时间比较长，可以使用异步的方式处理,结果在回调里面获取
		public void deleteSearchBatch(){
			DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
		    .filter(QueryBuilders.matchQuery("gender", "male"))      //查询            
		    .source("persons")                //index(索引名)                                    
		    .execute(new ActionListener<BulkByScrollResponse>() {     //回调监听     
		        @Override
		        public void onResponse(BulkByScrollResponse response) {
		            long deleted = response.getDeleted();   //删除文档的数量                 
		        }
		        @Override
		        public void onFailure(Exception e) {
		            // Handle the exception
		        }
		    });
		}
		//有两种 更新的方法
		//1 创建 UpdateRequest,通过client发送；
		//2 使用 prepareUpdate() 方法；
		public void update(){
			client = ElasticsearchUtils.getEsClient();
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("prodId", 2);
			updateMap.put("prodName", "iphone7s");
			updateMap.put("prodDesc", "手机");
			//updateMap.put("catId", 2);
			UpdateResponse update = client.prepareUpdate()
					.setIndex(ElasticsearchUtils.getIndexName())
					.setType(ElasticsearchUtils.getTypeName())
					.setDoc(updateMap)
					.setId("1")
					.execute()
					.actionGet();
			//.upsert(indexRequest); //如果不存在此文档 ，就增加 `indexRequest`否则更新
			System.out.println("更新成功 updateDate:"+update.getResult());//有可能更新不成功
			ElasticsearchUtils.closeClient();
		}
		//使用脚本更新文档
	    public void udpateMh() throws InterruptedException, ExecutionException{
	    	client = ElasticsearchUtils.getEsClient();
	    	UpdateRequest updateRequest = new UpdateRequest();
	    	updateRequest.index("index");
	    	updateRequest.type("type");
	    	updateRequest.id("1");
	    	//updateRequest.doc(jsonBuilder() .startObject()
	    	//            .field("gender", "male")
	    	//        .endObject());
	    	client.update(updateRequest).get();
	    }
	    //Upsert   更新插入,如果存在文档就更新，如果不存在就插入
		public void updateInsert() throws IOException{
	    	client = ElasticsearchUtils.getEsClient();
	    	XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();//帮助类，转成 json格式
	    	IndexRequest indexRequest = new IndexRequest("index", "type")//可以省掉ID号， 默认查询所有索引ID
	    	        .source(jsonBuilder     //此处查询条件可以省掉，默认查询所有属性类型
	    	                .startObject()
	    	                .field("name", "Jon someth_test")
	    	                .field("gender", "male_test")
	    	            .endObject());
	    	UpdateRequest updateRequest = new UpdateRequest("index", "type", "2")
	    	        .doc(XContentFactory.jsonBuilder()       //必须是新的实例，坑了很久，下面是更新的属性，没有的这个属性就增加，如果有就更新
	    	                .startObject()
	    	                .field("gender", "male_copy_test")
	    	            .endObject())
	    	        .upsert(indexRequest); //如果不存在此文档 ，就增加 `indexRequest`
	    	try {
				client.update(updateRequest).get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
	    }
}
