package com.lvmama.shholiday.notify;

import org.dom4j.Element;

public class TourOrderContentNotifyRQHandle extends AbstractShholidayOrderNotify {

	
	public TourOrderContentNotifyRQHandle(){
		super("OrderNotifyRQ", "OTA_TourOrderStatusNotifyRS");
	}
	
	@Override
	protected void handleOther(Element body) {
		
	}

}
