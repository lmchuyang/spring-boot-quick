package com.esutils.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.index.FilterLeafReader.FilterTerms;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import com.esutils.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BaseAggregationBuilder;
import org.elasticsearch.search.aggregations.InternalOrder.CompoundOrder;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class DemoFacet_group {

	private Client client=null;
	public DemoFacet_group(){
		if(client ==null)
			client = ElasticsearchUtils.getEsClient();
	}
	//聚合后排序
	public void groupTest(){
		 //构建查询请求体 //SearchRequestBuilder search = client.prepareSearch("gv_test").setTypes("gv_test");
        //单个分级查询  特别注意  TermsBuilder类6.3.0版本已经被 TermsAggregationBuilder取代
		SearchRequestBuilder sbuilder = client.prepareSearch("gv_test").setTypes("gv_test").setFrom(0).setSize(22);//.addSort("count.keyword",SortOrder.ASC);
		//聚合后对Aggregation结果排序,计算每个球队总年薪，并按照总年薪倒序排列
		//特别注意  Order类6.3.0版本已经被CompoundOrder取代
/*		AggregationBuilder teamAgg1= AggregationBuilders.terms("terms").field("id");//.order(CompoundOrder.aggregation("sum_count",false));
		SumAggregationBuilder salaryAgg1= AggregationBuilders.sum("sum_count").field("count.keyword");
		sbuilder.addAggregation(teamAgg1.subAggregation(salaryAgg1));
		SearchResponse response = sbuilder.execute().actionGet();
		*/
		SearchResponse response;
		SearchRequestBuilder responsebuilder = client.prepareSearch("gv_test")
				.setTypes("gv_test").setFrom(0).setSize(250);
		AggregationBuilder aggregation = AggregationBuilders
				.terms("agg")
				.field("id.keyword")
				.subAggregation(
						AggregationBuilders.topHits("top").size(10)).size(100);
		response = responsebuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("code", "1")))
				.addSort("id.keyword", SortOrder.ASC)
				.addAggregation(aggregation)// .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				//.setPostFilter(QueryBuilders.rangeQuery("price").gt(1000).lt(5000)) 先聚合再过滤
				.setExplain(true).execute().actionGet();
		SearchHits hits = response.getHits();
		Terms agg = response.getAggregations().get("agg");
		System.out.println(agg.getBuckets().size());
		//查询分组后的组合数据
		for (Terms.Bucket entry : agg.getBuckets()) {
			String key = (String) entry.getKey(); // bucket key
			long docCount = entry.getDocCount(); // Doc count
			System.out.println("key " + key + " doc_count " + docCount);
			// We ask for top_hits for each bucket
			TopHits topHits = entry.getAggregations().get("top");
			for (SearchHit hit : topHits.getHits().getHits()) {
				System.out.println(" -> id " + hit.getId() + " _source [{}]"
						+ hit.getSourceAsMap().get("count"));
			}
		}
		for(int i=0;i<hits.getHits().length;i++){
			System.out.println(hits.getHits()[i].getSourceAsString());
			System.out.println(hits.getHits()[i].getSourceAsMap().get("id"));
			System.out.println(hits.getHits()[i].getSourceAsMap().get("count"));
			System.out.println(hits.getHits()[i].getSourceAsMap().get("code"));
			System.out.println(hits.getHits()[i].getSourceAsMap().get("max_age"));
			
		}
		/*如果使用的是range聚合或者date range聚合，只需要改变aggregation就可以
		使用range聚合的时候：*/
		aggregation = AggregationBuilders.range("agg")
				.field("price").addUnboundedTo(50)
				.addRange(51, 100).addRange(101, 1000)
				.addUnboundedFrom(1001);

		//所有分组查询结果信，取值方式
	/*	Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        StringTerms teamAgg2= (StringTerms) aggMap.get("keywordAgg");
        Iterator<Bucket> teamBucketIt = teamAgg2.getBuckets().iterator();
        while (teamBucketIt .hasNext()) {
            Bucket buck1 = teamBucketIt .next();
            //球队名
            String team = buck1.getKey().toString();
            //记录数
            long count = buck1.getDocCount();
            //得到所有子聚合
            Map subaggmap = buck1.getAggregations().asMap();
            //avg值获取方法
            double avg_age= (double) subaggmap.get("avg_age");
            //sum值获取方法
            double total_salary = (double) subaggmap.get("total_salary");
            //...
        }*/
		ElasticsearchUtils.closeClient();
	}
	//单个field和多个field 分级聚合函数使用 
	public void gruop_max_min_avg(){
		SearchRequestBuilder sbuilder = client.prepareSearch("gv_test").setTypes("gv_test");
		       //聚合函数  计算每个球队年龄最大/最小/总/平均的球员年龄
				TermsAggregationBuilder teamAggmax= AggregationBuilders.terms("player_count ").field("team");
				MaxAggregationBuilder ageAgg= AggregationBuilders.max("max_age").field("age");
				sbuilder.addAggregation(teamAggmax.subAggregation(ageAgg));
				SearchResponse response = sbuilder.execute().actionGet();
				
				//聚合函数使用， 对多个field求 max/min/sum/avg   例如要计算每个球队年龄最大/最小/总/平均的球员年龄
				TermsAggregationBuilder teamAgg_method= AggregationBuilders.terms("team");
				AvgAggregationBuilder ageAgg_g= AggregationBuilders.avg("avg_age").field("age");
				SumAggregationBuilder salaryAgg= AggregationBuilders.sum("total_salary ").field("salary");
				sbuilder.addAggregation(teamAgg_method.subAggregation(ageAgg_g).subAggregation(salaryAgg));
				SearchResponse response3 = sbuilder.execute().actionGet();
			    
			//多个分组 .group by多个field
				//默认情况下，search执行后，仅返回10条聚合结果，如果想反悔更多的结果，需要在构建TermsBuilder 时指定size：
				//TermsAggregationBuilder teamAgges= AggregationBuilders.terms("team").size(15);
				TermsAggregationBuilder teamAggs= AggregationBuilders.terms("player_count ").field("team");
				TermsAggregationBuilder posAgg= AggregationBuilders.terms("pos_count").field("position");
				sbuilder.addAggregation(teamAggs.subAggregation(posAgg));
				SearchResponse responses2 = sbuilder.execute().actionGet();
	}

