package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CancelOrderCondition;
import com.lvmama.elong.model.CancelOrderResult;

public interface IOrderCancelService {
	CancelOrderResult cancelOrder(CancelOrderCondition condition) throws ElongServiceException;
}
