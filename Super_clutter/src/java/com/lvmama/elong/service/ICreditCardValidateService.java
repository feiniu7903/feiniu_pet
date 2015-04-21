package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCreditCardCondition;
import com.lvmama.elong.model.ValidateCreditCardResult;

public interface ICreditCardValidateService {
	ValidateCreditCardResult validateCreditCard(ValidateCreditCardCondition condition) throws ElongServiceException ;
}
