package com.lvmama.back.sweb.ord;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.comm.vo.CashAccountVO;

/**
 * "存款账户电话支付"按钮触发打开的表单.
 * @author sunruyi
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/back/ord/moneyAccountPay.ftl", type = "freemarker") })
public class OrderMoneyAccountPaymentAction extends BaseAction {
	
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
	 * 订单现金账户服务.
	 */
	private CashAccountService cashAccountService;
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
	/**
	 * 会员帐户名称.
	 */
	private String accountName;
	/**
	 * 存款账户绑定手机号.
	 */
	private String moneyAccountMobile;
	/**
	 * 隐藏手机中间4位
	 */
	private String moneyAccountMobileSafe="";
	/**
	 * 存款账户可以用于支付的最大余额，以元为单位.
	 */
	private float maxPayMoneyYuan;

	/**
	 * 是否可以全额支付.
	 */
	private boolean canTotalPay;
	/**
	 * 格式化的存款账户可以用于支付的最大余额.
	 */
	private String maxPayMoneyFormat;
	@Action("/ord/payment/orderMoneyAccountPay")
	public String execute() {
		try {
			order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId.trim()));
			CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserNo(order.getUserId());
			float payFen = order.getOughtPayFloat() - order.getActualPayFloat();
			accountName = order.getUserName();
			moneyAccountMobile = moneyAccount.getMobileNumber();
			if(StringUtils.isNotBlank(moneyAccountMobile)){
				moneyAccountMobileSafe=moneyAccountMobileSafeProcess(moneyAccountMobile);
			}
			maxPayMoneyYuan = moneyAccount.getMaxPayMoneyYuan();
			if(maxPayMoneyYuan >= payFen){
				canTotalPay = true;
			}else{
				canTotalPay = false;
			}
			DecimalFormat df = new DecimalFormat("0.00");
			paytotal = df.format(payFen);
			maxPayMoneyFormat = df.format(maxPayMoneyYuan);
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
	/**
	 * 替换手机3~7位的号码为*
	 * @author ZHANG Nan
	 * @param moneyAccountMobile
	 * @return
	 */
	private String moneyAccountMobileSafeProcess(String moneyAccountMobile){
		if(StringUtils.isNotBlank(moneyAccountMobile)&& moneyAccountMobile.length()>=7){
			char [] c=moneyAccountMobile.toCharArray();
			for(int i=3;i<7;i++){
				c[i]='*';
			}
			return String.valueOf(c);
		}
		return moneyAccountMobile;
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

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMoneyAccountMobile() {
		return moneyAccountMobile;
	}

	public void setMoneyAccountMobile(String moneyAccountMobile) {
		this.moneyAccountMobile = moneyAccountMobile;
	}

	public float getMaxPayMoneyYuan() {
		return maxPayMoneyYuan;
	}

	public void setMaxPayMoneyYuan(float maxPayMoneyYuan) {
		this.maxPayMoneyYuan = maxPayMoneyYuan;
	}
	public boolean isCanTotalPay() {
		return canTotalPay;
	}
	public void setCanTotalPay(boolean canTotalPay) {
		this.canTotalPay = canTotalPay;
	}
	public String getMaxPayMoneyFormat() {
		return maxPayMoneyFormat;
	}
	public void setMaxPayMoneyFormat(String maxPayMoneyFormat) {
		this.maxPayMoneyFormat = maxPayMoneyFormat;
	}
	public String getMoneyAccountMobileSafe() {
		return moneyAccountMobileSafe;
	}
	public void setMoneyAccountMobileSafe(String moneyAccountMobileSafe) {
		this.moneyAccountMobileSafe = moneyAccountMobileSafe;
	}
}
