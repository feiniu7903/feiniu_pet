package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CancelOrderCondition;
import com.lvmama.elong.model.CancelOrderResult;
import com.lvmama.elong.service.IOrderCancelService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapOrderCancelResult;

public class OrderCancelServiceImpl extends
		BaseService<CancelOrderCondition, WrapOrderCancelResult> implements
		IOrderCancelService {

	@Override
	public CancelOrderResult cancelOrder(CancelOrderCondition condition)
			throws ElongServiceException {
		WrapOrderCancelResult result = this.getResult(condition);
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
		return "hotel.order.cancel";
	}

	@Override
	public boolean isRequiredSSL() {
		return true;
	}

}
