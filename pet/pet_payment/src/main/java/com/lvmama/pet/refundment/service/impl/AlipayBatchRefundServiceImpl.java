package com.lvmama.pet.refundment.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alipay.config.AlipayConfig;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.GeneralSequenceNo;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ALIPAY_TRANSACTION_TYPE_PREFIX;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.AlipayUtil;

public class AlipayBatchRefundServiceImpl implements BankRefundmentService {

	protected transient final Log log = LogFactory.getLog(getClass());
	
	private CashAccountService cashAccountService;
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returnInfo = new BankReturnInfo();
		try{
			CashMoneyDraw cashMoneyDraw = cashAccountService.queryCashMoneyDraw(info.getObjectId());
			Map<String,String> requestparameters = initParameters(cashMoneyDraw);
			String batchNo = requestparameters.get("batch_no");
			String targetURL = PaymentConstant.getInstance().getProperty("ALIPAY_URL");
			log.info("DrawCash2Alipay targetURL = " + targetURL);
			String response = AlipayUtil.sendPostInfo(requestparameters, targetURL);
			ResponseInfo respInfo = new ResponseInfo(response);
			returnInfo.setSerial(batchNo);
			if (respInfo.isSuccess()) {
				CashDraw cashDraw = createCashDraw(respInfo,batchNo , cashMoneyDraw);
				cashAccountService.withDrawMoney(cashMoneyDraw, cashDraw);
				returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
				returnInfo.setCodeInfo("现金账户提现处理中");
			}else{
				returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				returnInfo.setCodeInfo(respInfo.getErrorMsg());
			}
		}catch(Exception e) {
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			returnInfo.setCodeInfo(e.getMessage());
			e.printStackTrace();
		}
		return returnInfo;
	}
	
	private CashDraw createCashDraw(ResponseInfo info, String batchNo, CashMoneyDraw cashMoneyDraw) {
		CashDraw fincCashDraw = new CashDraw();
		fincCashDraw.setAmount(cashMoneyDraw.getDrawAmount());
		fincCashDraw.setCreateTime( new Date() );
		fincCashDraw.setMoneyDrawId(cashMoneyDraw.getMoneyDrawId());
		fincCashDraw.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY.name());
		fincCashDraw.setOperatorName("SYSTEM");
		fincCashDraw.setSerial(batchNo);
		if (info.isSuccess()) {
			fincCashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.ApplyPayCashSuccess.name());
		} else {
			fincCashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
			fincCashDraw.setCallbackInfo(info.getErrorMsg());
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.ApplyPayCashRejected.name());
		}
		return fincCashDraw;
	}
	
	private Map<String,String> initParameters(CashMoneyDraw cashMoneyDraw) {
		Map<String,String> params = new HashMap<String, String>();
		PaymentConstant pc = PaymentConstant.getInstance();
		String batchNo = ALIPAY_TRANSACTION_TYPE_PREFIX.A.name()+GeneralSequenceNo.generateSerialNo();
        params.put("service","batch_trans_notify_no_pwd");
        params.put("partner",pc.getProperty("ALIPAY_PARTNER"));
        params.put("email",pc.getProperty("ALIPAY_SELLER_EMAIL"));
        params.put("notify_url",pc.getProperty("ALIPAY_DRAW_URL"));
        params.put("pay_date", DateUtil.getFormatDate(new Date(), "yyyyMMdd"));
        params.put("sign_type", "MD5");
        params.put("_input_charset", AlipayConfig.input_charset);
        params.put("batch_no", batchNo);
        params.put("batch_num", "1");
        params.put("account_name", pc.getProperty("ALIPAY_ACCOUNT_NAME"));
        float v = PriceUtil.convertToYuan(cashMoneyDraw.getDrawAmount().longValue());
        String drawAmount = new DecimalFormat("################0.00").format(v);
        params.put("batch_fee", drawAmount);
        String detailData = batchNo+"^"+cashMoneyDraw.getBankAccount()+"^"+cashMoneyDraw.getBankAccountName()+"^"+drawAmount+"^"+"DrawCashToAlipayAccount";
        params.put("detail_data", detailData);
        return params;
	}
	

	public class ResponseInfo{
		private boolean success;
		private String error;
		
		public ResponseInfo(String xmlResponse) {
			try {
				if(StringUtils.isBlank(xmlResponse)) {
					success = false;
				}
				log.info("DrawCash2Alipay Response: " + xmlResponse);
				Document requestDoc = DocumentHelper.parseText(xmlResponse);
				Element requestRoot = requestDoc.getRootElement();
				String result = requestRoot.elementText("is_success");
				if("T".equalsIgnoreCase(result)) {
					success=true;
				}else{
					success=false;
					error = "Alipay Refunded FAIL:" + requestRoot.elementText("error");
				}
			}catch(Exception e) {
				success=false;
				error = "Alipay Refunded FAIL:" + e.getMessage();
				e.printStackTrace();
			}
		}
		public boolean isSuccess() {
			return success;
		}
		
		public String getErrorMsg() {
			return error;
		}
	}
	
	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
