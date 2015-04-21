/**
 * 
 */
package com.lvmama.order.service.impl;

import java.io.File;
import java.io.FileWriter;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;

/**
 * @author yangbin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class EbkCertBuilderTest2 implements ApplicationContextAware{

	@Resource
	private EbkCertificateService ebkCertificateService;
	
	@Test
	public void testCreateChange()throws Exception{
		EbkCertificate newEbkCert=ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(8436L);
		//EbkCertificate oldEbkCert=ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(7482L);
		
		EbkCertBuilder builder = EbkCertBuilderFactory.create(newEbkCert);
		FileWriter writer = new  FileWriter(new File("d:/route_hotel2.html"));
		builder.makeCertContent(newEbkCert,writer);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}
	
}
