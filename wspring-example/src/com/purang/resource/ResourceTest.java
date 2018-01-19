package com.purang.resource;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


public class ResourceTest {

	public static void main(String[] args) {
		
		ResourceTest test = new ResourceTest();
		try {
			//test.testByteArrayResource();
			//test.testFileResource();
			test.testclassPath();
			test.testClasspathResourceByClass();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void testByteArrayResource() {
		Resource  resource = (Resource) new ByteArrayResource("hello world".getBytes());
		if (resource.exists()) {
			dumpStream(resource);
		}
	}
	public void dumpStream(Resource  resource) {
		InputStream is = null;
		try {
			is = resource.getInputStream();
			byte[] descbyte = new byte[(is.available())];
			is.read(descbyte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void testFileResource() {
		File file = new File("D:/test.txt");
		Resource resource = new FileSystemResource(file);
		if (resource.exists()) {
			dumpStream(resource);
		}
		try {
			System.out.println(resource.contentLength());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void  testclassPath() throws IOException{
		Resource resource = new ClassPathResource("/com/purang/resource/test1.properties");
		if (resource.exists()) {
			dumpStream(resource);
			System.out.println("资源");
		}
		System.out.println("path: "+resource.getFile().getAbsolutePath());
		System.out.println("length: "+resource.getFile().length());
	}
	public void testClasspathResourceByClass() throws IOException {
		Class clazz = this.getClass();
		Resource resource1 = new ClassPathResource("/com/purang/resource/test1.properties",clazz);
		if (resource1.exists()) {
			dumpStream(resource1);
		}
		 System.out.println("path:" + resource1.getFile().getAbsolutePath());
		 
		 Resource resource2 = new ClassPathResource("test1.properties",this.getClass());
		 if (resource2.exists()) {
			dumpStream(resource2);
		}
		 System.out.println("resource2: "+resource2.getFile().getAbsolutePath());
	}
	
}
