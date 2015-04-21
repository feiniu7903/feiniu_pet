package com.lvmama.comm.pet.po.sms;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class SmsContentTest {
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate() throws Exception {
		Date date = new Date();
		
		new SmsContent(null, null, 1, null, date);
		fail("ERROR");
	}
	
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate1() throws Exception {
		Date date = new Date();
		
		new SmsContent("", null, 1, null, date);
		fail("ERROR");
	}	

	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate2() throws Exception {
		Date date = new Date();
		
		new SmsContent("test", null, 1, null, date);
		fail("ERROR");
	}
	
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate3() throws Exception {
		Date date = new Date();
		
		new SmsContent("test", "123", 1, null, date);
		fail("ERROR");
	}
	
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate4() throws Exception {
		Date date = new Date();
		StringBuilder mobile = new StringBuilder("13917677725");
		for (int i = 0 ; i < 101 ; i++) {
			mobile.append(",").append("13917677725");
		}
		new SmsContent("test", mobile.toString(), 1, null, date);
		fail("ERROR");
	}
	
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate5() throws Exception {
		Date date = new Date();
		
		new SmsContent("test", "13917677725|13564981076", 1, null, date);
		fail("ERROR");
	}
	
	@Test(expected=Exception.class)
	public void smsContentStringStringIntStringDate6() throws Exception {
		Date date = new Date();
		
		new SmsContent("test", "13917677725", 0, null, date);
		fail("ERROR");
	}	


}
