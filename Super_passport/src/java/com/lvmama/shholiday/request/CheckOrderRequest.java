package com.lvmama.shholiday.request;

import java.util.List;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.CheckOrderResponse;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderBaseInfo;
import com.lvmama.shholiday.vo.order.OrderFavorInfo;
import com.lvmama.shholiday.vo.order.OrderPassenger;
import com.lvmama.shholiday.vo.order.ProductInfo;

public class CheckOrderRequest extends AbstractRequest {

	private Contact contact;
	private ProductInfo productInfo;
	private List<OrderPassenger> orderPassengers;
	private OrderBaseInfo orderBaseInfo;
	private List<OrderFavorInfo> orderFavorInfos;
	
	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourBookCheckRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return CheckOrderResponse.class;
	}

	public CheckOrderRequest(Contact contact,List<OrderPassenger> orderPassengers,
			OrderBaseInfo orderBaseInfo,ProductInfo productInfo,List<OrderFavorInfo> orderFavorInfos){
		super();
		this.contact=contact;
		this.orderBaseInfo=orderBaseInfo;
		this.orderFavorInfos=orderFavorInfos;
		this.orderPassengers=orderPassengers;
		this.productInfo=productInfo;
		makeParam();
	}
	
		private void makeParam(){
		
		addParam("orderBaseInfo",orderBaseInfo);
		addParam("productInfo",productInfo);
		addParam("contact",contact);
		addParam("orderPassengers",orderPassengers);
		addParam("favorInfos",orderFavorInfos);
	}
}
