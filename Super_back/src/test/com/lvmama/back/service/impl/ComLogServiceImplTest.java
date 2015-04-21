package com.lvmama.back.service.impl;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:applicationContext-back-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ComLogServiceImplTest implements ApplicationContextAware{

	@Resource
	ComLogService comLogService;

	//@Test
	public void testInsert() {
		String log="长度测试长度测试长度";
		StringBuffer sb=new StringBuffer();
		int MAX=300;
		for(int i=0;i<MAX;i++){
			sb.append(log);
		}
		
		comLogService.insert("PROD_PRODUCT", 1L, 2L, "admin", "test", "test", sb.toString(), "test");
	}
	
	@Before
	public void doBeforeClass(){
	}

	//@Test
	public void testSearch(){
		long ipv=3684649430L;
//		ComIpsArea ip=doFind(0, list.size()-1, ipv);
//		Assert.assertNotNull(ip);
//		System.out.println(ip.getIpStart()+"    "+ip.getIpEnd());
//		System.out.println(ip.getCityName()+"  "+ip.getCapitalName());
//		ip=ComIpsAreaData.selectComIpsAreaByIp(ipv);
//		System.out.println(ip.getCityName());
	}
	
	@Test
	public void testSearch2(){
		long date=System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			testSearch();
		}
		System.out.println("time:"+(System.currentTimeMillis()-date));
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
