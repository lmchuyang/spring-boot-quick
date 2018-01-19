package com.purang.abstrant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {


	public static void main(String[] args) throws ParseException {

		  Date date = new SimpleDateFormat("yyyyMMdd").parse("20160222");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
			//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format("20160222"));
		int i=0;
		if(i++==1){
			System.out.println(i+"$$$$$$$$$$$$$$$$");
		}
		System.out.println(i);
	}

}
