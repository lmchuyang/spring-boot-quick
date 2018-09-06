package com.esutils.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.esutils.model.User;

public class JsonUtils {

	//把实体类对象，转化为json对象
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object o){
		try{
			ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
			return writer.writeValueAsString(o);
		}catch(Exception e){
			e.printStackTrace();
			return "转换发生异常";
		}
	}
	
	public static void main(String[] args) {
		User user = new User(11, "kevin", 123, false);
		System.out.println( JsonUtils.toJson(user) );
	}
}
