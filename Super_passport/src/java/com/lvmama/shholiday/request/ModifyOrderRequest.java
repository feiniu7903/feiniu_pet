package com.lvmama.shholiday.request;

import java.util.List;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.ModifyOrderResponse;
import com.lvmama.shholiday.vo.order.OrderModifyInfo;
import com.lvmama.shholiday.vo.order.OrderPassenger;

public class ModifyOrderRequest extends AbstractRequest {

	@Override
	public String getTransactionName() {
		// TODO Auto-generated method stub
		return REQUEST_TYPE.OTA_TourBookModifyRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		// TODO Auto-generated method stub
		return ModifyOrderResponse.class;
	}

	public ModifyOrderRequest(List<OrderPassenger> orderPassengers,OrderModifyInfo modifyInfo){
		addParam("modifyInfo",modifyInfo);
		addParam("orderPassengers",orderPassengers);
	}
}
