package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCondition;
import com.lvmama.elong.model.ValidateResult;

public interface IOrderValidateService {
	ValidateResult validateOrder(ValidateCondition condition) throws ElongServiceException;
}
