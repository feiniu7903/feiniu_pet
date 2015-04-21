package com.lvmama.pet.vo;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

/**
 * 支付宝对账下载单个交易明细VO
 * @author ranlongfei 2012-7-2
 * @version
 */
public class AlipayAccountLogVO {

	/**
	 * 收入金额
	 */
	private Float income;
	/**
	 * 支出金额
	 */
	private Float outcome;
	/**
	 * 交易类型
	 */
	private String transCodeMsg;
	/**
	 * 交易发生日期
	 */
	private String transDate;
	/**
	 * 商户订单号(对账流水号)
	 */
	private String merchantOutOrderNo;
	/**
	 * 订单号(网关交易号)
	 */
	private String transOutOrderNo;
	/**
	 * 备注
	 */	
	private String memo;
	
	/**
	 * 判断充值前缀
	 */
	private static String CASH_RECHARGE_PREFIX=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.R.name();
	/**
	 * 判断提现前缀
	 */
	private static String CASH_MONEY_DRAW_PREFIX=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.D.name();
	/**
	 * 判断提现到支付宝前缀
	 */
	private static String CASH_MONEY_DRAW_ALIPAY_PREFIX=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.A.name();
	/**
	 * 退款交易前缀
	 */
	private static String LVMAMA_REFUNDMENT_PREFIX=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.LVMAMA_REFUNDMENT.name();
	
	public String transCodeMsgToCode(){
		if("在线支付".equals(this.getTransCodeMsg())){
			//如果商户订单号前缀为字母R 则标识为充值
			if(isCashRecharge()){
				return Constant.PAYMENT_OBJECT_TYPE.CASH_RECHARGE.name();
			}
			else{
				//否则为普通支付
				return Constant.TRANSACTION_TYPE.PAYMENT.name();
			}
		}
		else if("收费".equals(this.getTransCodeMsg())){
			//支付宝默认 退款返还手续费 类型 的注释 前缀位 [退费金额
			if(StringUtils.isNotBlank(this.getMemo()) && this.getMemo().indexOf("[退费金额")>=0){
				return Constant.TRANSACTION_TYPE.REFUNDMENT_FEE.name();	
			}
			//否则前缀为 交易服务费  的 为支付的手续费
			else if(StringUtils.isNotBlank(this.getMemo()) && this.getMemo().indexOf("服务费")>=0){
				return Constant.TRANSACTION_TYPE.PAYMENT_FEE.name();	
			}
		}
		else if("转账".equals(this.getTransCodeMsg())){
			//前缀为 LVMAMA_REFUNDMENT 的 为退款交易  在退款时退款原因写死 LVMAMA_REFUNDMENT+退款流水号
			if(this.getMemo().indexOf(LVMAMA_REFUNDMENT_PREFIX)>=0){
				return Constant.TRANSACTION_TYPE.REFUNDMENT.name();
			}
			//判断是否为提现到支付宝账户
			else if (isCashMoneyDrawAlipay()){
				return Constant.TRANSACTION_TYPE.CASH_MONEY_DRAW_ALIPAY.name();
			}
			else{
				//否则为对外付款
				return Constant.TRANSACTION_TYPE.FOREIGN_PAYMENT.name();
			}
		}
		else if("提现".equals(this.getTransCodeMsg())){
			//判断存款账户提现
			if(isCashMoneyDraw()){
				return Constant.TRANSACTION_TYPE.CASH_MONEY_DRAW.name();	
			}
			return Constant.TRANSACTION_TYPE.NORMAL_DRAWCASH.name();
		}
		return this.transCodeMsg;
	}
	/**
	 * 判断当前交易是否为充值
	 * @author ZHANG Nan
	 * @return
	 */
	private boolean isCashRecharge(){
		if(StringUtils.isNotBlank(this.getMerchantOutOrderNo())){
			if(CASH_RECHARGE_PREFIX.equals(this.getMerchantOutOrderNo().substring(0, 1))){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断当前交易是否为批量付款到卡
	 * @author ZHANG Nan
	 * @return
	 */
	private boolean isCashMoneyDraw(){
		if(StringUtils.isNotBlank(this.getMerchantOutOrderNo())){
			if(CASH_MONEY_DRAW_PREFIX.equals(this.getMerchantOutOrderNo().substring(0, 1))){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断当前交易是否为批量付款到支付宝账户
	 * @author ZHANG Nan
	 * @return
	 */
	private boolean isCashMoneyDrawAlipay(){
		if(StringUtils.isNotBlank(this.getMerchantOutOrderNo())){
			if(CASH_MONEY_DRAW_ALIPAY_PREFIX.equals(this.getMerchantOutOrderNo().substring(0, 1))){
				return true;
			}
		}
		return false;
	}
	/**
	 * 是否为支付宝红包支付
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isCouponPay(){
		if(StringUtils.isNotBlank(getMemo()) 
			&& Constant.TRANSACTION_TYPE.PAYMENT.name().equals(transCodeMsgToCode())
			&& getMemo().indexOf("红包回退")==0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 是否为支付宝-集分宝支付
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isCollectPointPay(){
		if(StringUtils.isNotBlank(getMemo()) 
			&& "转账".equals(this.getTransCodeMsg())
			&& getMemo().indexOf("集分宝兑换券")==0){
			return true;
		}
		else{
			return false;
		}
	}
	public String getBatchNo(){
		if(StringUtils.isNotBlank(this.getMemo())){
			return this.getMemo().replaceAll("LVMAMA_REFUNDMENT", "");
		}
		return this.getMemo();
	}
	
	public Float getIncome() {
		return income;
	}
	public void setIncome(Float income) {
		this.income = income;
	}
	public Float getOutcome() {
		return outcome;
	}
	public void setOutcome(Float outcome) {
		this.outcome = outcome;
	}
	public String getTransCodeMsg() {
		return transCodeMsg;
	}
	public void setTransCodeMsg(String transCodeMsg) {
		this.transCodeMsg = transCodeMsg;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getMerchantOutOrderNo() {
		return merchantOutOrderNo;
	}
	public void setMerchantOutOrderNo(String merchantOutOrderNo) {
		this.merchantOutOrderNo = merchantOutOrderNo;
	}
	public String getTransOutOrderNo() {
		return transOutOrderNo;
	}
	public void setTransOutOrderNo(String transOutOrderNo) {
		this.transOutOrderNo = transOutOrderNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}	
}
