package com.lvmama.shholiday.response;

import org.dom4j.Element;

public class UpdateOrderResponse extends AbstractResponse {

	
	public UpdateOrderResponse(){
		super("OTA_TourOrderUpdateRS");
	}
	
	@Override
	protected void parseBody(Element body) {
	}

}
