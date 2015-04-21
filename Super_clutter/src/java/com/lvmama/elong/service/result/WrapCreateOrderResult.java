package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.CreateOrderResult;

public class WrapCreateOrderResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private CreateOrderResult result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CreateOrderResult getResult() {
		return result;
	}

	public void setResult(CreateOrderResult result) {
		this.result = result;
	}

}
