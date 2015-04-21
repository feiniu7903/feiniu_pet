package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCreditCardCondition;
import com.lvmama.elong.model.ValidateCreditCardResult;
import com.lvmama.elong.service.ICreditCardValidateService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapValidateCreditCardResult;

public class CreditCardValidateServiceImpl extends
		BaseService<ValidateCreditCardCondition, WrapValidateCreditCardResult>
		implements ICreditCardValidateService {

	@Override
	public ValidateCreditCardResult validateCreditCard(
			ValidateCreditCardCondition condition) throws ElongServiceException {
		WrapValidateCreditCardResult result = this.getResult(condition);
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
		return "common.creditcard.validate";
	}

	@Override
	public boolean isRequiredSSL() {
		return true;
	}

}
