package com.lvmama.finance.settlement.ibatis.po;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrderItemMeta;


/**
 * 结算子单项
 * 
 * @author yanggan
 * 
 */
public class OrdSubSettlementItem extends SimpleOrderItemMeta{

	private Long subSettlementItemId;
	
	private Long subSettlementId;
	
	private Long orderItemMetaId;
	
	private Double itemPrice;
	
	private Double realItemPrice;
	
	private Double realItemPriceSum;
	
	private Double payAmount;
	
	private String branchName;
	
	private List<String> refundMemos;
	
	/**
	 * 销售价格
	 */
	private Double productPrice;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 供应商ID
	 */
	private Long supplierId;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 总数
	 */
	private Long totalQuantity;
	
	// 打款时间
	private Date paymentTime;
	// 订单实收金额
	private Double actualPay;
	// 结算周期
	private String settlementPeriod;
	// 银行名
	private String bankName;
	// 银行账户名
	private String bankAccountName;
	// 银行帐号
	private String bankAccount;
	// 实收金额
	private Double payedAmount;

	// 申请流水号
	protected String serialNo;
	// 通关码
	protected String code;
	
	// 下单日期
	private Date createDate;

	// 支付时间
	private Date payedTime;
	
	// 销售产品的所属公司
	private String filialeName;
	
	/**
	 * 景区回调信息
	 */
	private String extId;
	
	// 采购产品的产品经理
	private String manager;
	// 销售产品的产品类型
	private String productType;
	// 销售产品的产品子类型
	private String subProductType;
	
	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public String getExtId() {
		if(null == extId){
			return "";
		}
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getCreateDate() {
		if(null != createDate){
			return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
		}
		return "";
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPayedTime() {
		if(null != payedTime){
			return new SimpleDateFormat("yyyy-MM-dd").format(payedTime);
		}
		return "";
	}

	public void setPayedTime(Date payedTime) {
		this.payedTime = payedTime;
	}
	
	public String getSerialNo() {
		if(null == serialNo){
			return "";
		}
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCode() {
		if(null == code){
			return "";
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPaymentTimeStr() {
		if(null == paymentTime){
			return "";
		}
		return DateUtil.getFormatDate(paymentTime, "yyyy-MM-dd");
	}

	public Double getPayedAmount() {
		return payedAmount == null ? 0d : payedAmount;
	}

	public void setPayedAmount(Double payedAmount) {
		this.payedAmount = payedAmount;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Double getActualPay() {
		return actualPay;
	}

	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}

	public String getSettlementPeriod() {
		return settlementPeriod == null ? "" : settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public String getBankName() {
		return bankName == null ? "":bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountName() {
		return bankAccountName == null ? "" : bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccount() {
		return bankAccount == null ? "":bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	// 总数为打包数量*采购
	public Long getTotalQuantity() {
		totalQuantity = productQuantity * quantity;
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public OrdSubSettlementItem() {
	}

	public OrdSubSettlementItem(Long subSettlementItemId, Long orderItemMetaId) {
		this.subSettlementItemId = subSettlementItemId;
		this.orderItemMetaId = orderItemMetaId;
	}

	public OrdSubSettlementItem(Long subSettlementItemId, Long subSettlementId, Long orderItemMetaId, Double itemPrice, Double realItemPrice) {
		this.subSettlementItemId = subSettlementItemId;
		this.subSettlementId = subSettlementId;
		this.orderItemMetaId = orderItemMetaId;
		this.itemPrice = itemPrice;
		this.realItemPrice = realItemPrice;
	}

	public Long getSubSettlementItemId() {
		return this.subSettlementItemId;
	}

	public void setSubSettlementItemId(Long subSettlementItemId) {
		this.subSettlementItemId = subSettlementItemId;
	}

	public Long getSubSettlementId() {
		return this.subSettlementId;
	}

	public void setSubSettlementId(Long subSettlementId) {
		this.subSettlementId = subSettlementId;
	}

	public Long getOrderItemMetaId() {
		return this.orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Double getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getRealItemPrice() {
		return this.realItemPrice;
	}

	public void setRealItemPrice(Double realItemPrice) {
		this.realItemPrice = realItemPrice;
	}


	public Double getRealItemPriceSum() {
		return realItemPriceSum;
	}

	public void setRealItemPriceSum(Double realItemPriceSum) {
		this.realItemPriceSum = realItemPriceSum;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<String> getRefundMemos() {
		return refundMemos;
	}

	public void setRefundMemos(List<String> refundMemos) {
		this.refundMemos = refundMemos;
	}

	public String getRefundMemo(){
		StringBuffer sb = new StringBuffer();
		if(refundMemos.size()>0){
			for(String memo:refundMemos){
				sb.append("[").append(memo).append("]");
			}
		}
		return sb.toString();
	}

}