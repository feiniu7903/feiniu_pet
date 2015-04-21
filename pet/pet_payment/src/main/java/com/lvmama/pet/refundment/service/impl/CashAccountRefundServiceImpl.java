package com.lvmama.pet.refundment.service.impl;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.comm.vo.Constant.ComeFrom;

/**
 * 现金帐户的原路退回.
 * @author liwenzhan
 *
 */
public class CashAccountRefundServiceImpl implements BankRefundmentService{
	
	private static final Logger LOG = Logger.getLogger(AlipayRefundServiceImpl.class);

	private CashAccountService cashAccountService;
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		LOG.info("RefundmentToBankInfo:"+StringUtil.printParam(info));
		// 判断现金账户有没有退过款.
		Long protectCount = cashAccountService.selectProtectCount(info.getPayRefundmentId().toString(),ComeFrom.REFUND.toString());
		if (protectCount == 0L) {
			return cashAccountService.refund2CashAccount(info);	
		}
		else{
			BankReturnInfo bankReturnInfo=new BankReturnInfo();
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("不允许重复退款!");
			return bankReturnInfo;
		}    
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
