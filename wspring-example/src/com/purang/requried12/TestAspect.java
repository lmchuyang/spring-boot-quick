package com.purang.requried12;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
@Controller
@Aspect
public class TestAspect {

	@Pointcut(value="execution(* testWeb*(..))")//任何一个以“testWeb”开始的方法的执行：
	private void pointcut(){}
	@Before(value="pointcut()")
	public void before(){
		System.out.println("================Aspece before");
	}
}
