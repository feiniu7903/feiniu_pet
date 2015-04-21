package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.biz.service.CreditCardService;
import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.service.VstCreditCardService;
import com.lvmama.vst.api.biz.vo.CreditCardCheckVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelCreditCardService")
public class CreditCardServiceImpl implements CreditCardService{

	@Autowired
	private VstCreditCardService vstCreditCardService;
	
	@Override
	public ResponseVO<CreditCardCheckVo> checkCard(
			RequestVO<String> creditCardNoInfo) {

		ResponseVO<CreditCardCheckVo> response = new ResponseVO<CreditCardCheckVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<CreditCardCheckVo> result = vstCreditCardService
				.checkCard(creditCardNoInfo.getBody());

		CreditCardCheckVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
