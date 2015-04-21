package com.lvmama.pet.payment.callback.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.pet.payment.callback.data.LakalaQueryData;

/**
 * 拉卡拉支付.
 * 
 * <pre>
 * 用户在使用拉卡拉POS机时，拉卡拉系统首先调用驴妈妈查询接口，判断此时是否可以进行支付，
 * 如果可以，则拉卡拉系统进行扣款，扣款成功后再通知驴妈妈支付成功，如果此时网络中断，则不会再次通知！
 * </pre>
 * 
 * @author tom
 * @version Super二期 2011/03/20
 * @since Super二期
 * @see com.lvmama.common.UtilityTool#isValid(Object)
 * @see com.lvmama.common.UtilityTool#formatDate(String)
 * @see com.lvmama.common.ord.po.OrdOrder
 * @see com.lvmama.common.ord.service.OrderService
 * @see com.lvmama.payment.service.LakalaService
 * @see com.lvmama.payment.web.BaseAction
 */
@Results({ 
	@Result(name = LakalaQueryAction.SEARCH_ORDER, location = "/WEB-INF/pages/pay/lakala_search_order.ftl", type = "freemarker"),
	@Result(name = "error", location = "/WEB-INF/pages/pay/error.ftl", type = "freemarker")
})
public final class LakalaQueryAction extends BaseAction {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3430074181383241096L;
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(LakalaQueryAction.class);
	/**
	 * SEARCH_ORDER.
	 */
	static final String SEARCH_ORDER = "searchOrder";

	
	private PayPaymentService payPaymentService;

	private LakalaQueryData data;
	
	/**
	 * 查询订单.
	 * 
	 * <pre>
	 * 判断此时是否可以进行支付
	 * </pre>
	 *  http://pay.lvmama.com/payment/lakala/searchOrder.do
	 * @return {@link SEARCH_ORDER}
	 */
	@Action("/lakala/searchOrder")
	public String searchOrder() {
		try {
			data = new LakalaQueryData(super.getPureParaPair());
			if (data.checkSignature()) {
				LOG.info("verify success");
				List<PayPayment> paymentList = payPaymentService.selectByPaymentTradeNo(data.getPaymentTradeNo());
				Long paymentSum=0L;
				for (PayPayment payPayment : paymentList) {
					if(payPayment!=null){
						paymentSum+=payPayment.getAmount();	
					}
				}	
				if (PriceUtil.moneyConvertLongPrice(data.getAmount())==paymentSum) {
					LOG.info("order amount is correct");
					data.initCanPayAndTime();
					return SEARCH_ORDER;
				}
			}
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return ERROR;
	}

	public LakalaQueryData getData() {
		return data;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
}
