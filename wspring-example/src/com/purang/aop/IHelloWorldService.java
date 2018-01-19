package com.purang.aop;

public interface IHelloWorldService {

	public void sayHelloWorld();
	public void sayBefore(String str);
	public void sayAfterReturning(String str);
	public void sayAfterThrowing(Exception exp);
	
}
