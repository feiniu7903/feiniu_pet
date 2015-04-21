package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 信用卡电话支付表单类.
 * @author sunruyi huyunyan
 */
public class CreditCardForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4466067066275023965L;

	/**
	 * 订单号
	 */
	private String orderid;

	/**
	 * 订单金额
	 */
	private String paytotal;

	/**
	 * 借记卡姓名
	 */
	private String cardusername;

	/**
	 * 客服号码
	 */
	private String csno;

	/**
	 * 手机号码
	 */
	private String mobilenumber;

	/**
	 * 信用卡卡号
	 */
	private String cardNo;
	/**
	 * 信用卡有效期.
	 */
	private String expiryDate;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 备注.
	 */
	private String textarea;
	/**
	 * 银行网关.
	 */
	private String bankgate;
	
	/** 
	* 当前时间. 
	*/ 
	private String timestmp;
	/**
	 * 初始化.
	 * @param parameters
	 * @param payTimes
	 * @return
	 */
	public static CreditCardForm create(Map<String,String> parameters){
		CreditCardForm form=new CreditCardForm();
		for(String key:parameters.keySet()){
			try{
				BeanUtils.setProperty(form, key,parameters.get(key));
			}catch(Exception ex){				
			}
		}
		return form;
	}
	/**
	 * 格式化订单金额.
	 * @param paytotal
	 * @return
	 */
	public Long formatAmount() {
		// 进率：分换算成元
		final int MULTIPLIER = 100;
		String amount = new BigDecimal(paytotal).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		return (long) Float.parseFloat(amount) * MULTIPLIER;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPaytotal() {
		return paytotal;
	}
	public void setPaytotal(String paytotal) {
		this.paytotal = paytotal;
	}
	public String getCardusername() {
		return cardusername;
	}
	public void setCardusername(String cardusername) {
		this.cardusername = cardusername;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getTextarea() {
		return textarea;
	}
	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}
	public String getTimestmp() {
		return timestmp;
	}
	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}
	public String getBankgate() {
		return bankgate;
	}
	public void setBankgate(String bankgate) {
		this.bankgate = bankgate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
