package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.ConstantMsg;

public class CKQueryOrderBody implements CKBody {

	String phoneNo;
	
	
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public void init(String requestXml) throws DocumentException {
		phoneNo = TemplateUtils.getElementValue(requestXml,"//request/body/order/phoneNo");

	}

	@Override
	public String checkParams() {
		if(!StringUtil.validMobileNumber(this.phoneNo)){
			return ConstantMsg.CK_MSG.ERROE_PHONE.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}

}
