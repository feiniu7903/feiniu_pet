package com.lvmama.pet.pay.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayElementService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPaymentGatewayElementServiceTest {
	
	@Autowired
	PayPaymentGatewayElementService payPaymentGatewayElementService;
	
	@Test
	public void savePayPaymentElementGateway() {
		PayPaymentGatewayElement payPaymentGatewayElement=new PayPaymentGatewayElement(); 
		payPaymentGatewayElement.setGateway("CASH");
		payPaymentGatewayElement.setIsPaymentTradeNo(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsGatewayTradeNo(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsRefundSerial(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsCallbackTime(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsCallbackInfo(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingCompany(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingPerson(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingBank(Boolean.TRUE.toString().toUpperCase());
		payPaymentGatewayElement.setStatus(Constant.PAYMENT_GATEWAY_ELEMENT_STATUS.ENABLE.name());
		System.out.println(payPaymentGatewayElementService.savePayPaymentGatewayElement(payPaymentGatewayElement));
	}

	@Test
	public void updatePayPaymentElementGateway() {
		PayPaymentGatewayElement payPaymentGatewayElement=new PayPaymentGatewayElement(); 
		payPaymentGatewayElement.setPaymentGatewayElementId(1L);
		payPaymentGatewayElement.setGateway("POST");
		payPaymentGatewayElement.setIsPaymentTradeNo(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsGatewayTradeNo(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsRefundSerial(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsCallbackTime(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsCallbackInfo(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingCompany(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingPerson(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setIsReceivingBank(Boolean.FALSE.toString().toUpperCase());
		payPaymentGatewayElement.setStatus(Constant.PAYMENT_GATEWAY_ELEMENT_STATUS.FORBIDDEN.name());
		
		System.out.println(payPaymentGatewayElementService.updatePayPaymentGatewayElement(payPaymentGatewayElement));
	}

	@Test
	public void selectPaymentGatewayElementByPK() {
		Long paymentGatewayElementId=1L;
		PayPaymentGatewayElement payPaymentGatewayElement=payPaymentGatewayElementService.selectPaymentGatewayElementByPK(paymentGatewayElementId);
		System.out.println(StringUtil.printParam(payPaymentGatewayElement));
	}
	@Test
	public void selectPaymentGatewayElementByGateway() {
		String gateway="CASH";
		PayPaymentGatewayElement payPaymentGatewayElement=payPaymentGatewayElementService.selectPaymentGatewayElementByGateway(gateway);
		System.out.println(StringUtil.printParam(payPaymentGatewayElement));
	}

	@Test
	public void selectPayPaymentGatewayElementByParamMap() {
		List<PayPaymentGatewayElement> payPaymentGatewayElementList=payPaymentGatewayElementService.selectPayPaymentGatewayElementByParamMap(null);
		for (PayPaymentGatewayElement payPaymentGatewayElement : payPaymentGatewayElementList) {
			System.out.println(StringUtil.printParam(payPaymentGatewayElement));
		}
	}
}
