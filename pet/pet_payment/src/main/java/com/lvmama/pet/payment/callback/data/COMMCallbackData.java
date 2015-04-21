package com.lvmama.pet.payment.callback.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.Constant;


/**
 * 交通银行回调数据.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super 一期
 * @see com.lvmama.common.vo.Constant
 */
public class COMMCallbackData implements CallbackData {

	/**
	 * 商户客户号（商户编号） 商户客户号是银行生成的15 位编号.
	 */
	private String merID = "";
	/**
	 * 订单号.
	 */
	private String orderId = "";
	/**
	 * 订单金额.
	 */
	private String amount = "";
	/**
	 * 交易币种.
	 */
	private String curType = "";

	/**
	 *平台批次号 14124| PAYBATNO.
	 */
	private String payBatNO;

	/**
	 *商户批次号 412444 MERCHBATCHNO.
	 */
	private String merchBatchNo;

	/**
	 * 交易日期 yyyyMMdd.
	 */
	private String orderDate = "";
	/**
	 * 交易时间.
	 */
	private String orderTime = "";

	/**
	 * 交易流水号0000052.
	 */
	private String commSerial;
	/**
	 * 交易结果 1成功.
	 */
	private String success = "";
	/**
	 *手续费总额.
	 */
	private String feeAmount = "";
	/**
	 * 银行卡类型 1 0 借记卡 1 准贷记卡 2 贷记卡 3 他行银联卡.
	 */
	private String cardType;

	/**
	 *银行备注 100 BankMoNo.
	 */
	private String bankMoNo;
	/**
	 *错误信息描述 100 ErrDis.
	 */
	private String errDis;
	/**
	 *银行签名 2000 SignMsg.
	 */
	private String signMsg;
	/**
	 *验证返回的银行签名是否正确.
	 */
	private String isSign;

	/**
	 *包装返回的数据，除银行签名外.
	 */
	private String srcMsg;

	private String serial;
	
