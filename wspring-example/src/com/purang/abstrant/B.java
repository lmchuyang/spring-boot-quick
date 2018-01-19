package com.purang.abstrant;


public class B extends A{
	
	public static void main(String[] args) {
		B b= new B();
		b.test();
		b.test3();
		if (new Integer(59) == Integer.valueOf(59)){
			System.out.println(new Integer(59) == Integer.valueOf(59));
		}
		System.out.println(new Integer(59).equals(Integer.valueOf(59)));
		System.out.println(new Integer(59) == Integer.valueOf(59));
		Integer i01=59;
		int i02 = 59;
		System.out.println(i01==i02);
	}
	@Override
	public void test3() {
		System.out.println("test3");
	}
	
}
