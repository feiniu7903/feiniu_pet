package com.lvmama.jinjiang.model.request;

import java.util.Date;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.LineCodesResponse;


/**
 * 批量同步线路代码请求
 * @author chenkeke
 *
 */
public class LineCodesRequest extends AbstractRequest{

	private Date updateTimeStart;
	private Date updateTimeEnd;
	public LineCodesRequest(){
		
	}
	public LineCodesRequest (Date updateTimeStart,Date updateTimeEnd){
		this.updateTimeStart = updateTimeStart;
		this.updateTimeEnd = updateTimeEnd;
	}
	
	private REQUEST_TYPE type= REQUEST_TYPE.OTA_LineCodesRQ;
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
		return  LineCodesResponse.class;
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
}
