package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.CancelOrderResult;

public class WrapOrderCancelResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private CancelOrderResult result;

	public String getCode() {
		return code;
	}

	public CancelOrderResult getResult() {
		return result;
	}

	public void setResult(CancelOrderResult result) {
		this.result = result;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
