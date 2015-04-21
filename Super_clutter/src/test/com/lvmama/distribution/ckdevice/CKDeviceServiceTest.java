package com.lvmama.distribution.ckdevice;

import java.io.InputStream;

import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.distribution.service.DistributionForCKDeviceService;

@ContextConfiguration({ "classpath:applicationContext-clutter-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CKDeviceServiceTest {

	@Autowired
	private DistributionForCKDeviceService distributionForCKDeviceService;
	
	@Test
	public void CheckReservation() throws Exception{
		String xmlStr = this.getXmlStr("ckCheckReservation.xml");
		String message = distributionForCKDeviceService.checkReservation(xmlStr);
	}
	
	@Test
	public void getPrintInfo() throws Exception{
		String xmlStr = this.getXmlStr("ckGetPrintInfo.xml");
		String message = distributionForCKDeviceService.getPrintInfo(xmlStr);
	}
	
	@Test
	public void confirmPrint() throws Exception{
		String xmlStr = this.getXmlStr("ckconfirmPrint.xml");
		String message = distributionForCKDeviceService.confirmPrint(xmlStr);
	}
	
	@Test
	public void queryProductList() throws Exception{
		String xmlStr = this.getXmlStr("ckqueryProductList.xml");
		String message = distributionForCKDeviceService.queryProductList(xmlStr);
	}
	
	@Test
	public void queryOrder() throws Exception{
		String xmlStr = this.getXmlStr("ckQueryOrder.xml");
		String message = distributionForCKDeviceService.queryOrder(xmlStr);
	}
	
	@Test
	public void createOrder() throws Exception{
		String xmlStr = this.getXmlStr("ckCreateOrder.xml");
		String message = distributionForCKDeviceService.createOrder(xmlStr);
	}
	
	@Test
	public void confirmPayment() throws Exception{
		String xmlStr = this.getXmlStr("ckconfirmPayment.xml");
		String message = distributionForCKDeviceService.confirmPayment(xmlStr);
	}
	
	@Test
	public void requestPaymentChecksum() throws Exception{
		String xmlStr = this.getXmlStr("ckrequestPaymentChecksum.xml");
		String message = distributionForCKDeviceService.requestPaymentChecksum(xmlStr);
	}
	
	
	private String getXmlStr(String fileName) throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/lvmama/distribution/ckdevice/"+fileName);
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}

}
