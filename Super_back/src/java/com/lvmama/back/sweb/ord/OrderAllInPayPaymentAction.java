package com.lvmama.back.sweb.ord;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.utils.InfoBase64Coding;

/**
 * "借记卡电话支付"按钮触发打开的"通联电话支付 "表单.
 * @author sunruyi
 * @see java.net.URLEncoder
 * @see java.text.DecimalFormat
 * @see java.util.Calendar
 * @see org.apache.struts2.convention.annotation.Action
 * @see org.apache.struts2.convention.annotation.Result
 * @see org.apache.struts2.convention.annotation.Results
 * @see com.lvmama.back.sweb.BaseAction
 * @see com.lvmama.comm.bee.po.ord.OrdOrder
 * @see com.lvmama.common.ord.service.OrderService
 * @see com.lvmama.common.utils.InfoBase64Coding
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/back/ord/allInPay.ftl", type = "freemarker") })
public class OrderAllInPayPaymentAction extends BaseAction {
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 5005958404690894036L;
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
	 * md5签名.
	 */
	private String md5sign;
	/** 
	* 当前时间. 
	*/ 
	private String timestmp;
	/**
	 * 客服号码.
	 */
	private String csno;

	@Action("/ord/payment/allInPay")
	public String execute() {
		try {
			order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId.trim()));
			DecimalFormat df = new DecimalFormat("0.00");
			paytotal = df.format(order.getOughtPayFloat() - order.getActualPayFloat());
			Calendar c = Calendar.getInstance();
			timestmp = String.valueOf(c.getTimeInMillis());
			String sendMD5 = orderId + paytotal + timestmp; // orderID和payTotal做MD5加密
			md5sign = URLEncoder.encode(InfoBase64Coding.encrypt(sendMD5), "UTF-8"); // 创建一个MD5验证字符串
			csno = getOperatorName();
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

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getTimestmp() {
		return timestmp;
	}

	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}

	public String getMd5sign() {
		return md5sign;
	}

	public void setMd5sign(String md5sign) {
		this.md5sign = md5sign;
	}

}
