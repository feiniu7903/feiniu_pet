package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderListCondition;
import com.lvmama.elong.model.OrderListResult;

public interface IOrderListService {
	OrderListResult listOrder(OrderListCondition condition) throws ElongServiceException;
}
