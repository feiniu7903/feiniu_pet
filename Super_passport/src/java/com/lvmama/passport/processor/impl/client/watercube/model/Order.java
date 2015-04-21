package com.lvmama.passport.processor.impl.client.watercube.model;

import com.lvmama.passport.processor.impl.client.watercube.WaterCubeUtil;

/**
 * 水魔方--订单信息类
 * @author lipengcheng
 *
 */
public class Order {
	/** 产品编码 */
	private String productNum;
	/** 购买数量 */
	private String num;
	/** 手机号 */
	private String mobile;
	/** 使用时间 */
	private String useDate;
	/** 实名制类型 */
	private String realNameType;
	/** 姓名 */
	private String realName;
	/** 订单号 */
	private String orderNum;
	/** 16位编码 */
	private String code;
	/** 产品名称*/
	private String productName;
	/** 手机号*/
	private String phoneRev;
	/** 剩余可使用数量*/
	private String spareNum;
	/** 交易数量*/
	private String useNum;
	/** 开始有效期*/
	private String startValidityDate;
	/** 结束有效期*/
	private String endValidityDate;
	/** 出票时间*/
	private String addTime;
	/** 状态*/
	private String status;
	/** 转发手机号*/
	private String newMobile;
	/**
	 * 证件号码
	 */
	private String idCard;
	/**
	 * 证件类型 0:身份证 1:其他证件
	 */
	private String cardType;
	/**
	 * 构造提交订单报文
	 * @return
	 */
	public String buildAddOrderXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(WaterCubeUtil.buildXmlElement("product_num", productNum));
		xmlStr.append(WaterCubeUtil.buildXmlElement("num", num));
		xmlStr.append(WaterCubeUtil.buildXmlElement("mobile", mobile));
		xmlStr.append(WaterCubeUtil.buildXmlElement("use_date", useDate));
		xmlStr.append(WaterCubeUtil.buildXmlElement("real_name_type", realNameType));
		xmlStr.append(WaterCubeUtil.buildXmlElement("real_name", realName));
		xmlStr.append(WaterCubeUtil.buildXmlElement("id_card", idCard));
		xmlStr.append(WaterCubeUtil.buildXmlElement("card_type", cardType));
		return xmlStr.toString();
	}
	
	/**
	 * 构造查询订单报文
	 * @return
	 */
	public String buildQueryOrderXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(WaterCubeUtil.buildXmlElement("order_num", orderNum));
		return xmlStr.toString();
	}

	/**
	 * 构造取消订单报文
	 * @return
	 */
	public String buildCancelOrderXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(WaterCubeUtil.buildXmlElement("order_num", orderNum));
		xmlStr.append(WaterCubeUtil.buildXmlElement("num", num));
		return xmlStr.toString();
	}
	
	/**
	 * 构造重发电子票报文
	 * @return
	 */
	public String buildRepeatOrderXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(WaterCubeUtil.buildXmlElement("order_num", orderNum));
		return xmlStr.toString();
	}
	
	/**
	 * 构造转发电子票报文
	 * @return
	 */
	public String buildSendtoOrderXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(WaterCubeUtil.buildXmlElement("order_num", orderNum));
		xmlStr.append(WaterCubeUtil.buildXmlElement("old_mobile", mobile));
		xmlStr.append(WaterCubeUtil.buildXmlElement("new_mobile", newMobile));
		return xmlStr.toString();
	}
	
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getRealNameType() {
		return realNameType;
	}

	public void setRealNameType(String realNameType) {
		this.realNameType = realNameType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhoneRev() {
		return phoneRev;
	}

	public void setPhoneRev(String phoneRev) {
		this.phoneRev = phoneRev;
	}

	public String getSpareNum() {
		return spareNum;
	}

	public void setSpareNum(String spareNum) {
		this.spareNum = spareNum;
	}

	public String getUseNum() {
		return useNum;
	}

	public void setUseNum(String useNum) {
		this.useNum = useNum;
	}

	public String getStartValidityDate() {
		return startValidityDate;
	}

	public void setStartValidityDate(String startValidityDate) {
		this.startValidityDate = startValidityDate;
	}

	public String getEndValidityDate() {
		return endValidityDate;
	}

	public void setEndValidityDate(String endValidityDate) {
		this.endValidityDate = endValidityDate;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
