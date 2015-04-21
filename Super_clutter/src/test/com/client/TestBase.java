package com.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {
	public ApplicationContext context = null;
	public TestBase(){
		context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
	}
}	
