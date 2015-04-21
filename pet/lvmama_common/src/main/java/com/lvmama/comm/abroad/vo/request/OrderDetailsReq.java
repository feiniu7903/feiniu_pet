package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class OrderDetailsReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String OrderID;//订单id

	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

}
