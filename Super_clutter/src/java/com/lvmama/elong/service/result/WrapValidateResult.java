package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.HotelList;
import com.lvmama.elong.model.ValidateResult;

public class WrapValidateResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private ValidateResult result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ValidateResult getResult() {
		return result;
	}

	public void setResult(ValidateResult result) {
		this.result = result;
	}

}
