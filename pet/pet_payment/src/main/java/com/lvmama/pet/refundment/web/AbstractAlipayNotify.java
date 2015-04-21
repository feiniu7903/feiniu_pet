package com.lvmama.pet.refundment.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.util.CheckURL;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

public abstract class AbstractAlipayNotify {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	protected Map<String,String> info = new HashMap<String,String>();
	protected Map<String, Object> requestPrameterMap;
	protected CashAccountService cashAccountService = (CashAccountService)SpringBeanProxy.getBean("cashAccountService");
	
	/**
	 * 以下用于处理支付宝返回信息 
	 */
    protected void init() {
		//获得POST 过来参数设置到新的params中
		for (Iterator<String> iter = requestPrameterMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestPrameterMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			info.put(name, valueStr);
			log.info(name + " = " + valueStr);
		}
    }
    
    /**
     * 根据参数判断支付是否成功
     * @param paraMap : 信息集合
     * @return 是否成功  true为成功  false为失败
     */
    public boolean isComefromAlipay() {
    	PaymentConstant pc = PaymentConstant.getInstance();
    	String notify_id = info.get("notify_id");
    	String partner = pc.getProperty("ALIPAY_PARTNER");
        String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "&partner=" + partner
				+ "&notify_id=" + notify_id;
		//获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);
		log.info("notify_id = " + notify_id);
		log.info("responseTxt from alipay to check notify_id: "+responseTxt);
		if(responseTxt.equals("true")){
			return true;
		}
        return false;
    }
	
    protected boolean callbackForSuccess(CashDraw fincCashDraw) {
		CashMoneyDraw fincMoneyDraw = cashAccountService.queryCashMoneyDraw(fincCashDraw.getMoneyDrawId());
		if(Constant.FINC_CASH_STATUS.PayCashSuccess.name().equalsIgnoreCase(fincMoneyDraw.getPayStatus())
				|| Constant.FINC_CASH_STATUS.PayCashFailedByBankRefundment.name().equalsIgnoreCase(fincMoneyDraw.getPayStatus())
				|| Constant.FINC_CASH_STATUS.PayCashFailed.name().equalsIgnoreCase(fincMoneyDraw.getPayStatus())) {
			log.info("FincMoneyDraw's PayStatus has already been " + fincMoneyDraw.getPayStatus()
					+ " by the moneyDrawId = " + fincCashDraw.getMoneyDrawId()
					+ " with cashDrawId = " + fincCashDraw.getCashDrawId());
			return true;
		}
		boolean success =cashAccountService.callbackForDrawMoneyHandle(fincCashDraw.getSerial(), 
				fincCashDraw.getGatewayTradeNo(), true, Constant.FINC_CASH_STATUS.PayCashSuccess.name(), null);
		log.info("callbackForDrawMoney : call orderServiceProxy.callbackForDrawMoney successfully.");
		return success;
	}
	
	protected boolean callbackForFailed(CashDraw cashDraw, String status, String memo) {
		if (!cashDraw.isNewlyCreated()) {
			log.info("callbackForDrawMoney : Failed draw money, this record had callbacked in " + cashDraw.getCallbackTime());
			return true;
		}
		String payStatus="";
		if(Constant.ALIPAY_NOTIFY_TYPE.bptb_unfreeze_notify.name().equalsIgnoreCase(status)) {	//批次余额不足
			payStatus=Constant.FINC_CASH_STATUS.PayCashFailedByUnfreeze.name();
		}else {
			payStatus=Constant.FINC_CASH_STATUS.PayCashFailed.name();
		}
		boolean success =cashAccountService.callbackForDrawMoneyHandle(cashDraw.getSerial(), 
				cashDraw.getGatewayTradeNo(), false, payStatus, memo);
		log.info("callbackForDrawMoney : Failed draw money, memo: " + memo);
		return success;
	}

}
