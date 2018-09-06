package es;

import com.esutils.controller.Demo4Mapping;

public class TestMapping {
	
	public static void main(String[] args) {
		Demo4Mapping mapping = new Demo4Mapping();
	
		try {
			//mapping.createMapping();
			mapping.getMapping();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
