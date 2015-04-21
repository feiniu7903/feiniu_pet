package com.lvmama.pet.job.sms;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.pet.job.BaseTest;

public class ForPaymentReceiverLogicTest{

	//@Autowired
	private static ForPaymentReceiverLogic forPaymentReceiverLogic;
	
	//@Test
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:/applicationContext-pet-job-beans.xml");
		forPaymentReceiverLogic = context.getBean(ForPaymentReceiverLogic.class);
		String mobile="18616016302";
		String code="YUVG";
		int x=forPaymentReceiverLogic.execute(mobile, code);
		Assert.assertEquals(x, ForPaymentReceiverLogic.SKIP_MESSAGE);
	}

}
