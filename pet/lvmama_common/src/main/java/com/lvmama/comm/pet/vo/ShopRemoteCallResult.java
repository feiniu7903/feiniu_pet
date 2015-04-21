package com.lvmama.comm.pet.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 积分商城远程调用返回的实体类
 * 当使用远程调用时，可能会返回不同的错误，所以使用此类来具体说明失败的原因
 * @author Brian
 *
 */
public class ShopRemoteCallResult implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5416064923425196668L;
	private boolean result;
	private int errorCode;
	private String errorText;
	private Object object;
	
	public ShopRemoteCallResult() {
		
	}
	
	public ShopRemoteCallResult(boolean result, int errorCode, String errorText) {
		this.result = result;
		this.errorCode = errorCode;
		this.errorText = errorText;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("result", result)
				.append("errorCode", errorCode).append("errorText", errorText).append("object", object).toString();
	}
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
