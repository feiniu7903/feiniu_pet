package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.RelatedOrderResult;

public class WrapOrderRelationResult {
	@JSONField(name = "Code")
	private String code;
	
	@JSONField(name = "Result")
	protected RelatedOrderResult result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public RelatedOrderResult getResult() {
		return result;
	}

	public void setResult(RelatedOrderResult result) {
		this.result = result;
	}

}
