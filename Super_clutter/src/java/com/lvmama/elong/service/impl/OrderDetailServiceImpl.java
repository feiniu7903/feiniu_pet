package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderDetailResult;
import com.lvmama.elong.model.OrderIdsCondition;
import com.lvmama.elong.service.IOrderDetailService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapOrderDetailResult;

public class OrderDetailServiceImpl extends
		BaseService<OrderIdsCondition, WrapOrderDetailResult> implements
		IOrderDetailService {

	@Override
	public OrderDetailResult detailOrder(OrderIdsCondition condition)
			throws ElongServiceException {
		WrapOrderDetailResult result = this.getResult(condition);
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
		return "hotel.order.detail";
	}

	@Override
	public boolean isRequiredSSL() {
		return true;
	}

}
