package com.lvmama.jinjiang.model.request;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.GetVisasResposne;

/**
 * 获取签证信息
 * @author chenkeke
 *
 */
public class GetVisasRequest extends AbstractRequest{
	private String visaCode;
	public GetVisasRequest(){
		
	}
	public GetVisasRequest(String visaCode){
		this.visaCode = visaCode;
	}

	private REQUEST_TYPE type= REQUEST_TYPE.OTA_GetVisasRQ;
	@Override
	public String getTransactionName() {
		return type.name();
	}
	@Override
	public String getTransactionMethod() {
		return type.getMethod();
	};

	@Override
	public Class<? extends Response> getResponseClazz() {
		return  GetVisasResposne.class;
	}
	public String getVisaCode() {
		return visaCode;
	}

	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}
}
