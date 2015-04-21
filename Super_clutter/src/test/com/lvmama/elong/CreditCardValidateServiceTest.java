package com.lvmama.elong;

import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCreditCardCondition;
import com.lvmama.elong.model.ValidateCreditCardResult;
import com.lvmama.elong.service.ICreditCardValidateService;
import com.lvmama.elong.service.impl.CreditCardValidateServiceImpl;
import com.lvmama.elong.utils.DES;

public class CreditCardValidateServiceTest {

	@Test
	public void testGetResult() throws Exception {
		String key = "ef2e2d74";
		ValidateCreditCardCondition condition = new ValidateCreditCardCondition();
		condition.setCreditCardNo(new String(DES.encrypt(
				(System.currentTimeMillis() + "#1234456789098711"), key)));
		ICreditCardValidateService creditCardValidateService = new CreditCardValidateServiceImpl();
		ValidateCreditCardResult validateCreditCardResult = creditCardValidateService
				.validateCreditCard(condition);
		System.out.println(validateCreditCardResult);
	}

}
