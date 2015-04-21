package com.lvmama.finance.group.ibatis.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 团订单详细信息
 * 
 * @author yanggan
 *
 */
public class OrderInfoDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
//	private Long groupSettlementId;
	// 产品经理
	private String userName;
	// 供应商ID
	private Long supplierId;
	// 供应商名称
	private String supplierName;
	// 团号
	private String travelGroupCode;
	// 订单号
	private Long orderId;
	// 游玩时间
	private Date visitTime;
	// 销售产品名称
	private String productName;
	// 销售产品ID
	private Long productId;
	// 采购产品名称
	private String productName1;
	// 采购产品ID
	private Long metaProductId;
	// 结算周期
	private String settlementPeriod;
	// 购买数量
	private Long quantity;
	//打包数量
	private Long productQuantity;
	// 销售价
	private Double sellPrice;
	// 实际结算价
	private Double actualSettlementPrice;
	// 应付金额
	private Double oughtPay;
	// 实付金额
	private Double actualPay;
	// 联系人
	private String name;
	// 银行名称
	private String bankName;
	// 银行账户名
	private String bankAccountName;
	// 银行账户
	private String bankAccount;
	// 已打款金额
	private Double payAmount;
	// 申请金额
	private Double subTotalCosts;
	// 本次打款金额
	private Double surplusAmount;
	// 应付总金额
	private Double sumOughtPay;
	// 实付总金额
	private Double sumActualPay = 0d;
	// 币种
	private String currency;
	//订单子子项ID
	private Long orderItemMetaId;
	
	private Float settlementPrice;
	
	private Float totalSettlementPrice;
	
	private String targetName;
	private String refundMemo;
	private String subProductType;
	private String filialeName;
	private Date orderCreateTime;
	private Date orderPaymentTime;
	private String companyId;
	/**
	 * 币种单位
	 */
	private String unit;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyName() {
		return Constant.FIN_CURRENCY.getCnName(this.currency);
	}
	public Double getSumOughtPay() {
		if(null == sumOughtPay){
			return 0d;
		}
		return sumOughtPay;
	}
	public void setSumOughtPay(Double sumOughtPay) {
		this.sumOughtPay = sumOughtPay;
	}
	public Double getSumActualPay() {
		if(null == sumActualPay){
			return 0d;
		}
		return sumActualPay;
	}
	public void setSumActualPay(Double sumActualPay) {
		this.sumActualPay = sumActualPay;
	}
	public Double getPayAmount() {
		if(null == payAmount){
			return 0d;
		}
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getSubTotalCosts() {
		if(null == subTotalCosts){
			return 0d;
		}
		return subTotalCosts;
	}
	public void setSubTotalCosts(Double subTotalCosts) {
		this.subTotalCosts = subTotalCosts;
	}
	public Double getSurplusAmount() {
		if(null == surplusAmount){
			return 0d;
		}
		return surplusAmount;
	}
	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getVisitTime() {
		if(null != visitTime){
			return new SimpleDateFormat("yyyy-MM-dd").format(visitTime);
		}
		return "";
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public String getProductName() {
		if(null == productName){
			return "";
		}
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName1() {
		if(null == productName1){
			return "";
		}
		return productName1;
	}
	public void setProductName1(String productName1) {
		this.productName1 = productName1;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
	public String getSettlementPeriod() {
		return settlementPeriod;
	}
	public String getSettlementPeriodStr(){
		if(this.settlementPeriod == null){
			return "";
		}else{
			return Constant.SETTLEMENT_PERIOD.getCnName(this.settlementPeriod);
		}
	}
	public String getCompanyName() {
		if (companyId == null) {
			return "";
		}
		return Constant.SETTLEMENT_COMPANY.getCnName(this.companyId);
	}
	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}
	public Long getQuantity() {
		return quantity;
	}
	public Long getTotalQuantity() {
		if(this.quantity == null || this.productQuantity == null){
			return 0l;
		}
		return this.quantity * this.productQuantity;
	}

	
	public Float getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Float settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public void setActualSettlementPrice(Double actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}
	public Double getOughtPay() {
		return oughtPay;
	}
	public void setOughtPay(Double oughtPay) {
		this.oughtPay = oughtPay;
	}
	public Double getActualPay() {
		return actualPay;
	}
	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}
	public String getName() {
		if(null == name){
			return "";
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBankName() {
		if(null == bankName){
			return "";
		}
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountName() {
		if(null == bankAccountName){
			return "";
		}
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccount() {
		if(null == bankAccount){
			return "";
		}
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getUserName() {
		if(null == userName){
			return "";
		}
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		if(null == supplierName){
			return "";
		}
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getTravelGroupCode() {
		if(null == travelGroupCode){
			return "";
		}
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public Long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Float getTotalSettlementPrice() {
		return totalSettlementPrice;
	}
	public String getFilialeName() {
		return filialeName;
	}
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	public String getFilialeNameName(){
		return Constant.FILIALE_NAME.getCnName(this.filialeName);
	}
	public void setTotalSettlementPrice(Float totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Date getOrderPaymentTime() {
		return orderPaymentTime;
	}
	public void setOrderPaymentTime(Date orderPaymentTime) {
		this.orderPaymentTime = orderPaymentTime;
	}
	public String getSubProductType() {
		return subProductType;
	}
	
	public String getSubProductTypeStr() {
		if(this.subProductType != null){
			return Constant.SUB_PRODUCT_TYPE.getCnName(this.subProductType);
		}else{
			return "";
		}
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
}