//测试通过，分组成功，聚合出错，可能是创建类型有误
	public void codeDesc(){
		//TermsAggregationBuilder 
		SearchRequestBuilder  responsebuilder = client.prepareSearch("gv_test")
				.setTypes("gv_test").setFrom(0).setSize(250);
		TermsAggregationBuilder aggregationsx =  AggregationBuilders.terms("agg").field("code.keyword").size(100);
        //将分组聚合请求插入到主请求体重
		aggregationsx.subAggregation(AggregationBuilders.sum("sum_count").field("count"));//可能因为不是long类型，不能求和
		aggregationsx.subAggregation(AggregationBuilders.avg("avg_id").field("id.keyword"));
		responsebuilder.addAggregation(aggregationsx);//.setExplain(true).execute().actionGet();必须提交查询
        //发送查询，获取聚合结果
       Terms tms=  responsebuilder.get().getAggregations().get("agg");
       SearchResponse  searchResponse =responsebuilder.execute().actionGet();
       
       Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
       
       StringTerms teamAgg= (StringTerms) aggMap.get("agg");
       Iterator<Bucket> teamBucketIt = teamAgg.getBuckets().iterator();
       while (teamBucketIt .hasNext()) {
           Bucket buck = teamBucketIt .next();
           //球队名
           String team = (String) buck.getKey();
           //记录数
           long count = buck.getDocCount();
           //得到所有子聚合
           Map subaggmap = buck.getAggregations().asMap();
           System.out.println("team: "+team+"==== "+"count: "+count);
           //avg值获取方法
           double avg_id= ((InternalAvg) subaggmap.get("avg_id")).getValue();
           //sum值获取方法
           double sum_count = ((InternalSum) subaggmap.get("sum_count")).getValue();
           //...
           //max/min以此类推
           System.out.println("avg_id: "+avg_id+" ==== "+"sum_count: "+sum_count);
           
         //  Max maxs = tm.getAggregations().get("totaldistance");
         // Sum sum = tm.getAggregations().get("sumAgg"); 
       }
      
       SearchHits hits = searchResponse.getHits();
		Terms agg = responsebuilder.get().getAggregations().get("agg");
		System.out.println(agg.getBuckets().size());
		for(int i=0;i<hits.getHits().length;i++){
			System.out.println(hits.getHits()[i].getSourceAsString());
			System.out.println(hits.getHits()[i].getSourceAsMap().get("id"));
			System.out.println(hits.getHits()[i].getSourceAsMap().get("count"));
			System.out.println(hits.getHits()[i].getSourceAsMap().get("code"));
			
		}
		ElasticsearchUtils.closeClient();
	
	}
	//分组聚合 另一种方式，用查询也行,子查询过滤条件  setQuery
	 public void sumAfterAgg() {
	        SearchResponse response = client.prepareSearch("gv_test").setTypes("gv_test")//.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("code", "1")))
	                .addAggregation(AggregationBuilders.terms("userAgg").field("id.keyword")
	                        .subAggregation(AggregationBuilders.max("count").field("")))
	                .get();//由于count类型是text 无法计算
	        Terms userAgg = response.getAggregations().get("userAgg");
	        System.out.println(userAgg.getBuckets().size());
	        for (Terms.Bucket entry : userAgg.getBuckets()) {
	            Sum sum = entry.getAggregations().get("sumAgg");
	          //  System.out.println("user:" + entry.getKey() + "----------" + "sum:" + sum.getValue());
	        }
	    }

	//使用BoolQueryBuilder进行复合查询
	public void booleQueryAll(){
		Client client = ElasticsearchUtils.getEsClient();
		BoolQueryBuilder bq = QueryBuilders.boolQuery();
		//单个匹配，搜索name为jack的文档
		MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "jack");
		//查询结果，全部封装在接口里。prepareSearch()有无参数都 可，prepareSearch("indexName",...)
		SearchResponse searchResponse = client.prepareSearch().setQuery(queryBuilder).get();
	        SearchHits hits = searchResponse.getHits();
	        SearchHit[] searchHits = hits.getHits();
	        int i = 0;
	        for (SearchHit searchHit : searchHits) {
	        	String name = (String) searchHit.getSourceAsMap().get("name");
	            String birth = (String) searchHit.getSourceAsMap().get("birth");
	            String interest = (String) searchHit.getSourceAsMap().get("interest");
	            System.out.println("-------------" + (++i) + "------------");
	            System.out.println(name);
	            System.out.println(birth);
	            System.out.println(interest);
	        }
		//matchAllQuery()方法用来匹配全部文档
		 MatchAllQueryBuilder AllqueryBuilderss = QueryBuilders.matchAllQuery();
		 //多个字段匹配某一个值
		 MultiMatchQueryBuilder MultiqueryBuilder = QueryBuilders.multiMatchQuery("music",
		            "name", "interest");//搜索name中或interest中包含有music的文档（必须与music一致）
		 
		 //模糊查询，?匹配单个字符，*匹配多个字符
		 WildcardQueryBuilder likequeryBuilder = QueryBuilders.wildcardQuery("name",
		            "*jack*");//搜索名字中含有jack文档（name中只要包含jack即可）
		 
		//模糊查询
		 WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
		             "name", "*jack*");//搜索名字中含有jack的文档
		 WildcardQueryBuilder queryBuilder2 = QueryBuilders.wildcardQuery(
		             "interest", "*read*");//搜索interest中含有read的文档
		 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		 //name中必须含有jack,interest中必须含有read,相当于and
		 boolQueryBuilder.must(queryBuilder1);
		 boolQueryBuilder.must(queryBuilder2);

