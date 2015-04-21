package com.lvmama.jinjiang.model.response;

import com.lvmama.jinjiang.model.OutOrder;

public class AddOrderResponse extends AbstractResponse{
	private OutOrder outOrder;

	public OutOrder getOutOrder() {
		return outOrder;
	}

	public void setOutOrder(OutOrder outOrder) {
		this.outOrder = outOrder;
	}
}
