package com.example.demo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jdk.nashorn.api.scripting.JSObject;
import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
	
	@Autowired
	Client client;

	//增加,重复增加数据会报错
	//@Test
	public  void insert2(){
			Map<String, String> map= new HashMap<String,String>();
			map.put("article", "Jon someth_test");
			map.put("gender", "male_test");
			//获取服务器实态
			//索引值不能有大写,同一个索引，类型必须相同, 要在同一个索引名称和类型下增加多数据，索引id必须不同，否则会覆盖
			IndexResponse indexrespone = client.prepareIndex("article","article","1")//有不同参数构造函数
			.setSource(map).get();//相同索引类型，ID必须不一样
			System.out.println("插入成功, isCreated="+indexrespone.getResult().toString());
			client.close();
		}
		
		//综合分配查询， 聚合查询，分组查询按条件查询等，并且只返回想要的字段
	    @Test
		public void query(){
	    	QueryBuilder querybuild = QueryBuilders.queryStringQuery("LEFYECG20JHN19938");//索引内所有字段里 有匹配的项都 查询出来
	    	QueryBuilder query = QueryBuilders.matchQuery("vin","LEFYECG20JHN19938");//LEFYECG20JHN19938  沪BZS601
			//搜索数据
			SearchResponse serResponse = client.prepareSearch("ims_tsp_completecondition_index")
					.setQuery(query)
					.setFetchSource(new String[]{"platelicenseno","longitude","latitude","totaldistance"}, null)//includes 第一个参数是包括要展示的参数，excludes第二个是要排除的参数
					.setFrom(0)
					.setSize(15)
					.execute().actionGet();
			SearchHits hits = serResponse.getHits();
			for(SearchHit hit:hits){
				Map<String, Object> map = hit.getSourceAsMap();//可以转化成map再对操作数据
				System.out.println("article get="+hit.getSourceAsString());
				System.out.println("vin = "+hit.getSourceAsMap().get("vin"));
			}
			
	    	QueryBuilder queryrang = QueryBuilders.rangeQuery("createtime").gt("2018-08-01").lt("2018-08-30");//过滤查询( gt大于)  (lt小于)
			//查询总里程不能等于空
			QueryBuilder query1 = QueryBuilders.boolQuery()
					.must(QueryBuilders.existsQuery("totaldistance")).filter(queryrang);
			SearchResponse  gruopSearch = client.prepareSearch("ims_tsp_completecondition_index")
					.setQuery(query1)
					.addAggregation(AggregationBuilders.terms("vinGroup").field("vin.keyword")//vin.keyword    platelicenseno
					//.subAggregation(AggregationBuilders.sum("totaldistance").field("totaldistance")) //求和
					.subAggregation(AggregationBuilders.max("totaldistance").field("totaldistance"))//最大值 也就是车子现在的里程数
					.size(15))//.size(15) 在内层设定返回数;    .setFrom(0).setSize(15)//默认返回10个桶，此参数设置其实是无效
					.setExplain(true).get();
					
			Terms trem = gruopSearch.getAggregations().get("vinGroup");
			
			for(Terms.Bucket tm:trem.getBuckets()){
				double mx =  ((InternalMax) tm.getAggregations().asMap().get("totaldistance")).getValue();
				//System.out.println("mx:"+mx);
				InternalMax max = (InternalMax) tm.getAggregations().get("totaldistance");//还是必须要转化
				System.out.println("maxXXXXXXX "+max.getValue());
				// Sum sum = tm.getAggregations().get("totaldistance");//此方法只能在sum的时候才能用上，其它聚合函数都会报错，可用性值 
				//System.out.println(new BigDecimal(sum.getValue()).toPlainString());
				System.out.println(tm.getKey());
				Max maxs = tm.getAggregations().get("totaldistance");
				System.out.println("maxs.getValue(): "+maxs.getValue());
			}
			client.close();
		}
	    
	    //查询过滤范围,注意， elasticsearch里面的字段全部是小写,分组查询按最大值最小值差计算每辆车在时间段里跑的公里数，
	    //按照总公里数排序,去除重复数据等功能
	    @Test
		public void queryFilterMaxMin(){
	    	QueryBuilder queryrang = QueryBuilders.rangeQuery("createtime").gt("2018-01-01").lt("2018-08-30");//过滤查询( gt大于)  (lt小于)
	    	 @SuppressWarnings("rawtypes")
			SortBuilder sortBuilder=SortBuilders.fieldSort("totaldistance");//排序
	         sortBuilder.order(SortOrder.DESC);
			//查询总里程不能等于空
			QueryBuilder query1 = QueryBuilders.boolQuery()
					.must(QueryBuilders.existsQuery("totaldistance")) //字段为空的判断方式 existsQuery，表示存在值或不存在值，由must和mustNot
					.filter(queryrang);
			SearchResponse  search = client.prepareSearch("ims_tsp_completecondition_index")
					.setQuery(query1).addAggregation(AggregationBuilders.terms("vinGroup").field("vin.keyword")
							.subAggregation(AggregationBuilders.max("maxtotaldistance").field("totaldistance"))
							.subAggregation(AggregationBuilders.min("mintotaldistance").field("totaldistance")).size(20))//分组后面加返回数据条数
					.addSort(sortBuilder)
					//.setFrom(0).setSize(100)//默认返回10个桶，此参数设置其实是无效，仅仅是分页参数
					.get();
			Terms terms = search.getAggregations().get("vinGroup");
			for(Terms.Bucket tm:terms.getBuckets()){
				Map<String, Aggregation> map = tm.getAggregations().getAsMap();
				Max max = (InternalMax)map.get("maxtotaldistance");
				Min min = (InternalMin)map.get("mintotaldistance");
				System.out.println("max-min: "+(max.getValue()-min.getValue())+" vin: "+tm.getKey()+" max: "+max.getValue()+" min: "+min.getValue()+" doc_count:"+tm.getDocCount());
			}
  			//Cardinality 去除重复数据 ,统计总数
  			SearchResponse sr = client.prepareSearch("ims_tsp_completecondition_index")
  					.addAggregation(AggregationBuilders.cardinality("type_count").field("vin.keyword")).execute().actionGet();
  	        Cardinality result = sr.getAggregations().get("type_count");
  	        System.out.println("type_count: "+result.getValue());
			client.close();
	    }
	    
	    @Test
	  		public void queryTerms(){
	    	System.out.println("###########################");
	    }
}
