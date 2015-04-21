package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IOrderPayment;

public class OrderPaymentTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelOrderPaymentService")
	private IOrderPayment orderPayment;
//	@Test
//	public void paymentCallbackTest(){
//		boolean retCode=orderPayment.paymentCallback("1341", "11900", "");
//		System.out.println(retCode);
//	}
	@Test
	public void refundCallbackTest(){
		boolean retCode=orderPayment.refundCallback("1781", "0", "1111257637");
		System.out.println(retCode);
	}
}
