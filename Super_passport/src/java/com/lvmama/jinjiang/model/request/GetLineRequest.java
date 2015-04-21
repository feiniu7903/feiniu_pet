package com.lvmama.jinjiang.model.request;

import java.util.Date;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.GetLineResposne;

/**
 * 获取线路
 * @author chenkeke
 *
 */
public class GetLineRequest extends AbstractRequest{
	private String lineCode;
	private Date updateTimeStart;
	private Date updateTimeEnd;
	private REQUEST_TYPE type= REQUEST_TYPE.OTA_GetLineRQ;
	public GetLineRequest(){
		
	}
	public GetLineRequest(String lineCode,Date updateTimeStart,Date updateTimeEnd){
		this.lineCode = lineCode;
		this.updateTimeStart = updateTimeStart;
		this.updateTimeEnd = updateTimeEnd;
	}
	
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
		return  GetLineResposne.class;
	}
	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}
	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}
	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}
	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
}
