package com.lvmama.shholiday.response;

import org.dom4j.Element;

public class CancelOrderResponse extends AbstractResponse {



	public CancelOrderResponse() {
		super("OTA_TourOrderPayRS");
	}

	@Override
	protected void parseBody(Element body) {
		
	}

	
}
