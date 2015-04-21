package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.Constant;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPaymentServiceTest {
	
	@Autowired
	PayPaymentService payPaymentService;
	@Test
	public void selectByObjectId(){
		List<PayPayment> payPaymentList=payPaymentService.selectByObjectIdAndBizType(1307810L, "SUPER_ORDER");
		for (PayPayment payPayment : payPaymentList) {
			System.out.println(payPayment.getPaymentGateway());
		}
	}
	
	@Test
	public void transferPaymentTest(){
		Long orgObjectId = 341917L;
		Long newObjectId = 1L;
		String bizType = "SUPER_ORDER";
		String objectType = "ORD_ORDER";
		Long transferedPaymentCount = payPaymentService.selectPaymentSuccessCount(orgObjectId);
		
		payPaymentService.transferPayment(orgObjectId, newObjectId, bizType, objectType);
		
		Assert.assertEquals(transferedPaymentCount, payPaymentService.selectPaymentSuccessCount(newObjectId));
		
		for (PayPayment payment : payPaymentService.selectByObjectIdAndBizType(newObjectId, bizType)) {
			Assert.assertEquals(orgObjectId, payment.getOriObjectId());
		}
	}
	@Test
	public void selectPayPaymentAndDetailByParamMap(){
		Map<String,String> paramMap=new HashMap<String,String>();
		//paramMap.put("objectId", "345189");
		//paramMap.put("paymentGateway", "CASH");
		//paramMap.put("gatewayTradeNo", "2012121261301958");
		//paramMap.put("receivingPerson", "cs0983");
		paramMap.put("cashAuditStatus", Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED.name());
		paramMap.put("start", "0");
		paramMap.put("end", "10");
		List<CashPaymentComboVO> cashPaymentComboVOList=payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		for (CashPaymentComboVO cashPaymentComboVO : cashPaymentComboVOList) {
			System.out.println(StringUtil.printParam(cashPaymentComboVO));
		}
	}
	@Test
	public void selectPayPaymentAuditAmountByParamMap(){
		Map<String,Long> map=payPaymentService.selectPayPaymentAuditAmountByParamMap(null);
		System.out.println(map.get("PAYMENTAMOUNTSUM"));
		System.out.println(map.get("AUDITPASSAMOUNTSUM"));
	}
	@Test
	@Rollback(false)
	public void callBackPayPayment(){
		PayPayment payment = new PayPayment();
		payment.setPaymentTradeNo("201212261556291591307307");
		payment.setCallbackInfo("测试");
		payment.setGatewayTradeNo("");
		payPaymentService.callBackPayPayment(payment, true);
	}
}
