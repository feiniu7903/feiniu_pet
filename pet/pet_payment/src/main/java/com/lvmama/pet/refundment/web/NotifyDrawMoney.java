package com.lvmama.pet.refundment.web;

public interface NotifyDrawMoney {
	
	boolean checkSignature();
	
	boolean isComefromAlipay();
	
	boolean process() throws Exception;
	
}
