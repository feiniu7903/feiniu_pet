package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;

/**
 * AbstractDetail.
 *
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.common.UtilityTool#isValid(Object)
 */
public abstract class AbstractDetail implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6100574488777309424L;
	private boolean checked = false;
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 采购产品名称.
	 */
	private String metaProductName;
	
	/**
	 * 采购产品类别名称
	 */
	private String branchName;
	/**
	 * 成人数.
	 */
	private Long adultQuantity;
	/**
	 * 儿童数.
	 */
	private Long childQuantity;
	/**
	 * 单履行对象通关人数.
	 */
	private Long singlePerformPassedQuantity;
	/**
	 * 多履行对象通过人数.
	 */
	private Long multiplePerformPassedQuantity;
	/**
	 * 金额.<br>
	 * 金额 = 结算价 X 数量， 以分为单位
	 */
	private Long amount;
	/**
	 * 订单联系人姓名.
	 */
	private String contactName;
	/**
	 * 订单联系人手机.
	 */
	private String contactMobile;
	/**
	 * 截止期.
	 */
	private Date deadlineTime;
	/**
	 * 传真备注.
	 */
	private String faxMemo;
	/**
	 * orderItemMetaId.
	 */
	private Long orderItemMetaId;
	/**
	 * 履行对象ID.
	 */
	private Long targetId;
	/**
	 * 订单状态.
	 */
	private String orderStatus;
	/**
	 * 支付目标.
	 */
	private String paymentTarget;
	/**
	 * singlePerformPassedQuantity为null时为-1.
	 */
	private String singlePerformPassedQuantityFlag;

	/**
	 * multipleQuantity为null时为-1.
	 */
	private String multipleQuantityFlag;
	/**
	 * 是否通关，<code>true</code>代表已通关，<code>false</code>代表未通关.
	 */
	private boolean isPass;
	/**
	 * 使用时间.
	 * 
	 */
	private Date usedTime;
	/**
	 * 通关码状态.
	 */
	private String status;
	/**
	 * 通关时间
	 */
	private Date performTime;
	
	/**
	 * 凭证类型
	 */
	private String certificateType;
	/**
	 * 订单创建时间
	 */
	private Date orderCreateTime;
	
	/**
	 * 市价
	 */
	private Long prodPrice;
	
	/**
	 * getOrderId.
	 *
	 * @return 订单ID
	 */
	public final Long getOrderId() {
		return orderId;
	}

	/**
	 * setOrderId.
	 *
	 * @param orderId
	 *            订单ID
	 */
	public final void setOrderId(final Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * getMetaProductName.
	 *
	 * @return 采购产品名称
	 */
	public final String getMetaProductName() {
		return metaProductName;
	}

	/**
	 * setMetaProductName.
	 *
	 * @param metaProductName
	 *            采购产品名称
	 */
	public final void setMetaProductName(final String metaProductName) {
		this.metaProductName = metaProductName;
	}

	/**
	 * getVisitorQuantity.
	 *
	 * @return 游玩人数.<br>
	 *         游玩人数 = 成人数 + 儿童数
	 */
	public final Long getVisitorQuantity() {
		return (UtilityTool.isValid(adultQuantity) ? adultQuantity : 0L) + (UtilityTool.isValid(childQuantity) ? childQuantity : 0L);
	}

	/**
	 * getAdultQuantity.
	 *
	 * @return 成人数
	 */
	public final Long getAdultQuantity() {
		return adultQuantity;
	}

	/**
	 * setAdultQuantity.
	 *
	 * @param adultQuantity
	 *            成人数
	 */
	public final void setAdultQuantity(final Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	/**
	 * getChildQuantity.
	 *
	 * @return 儿童数
	 */
	public final Long getChildQuantity() {
		return childQuantity;
	}

	/**
	 * setChildQuantity.
	 *
	 * @param childQuantity
	 *            儿童数
	 */
	public final void setChildQuantity(final Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	/**
	 * getSinglePerformPassedQuantity.
	 *
	 * @return 单履行对象通关人数
	 */
	public final Long getSinglePerformPassedQuantity() {
		return singlePerformPassedQuantity;
	}

	/**
	 * setSinglePerformPassedQuantity.
	 *
	 * @param singlePerformPassedQuantity
	 *            单履行对象通关人数
	 */
	public final void setSinglePerformPassedQuantity(final Long singlePerformPassedQuantity) {
		this.singlePerformPassedQuantity = singlePerformPassedQuantity;
	}

	/**
	 * getMultiplePerformPassedQuantity.
	 *
	 * @return 多履行对象通过人数
	 */
	public final Long getMultiplePerformPassedQuantity() {
		return multiplePerformPassedQuantity;
	}

	/**
	 * setMultiplePerformPassedQuantity.
	 *
	 * @param multiplePerformPassedQuantity
	 *            多履行对象通过人数
	 */
	public final void setMultiplePerformPassedQuantity(final Long multiplePerformPassedQuantity) {
		this.multiplePerformPassedQuantity = multiplePerformPassedQuantity;
	}

	/**
	 * getAmount.
	 *
	 * @return 金额.<br>
	 *         金额 = 结算价 X 数量， 以分为单位
	 */
	public final Long getAmount() {
		return amount;
	}
	
	/**
	 * 身份证号
	 */
	private String contactCertNo;

	public String getContactCertNo() {
		return contactCertNo;
	}

	public void setContactCertNo(String contactCertNo) {
		this.contactCertNo = contactCertNo;
	}
	
	/**
	 * setAmount.
	 *
	 * @param amount
	 *            金额.<br>
	 *            金额 = 结算价 X 数量， 以分为单位
	 */
	public final void setAmount(final Long amount) {
		this.amount = amount;
	}

	/**
	 * getAmountYuan.
	 *
	 * @return 金额.<br>
	 *         金额 = 结算价 X 数量， 以元为单位
	 */
	public final String getAmountYuan() {
		String temp = "-";
		if (amount.intValue() != 0) {
			temp = String.valueOf(amount / 100);
		}
		return temp;
	}

	/**
	 * getContactName.
	 *
	 * @return 订单联系人姓名
	 */
	public final String getContactName() {
		return contactName;
	}

	/**
	 * setContactName.
	 *
	 * @param contactName
	 *            订单联系人姓名
	 */
	public final void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	/**
	 * getContactMobile.
	 *
	 * @return 订单联系人手机
	 */
	public final String getContactMobile() {
		return contactMobile;
	}

	/**
	 * setContactMobile.
	 *
	 * @param contactMobile
	 *            订单联系人手机
	 */
	public final void setContactMobile(final String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * getDeadlineTime.
	 *
	 * @return 截止期
	 */
	public final Date getDeadlineTime() {
		return deadlineTime;
	}

	/**
	 * setDeadlineTime.
	 *
	 * @param deadlineTime
	 *            截止期
	 */
	public final void setDeadlineTime(final Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	/**
	 * 获取通关人数.
	 *
	 * @return 通关人数
	 */
	public final Long getPerformPassedQuantity() {
		Long performPassedQuantity = Long.valueOf(0);
		if (singlePerformPassedQuantity > 0) {
			performPassedQuantity = singlePerformPassedQuantity;
		} else if (multiplePerformPassedQuantity > 0) {
			performPassedQuantity = multiplePerformPassedQuantity;
		}else if(this.getIsPass()&&performPassedQuantity==0){
			performPassedQuantity=this.adultQuantity+this.childQuantity;
		}
		return performPassedQuantity;
	}
	
	/**
	 * 得到实际金额
	 */
	public Long getActualQuantity() {
		if(Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(this.paymentTarget)){
			return this.amount;
		}
		return this.getPerformPassedQuantity() * prodPrice/100;
	}
	
	/**
	 * getFaxMemo.
	 *
	 * @return 传真备注
	 */
	public final String getFaxMemo() {
		return faxMemo;
	}

	/**
	 * setFaxMemo.
	 *
	 * @param faxMemo
	 *            传真备注
	 */
	public final void setFaxMemo(final String faxMemo) {
		this.faxMemo = faxMemo;
	}

	/**
	 * getOrderItemMetaId.
	 *
	 * @return orderItemMetaId
	 */
	public final Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	/**
	 * setOrderItemMetaId.
	 *
	 * @param orderItemMetaId
	 *            orderItemMetaId
	 */
	public final void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * getTargetId.
	 *
	 * @return 履行对象ID
	 */
	public Long getTargetId() {
		return targetId;
	}

	/**
	 * setTargetId.
	 *
	 * @param targetId
	 *            履行对象ID
	 */
	public void setTargetId(final Long targetId) {
		this.targetId = targetId;
	}

	/**
	 * getOrderStatus.
	 *
	 * @return 订单状态
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * setOrderStatus.
	 *
	 * @param orderStatus
	 *            订单状态
	 */
	public void setOrderStatus(final String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 订单是否为取消
	 *
	 * @return
	 */
	public boolean isCanceled() {
		return Constant.ORDER_STATUS.CANCEL.name().equalsIgnoreCase(this.orderStatus.trim());
	}

	/**
	 * getPaymentTarget.
	 *
	 * @return 支付目标
	 */
	public String getPaymentTarget() {
		return paymentTarget;
	}

	/**
	 * setPaymentTarget.
	 *
	 * @param paymentTarget
	 *            支付目标
	 */
	public void setPaymentTarget(final String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}
	/**
	 * 是否支付给供应商
	 * @return
	 */
	public boolean isPayToSupplier(){
		if(Constant.PAYMENT_TARGET.TOLVMAMA.name().equalsIgnoreCase(this.getPaymentTarget())){
			return false;
		}
		return true;
	}

	/**
	 * 是否支付给供应商
	 * @return
	 */
	public boolean isPayToLvmama(){
		return !isPayToSupplier();
	}
	/**
	 * getSinglePerformPassedQuantityFlag.
	 *
	 * @return singlePerformPassedQuantity为null时为-1
	 */
	public String getSinglePerformPassedQuantityFlag() {
		return singlePerformPassedQuantityFlag;
	}

	/**
	 * setSinglePerformPassedQuantityFlag.
	 *
	 * @param singlePerformPassedQuantityFlag
	 *            singlePerformPassedQuantity为null时为-1
	 */
	public void setSinglePerformPassedQuantityFlag(
			final String singlePerformPassedQuantityFlag) {
		this.singlePerformPassedQuantityFlag = singlePerformPassedQuantityFlag;
	}

	/**
	 * getMultipleQuantityFlag.
	 *
	 * @return multipleQuantity为null时为-1
	 */
	public String getMultipleQuantityFlag() {
		return multipleQuantityFlag;
	}

	/**
	 * setMultipleQuantityFlag.
	 *
	 * @param multipleQuantityFlag
	 *            multipleQuantity为null时为-1
	 */
	public void setMultipleQuantityFlag(final String multipleQuantityFlag) {
		this.multipleQuantityFlag = multipleQuantityFlag;
	}

	/**
	 * getIsPass.
	 *
	 * @return <code>true</code>代表已通关，<code>false</code>代表未通关
	 */
	public boolean getIsPass() {
		boolean flag = true;
		if (("-1".equals(singlePerformPassedQuantityFlag)
				&& multipleQuantityFlag.equals("-1"))
				|| Constant.ORDER_STATUS.CANCEL.name().equalsIgnoreCase(
						this.getOrderStatus())
				) {
			flag = false;
		}
		return flag;
	}

	/**
	 * setIsPass.
	 *
	 * @param isPass
	 *            <code>true</code>代表已通关，<code>false</code>代表未通关
	 */
	public void setIsPass(final boolean isPass) {
		this.isPass = isPass;
	}
	
	public boolean isNotPass(){
		return !this.getIsPass();
	}

	/**
	 * getUsedTime.
	 * 
	 * @return 使用时间.
	 */
	public Date getUsedTime() {
		return usedTime;
	}

	/**
	 * setUsedTime.
	 * 
	 * @param usedTime
	 *            使用时间.
	 */
	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	/**
	 * getStatus.
	 * 
	 * @return 通关码状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setStatus.
	 * 
	 * @param status
	 *            通关码状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPerformTime() {
		return performTime;
	}

	public void setPerformTime(Date performTime) {
		this.performTime = performTime;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Long getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Long prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	
}
