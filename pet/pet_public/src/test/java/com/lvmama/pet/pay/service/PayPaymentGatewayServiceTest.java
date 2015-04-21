package com.lvmama.pet.pay.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPaymentGatewayServiceTest {
	
	@Autowired
	PayPaymentGatewayService payPaymentGatewayService;
	
	@Test
	public void savePayPaymentGateway() {
		PayPaymentGateway payPaymentGateway=new PayPaymentGateway(); 
		payPaymentGateway.setGateway("TEST");
		payPaymentGateway.setGatewayName("测试");
		payPaymentGateway.setGatewayStatus("ENABLE");
		payPaymentGateway.setGatewayType("ONLINE");
		payPaymentGateway.setIsAllowRefund("TRUE");
		payPaymentGateway.setRefundGateway("TEST");
		payPaymentGateway.setCreateTime(new Date());
		System.out.println(payPaymentGatewayService.savePayPaymentGateway(payPaymentGateway));
	}

	@Test
	public void updatePayPaymentGateway() {
		PayPaymentGateway payPaymentGateway=new PayPaymentGateway(); 
		payPaymentGateway.setGateway("TEST2");
		payPaymentGateway.setGatewayName("测试2");
		payPaymentGateway.setGatewayStatus("FORBIDDEN");
		payPaymentGateway.setGatewayType("DIST");
		payPaymentGateway.setIsAllowRefund("FALSE");
		payPaymentGateway.setRefundGateway("");
		payPaymentGateway.setPaymentGatewayId(95L);
		payPaymentGateway.setCreateTime(new Date());
		
		System.out.println(payPaymentGatewayService.updatePayPaymentGateway(payPaymentGateway));
	}

	@Test
	public void selectPaymentGatewayByPK() {
		Long paymentGatewayId=95L;
		PayPaymentGateway payPaymentGateway=payPaymentGatewayService.selectPaymentGatewayByPK(paymentGatewayId);
		System.out.println(payPaymentGateway.toString());
	}
	@Test
	public void selectPaymentGatewayByGateway() {
		String gateway="ALIPAY";
		PayPaymentGateway payPaymentGateway=payPaymentGatewayService.selectPaymentGatewayByGateway(gateway);
		System.out.println(payPaymentGateway.toString());
	}

	@Test
	public void selectPayPaymentGatewayByParamMap() {
		Map<String, String> paramMap=new HashMap<String,String>();
		paramMap.put("isAllowRefund", "TRUE");
		paramMap.put("start", "0");
		paramMap.put("end", "10");
		paramMap.put("orderby", "CREATE_TIME");
		paramMap.put("order", "DESC");
		List<PayPaymentGateway> payPaymentGatewayList=payPaymentGatewayService.selectPayPaymentGatewayByParamMap(null);
		for (PayPaymentGateway payPaymentGateway : payPaymentGatewayList) {
			System.out.println(payPaymentGateway.toString());
		}
	}
}
