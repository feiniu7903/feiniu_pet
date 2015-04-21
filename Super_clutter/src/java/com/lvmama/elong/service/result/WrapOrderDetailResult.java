package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.OrderDetailResult;
import com.lvmama.elong.model.OrderListResult;

public class WrapOrderDetailResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private OrderDetailResult result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OrderDetailResult getResult() {
		return result;
	}

	public void setResult(OrderDetailResult result) {
		this.result = result;
	}

}
