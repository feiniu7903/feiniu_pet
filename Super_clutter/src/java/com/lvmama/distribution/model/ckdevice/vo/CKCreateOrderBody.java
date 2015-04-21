package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.JAXBUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.ConstantMsg;
import com.lvmama.distribution.model.ckdevice.OrderInfo;
import com.lvmama.distribution.model.ckdevice.Request;

public class CKCreateOrderBody implements CKBody {

	Request request;
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public void init(String requestXml) throws DocumentException {
		try {
			request = (Request) JAXBUtil.xml2Bean(requestXml, Request.class);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@Override
	public String checkParams() {
		if(request == null || request.getBody()==null || request.getBody().getOrderList()==null || request.getBody().getOrderList().size()<1){
			return ConstantMsg.CK_MSG.UNDEFINED_PARAM.getCode();
		}
		String moblie = request.getBody().getOrderList().get(0).getFirstVisitCustomer().getMobile();
		if(!StringUtil.validMobileNumber(moblie)){
			return ConstantMsg.CK_MSG.PHONE_ERROR.getCode();
		}
		for(OrderInfo orderInfo : request.getBody().getOrderList())
		if (orderInfo.getProductBranch().getBranchList() == null || orderInfo.getProductBranch().getBranchList().isEmpty()){
			return ConstantMsg.CK_MSG.NO_CHOOSE_PROD.getCode();
		}
		
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}

	public static void main(String are[])
	{
		System.out.println(StringUtil.validMobileNumber("18621635705"));
	}
}