/*		 BoolFilterBuilder filterBuilder = FilterBuilders.boolFilter()
	                .must(FilterBuilders.missingFilter("jiaxiaoId"));
	        searchRequestBuilder.setPostFilter(filterBuilder);*/
	     //使用should   相当于or
		 WildcardQueryBuilder orqueryBuilder1 = QueryBuilders.wildcardQuery(
		            "name", "*jack*");//搜索名字中含有jack的文档
		WildcardQueryBuilder orqueryBuilder2 = QueryBuilders.wildcardQuery(
		            "interest", "*read*");//搜索interest中含有read的文档
		 
		BoolQueryBuilder boolQueryBuilderor = QueryBuilders.boolQuery();
		//name中含有jack或者interest含有read，相当于or
		boolQueryBuilderor.should(orqueryBuilder1);
		boolQueryBuilderor.should(orqueryBuilder2);
		SearchResponse searchResponset = client.prepareSearch().setQuery(boolQueryBuilderor).get();
        SearchHits hit5 = searchResponset.getHits();
        SearchHit[] searchHits5 = hit5.getHits();
       // for (SearchHit searchHit : searchHits5)
	}
	//Elasticsearch强大的聚合功能Facet 分组统计, 按married分组，统计出结婚和未婚的各多少,可以分组统计一对多的关系
	public void Facet(){
		Client client = ElasticsearchUtils.getEsClient();
		SearchRequestBuilder searchBuilder = client.prepareSearch("gv_test").setTypes("gv_test");
		//过滤器, 先过滤user年龄，在正常的年龄范围内做分组
		BoolQueryBuilder boolfilter= QueryBuilders.boolQuery();
		boolfilter.must(QueryBuilders.matchAllQuery());
		boolfilter.must(QueryBuilders.rangeQuery("age").from(1).to(20));
		//分组条件
		String facetName = "marriedFacet";
		TermsAggregationBuilder st= null;
				
	}
	//增加
		public  void insert(){
			Map<String, String> map= new HashMap<String,String>();
			map.put("id", "1");
			map.put("count","3");
			map.put("code", "1");
			//获取服务器实态
			Client client = ElasticsearchUtils.getEsClient();
			//索引值不能有大写,同一个索引，类型必须相同, 要在同一个索引名称和类型下增加多数据，索引id必须不同，否则会覆盖
			IndexResponse indexrespone = client.prepareIndex("gv_test","gv_test")//有不同参数构造函数
			.setSource(map).get();//相同索引类型，ID必须不一样
			System.out.println("插入成功, isCreated="+indexrespone.getResult().toString());
			ElasticsearchUtils.closeClient();
		}
	  
}
