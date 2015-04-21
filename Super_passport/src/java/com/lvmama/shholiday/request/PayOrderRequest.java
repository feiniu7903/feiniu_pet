package com.lvmama.shholiday.request;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.PayOrderResponse;
import com.lvmama.shholiday.vo.order.OrderPayInfo;

public class PayOrderRequest extends AbstractRequest {

	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourOrderPayRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return PayOrderResponse.class;
	}
	

	public PayOrderRequest(OrderPayInfo payInfo){
		addParam("payInfo",payInfo);
	}
}
