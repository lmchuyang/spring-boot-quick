package com.purang.test;

public class Printer {

	private int counter=0;
	public void print(String type) {
		System.out.println(type+" printer:"+counter++);
	}
}
