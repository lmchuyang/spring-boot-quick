package com.purang.test;

public abstract class HelloImpl5 implements HelloApi{

	private Printer print;
	@Override
	public void sayHello() {
		print.print("setter");
		createPrototypePrinter().print("prototype");
		createPrototypePrinter().print("singleton");
	}
	 public abstract Printer createPrototypePrinter();  
	 public Printer createSingletonPrinter() {
	        System.out.println("该方法不会被执行，如果输出就错了");  
	        return new Printer();  
	    }
	    public void setPrinter(Printer printer) {  
	        this.print = printer;  
	    }
}
