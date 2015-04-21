package com.lvmama.tnt.partner.biz.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.vo.CreditCardCheckVo;


/**
 * 信用卡校验接口
 * @author gaoyafeng
 *
 */
public interface CreditCardService {
	
	/**
	 * 信用卡校验
	 * @param CreditCardNo 信用卡号
	 * @return 信用卡校验结果
	 */
	ResponseVO<CreditCardCheckVo> checkCard(RequestVO<String> creditCardNoInfo);
	

}
