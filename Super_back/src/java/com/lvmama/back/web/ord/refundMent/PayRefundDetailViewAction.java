package com.lvmama.back.web.ord.refundMent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.vo.PayRefundDetail;


/**
 * 退款明细查看类.
 * 
 * @author fengyu
 */
@SuppressWarnings("unused")
public class PayRefundDetailViewAction extends BaseAction {

	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(PayRefundDetailViewAction.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 退款服务接口
	 */
	private PayPaymentRefundmentService payPaymentRefundmentService;

	private String paymentRefundmentId;
	
	/**
	 * 退款明细
	 */
	private PayRefundDetail payRefundDetail;

	/**
	 * @return
	 */
	public void doBefore() {
		payRefundDetail = payPaymentRefundmentService.selectPayRefundDetailByPaymentRefundmentId(Long.valueOf(paymentRefundmentId));
	}

	public PayPaymentRefundmentService getPayPaymentRefundmentService() {
		return payPaymentRefundmentService;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public String getPaymentRefundmentId() {
		return paymentRefundmentId;
	}

	public void setPaymentRefundmentId(String paymentRefundmentId) {
		this.paymentRefundmentId = paymentRefundmentId;
	}

	public PayRefundDetail getPayRefundDetail() {
		return payRefundDetail;
	}

	public void setPayRefundDetail(PayRefundDetail payRefundDetail) {
		this.payRefundDetail = payRefundDetail;
	}

}
