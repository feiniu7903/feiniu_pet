package com.lvmama.report.po;

import com.lvmama.comm.vo.Constant;


/**
 * 游客信息的MV
 * 
 * @author yangchen
 * 
 */
public class VistorDetailBasicMV {
	/** 订单ID **/
	private Long orderId;
	/** 产品ID **/
	private Long prodProductId;
	/** 产品名称 **/
	private String prodProductName;
	/** 支付时间 **/
	private String payTime;
	/** 游玩时间 **/
	private String visitTime;
	/** 订单人数 **/
	private Long quantity;
	/** 分公司 **/
	private String filialeName;
	/** 订单联系人 **/
	private String userName;
	/** 联系人电话 **/
	private String mobileNumber;
	/** 客服备注 **/
	private String csMemo;
	/** 游客备注 **/
	private String vistorMemo;
	/** 产品经理 **/
	private String realName;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCsMemo() {
		return csMemo;
	}

	public void setCsMemo(String csMemo) {
		this.csMemo = csMemo;
	}

	public String getVistorMemo() {
		return vistorMemo;
	}

	public void setVistorMemo(String vistorMemo) {
		this.vistorMemo = vistorMemo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getZhFilialeName()
	{
		return Constant.FILIALE_NAME.getCnName(filialeName); 
	}
}
