package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.ConstantMsg;

public class CKPrintInfoBody implements CKBody {

	private Long orderId;
	
	
	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	@Override
	public void init(String requestXml) throws DocumentException {
		String id = TemplateUtils.getElementValue(requestXml, "//request/body/order/orderId");
		if(StringUtil.isNumber(id)){
			this.orderId = Long.valueOf(id);
		}
	}

	@Override
	public String checkParams() {
		if(getOrderId()==null){
			return ConstantMsg.CK_MSG.UNDEFINED_PARAM.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
}
