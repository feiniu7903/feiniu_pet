package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 通联电话支付.
 * 
 * @author sunruyi
 * @see java.text.NumberFormat
 * @see java.text.ParseException
 * @see java.util.Map
 * @see com.kayak.telpay.mpi.objects.TransMsg
 * @see com.kayak.telpay.mpi.util.UrlUtil
 * @see com.lvmama.payment.vo.PaymentConstant
 */
public class AllInPayForm implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4059231964843215330L;

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
	 * 借记卡卡号
	 */
	private String cardNo;

	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 备注.
	 */
	private String textarea;
	/**
	 * 支付次数.
	 */
	private Long paymentTimes;
	
	/** 
	* 当前时间. 
	*/ 
	private String timestmp;
	
	
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
	public Long getPaymentTimes() {
		return paymentTimes;
	}
	public void setPaymentTimes(Long paymentTimes) {
		this.paymentTimes = paymentTimes;
	}
	public String getTimestmp() {
		return timestmp;
	}
	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}
	public static AllInPayForm create(Map<String,String> parameters,Long payTimes){
		AllInPayForm form=new AllInPayForm();
		for(String key:parameters.keySet()){
			try{
				BeanUtils.setProperty(form, key,parameters.get(key));
			}catch(Exception ex){				
			}
		}
		form.setPaymentTimes(payTimes);
		return form;
	}
	
	

}
