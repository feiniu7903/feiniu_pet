package com.lvmama.bee;

import java.net.MalformedURLException;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caucho.hessian.client.HessianProxyFactory;

@ContextConfiguration(locations = { "classpath*:/applicationContext-bee-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
	private final static String url = "http://localhost:8062/bee_public/remoting/";
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName,Class<T> requiredType) {
		T t=null;
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			t = (T) factory.create(requiredType, url+beanName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return t;
	}
}
