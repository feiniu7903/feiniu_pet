package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.vo.PayRefundDetail;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPaymentRefundmentServiceTest {
	
	@Autowired
	PayPaymentRefundmentService payPaymentRefundmentService;
	@Test
	public void selectByObjectId(){
		List<PayPaymentRefundment> payPaymentRefundmentList=payPaymentRefundmentService.selectRefundListByObjectIdAndBizType(null, null,null);
		System.out.println(payPaymentRefundmentList);
	}
	@Test
	public void getRefunmentAmountByPaymentId(){
		Long l=payPaymentRefundmentService.getRefunmentAmountByPaymentId("8150162");
		System.out.println(l);
	}
	@Test
	public void selectPayRefundDetailList(){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("objectId", 1306027);
		List<PayRefundDetail> payRefundDetailList=payPaymentRefundmentService.selectPayRefundDetailList(param, 0, 10);
		for (PayRefundDetail payRefundDetail : payRefundDetailList) {
			System.out.println("----------------------------------------"+payRefundDetail);
		}
	}
}
