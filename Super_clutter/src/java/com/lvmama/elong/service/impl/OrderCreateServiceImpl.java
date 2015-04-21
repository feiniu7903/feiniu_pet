package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CreateOrderCondition;
import com.lvmama.elong.model.CreateOrderResult;
import com.lvmama.elong.service.IOrderCreateService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapCreateOrderResult;

public class OrderCreateServiceImpl extends
		BaseService<CreateOrderCondition, WrapCreateOrderResult> implements
		IOrderCreateService {

	@Override
	public CreateOrderResult createOrder(CreateOrderCondition condition)
			throws ElongServiceException {
		WrapCreateOrderResult result = this.getResult(condition);
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
		return "hotel.order.create";
	}

	@Override
	public boolean isRequiredSSL() {
		return true;
	}

}
