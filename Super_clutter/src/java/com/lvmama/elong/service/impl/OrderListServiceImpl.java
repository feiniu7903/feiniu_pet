package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderListCondition;
import com.lvmama.elong.model.OrderListResult;
import com.lvmama.elong.service.IOrderListService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapOrderListResult;

public class OrderListServiceImpl extends
		BaseService<OrderListCondition, WrapOrderListResult> implements
		IOrderListService {

	@Override
	public OrderListResult listOrder(OrderListCondition condition)
			throws ElongServiceException {
		WrapOrderListResult result = this.getResult(condition);
		if (null != result) {
			if ("0".equals(result.getCode())) {
				return result.getResult();
			} else {
				throw new ElongServiceException(result.getCode());
			}
		} else {
			throw new ElongServiceException("网络超时，请稍后再试");
		}
	}

	@Override
	public String method() {
		return "hotel.order.list";
	}

	@Override
	public boolean isRequiredSSL() {
		return true;
	}

}
