package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.RelatedOrderCondition;
import com.lvmama.elong.model.RelatedOrderResult;
import com.lvmama.elong.service.IOrderRelationService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.WrapOrderRelationResult;

public class OrderRelationServiceImpl extends BaseService<RelatedOrderCondition, WrapOrderRelationResult> implements
		IOrderRelationService {

	@Override
	public RelatedOrderResult getRelatedOrder(RelatedOrderCondition condition)
			throws ElongServiceException {
		WrapOrderRelationResult result = this.getResult(condition);
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
		// TODO Auto-generated method stub
		return "hotel.order.related";
	}

	@Override
	public boolean isRequiredSSL() {
		// TODO Auto-generated method stub
		return true;
	}


}