	/**
	 * 包装交通银行的返回数据.
	 * @param map
	 * 
	 * @return COMMCallbackData
	 *           包装好的COMMCallbackData
	 */
	public COMMCallbackData(final Map<String, String> map) {
		this.serial  = map.get("serial");
		String notifyMsg = map.get("notifyMsg");
		int lastIndex = notifyMsg.lastIndexOf("|");
		String signMessage = notifyMsg.substring(lastIndex + 1, notifyMsg
				.length());// 获取签名信息
		String srcMessage = notifyMsg.substring(0, lastIndex + 1);
		this.setSignMsg(signMessage);
		this.setSrcMsg(srcMessage);
		java.util.StringTokenizer stName = new java.util.StringTokenizer(
				srcMessage, "|");
		SortCommDate(stName);
	}
	/**
	 * 数据放入到对象中.
	 * @param StringTokenizer
	 */
	private void SortCommDate(java.util.StringTokenizer stName) {
		List<String> vc = SortCommCallList(stName);
		this.setMerID(String.valueOf(vc.get(0)));
		this.setOrderId(String.valueOf(vc.get(1)));
		this.setAmount(String.valueOf(vc.get(2)));
		this.setCurType(String.valueOf(vc.get(3)));
		this.setPayBatNO(String.valueOf(vc.get(4)));
		this.setMerchBatchNo(String.valueOf(vc.get(5)));
		this.setOrderDate(String.valueOf(vc.get(6)));
		this.setOrderTime(String.valueOf(vc.get(7)));
		this.setCommSerial(String.valueOf(vc.get(8)));
		this.setSuccess(String.valueOf(vc.get(9)));
		this.setFeeAmount(String.valueOf(vc.get(10)));
		this.setCardType(String.valueOf(vc.get(11)));
		this.setBankMoNo(String.valueOf(vc.get(12)));
		this.setErrDis(String.valueOf(vc.get(13)));
		this.setSignMsg(String.valueOf(vc.get(14)));
	}
	/**
	 * 包装数据成列表hua
	 * @param StringTokenizer
	 * 
	 * @return Vector
	 *           包装好的Vector
	 */
	private List<String> SortCommCallList(
			java.util.StringTokenizer stName) {
		List<String> vc = new ArrayList<String>();
		int i = 0;
		while (stName.hasMoreTokens()) {
			String value = (String) stName.nextElement();
			if (value.equals("")) {
				value = "&nbsp;";
			}
			vc.add(i++, value);
		}
		return vc;
	}
	
	
	/**
	 * 判断返回签名是否正确.
	 * 
	 * @return true代表返回签名验证正确   false签名失败 
	 */
	@Override
	public boolean checkSignature() {
		int veriyCode = -1;
		if (veriyCode < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 *  商户客户号.
	 *  
	 * @return 商户客户号
	 */
	public String getMerID() {
		return merID;
	}
	/**
	 * 设置商户客户号.
	 * 
	 * @param merID
	 *            商户客户号.
	 */
	public void setMerID(String merID) {
		this.merID = merID;
	}
	/**
	 *  订单号.
	 *  
	 * @return  订单号.
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置订单号.
	 * 
	 * @param orderId
	 *            订单号.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 *  订单金额.
	 *  
	 * @return 支付金额，以分为单位.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 设置订单金额.
	 * 
	 * @param amount
	 *            订单金额.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 *  交易币种.
	 *  
	 * @return  curType
	 *    curType交易币种CNY为人民币.
	 */
	public String getCurType() {
		return curType;
	}
	/**
	 * 设置交易币种.
	 * 
	 * @param curType
	 *            交易币种CNY为人民币.
	 */
	public void setCurType(String curType) {
		this.curType = curType;
	}
	/**
	 *  交易日期.
	 *  
	 * @return 交易日期 yyyyMMdd.
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * 设置交易日期.
	 * 
	 * @param orderDate
	 *            交易日期 yyyyMMdd.
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	/**
	 *  交易时间 .
	 *  
	 * @return 交易时间 HHmmss.
	 */
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置交易时间.
	 * 
	 * @param orderTime
	 *            交易时间 HHmmss.
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 *   返回的交易流水号 .
	 *   
	 * @return commSerial 
	 *         交易流水号.
	 */
	public String getCommSerial() {
		return commSerial;
	}
	/**
	 * 设置交易流水号.
	 * 
	 * @param commSerial
	 *            交易流水号.
	 */
	public void setCommSerial(String commSerial) {
		this.commSerial = commSerial;
	}
	/**
	 *  返回交易结果 .
	 *  
	 * @return  1成功.
	 */
	public String getSuccess() {
		return success;
	}
	/**
	 * 设置交易结果.
	 * 
	 * @param success
	 *            交易结果.
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
	/**
	 *  返回的手续费总额.
	 *  
	 * @return feeAmount 
	 *           以元为单位.
	 */
	public String getFeeAmount() {
		return feeAmount;
	}
	/**
	 * 设置手续费.
	 * 
	 * @param feeAmount
	 *            手续费.
	 */
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * 返回的银行备注.
	 * 
	 * @return  bankMoNo 
	 *        银行备注.
	 */
	@Override
	public String getCallbackInfo() {
		return this.bankMoNo;
	}
	/**
	 * 网关支付号.
	 * 
	 * @return commSerial 
	 *        网关支付号.
	 */
	@Override
	public String getGatewayTradeNo() {
		return commSerial;
	}

	/**
	 * 返回的银行错误信息描述.
	 * 
	 * @return errDis 
	 *     错误信息描述.
	 */
	@Override
	public String getMessage() {
		return this.errDis;
	}

	/**
	 * 返回的支付金额.
	 * 
	 * @return  amount
	 *          支付金额，以分为单位.
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(this.amount) * 100;
	}

	/**
	 * 返回支付网关名称.
	 * 
	 * @return 支付网关名称
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.COMM.name();
	}

	/**
	 * 是否支付成功.
	 * 
	 * @return 1代表支付成功，2.3代表支付不成功
	 */
	@Override
	public boolean isSuccess() {
		return "1".equals(this.success);
	}
	/**
	 *  返回的平台批次号.
	 * 
	 * @return  payBatNO
	 *             平台批次号.
	 */
	public String getPayBatNO() {
		return payBatNO;
	}
	/**
	 * 设置平台批次号.
	 * 
	 * @param payBatNO
	 *            平台批次号.
	 */
	public void setPayBatNO(String payBatNO) {
		this.payBatNO = payBatNO;
	}
	/**
	 * 返回的商户批次号.
	 * 
	 * @return merchBatchNo
	 *              商户批次号.
	 */
	public String getMerchBatchNo() {
		return merchBatchNo;
	}
	/**
	 * 设置商户批次号.
	 * 
	 * @param merchBatchNo
	 *            商户批次号.
	 */
	public void setMerchBatchNo(String merchBatchNo) {
		this.merchBatchNo = merchBatchNo;
	}
	/**
	 * 返回的银行卡类型 .
	 * 
	 * @return  cardType
	 *            银行卡类型.
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * 设置银行卡类型.
	 * 
	 * @param cardType
	 *            银行卡类型.
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * 返回的银行备注.
	 * 
	 * @return  bankMoNo 
	 *            银行备注.
	 */
	public String getBankMoNo() {
		return bankMoNo;
	}
	/**
	 * 设置银行备注.
	 * 
	 * @param bankMoNo
	 *           银行备注.
	 */
	public void setBankMoNo(String bankMoNo) {
		this.bankMoNo = bankMoNo;
	}
	/**
	 * 返回的错误信息描述.
	 * 
	 * @return errDis
	 *           错误信息
	 */
	public String getErrDis() {
		return errDis;
	}

	/**
	 *设置 错误信息.
	 * 
	 * @param errDis
	 *           错误信息.
	 */
	public void setErrDis(String errDis) {
		this.errDis = errDis;
	}
	/**
	 * 返回的银行签名 .
	 * 
	 * @return signMsg
	 *          银行签名.
	 */
	public String getSignMsg() {
		return signMsg;
	}
	/**
	 *设置银行签名.
	 * 
	 * @param signMsg
	 *           银行签名.
	 */
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	/**
	 * 验证返回的银行签名是否正确.
	 * 
	 * @return isSign 不等于0为正确
	 */
	public String getIsSign() {
		return isSign;
	}
	/**
	 * 设置银行签名是否正确.
	 * 
	 * @param signMsg
	 *           银行签名不等于0为正确.
	 */
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	/**
	 * 包装返回的数据，除银行签名外.
	 * 
	 * @return srcMsg
	 *         包装返回的数据，除银行签名外.
	 */
	public String getSrcMsg() {
		return srcMsg;
	}
	/**
	 * 银行签名.
	 * 
	 * @param srcMsg
	 *           返回的数据，除银行签名外.
	 */
	public void setSrcMsg(String srcMsg) {
		this.srcMsg = srcMsg;
	}
	@Override
	public String getPaymentTradeNo() {
		return this.orderId;
	}
	@Override
	public String getRefundSerial() {
		return null;
	}
	@Override
	public Date getCallBackTime() {
		return new Date();
	}

}
