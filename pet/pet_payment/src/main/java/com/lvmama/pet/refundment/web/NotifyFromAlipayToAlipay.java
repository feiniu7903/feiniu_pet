package com.lvmama.pet.refundment.web;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alipay.util.SignatureHelper_return;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PaymentConstant;

public class NotifyFromAlipayToAlipay extends AbstractAlipayNotify implements NotifyDrawMoney {
	
	public NotifyFromAlipayToAlipay(Map map) {
		super.requestPrameterMap = map;
		init();
	}
	
	@Override
	public boolean checkSignature() {
    	PaymentConstant pc = PaymentConstant.getInstance();
    	String sign = info.get("sign");
    	String mysign = SignatureHelper_return.sign(info, pc.getProperty("ALIPAY_KEY"));
		log.info("async Alipay ALIPAY_KEY = " + pc.getProperty("ALIPAY_KEY"));
		log.info("async Alipay sign = " + sign);
		log.info("async Alipay mysign = " + mysign);
		if(sign.equalsIgnoreCase(mysign)) {
			return true;
		}
		return false;
	}
	
	public boolean process() throws Exception {
		String successDetails = info.get("success_details");
		String failDetails = info.get("fail_details");
		log.info("async Alipay successDetails = " + successDetails);
		log.info("async Alipay failDetails = " + failDetails);
		boolean flag = false;
		if(StringUtils.isNotBlank(successDetails)) {
			flag = flag || processDetail(successDetails);
		}
		if(StringUtils.isNotBlank(failDetails)) {
			flag = flag || processDetail(failDetails);
		}
		return flag;
	}
	
	private boolean processDetail(String details) throws Exception {
		String[] tradeResults = details.split("\\|");
		if(tradeResults == null || tradeResults.length == 0) {
			return false;
		}
		String serialNo = info.get("batch_no");
		boolean flag = true;
		for(int i = 0; i < tradeResults.length; i++) {
			String part[] = tradeResults[i].split("\\^");
			Long amount = new Long(Math.round(Float.parseFloat(part[3]) * 100));
			String status = part[4];
			String reason = part[5];
			String tradeNo = part[6];
			String drawDate = part[7];
			Date transDate = DateUtil.toDate(drawDate, "yyyyMMddHHmmss");
			log.info("serialNo = " + serialNo + " amount = " + amount + " status = " + status + " tradeNo = " + tradeNo + " memo = " + reason + " transDate = " + transDate);
			log.info("callback status = " + status);
			CashDraw fincCashDraw = cashAccountService.findCashDrawBySerial(serialNo);
			if("S".equalsIgnoreCase(status)) {
				fincCashDraw.setGatewayTradeNo(tradeNo);
				fincCashDraw.setTransTime(transDate);
				flag = flag && callbackForSuccess(fincCashDraw);
			} else {
				flag = flag && callbackForFailed(fincCashDraw, status, reason);
			}
		}
		return flag;
	}

}
