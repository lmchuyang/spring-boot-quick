package com.esutils.controller;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.esutils.utils.ElasticsearchUtils;

/* ElasticSearch的索引（index）相当于数据库，类型(type)相当于数据表，映射(Mapping)相当于数据表的表结构。
 * ElasticSearch中的映射（Mapping）用来定义一个文档，可以定义所包含的字段以及字段的类型、分词器及属性等等。
映射可以分为动态映射和静态映射。 
（1）动态映射 
我们知道，在关系数据库中，需要事先创建数据库，然后在该数据库实例下创建数据表，然后才能在该数据表中插入数据。而ElasticSearch中不需要事先定义映射（Mapping），
文档写入ElasticSearch时，会根据文档字段自动识别类型，这种机制称之为动态映射。
（2）静态映射 
当然，在ElasticSearch中也可以事先定义好映射，包含文档的各个字段及其类型等，这种方式称之为静态映射。
*/
public class Demo4Mapping {
	
	private String indexName = "test_mapping_index";
	private String typeName = "test_mapping_type";
	//创建mapping 
	public void createMapping() throws Exception{
		Client client  =  ElasticsearchUtils.getEsClient();
		//首先创建index
		CreateIndexResponse createIndexResponse =  client.admin().indices()
			.prepareCreate(indexName).execute().actionGet();
		System.out.println("createIndexResponse="+createIndexResponse.isAcknowledged());
		
		//索引类型
		PutMappingRequestBuilder mappingRequest =  client.admin().indices().preparePutMapping(indexName)
			.setType(typeName)
			.setSource(createTestModelMapping());
		PutMappingResponse putMappingResponse =  mappingRequest.execute().actionGet();
		System.out.println("putMappingResponse="+putMappingResponse.isAcknowledged());
		client.close();
	}
	//定义数据结构
	private XContentBuilder createTestModelMapping() throws Exception{
		XContentBuilder mapping = XContentFactory.jsonBuilder()
			.startObject()
				.startObject(typeName)
					.startObject("properties")
						.startObject("id")
							.field("type", "long")
							//..field("store", "yes")
						.endObject()
						.startObject("type")
							.field("type", "text")
							//.field("index", "not_analyzed")
						.endObject()
						.startObject("catIds")
							.field("type", "integer")
						.endObject()
						.startObject("catName")
							.field("type", "text")
						.endObject()
						.startObject("code")
						.field("type", "long")
					    .endObject()
					.endObject()
				.endObject()
			.endObject();		
		return mapping;
	}
	
	//获得mapping
	public void getMapping() throws IOException{
		
		Client client =   ElasticsearchUtils.getEsClient();
		ClusterState cs =   client.admin().cluster().prepareState().setIndices(indexName).execute().actionGet().getState();
		IndexMetaData imd =   cs.getMetaData().index(indexName);		
		MappingMetaData mdd  = imd.mapping(typeName);
		System.out.println(mdd.sourceAsMap());

		client.close();
	}
}
