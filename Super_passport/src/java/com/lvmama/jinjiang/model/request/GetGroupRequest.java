package com.lvmama.jinjiang.model.request;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.GetGroupResposne;

/**
 * 实时获取团信息
 * @author chenkeke
 *
 */
public class GetGroupRequest extends AbstractRequest{
	private String groupCode;
	public GetGroupRequest(){
		
	}
	public GetGroupRequest(String groupCode){
		this.groupCode = groupCode;
	}
	
	private REQUEST_TYPE type= REQUEST_TYPE.OTA_GetGroupRQ;
	@Override
	public String getTransactionName() {
		return type.name();
	}
	@Override
	public String getTransactionMethod() {
		return type.getMethod();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return  GetGroupResposne.class;
	}
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
}
