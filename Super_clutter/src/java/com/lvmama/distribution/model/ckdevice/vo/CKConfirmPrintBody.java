package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.ConstantMsg;

public class CKConfirmPrintBody implements CKBody {
	
	private Long orderId;
	private String reservationNo;
	private String noType;

	
	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public String getReservationNo() {
		return reservationNo;
	}


	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}


	public String getNoType() {
		return noType;
	}


	public void setNoType(String noType) {
		this.noType = noType;
	}


	@Override
	public void init(String requestXml) throws DocumentException {
		String id = TemplateUtils.getElementValue(requestXml, "//request/body/order/orderId");
		if(StringUtil.isNumber(id)){
			this.orderId = Long.valueOf(id);
		}
		reservationNo = TemplateUtils.getElementValue(requestXml, "//request/body/order/revervationNo");
		noType = TemplateUtils.getElementValue(requestXml, "//request/body/order/noType");

	}

	@Override
	public String checkParams() {
		if(this.getOrderId()==null){
			return ConstantMsg.CK_MSG.ORDER_EXITS.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
}
