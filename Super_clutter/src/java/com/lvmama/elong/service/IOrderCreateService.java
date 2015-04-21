package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CreateOrderCondition;
import com.lvmama.elong.model.CreateOrderResult;
import com.lvmama.elong.service.result.WrapCreateOrderResult;

public interface IOrderCreateService {
	CreateOrderResult createOrder(CreateOrderCondition condition) throws ElongServiceException;
}
