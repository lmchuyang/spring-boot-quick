package com.purang.hql;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Start1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -12);
		System.out.println(calendar.getTime());
		
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
		
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String date=sp.format(new Date());
		ParsePosition pos=new ParsePosition(0);
		Date newDate=sp.parse(date,pos);
		System.out.println("====: "+newDate.getTime());
	}

}
