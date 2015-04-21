package com.lvmama.passport.processor.impl.client.gulangyu.model;
/**
 * 鼓浪屿返回的订单信息
 * @author lipengcheng
 *
 */
public class OrderInfo {

	/** 订单号,同接收的订单号 */
	private String uuOrderNum;
	/** 16U订单号,16U返回的唯一订单号 */
	private String uu16uOrder;
	/** 凭证号 */
	private String uuCertnum;
	/** 景区ID */
	private String uuLid;
	/** 门票ID */
	private String uuTid;
	/** 游玩时间 */
	private String uuPlayTime;
	/** 取票人姓名 */
	private String uuOrderName;
	/** 取票人手机 */
	private String uuOrderTel;
	/** 订单总金额 */
	private String uuTotal;
	/** 支付情况 */
	private String uuPayInfo;

	public String getUuOrderNum() {
		return uuOrderNum;
	}

	public void setUuOrderNum(String uuOrderNum) {
		this.uuOrderNum = uuOrderNum;
	}

	public String getUu16uOrder() {
		return uu16uOrder;
	}

	public void setUu16uOrder(String uu16uOrder) {
		this.uu16uOrder = uu16uOrder;
	}

	public String getUuCertnum() {
		return uuCertnum;
	}

	public void setUuCertnum(String uuCertnum) {
		this.uuCertnum = uuCertnum;
	}

	public String getUuLid() {
		return uuLid;
	}

	public void setUuLid(String uuLid) {
		this.uuLid = uuLid;
	}

	public String getUuTid() {
		return uuTid;
	}

	public void setUuTid(String uuTid) {
		this.uuTid = uuTid;
	}

	public String getUuPlayTime() {
		return uuPlayTime;
	}

	public void setUuPlayTime(String uuPlayTime) {
		this.uuPlayTime = uuPlayTime;
	}

	public String getUuOrderName() {
		return uuOrderName;
	}

	public void setUuOrderName(String uuOrderName) {
		this.uuOrderName = uuOrderName;
	}

	public String getUuOrderTel() {
		return uuOrderTel;
	}

	public void setUuOrderTel(String uuOrderTel) {
		this.uuOrderTel = uuOrderTel;
	}

	public String getUuTotal() {
		return uuTotal;
	}

	public void setUuTotal(String uuTotal) {
		this.uuTotal = uuTotal;
	}

	public String getUuPayInfo() {
		return uuPayInfo;
	}

	public void setUuPayInfo(String uuPayInfo) {
		this.uuPayInfo = uuPayInfo;
	}

}