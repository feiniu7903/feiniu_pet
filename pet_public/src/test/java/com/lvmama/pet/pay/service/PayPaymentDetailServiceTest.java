package com.lvmama.pet.pay.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPaymentDetailServiceTest {
	
	@Autowired
	PayPaymentDetailService payPaymentDetailService;
	
	@Test
	public void savePayPaymentDetail() {
		PayPaymentDetail payPaymentDetail=new PayPaymentDetail();
		payPaymentDetail.setPaymentId(15899L);
		payPaymentDetail.setReceivingCompanyId(2L);
		payPaymentDetail.setReceivingBankId(3L);
		payPaymentDetail.setReceivingPerson("admin");
		payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED.name());
		System.out.println(payPaymentDetailService.savePayPaymentDetail(payPaymentDetail));
	}

	@Test
	public void updatePayPaymentDetail() {
		PayPaymentDetail payPaymentDetail=new PayPaymentDetail(); 
		payPaymentDetail.setPaymentDetailId(2L);
		payPaymentDetail.setReceivingPerson("admin2");
		payPaymentDetail.setCashLiberateMoneyPerson("admin");
		payPaymentDetail.setCashLiberateMoneyDate(new Date());
		payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE.name());
		payPaymentDetail.setAuditPerson("admin");
		payPaymentDetail.setReceivingBankId(4L);
		System.out.println(payPaymentDetailService.updatePayPaymentDetail(payPaymentDetail));
	}

	@Test
	public void selectPaymentDetailByPK() {
		Long paymentDetailId=2L;
		PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPK(paymentDetailId);
		System.out.println(StringUtil.printParam(payPaymentDetail));
	}
	@Test
	public void selectPaymentDetailByPaymentId() {
		String paymentId="15899";
		PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(paymentId);
		System.out.println(StringUtil.printParam(payPaymentDetail));
	}

	@Test
	public void selectPayPaymentDetailByParamMap() {
		List<PayPaymentDetail> payPaymentDetailList=payPaymentDetailService.selectPayPaymentDetailByParamMap(null);
		for (PayPaymentDetail payPaymentDetail : payPaymentDetailList) {
			System.out.println(StringUtil.printParam(payPaymentDetail));
		}
	}
}
