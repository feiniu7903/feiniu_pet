package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.OrderListResult;

public class WrapOrderListResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private OrderListResult result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OrderListResult getResult() {
		return result;
	}

	public void setResult(OrderListResult result) {
		this.result = result;
	}

}
