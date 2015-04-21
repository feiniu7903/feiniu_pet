package com.lvmama.pet.vo;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 中行分期对账PO.
 * @author sunruyi
 */
public class BOCInstalReconciliation {
	/**
	 * 成员变量数量.
	 */
	private final static int FIELD_QUANTITY = 18;
	/**
	 * 商户号.
	 */
	private String masterId;
	/**
	 * 机构代码.
	 */
	private String organCode;
	/**
	 * 终端号.
	 */
	private String terminalId;
	/**
	 * 批号.
	 */
	private String batchNo;
	/**
	 * 交易卡号.
	 */
	private String transCardNo;
	/**
	 * 交易日期.
	 */
	private String transDate;
	/**
	 * 交易时间.
	 */
	private String transTime;
	/**
	 * 交易金额.
	 */
	private String transAmount;
	private float transAmountYuan;
	/**
	 * 手续费.
	 */
	private String instalmentFee;
	private float instalmentFeeYuan;
	/**
	 * 结算金额.
	 */
	private String settleAmount;
	private float settleAmountYuan;
	/**
	 * 授权码.
	 */
	private String authCode;
	/**
	 * 交易码.
	 */
	private String transCode;
	/**
	 * 分期.
	 */
	private String instalmentNum;
	/**
	 * 是否分期.
	 */
	private String isInstalment;
	/**
	 * 卡别.
	 */
	private String cardType;
	/**
	 * 参考号.
	 */
	private String retrReferenceNum;
	/**
	 * POS流水号.
	 */
	private String posSerialNo;
	/**
	 * 订单号.
	 */
	private String orderId;
	
	public BOCInstalReconciliation(){
		
	}
	public BOCInstalReconciliation(String filesStr){
		String[] filesArry = filesStr.split("\\|");
		if(filesArry.length == FIELD_QUANTITY){
			this.masterId = filesArry[0];
			this.organCode = filesArry[1];
			this.terminalId = filesArry[2];
			this.batchNo = filesArry[3];
			this.transCardNo = filesArry[4];
			this.transDate = filesArry[5];
			this.transTime = filesArry[6];
			this.transAmount = filesArry[7];
			this.instalmentFee = filesArry[8];
			this.settleAmount = filesArry[9];
			this.authCode = filesArry[10];
			this.transCode = filesArry[11];
			this.instalmentNum = filesArry[12];
			this.isInstalment = filesArry[13];
			this.cardType = filesArry[14];
			this.retrReferenceNum = filesArry[15];
			this.posSerialNo = filesArry[16];
			this.orderId = filesArry[17];
		}
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTransCardNo() {
		return transCardNo;
	}
	public void setTransCardNo(String transCardNo) {
		this.transCardNo = transCardNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getInstalmentFee() {
		return instalmentFee;
	}
	public void setInstalmentFee(String instalmentFee) {
		this.instalmentFee = instalmentFee;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getInstalmentNum() {
		return instalmentNum;
	}
	public void setInstalmentNum(String instalmentNum) {
		this.instalmentNum = instalmentNum;
	}
	public String getIsInstalment() {
		if("1".equals(isInstalment)){
			return "是";
		} else {
			return "否";
		}
	}
	public void setIsInstalment(String isInstalment) {
		this.isInstalment = isInstalment;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getRetrReferenceNum() {
		return retrReferenceNum;
	}
	public void setRetrReferenceNum(String retrReferenceNum) {
		this.retrReferenceNum = retrReferenceNum;
	}
	public String getPosSerialNo() {
		return posSerialNo;
	}
	public void setPosSerialNo(String posSerialNo) {
		this.posSerialNo = posSerialNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public float getTransAmountYuan() {
		transAmountYuan = PriceUtil.convertToYuan(Long.valueOf(transAmount));
		return transAmountYuan;
	}
	public void setTransAmountYuan(float transAmountYuan) {
		this.transAmountYuan = transAmountYuan;
	}
	public float getInstalmentFeeYuan() {
		instalmentFeeYuan = PriceUtil.convertToYuan(Long.valueOf(instalmentFee));
		return instalmentFeeYuan;
	}
	public void setInstalmentFeeYuan(float instalmentFeeYuan) {
		this.instalmentFeeYuan = instalmentFeeYuan;
	}
	public float getSettleAmountYuan() {
		settleAmountYuan = PriceUtil.convertToYuan(Long.valueOf(settleAmount));
		return settleAmountYuan;
	}
	public void setSettleAmountYuan(float settleAmountYuan) {
		this.settleAmountYuan = settleAmountYuan;
	}
}
