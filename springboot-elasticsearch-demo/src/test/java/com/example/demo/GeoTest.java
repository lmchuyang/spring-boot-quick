package com.example.demo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoTest {

	@Autowired
	Client client;
	   @Test
	 		public void queryTerms(){
	     	System.out.println("###########################");
	         }
	   //取出20公里以内的所有站点，但是不计算距离
	   @Test
	    public void testDistanceQuery() {
	        GeoDistanceQueryBuilder queryBuilder = QueryBuilders.geoDistanceQuery("geo")//location
	            .point(31.32822800, 120.53157000)
	            .distance(20, DistanceUnit.KILOMETERS)
	            .geoDistance(GeoDistance.ARC);//最慢但最精确的是 arc 计算方式
	        SearchResponse response = client.prepareSearch("tms_site_index")
	                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	                .setQuery(queryBuilder).execute().actionGet();
	        System.out.println(response);
	        System.err.println(response.getHits().getTotalHits());
	        SearchHits hits = response.getHits();
	        for (SearchHit hit : hits) {
	             String name = (String) hit.getSourceAsMap().get("site_name");
	             String address = (String) hit.getSourceAsMap().get("address");
	             String location = (String)hit.getSourceAsMap().get("geo");
	              String[] str = location.split(",");
	             System.out.println(name+"的坐标："+str[0]+str[1]+ "地址" + address);
	         }
	    	 client.close();
	    	 }
	    
	// 获取附近的人，并且计算每个站点离我多远， 用排序从最小距离开始
	     @Test
	     public void testGetNearbyPeople() {
	    	 double lat=30.381222; double lon=120.106685;
	    	 String index="tms_site_index"; String type="doc";
	         SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
	         srb.setFrom(0).setSize(100);//100人
	         GeoDistanceQueryBuilder location1 = QueryBuilders.geoDistanceQuery("geo").point(lat,lon).distance(10,DistanceUnit.KILOMETERS);
	         srb.setPostFilter(location1);
	         // 获取距离多少公里 这个才是获取点与点之间的距离的
	         GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("geo",lat,lon);
	         sort.unit(DistanceUnit.METERS);
	         sort.order(SortOrder.ASC);
	         sort.point(lat,lon);
	         srb.addSort(sort);
	  
	         SearchResponse searchResponse = srb.execute().actionGet();
	         SearchHits hits = searchResponse.getHits();
	         // 搜索耗时
	         Float usetime = searchResponse.getTook().millis() / 1000f;
	         System.out.println("附近的站点(" + hits.getTotalHits() + "个)，耗时("+usetime+"秒)：");
	         for (SearchHit hit : hits) {
	             String name = (String) hit.getSourceAsMap().get("site_name");
	             String location = (String)hit.getSourceAsMap().get("geo");
	             String[] str = location.split(",");
	             // 获取距离值，并保留两位小数点
	             BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
	             Map<String, Object> hitMap = hit.getSourceAsMap();//赋值对象共用地址，对象改变原始数据也改变
	             // 在创建MAPPING的时候，属性名的不可为geoDistance。
	             hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
	             System.out.println(name+"的坐标："+str[0]+str[1] + " 此站点距离我" + hit.getSourceAsMap().get("geoDistance") + DistanceUnit.METERS.toString());
	         }
	  
	     }
	
	     
}
