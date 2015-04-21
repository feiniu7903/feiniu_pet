package com.lvmama.tnt.recognizance.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;

/**
 * 现金账户变化
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntRecognizanceChange implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 主键
	 */
	private Long changeId;
	/**
	 * recognizanceId
	 */
	private Long recognizanceId;
	/**
	 * 金额，以分为单位
	 */
	private Long amount;
	/**
	 * 类型(扣款,充值)
	 */
	private String type;
	/**
	 * 变动原因
	 */
	private String reason;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 转账单编号
	 */
	private String billNo;

	/**
	 * 转账单日期
	 */
	private Date billTime;

	/**
	 * 银行名称或支付宝
	 */
	private String bankName;
	
	/**
	 * 账户名
	 */
	private String bankAccountName;
	/**
	 * 帐户号码
	 */
	private String bankAccount;

	private String approveStatus;// 审核状态

	private String approveReason;// 审核原因

	private Date approveTime;// 审核时间

	private String approver;// 处理人

	// columns END

	/** ------临时字段分割线 ------- **/
	/**
	 * 用于查询
	 */
	private Long userId; // 分销商id

	private String userName;// 分销商账户

	private String realName, companyName;// 分销商名称,企业名称

	private String notType; // 用于查询时查询不是该类型

	private Boolean dealed; // 是否已处理

	public String getStringBillTime() {
		return TntUtil.formatDate(billTime);
	}

	public void setStringBillTime(String stringBillTime) {
		this.setBillTime(TntUtil.stringToDate(stringBillTime));
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public TntRecognizanceChange() {

	}

	public TntRecognizanceChange(Long changeId) {
		this.changeId = changeId;
	}

	public void setChangeId(Long value) {
		this.changeId = value;
	}

	public Long getChangeId() {
		return this.changeId;
	}

	public void setRecognizanceId(Long value) {
		this.recognizanceId = value;
	}

	public Long getRecognizanceId() {
		return this.recognizanceId;
	}

	public void setAmount(Long value) {
		this.amount = value;
	}

	public Long getAmount() {
		return this.amount;
	}

	public void setType(String value) {
		this.type = value;
	}

	public String getType() {
		return this.type;
	}

	public void setReason(String value) {
		this.reason = value;
	}

	public String getReason() {
		return this.reason;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getStrCreateTime() {
		Date date = getCreateTime();
		if (date != null) {
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return SDF.format(date);
		}
		return "";
	}

	public String getTypeName() {
		return TntRecognizance.TYPE.getDesc(getType());
	}

	public boolean isDebit() {
		return TntRecognizance.TYPE.isDebit(getType());
	}

	public String getNotType() {
		return notType;
	}

	public void setNotType(String notType) {
		this.notType = notType;
	}

	public String getAmountY() {
		return "" + PriceUtil.convertToYuan(getAmount());
	}

	public void setAmountY(String amountY) {
		this.setAmount(PriceUtil.convertToFen(amountY));
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveReason() {
		return approveReason;
	}

	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public boolean getCanEdit() {
		return TntConstant.RECOGNIZANCE_CHANGE_STATUS.canEdit(approveStatus);
	}

	public boolean getCanConfirm() {
		return TntConstant.RECOGNIZANCE_CHANGE_STATUS.canConfirm(approveStatus);
	}

	public boolean getCanApprove() {
		return TntConstant.RECOGNIZANCE_CHANGE_STATUS.canApprove(approveStatus);
	}

	public void trim() {
		if (realName != null)
			setRealName(realName.trim());
	}

	public Boolean getDealed() {
		return dealed;
	}

	public void setDealed(Boolean dealed) {
		this.dealed = dealed;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCompanyName() {
		return companyName != null ? companyName : "个人";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
