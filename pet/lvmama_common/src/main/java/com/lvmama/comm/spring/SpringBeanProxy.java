package com.lvmama.comm.spring;

import org.springframework.context.ApplicationContext;


/**
 * @author Alex
 *
 */
public class SpringBeanProxy {

	private static ApplicationContext applicationContext;
	
	public synchronized static void setApplicationContext(ApplicationContext arg0) {
		applicationContext = arg0;
	}
	
    /**
     * 获取bean的方法 
     * @param <T>
     * @param clazz
     * @param beanName
     * @return
     */
    public static <T> T getBean(Class<T> clazz,String beanName){
    	Object o = applicationContext.getBean(beanName);
    	if(o!=null){
    		return clazz.cast(o);
    	}
    	return null;
    }
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
}