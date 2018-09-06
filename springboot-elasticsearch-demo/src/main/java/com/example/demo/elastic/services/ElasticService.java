package com.example.demo.elastic.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class ElasticService {

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	Client client;

	public <T> T getById(String index, String id, Class<?> T)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		return getById(index, index, id, T);
	}

	public <T> T getById(String index, String type, String id, Class<?> T)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.idsQuery().addIds(id)).execute().actionGet();

		System.out.println(response);

		long totalHits = response.getHits().getTotalHits();

		// 没有找到
		if (totalHits == 0) {
			return null;
		} else if (totalHits != 1) {
			throw new RuntimeException("出错了，找到了不止一条：" + totalHits);
		}

		SearchHit searchHit = response.getHits().getHits()[0];

		Map<String, Object> source = searchHit.getSourceAsMap();

		Object obj = T.newInstance();
		BeanUtils.copyProperties(source, obj);

		return (T) obj;
	}

	public void addIndex(String index, String id, Object data) {
		try {
			addIndex(index, index, id, data);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SneakyThrows
	public void addIndex(String index, String type, String id, Object data) throws JsonGenerationException, JsonMappingException, IOException {
		IndexResponse response = client.prepareIndex(index, type, id).setSource(data).get();
		//mapper.writeValueAsBytes(data)  过时不建议使用
		System.out.println(response);
	}
}
