package com.lvmama.report.po;

import java.sql.Timestamp;
import java.text.NumberFormat;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 现金账户日记账
 * 
 * @author yanggan
 * 
 */
public class MoneyUserChangeTV implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 现金账户ID
	 */
	private String moneyUserId;
	/**
	 * 本次交易发生的实际金额
	 */
	private Long amount;
	/**
	 * 本次交易的借贷方向 借方：DEBIT 贷方：CREDIT
	 */
	private String direction;
	/**
	 * 本次交易发生前现金账户的充值余额
	 */
	private Long rechargeBalanceBeforeChange;
	/**
	 * 本次交易发生前现金账户的退款余额
	 */
	private Long refundBalanceBeforeChange;
	
	/**
	 * 本次交易发生前账户的奖金余额
	 */
	private Long bonusBalanceBeforeChange;
	
	/**
	 * 本次交易的类型 退款支付：REFUND_BALANCE_PAY 余额支付：RECHARGE_BALANCE_PAY 充值：RECHARGE
	 * 退款：REFUND 提现：DRAW
	 */
	private String type;
	/**
	 * 本次交易发生的时间
	 */
	private Timestamp createTime;
	/**
	 * 业务ID
	 */
	private String businessId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 流水号
	 */
	private String serial;
	/**
	 * 支付渠道
	 */
	private String paymentGateway;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 账户类型,"CASH":现金  "BONUS":奖金
	 */
	private String accountType;

	public MoneyUserChangeTV() {
	}

	public MoneyUserChangeTV(Long id, String moneyUserId, Long amount, Long rechargeBalanceBeforeChange, Long refundBalanceBeforeChange, String type, Timestamp createTime, String businessId, String direction) {
		this.id = id;
		this.moneyUserId = moneyUserId;
		this.amount = amount;
		this.rechargeBalanceBeforeChange = rechargeBalanceBeforeChange;
		this.refundBalanceBeforeChange = refundBalanceBeforeChange;
		this.type = type;
		this.createTime = createTime;
		this.businessId = businessId;
		this.direction = direction;
	}

	public MoneyUserChangeTV(Long id, String moneyUserId, Long amount, Long rechargeBalanceBeforeChange, Long refundBalanceBeforeChange, String type, Timestamp createTime, String businessId, String orderNo, String serial, String paymentGateway, String memo, String direction) {
		this.id = id;
		this.moneyUserId = moneyUserId;
		this.amount = amount;
		this.rechargeBalanceBeforeChange = rechargeBalanceBeforeChange;
		this.refundBalanceBeforeChange = refundBalanceBeforeChange;
		this.type = type;
		this.createTime = createTime;
		this.businessId = businessId;
		this.orderNo = orderNo;
		this.serial = serial;
		this.paymentGateway = paymentGateway;
		this.memo = memo;
		this.direction = direction;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMoneyUserId() {
		return this.moneyUserId;
	}

	public void setMoneyUserId(String moneyUserId) {
		this.moneyUserId = moneyUserId;
	}

	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getRechargeBalanceBeforeChange() {
		return this.rechargeBalanceBeforeChange;
	}

	public void setRechargeBalanceBeforeChange(Long rechargeBalanceBeforeChange) {
		this.rechargeBalanceBeforeChange = rechargeBalanceBeforeChange;
	}

	public Long getRefundBalanceBeforeChange() {
		return this.refundBalanceBeforeChange;
	}

	public void setRefundBalanceBeforeChange(Long refundBalanceBeforeChange) {
		this.refundBalanceBeforeChange = refundBalanceBeforeChange;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPaymentGateway() {
		return this.paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getMemo() {
		if("BONUS_RETURN".equals(type)){//奖金返现
			return Constant.BonusOperation.getCnName(memo);
		}
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 发生时间格式化
	 * 
	 * @return
	 */
	public String getCreateTimeFormat() {
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd");
	}

	/**
	 * 借款数额(元)
	 */
	public String getDebitAmount() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return "DEBIT".equals(direction) ? nf.format(amount / 100.00) : "";
	}

	/**
	 * 贷款数额(元)
	 */
	public String getCreditAmount() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return "CREDIT".equals(direction) ? nf.format(0 - amount / 100.00) : "";
	}

	/**
	 * 总余额(元)
	 * 
	 * @return
	 */
	public String getTotalBalance() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		if("CASH".equals(accountType)||null==accountType){//现金余额
			return nf.format((rechargeBalanceBeforeChange + refundBalanceBeforeChange + amount) / 100.00);
		}else if("BONUS".equals(accountType)){//奖金余额
			return nf.format((bonusBalanceBeforeChange + amount) / 100.00);
		}
		return "";
	}

	/**
	 * 操作类型
	 * 
	 * @return
	 */
	public String getTypeName() {
		if ("REFUND_BALANCE_PAY".equals(type) || "RECHARGE_BALANCE_PAY".equals(type)) {
			return "现金支付";
		}else if ("RECHARGE".equals(type)) {
			return "现金充值";
		} else if ("REFUND".equals(type)) {
			return "现金退款";
		} else if ("DRAW".equals(type)) {
			return "现金提现";
		}else if ("BONUS_RETURN".equals(type)) {
			return "奖金返现";
		} else if ("BONUS_BALANCE_PAY".equals(type)) {
			return "奖金支付";
		}  else if ("BONUS_REFUND".equals(type)) {
			return "奖金退款";
		} else {
			return "";
		}
	}

	/**
	 * 支付渠道名称
	 * 
	 * @return
	 */
	public String getPaymentGatewayName() {
		return Constant.PAYMENT_GATEWAY.getCnName(paymentGateway);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * 获得账户类型
	 * @return
	 */
	public String getAccountTypeName(){
		if("CASH".equals(accountType)||null==accountType){
			return "现金";
		}else if("BONUS".equals(accountType)){
			return "奖金";
		}
		return null;
	}

	public Long getBonusBalanceBeforeChange() {
		return bonusBalanceBeforeChange;
	}

	public void setBonusBalanceBeforeChange(Long bonusBalanceBeforeChange) {
		this.bonusBalanceBeforeChange = bonusBalanceBeforeChange;
	}
	
	
	
}