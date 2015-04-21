package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCondition;
import com.lvmama.elong.model.ValidateResult;
import com.lvmama.elong.service.IOrderValidateService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapValidateResult;

public class OrderValidateServiceImpl extends
		BaseService<ValidateCondition, WrapValidateResult> implements
		IOrderValidateService {

	@Override
	public String method() {
		return "hotel.data.validate";
	}

	@Override
	public boolean isRequiredSSL() {
		return false;
	}

	@Override
	public ValidateResult validateOrder(ValidateCondition condition)
			throws ElongServiceException {
		WrapValidateResult result = this.getResult(condition);
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

}
