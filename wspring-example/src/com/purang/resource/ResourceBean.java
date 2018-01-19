package com.purang.resource;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class ResourceBean implements ResourceLoaderAware{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private ResourceLoad reLoad;
	
	public ResourceLoad getReLoad() {
		return reLoad;
	}

	@Override
	public void setResourceLoader(ResourceLoader arg0) {
		this.reLoad = reLoad;
	}

}
