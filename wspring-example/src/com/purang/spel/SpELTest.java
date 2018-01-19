package com.purang.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     SpELTest ste = new SpELTest();
     ste.hellowold();
     ste.testParserContext();
	}

	public void hellowold() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = (Expression) parser.parseExpression("('Hello' + ' World').concat(#end)");
		 EvaluationContext context = new StandardEvaluationContext();  
	     context.setVariable("end", "!");  
	     System.out.println("Hello World:"+expression.getValue(context)); 
	}
	public void testParserContext() {
		SpelExpressionParser parser = new SpelExpressionParser();
		ParserContext  context = new ParserContext(){
			@Override
			public String getExpressionPrefix() {
				return "#{";
			}

			@Override
			public String getExpressionSuffix() {
				return "}";
			}

			@Override
			public boolean isTemplate() {
				return true;
			}
		};
		String str="#{'expression '}#{'parser!'}";
		Expression pre = parser.parseExpression(str,context);
		System.out.println(pre.getValue());
		
		String str2 = parser.parseExpression("\"Hello World!\"").getValue(String.class);
		System.out.println(str2);
		
		String expression1 = "2>1 and (!true or !false)";  
		boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);  
		System.out.println(result1);
		
	}
}
