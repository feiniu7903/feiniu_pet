package com.lvmama.pet.refundment.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cmb.netpayment.Settle;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 招行直连退款服务类.
 * 
 * @author fengyu
 * @see cmb.netpayment.Settle
 * @see com.lvmama.comm.pet.po.pay.BankReturnInfo
 * @see com.lvmama.comm.pet.service.pay.BankRefundmentService
 * @see com.lvmama.comm.utils.PriceUtil
 * @see com.lvmama.comm.vo.PaymentConstant
 * @see com.lvmama.comm.vo.RefundmentToBankInfo
 */
public class CMBRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBRefundServiceImpl.class);

	private PaymentConstant pc = PaymentConstant.getInstance();

	private final static int LOGIN_SUCCESS_FLAG = 0;
	private final static int REFUND_SUCCESS_FLAG = 0;

	/**
	 * 无参构造函数.
	 */
	public CMBRefundServiceImpl() {

	}

	/**
	 * 退款.
	 * 
	 * @return 是否退款成功.
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo refundmentToBankInfo) {
		/**
		 * 退款选项.
		 */
		String CMB_OPTION = pc.getProperty("CMB_OPTION");
		/**
		 * 分行ID
		 */
		String CMB_BRANCHID = pc.getProperty("CMB_BRANCHID");

		/**
		 * 用户号
		 */
		String CMB_CONO = pc.getProperty("CMB_CONO");
		/**
		 * 密码
		 */
		String CMB_PWD = pc.getProperty("CMB_PWD");
		/**
		 * 商户密钥
		 */
		String CMB_PSZKEY = pc.getProperty("CMB_PSZKEY");

		Settle settle = new Settle();
		settle.SetOptions(CMB_OPTION);
		int loginRet = settle.LoginC(CMB_BRANCHID, CMB_CONO, CMB_PWD);

		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		boolean isSuccess = false;
		String errCode = ""; 

		if (loginRet == LOGIN_SUCCESS_FLAG) {// 登录成功
			LOG.info("CMB Refundment : Login Successfully!");
			Date payTime = refundmentToBankInfo.getCallbackTime();
			String orderId = refundmentToBankInfo.getPaymentTradeNo();
			Long refundAmount = refundmentToBankInfo.getRefundAmount();
			String amount = String.valueOf(PriceUtil.convertToYuan(refundAmount));
			String description = "";
			int refundRet = settle.RefundOrder(formatDate(payTime), orderId,amount, description, CMB_PSZKEY);

			if (refundRet == REFUND_SUCCESS_FLAG) {// 退款成功
				isSuccess = true;
			} else {
				errCode = settle.GetLastErr(refundRet);
				LOG.error("CMB Refundment : Refund Fail! error code = "+ errCode);
			}
		} else {
			errCode = settle.GetLastErr(loginRet);
			LOG.error("CMB Refundment : Login Fail! error code = " + errCode);
		}
		if(isSuccess){
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		}
		else{
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		}
		bankReturnInfo.setCodeInfo(errCode);
		return bankReturnInfo;
	}

	private static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
}
