package com.esutils.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import com.esutils.utils.ElasticsearchUtils;

//用游标的方式批量删除数据，每次操作会得到上一次游标执行的值，再往下执行
public class Scroll {

	public void scrollTest(){
		Client client = ElasticsearchUtils.getEsClient();
		SearchResponse searchResponse =
						client.prepareSearch("java_demo_index")
			.setTypes("java_demo_type")
			.setSearchType(SearchType.QUERY_THEN_FETCH)		//加上这个据说可以提高性能，但第一次却不返回结果
			.setSize(5)			//实际返回的数量为5*index的主分片格式，如果index是默认配置的话
			.setScroll(TimeValue.timeValueMinutes(8))	//这个游标维持多长时间
			.execute().actionGet();
		//第一次查询，只返回数量和一个scrollId
				System.out.println(searchResponse.getHits().getTotalHits());
				System.out.println(searchResponse.getHits().getHits().length);  //受setSize(5)的影响，每个分片只返回5个
				//第一次运行没有结果
				for (SearchHit hit :searchResponse.getHits()){
								System.out.println(hit.getSourceAsString());
				}
				System.out.println("getScrollId: "+searchResponse.getScrollId());
				searchResponse =  client.prepareSearchScroll(searchResponse.getScrollId()) //拿到上一次的游标ID
						.setScroll(TimeValue.timeValueMinutes(8))				
						.execute().actionGet();
				
				System.out.println(searchResponse.getHits().getTotalHits());
				
				System.out.println(searchResponse.getHits().getHits().length);
				for (SearchHit hit :searchResponse.getHits()) {
								System.out.println(hit.getSourceAsString());
				}
				ElasticsearchUtils.closeClient();;
	}
}
