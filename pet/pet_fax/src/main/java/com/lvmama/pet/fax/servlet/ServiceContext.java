package com.lvmama.pet.fax.servlet;

import org.springframework.context.ApplicationContext;


/**
 * @author Alex
 *
 */
public class ServiceContext {

	private static ApplicationContext applicationContext;
	
	public synchronized static void setApplicationContext(ApplicationContext arg0) {
		applicationContext = arg0;
	}
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
}