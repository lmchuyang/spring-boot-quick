package com.purang.aop;

public class HelloWorldService implements IHelloWorldService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sayHelloWorld() {
		System.out.println("============Hello World!");
	}

	@Override
	public void sayBefore(String str) {
		System.out.println("============ say "+str);		
	}

	@Override
	public void sayAfterReturning(String param) {
		 System.out.println("===========sayAfterReturning advice param:" + param);  
	}

	@Override
	public void sayAfterThrowing(Exception exp) {
		System.out.println("============ Exception "+exp);		
	}
	
}
