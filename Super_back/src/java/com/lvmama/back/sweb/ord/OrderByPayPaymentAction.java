package com.lvmama.back.sweb.ord;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 百付 电话支付.
 * @author liwenzhan
 * @see java.net.URLEncoder
 * @see java.text.DecimalFormat
 * @see java.util.Calendar
 * @see org.apache.struts2.convention.annotation.Action
 * @see org.apache.struts2.convention.annotation.Result
 * @see org.apache.struts2.convention.annotation.Results
 * @see com.lvmama.back.sweb.BaseAction
 * @see com.lvmama.comm.bee.po.ord.OrdOrder
 * @see com.lvmama.common.ord.service.OrderService
 */
@Results({ 
	@Result(name = "success", location = "/WEB-INF/pages/back/ord/by_postdate_pay.ftl", type = "freemarker")})
public class OrderByPayPaymentAction extends BaseAction{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1399559322685908871L;
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(OrderByPayPaymentAction.class);
	
	/**
	 * 订单ID.
	 */
	private String orderId;
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单对象.
	 */
	private OrdOrder order;
	/**
	 * 订单金额.
	 */
	private String paytotal;
	/**
	 * 客服号码.
	 */
	private String csno;
	/**
	 * 手机号
	 */
	private String mobilenumber;
	/**
	 * 
	 */
	private  String signature;
	
	/**
	 * 对象类型(订单).
	 */
	private  final String objectType = "ORD_ORDER";
	/**
	 * 业务类型.
	 */
	private final String bizType = Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name();
	
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private final String paymentType = Constant.PAYMENT_OPERATE_TYPE.PAY.name();
	
	/**
	 * 产品接口
	 */
	private ProdProductService prodProductService;
	/**
	 * 产品订金(分)
	 */
	private String payDeposit="0";
	
	@Action("/ord/payment/byPay")
	public String createPayment() {
		try {
			order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId.trim()));
			paytotal = String.valueOf(PriceUtil.convertToYuan(order.getOughtPay() - order.getActualPay()));
			csno = getOperatorName();
			orderId = order.getOrderId().toString();
			signature = String.valueOf(order.getOughtPayYuan())+String.valueOf(order.getOrderId())+objectType+paymentType;
			OrdOrderItemProd ordOrderItemProd =order.getMainProduct();
			if(ordOrderItemProd!=null){
				ProdProduct prodProduct=prodProductService.getProdProduct(ordOrderItemProd.getProductId());
				if(prodProduct!=null){
					payDeposit=String.valueOf(PriceUtil.convertToYuan(prodProduct.getPayDeposit()));	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getCsno() {
		return csno;
	}

	public void setCsno(String csno) {
		this.csno = csno;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public String getBizType() {
		return bizType;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public String getPayDeposit() {
		return payDeposit;
	}

	public void setPayDeposit(String payDeposit) {
		this.payDeposit = payDeposit;
	}
}
