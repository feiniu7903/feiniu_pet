package com.lvmama.pet.vo;

import java.util.Date;

import com.lvmama.comm.vo.Constant;
/**
 * 银联的对账记录对象
 * @author ZHANG Nan
 */
public class UnionPayResponseBean {
	/**
	 * 商户订单号
	 */
	private String bankPaymentTradeNo;
	/**
	 * 交易流水号：报文里面返回给你们的qid（交易流水号 ） 21位。前台通知/后台通知/查询接口都含有这个值，这值也用于退货交易必填的origQid原始交易流水号。
	 */
	private String bankGatewayTradeNo;
	/**
	 * 交易类型：
	    01消费
	    31消费撤销
	    02预授权
	    32预授权撤销
	    03预授权完成
	    33预授权完成撤销 
	    04退货
	 */
	private String transactionType;
	/**
	 * 交易时间：商户订单时间，即报文里面传过来的orderTime（交易时间）。
	 */
	private Date transactionTime;
	/**
	 * 金额：+和.点是固定格式，可以不参考。里面的金额长度固定，单位为分
	 */
	private Long amount;
	/**
		支付方式：
		1：认证支付
		2：快捷支付
		3：其他支付
		4：储值卡支付
		5：网银支付
		6：后台类交易
		7：小额支付
		8：认真支付开通验证
		9：小额支付开通
		10：牡丹畅通卡支付
		11：IC卡支付
		12：中铁银通卡支付
	 */
	private String payType;
	/**
	 * 卡性质：
		01：借记卡
		02：贷记卡
		03：储值卡
	 */
	private String cardType;
	/**
	 *  卡号：只显示前6位和最后1位。
	 */
	private String cardNo;
	
	/**
	 * 判断收入或支出
	 * @author ZHANG Nan
	 * @return
	 */
	public String transCodeMsgToCode(){
		//收入
		if("01".equals(this.getTransactionType()) || "03".equals(this.getTransactionType())){
			return Constant.TRANSACTION_TYPE.PAYMENT.name();
		}
		//支出
		else if("04".equals(this.getTransactionType())){
			return Constant.TRANSACTION_TYPE.REFUNDMENT.name();
		}
		return this.getTransactionType();
	} 
	/**
	 * 是否需要勾兑(银联预授权只有01、03、04需要勾兑,其它状态并没有产生真实交易,故不需进行勾兑(预授权完成撤销功能,我们没有使用不需要考虑)
		    01消费
		    31消费撤销
		    02预授权
		    32预授权撤销
		    03预授权完成
		    33预授权完成撤销 
		    04退货
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isNeedRecon(){
		if("01".equals(this.getTransactionType()) || "03".equals(this.getTransactionType()) || "04".equals(this.getTransactionType())){
			return true;
		}
		return false;
	}
	public String getBankPaymentTradeNo() {
		return bankPaymentTradeNo;
	}
	public void setBankPaymentTradeNo(String bankPaymentTradeNo) {
		this.bankPaymentTradeNo = bankPaymentTradeNo;
	}
	public String getBankGatewayTradeNo() {
		return bankGatewayTradeNo;
	}
	public void setBankGatewayTradeNo(String bankGatewayTradeNo) {
		this.bankGatewayTradeNo = bankGatewayTradeNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
