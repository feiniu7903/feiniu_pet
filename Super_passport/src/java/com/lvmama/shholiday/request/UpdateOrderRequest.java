package com.lvmama.shholiday.request;

import java.util.List;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.UpdateOrderResponse;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderPassenger;

public class UpdateOrderRequest extends AbstractRequest {

	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourOrderUpdateRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz(){
		return UpdateOrderResponse.class;
	}
	
	public UpdateOrderRequest(List<OrderPassenger> orderPassengers,Contact contact,String uniqueID){
		addParam("orderPassengers",orderPassengers);
		addParam("contact",contact);
		addParam("uniqueID",uniqueID);
	}

}
