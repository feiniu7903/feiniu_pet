package com.lvmama.pet;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.service.pay.NotifierService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 通知业务系统
 * @author Alex Wang
 *
 */
public class PayPaymentNotifier {
	private Logger LOG = Logger.getLogger(this.getClass());
	
	private NotifierService beeOrderPaymentService;
	
	private NotifierService antOrderPaymentService;
	
	private NotifierService superOrderPaymentService;
	
	private NotifierService hotelPaymentService;
	
	private NotifierService cashAccountServiceProxy;
	
	private NotifierService vstPaymentServiceProxy;
	
	private PayPaymentService payPaymentService;
	
	private PayPaymentRefundmentService payPaymentRefundmentService;
	
	/**
	 * 退款成功后通知业务系统进行后续处理
	 * @param payRefundment
	 * @return
	 */
	public boolean notify(PayPaymentRefundment payRefundment) {
		boolean result = notifyBiz(payRefundment);
		if (result) {
			payRefundment.setNotified("true");
			payRefundment.setNotifyTime(new Date());
			payPaymentRefundmentService.updatePyamentRefundment(payRefundment);
			return true;
		}
		return false;
	}
	
	/**
	 * 支付成功后订单的处理 .
	 * @param payment
	 * @return
	 */
	public boolean notify(PayPayment payPayment) {
		boolean result = notifyBiz(payPayment);
		if (result) {
			payPayment.setNotified("true");
			payPayment.setNotifyTime(new Date());
			payPaymentService.updatePayment(payPayment);
			return true;
		}
		return false;
	}
	
	/**
	 * 支付转移后的订单处理
	 * @param payPayments 支付转移的记录
	 * @return 
	 */
	public boolean notifyForTransfer(final List<PayPayment> payPayments) {
		boolean result = noftiyBizForTransfer(payPayments);
		return result;
	}
	
	/**
	 * 业务系统处理.
	 * @param payPayment
	 * @param result
	 * @return
	 */
	private boolean notifyBiz(PayPaymentRefundment payRefundment) {
		boolean result = false;
		if (payRefundment.isBeeOrderPayment()) {
			result  = beeOrderPaymentService.refundSuccess(payRefundment);
		}else if(payRefundment.isAntOrderPayment()) {
			result  = antOrderPaymentService.refundSuccess(payRefundment);
		}else if(payRefundment.isSuperOrderPayment() && vaildRefundmentSatus(payRefundment.getObjectId())) {
			//如果是super系统并且 当前退款单的所有退款记录都为退款成功,那么发送消息给super系统(对于super系统最小单位  退款单)
			result  = superOrderPaymentService.refundSuccess(payRefundment);
		}else if(payRefundment.isTransHotelOrderPayment()) {
			result  = hotelPaymentService.refundSuccess(payRefundment);
		}else if(payRefundment.isCashAccountRechargePayment()) {
			result  = cashAccountServiceProxy.refundSuccess(payRefundment);
		}else if(payRefundment.isVstOrderPayment()){
			result = vstPaymentServiceProxy.refundSuccess(payRefundment);
		}
		LOG.info("notify business system, result: " + result);
		return result;
	}
	
	/**
	 * 业务系统处理.
	 * @param payPayment
	 * @param result
	 * @return
	 */
	private boolean notifyBiz(PayPayment payPayment) {
		boolean result = false;
		LOG.info("payPayment:"+StringUtil.printParam(payPayment));
		if (payPayment.isBeeOrderPayment()) {
			result  = beeOrderPaymentService.paymentSuccess(payPayment);
		}else if(payPayment.isAntOrderPayment()) {
			result  = antOrderPaymentService.paymentSuccess(payPayment);
		}else if(payPayment.isSuperOrderPayment()) {
			result  = superOrderPaymentService.paymentSuccess(payPayment);
		}else if(payPayment.isTransHotelOrderPayment()) {
			result  = hotelPaymentService.paymentSuccess(payPayment);
		}else if(payPayment.isCashAccountRechargePayment()) {
			result  = cashAccountServiceProxy.paymentSuccess(payPayment);
		}else if(payPayment.isVstOrderPayment()){
			result  = vstPaymentServiceProxy.paymentSuccess(payPayment);
		}
		LOG.info("notify business system, result: " + result);
		return result;
	}
	
	/**
	 * 当前退款单的所有退款记录是否都是退款成功的.
	 * @param objectId 退款单的ID
	 * @return
	 */
	private boolean vaildRefundmentSatus(Long objectId){
		List<PayPaymentRefundment> payRefundmentList =  payPaymentRefundmentService.selectRefundListByObjectIdAndBizType(objectId, Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name(),null);
		if(payRefundmentList !=null && payRefundmentList.size() > 0){
			for(PayPaymentRefundment payRefundment:payRefundmentList){
			  if(!payRefundment.isSuccess()){
				  return false;  
			  }
			}
		}
		return true;	
	}
	
	
	
	/**
	 * 回调业务系统处理支付转移成功后的操作
	 * @param payPayments 转移的支付记录
	 * @return
	 */
	private boolean noftiyBizForTransfer(final List<PayPayment> payPayments) {
		boolean result = false;
		if (null != payPayments && !payPayments.isEmpty()) {
			PayPayment payPayment = payPayments.get(0);
			if (payPayment.isBeeOrderPayment()) {
				result  = beeOrderPaymentService.transferPaymentSuccess(payPayments);
			}else if(payPayment.isAntOrderPayment()) {
				result  = antOrderPaymentService.transferPaymentSuccess(payPayments);
			}else if(payPayment.isSuperOrderPayment()) {
				result  = superOrderPaymentService.transferPaymentSuccess(payPayments);
			}else if(payPayment.isTransHotelOrderPayment()) {
				result  = hotelPaymentService.transferPaymentSuccess(payPayments);
			}else if(payPayment.isCashAccountRechargePayment()) {
				result  = cashAccountServiceProxy.transferPaymentSuccess(payPayments);
			}			
		}
		LOG.info("notify business system, result: " + result);
		return result;
	}

	public void setBeeOrderPaymentService(NotifierService beeOrderPaymentService) {
		this.beeOrderPaymentService = beeOrderPaymentService;
	}

	public void setAntOrderPaymentService(NotifierService antOrderPaymentService) {
		this.antOrderPaymentService = antOrderPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setSuperOrderPaymentService(NotifierService orderPaymentService) {
		this.superOrderPaymentService = orderPaymentService;
	}

	public void setHotelPaymentService(NotifierService hotelPaymentService) {
		this.hotelPaymentService = hotelPaymentService;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setCashAccountServiceProxy(NotifierService cashAccountServiceProxy) {
		this.cashAccountServiceProxy = cashAccountServiceProxy;
	}

	public void setVstPaymentServiceProxy(NotifierService vstPaymentServiceProxy) {
		this.vstPaymentServiceProxy = vstPaymentServiceProxy;
	}
	
}
