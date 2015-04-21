package com.lvmama.comm.vo.train.notify;

import com.lvmama.comm.vo.train.ReqVo;

public class ProductUpdateNotifyVo extends ReqVo{
	/**
	 * 接口ID
	 */
	private String interfaceId;
	/**
	 * 更新数据对应的日期
	 */
	private String requestDate;
	/**
	 * all-获取全量数据；update-获取变化数据
	 */
	private String requestType;
	/**
	 * 更新请求描述
	 */
	private String operInfo;
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getOperInfo() {
		return operInfo;
	}
	public void setOperInfo(String operInfo) {
		this.operInfo = operInfo;
	}
	
	@Override
	public String toString(){
		return "产品数据更新通知参数：(merchant_id:"+this.getMerchantId()
			 + "|sign:" + this.getSign()
			 + "|interface_id:" + this.getInterfaceId()
			 + "|request_date:" + this.getRequestDate()
			 + "|oper_info:" + this.getOperInfo() + ")";
	}
}
