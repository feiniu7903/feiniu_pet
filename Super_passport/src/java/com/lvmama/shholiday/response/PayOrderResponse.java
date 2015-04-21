package com.lvmama.shholiday.response;

import org.dom4j.Element;

public class PayOrderResponse extends AbstractResponse {

	public PayOrderResponse(){
		super("OTA_TourOrderPayRS");
	}
	
	@Override
	protected void parseBody(Element body) {
		
	}

}
