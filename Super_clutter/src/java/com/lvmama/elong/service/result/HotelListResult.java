package com.lvmama.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.elong.model.HotelList;

public class HotelListResult {

	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Result")
	private HotelList result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HotelList getResult() {
		return result;
	}

	public void setResult(HotelList result) {
		this.result = result;
	}

}
