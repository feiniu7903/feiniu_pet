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

@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/ord/chinapnr.ftl", type="freemarker") })
public class OrderPaymentAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641920109347330498L;
	private String orderId;
	private OrderService orderServiceProxy;
	private OrdOrder order;
	private String paytotal;
	private String md5sign;
	private String csno;
	private String timestmp;
	
	@Action("/ord/payment/chinapnr")
	public String execute() {
		try{
			order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId.trim()));
			DecimalFormat df = new DecimalFormat("0.00");
			paytotal = df.format(order.getOughtPayFloat() - order.getActualPayFloat());
			Calendar c = Calendar.getInstance();
			timestmp = String.valueOf(c.getTimeInMillis());
			String sendMD5 = orderId+ paytotal + timestmp;	// orderID和payTotal做MD5加密
			md5sign = URLEncoder.encode(InfoBase64Coding.encrypt(sendMD5), "UTF-8");	// 创建一个MD5验证字符串
			csno = getOperatorName();
		}catch(Exception e) {
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

	public String getMd5sign() {
		return md5sign;
	}


	public void setMd5sign(String md5sign) {
		this.md5sign = md5sign;
	}


	public String getCsno() {
		return csno;
	}


	public void setCsno(String csno) {
		this.csno = csno;
	}


	public String getTimestmp() {
		return timestmp;
	}


	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}
