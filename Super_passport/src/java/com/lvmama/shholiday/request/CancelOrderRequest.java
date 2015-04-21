package com.lvmama.shholiday.request;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.CancelOrderResponse;

public class CancelOrderRequest extends AbstractRequest {

	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourOrderCancelRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return  CancelOrderResponse.class;
	}
	
	public CancelOrderRequest(String orderNo){
		addParam("orderNo",orderNo);
	}

}
