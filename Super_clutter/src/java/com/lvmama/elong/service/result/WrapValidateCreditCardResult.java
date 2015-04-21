package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.CancelOrderResult;
import com.lvmama.elong.model.ValidateCreditCardResult;

public class WrapValidateCreditCardResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private ValidateCreditCardResult result;

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public ValidateCreditCardResult getResult() {
		return result;
	}

	public void setResult(ValidateCreditCardResult result) {
		this.result = result;
	}
	
}
