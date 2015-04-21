package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.OrderDetailsReq;
import com.lvmama.comm.abroad.vo.response.OrderDetailsRes;

public interface IOrderDetails {
	public OrderDetailsRes getOrderDetails(OrderDetailsReq detailsReq);

}
