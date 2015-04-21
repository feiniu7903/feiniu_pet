package com.lvmama.back.sweb.invoice;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.service.ord.OrderService;

public abstract class BaseInvoiceAction extends BaseAction {

	/**
	 * 订单服务
	 */
	protected OrderService orderServiceProxy;

	public BaseInvoiceAction() {
		super();
	}

	/**
	 * @param orderServiceProxy the orderServiceProxy to set
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}