package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.ConstantMsg;


public class CheckReservationBody  implements CKBody{

	private String revervationNo;
	private String noType;
	private String phoneNo;
	
	public String getRevervationNo() {
		return revervationNo;
	}

	public void setRevervationNo(String revervationNo) {
		this.revervationNo = revervationNo;
	}

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	@Override
	public void init(String requestXml) throws DocumentException {
		revervationNo = TemplateUtils.getElementValue(requestXml, "//request/body/order/revervationNo");
		noType = TemplateUtils.getElementValue(requestXml, "//request/body/order/noType");
		phoneNo = TemplateUtils.getElementValue(requestXml, "//request/body/order/phoneNo");
		
	}

	@Override
	public String checkParams() {
		if(!"1".equals(noType)&&!"2".equals(noType)){
			return ConstantMsg.CK_MSG.CODE_EXISTS.getCode();
		}else if("2".equals(noType)){
			if(revervationNo!=null && revervationNo.length()>10){
				int index = revervationNo.indexOf("TCOD") ;
				if(index<4){
					return ConstantMsg.CK_MSG.CODE_EXISTS.getCode();
				}
				revervationNo = revervationNo.substring(4, index);
			}else{
				return ConstantMsg.CK_MSG.CODE_EXISTS.getCode();
			}
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	
	

}
