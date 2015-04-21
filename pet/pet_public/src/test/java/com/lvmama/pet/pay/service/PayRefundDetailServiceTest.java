package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.vo.PayRefundDetail;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayRefundDetailServiceTest {

	@Autowired
	PayPaymentRefundmentService payPaymentRefundmentService;

	@Test
	public void selectPayRefundDetailByParams() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("refundStatus", "PROCESSING");
		List<PayRefundDetail> payRefundDetailList = payPaymentRefundmentService.selectPayRefundDetailList(paramMap, 0, Integer.MAX_VALUE);

		for (PayRefundDetail payRefundDetail : payRefundDetailList) {
			System.out.println(payRefundDetail.toString());
		}

		System.out.println(payRefundDetailList.size() + " Rows in total ");
	}


}
