package com.lvmama.shholiday.request;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.OrderDetailResponse;

public class OrderDetailRequest extends AbstractRequest {

	@Override
	public String getTransactionName() {
		
		return REQUEST_TYPE.OTA_TourOrderDetailRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		
		return OrderDetailResponse.class;
	}
	
	public OrderDetailRequest(String orderNo){
		addParam("orderNo",orderNo);
	}

}
