package com.lvmama.back.sweb.ord;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * POS机生成新建状态支付记录时 选择支付金额及支付方式
 * @author ZHANG Nan
 *
 */
@Results({ 
	@Result(name = "comm_pos", location = "/WEB-INF/pages/back/ord/init_comm_pos_pay.ftl", type = "freemarker"),
	@Result(name = "sand_pos", location = "/WEB-INF/pages/back/ord/init_sand_pos_pay.ftl", type = "freemarker")
})
public class OrderPOSPaymentAction extends BaseAction{
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 1887234652908299777L;

	/**
	 * 订单ID.
	 */
	private String orderId;
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单金额.
	 */
	private String paytotal;
	/**
	 * 本次支付金额
	 */
	private String amount;
	
	private String signature="";
	
	private String objectType=Constant.PAYMENT_OBJECT_TYPE.ORD_ORDER.name();
	
	private String paymentType=Constant.PAYMENT_OPERATE_TYPE.PAY.name();
	
	private String bizType=Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name();
	
	/**
	 * POS机类型 交通银行或杉德
	 */
	private String posType;
	
	/**
	 * 订单对象.
	 */
	private OrdOrder order;
	
	@Action("/ord/payment/initPosPaymentRecord")
	public String initPosPaymentRecord() {
		order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId.trim()));
		paytotal = String.valueOf(PriceUtil.convertToYuan(order.getOughtPay() - order.getActualPay()));
		return posType;
	}
	/**
	 * 生成MD5签名
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	@Action("/ord/payment/posReGenerateSignature")
	public void reGenerateSignature() throws IOException{
		Long amountFen=PriceUtil.convertToFen(amount);
		String dataStr = String.valueOf(orderId)+objectType+String.valueOf(amountFen)+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
		LOG.info("dataStr="+dataStr);
		signature = MD5.md5(dataStr);
		getResponse().getWriter().write("{newSignature:'"+signature+"',amount:'"+String.valueOf(amountFen)+"'}");
	}
	
	
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getPosType() {
		return posType;
	}


	public void setPosType(String posType) {
		this.posType = posType;
	}


	public OrdOrder getOrder() {
		return order;
	}


	public void setOrder(OrdOrder order) {
		this.order = order;
	}


	public String getPaytotal() {
		return paytotal;
	}


	public void setPaytotal(String paytotal) {
		this.paytotal = paytotal;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getObjectType() {
		return objectType;
	}


	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
}
