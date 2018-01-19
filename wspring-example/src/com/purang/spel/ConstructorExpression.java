package com.purang.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;


public class ConstructorExpression {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConstructorExpression con = new ConstructorExpression();
		con.textContstructor();
	}

	public void textContstructor() {
		 ExpressionParser parser = new SpelExpressionParser();  
		    String result1 = parser.parseExpression("new String('haha')").getValue(String.class);  
		    System.out.println(result1.equals("haha")?true:false);
		    
		    //Date result2 = parser.parseExpression("new java.util.Date()").getValue(Date.class);  
		    //Assert.assertNotNull(result2);  
		    String str2 = parser.parseExpression("new com.purang.spel.ConstructorExpression().retString('abc')").getValue(String.class);
		    System.out.println(str2);
		    
	}
	public String  retString(String str) {
		return "asdfghjkl";
	}
}
