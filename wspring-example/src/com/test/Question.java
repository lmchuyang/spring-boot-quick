package com.test;

import java.util.ArrayList;
import java.util.List;


public class Question {
	 //static int h[] = new int[10];
	StringBuffer sddBuffer = new StringBuffer();
	
	int h[] = new int[10];
	
	public static void main(String[] args) {
	int[] a={1,2,3,4,5,6};
	int[]  b;
	b=a;
	System.out.println(b[2]);
	}
	public void name1() {
		int $e,a,b=0;
		Question que= new Question();
	  System.out.println(que.h[9]);	
	  String str="abcde";
	  String str1[]={"a","b","c"};;
	  System.out.println(str.length());
	  System.out.println(str1.length);
	  List list= new ArrayList();
	  list.add("abc");
	  System.out.println(list.size());
	  
	  
		A:for (int i = 0; i < 5; i++) {
			B:for (int j = 0; j < 5; j++) {
				C:for (int j2 = 0; j2 < 5; j2++) {
						System.out.println("i="+i+",j="+j+",j2="+j2);
						break C;
				}
			}
		}
	  
	  StringBuffer strt = new StringBuffer("select count(*) form test");
	  strt.append(" ");
	}
}
