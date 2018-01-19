package com.test;

import java.util.Scanner;


public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test1 test1 = new Test1();
		//test1.test1();
		//test1.test2();
		//test1.test3();
		//test1.test4();
		//test1.test5();
		//test1.test7();
		//test1.test8();
		//test1.test9();
		test1.test11();
	}

	public void test1(){
		double sum=0;
		double str=0;
		for (double i =1 ; i <=100; i++) {
			if(i%2==0){
				str=-1;
			}else {
				str=1;
			}
			sum=sum+str*(1/i);
			//sum=sum+Math.pow(-1, i+1)*(1/i);
		}
		System.out.println("1-1/2+1/3-1/4+...+1/99 ="+sum);
		System.out.println((-1*(1.0/5)));
	}
	public void test2(){
		Scanner scan = new Scanner(System.in);
		String str=scan.nextLine();
		int m=0,n=0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c>='a' && c<='z')
				n++;
			else if(c>='A' && c<='Z')
				m++;
		}
		System.out.println("M:"+m+" N:"+n);
		if (m>n) 
			System.out.println("M:"+m);
		else 
			System.out.println("N:"+n);
		int f=0;f++;
		System.out.println(f);
	}
	public  void test3(){
		int a=0,b=0,c=0;
		for (int i = 100; i <= 1000; i++) {
			a=i%10;
			b=(i/10)%10;
			c=(i/100)%10;
			if (i==(Math.pow(a, 3)+Math.pow(b, 3)+Math.pow(c, 3))) {
				System.out.println(i);
			}
		}
		System.out.println(153%10+"===="+(153/10)%10+"====="+(153/100)%10);
	}
	public void  test4() {
		Scanner scan= new Scanner(System.in);
		int num=scan.nextInt();
		if (num%7==0 && num%5==0) {
			System.out.println("yes");
		}else {
			System.out.println("NO");
		}
	}
	
	public void  test5() {
		Scanner scan= new Scanner(System.in);
		String str=scan.nextLine();
		char[] c = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','b','b','a','b','c'};
		for (int i = 0; i < str.length(); i++) {
			
			//char[] cl=new char[str.length()];
			//cl[i]=str.charAt(i);
			char cc =str.charAt(i);
			for (int j = 0; j < 26; j++) {
				if (cc==c[j]) {
					char zc=c[j+3];
					System.out.print(zc);
				}
			}
		}
	}
	//http://down.51cto.com/data/421031
	public void test7(){
		System.out.println("请输入1-7的数字");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		switch (num) {
		case 1:{
			System.out.println("Monday");
			break;
		}
		case 2:{
			System.out.println("Tuesday");
			break;
		}
		case 3:{
			System.out.println("Wednesday");
			break;
		}
		case 4:{
			System.out.println("Thursday");
			break;
		}
		case 5:{
			System.out.println("Friday");
			break;
		}
		case 6:{
			System.out.println("Staturday");
			break;
		}case 7:{
			System.out.println("Sunday");
			break;
		}
		default:{
			System.out.println("default");
			break;
		}
			
		}
		int i=10;
		while (i<15) {
			System.out.println(i);
			i++;
		}
	}
	public  void test8(){
		Scanner cin = new Scanner(System.in);
		int a = cin.nextInt();
		int b = cin.nextInt();
		int c = cin.nextInt(); 
		int f =0;
		if (a+b>c && a+c>b) {
			f = a+b+c;
			System.out.println("周长: "+f);
		}else {
			System.out.println("不能构成三角形");
		}
	}
	public void test9(){
		Scanner sum = new Scanner(System.in);
		System.out.println("请输入 a b c 的值");
		int a = sum.nextInt();
		int b = sum.nextInt();
		int c = sum.nextInt();
		int t = b*b-4*a*c;
		double x1=0,x2=0;
		System.out.println( "方程 "+a+"*x*x"+"+"+b+"*x+"+c +"= 0");
		if (t<0) 
		System.out.println("此方程无解");
		else if (t>=0) {
			x1=(-b+Math.pow(t, 0.5))/(2*a);
			x2=(-b-Math.pow(t, 0.5))/(2*a);
		}
		System.out.println("x1="+x1+" x2="+x2);
	}
	
	public void test10(){
		int a=0,b=1,c=1;
		for (int i = 1; i <=20; i++) {
			a=b;
			b=c;
			c=a+b;
			System.out.println("第"+i+"个月老兔子有"+b+"对幼兔子有"+a+"对总数有"+c);
		}
	}
	public void test11(){
		boolean flag=false;
		for (int i = 103; i < 1000; i=i+10) {
			for (int j = 2; j < i/2; j++) {
				if (i%j==0) {
					flag = true;
				}
				if (flag) {
					System.out.println(i);
				}
			}
		}
	}
	
}
