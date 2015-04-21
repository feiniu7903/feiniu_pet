package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
 
import com.lvmama.passport.processor.impl.client.zhiyoubao.ZhiyoubaoUtil;
public class ReturnTicket {
	private String orderCode; // 子订单号
	private String orderType; // 子订单类型
	private String returnNum; // 退票数量
	
	public ReturnTicket() {}

	public ReturnTicket(String orderCode, String orderType, String returnNum) {
		this.orderCode = orderCode;
		this.orderType = orderType;
		this.returnNum = returnNum;
	}

	public String toReturnTicketReq() {
		StringBuilder sbi = new StringBuilder();
		sbi.append("<returnTicket>")
		.append(ZhiyoubaoUtil.buildElement("orderCode",orderCode))
		.append(ZhiyoubaoUtil.buildElement("orderType",orderType))
		.append(ZhiyoubaoUtil.buildElement("returnNum",returnNum))
		.append("</returnTicket>");
		return sbi.toString();
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}

}
