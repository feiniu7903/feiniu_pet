package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderDetailResult;
import com.lvmama.elong.model.OrderIdsCondition;

public interface IOrderDetailService {
	OrderDetailResult detailOrder(OrderIdsCondition condition)
			throws ElongServiceException;
}
