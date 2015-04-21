package com.lvmama.jinjiang.model.request;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.Order;
import com.lvmama.jinjiang.model.response.AddOrderResponse;

/**
 * 创建订单接口
 * @author chenkeke
 *
 */
public class AddOrderRequest extends AbstractRequest{
	private Order order;
	private REQUEST_TYPE type= REQUEST_TYPE.OTA_AddOrderRQ;
	public AddOrderRequest(Order order){
		this.order = order;
	}
	
	public AddOrderRequest(){
		
	}
	@Override
	public String getTransactionName() {
		return type.name();
	}
	@Override
	public String getTransactionMethod() {
		return type.getMethod();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return  AddOrderResponse.class;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


}
