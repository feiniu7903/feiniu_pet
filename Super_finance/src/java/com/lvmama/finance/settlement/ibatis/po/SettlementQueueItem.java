package com.lvmama.finance.settlement.ibatis.po;

import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrderItemMeta;

/**
 * 结算队列项
 * @author zhaojindong
 */
public class SettlementQueueItem extends SimpleOrderItemMeta{
	/**
	 * 结算队列项ID
	 */
	private Long settlementQueueItemId;
    /**
     * 结算价
     */
    private Long settlementPrice = 0L;
    /**
     * 实际结算价
     */
    private Long realSettlementPrice = 0L;
    /**
     * 建议打款时间
     */
    private Date suggestionPayTime;
    
    /**
     * 状态
     */
    private String status;
    /**
     * 来源结算单ID
     */
    private Long settlementId;
    /**
     * 来源结算子单ID
     */
    private Long subSettlementId;
    
    /**
     * 已打款金额
     */
    private Long payedAmount = 0L;
    /**
     * 支付对象
     */
    private String paymentTarget;
    /**
     * 采购产品分支ID
     */
    private Long metaBranchId;
    /**
     * 采购产品分支类别名称
     */
    private String branchTypeName;
    
    /**
     * 退款备注
     */
	protected String refundMemo;
	
	/**
	 * 实际结算总价
	 */
	private Long totalSettlementPrice;
	
	/**
	 * 下单时间
	 */
	private Date createTime;
	
	/**
	 * 付款时间
	 */
	private Date paymentTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		if(createTime == null){
			return null;
		}
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd HH:mm:ss");
	}
	

	public String getPaymentTimeStr() {
		if(paymentTime == null){
			return null;
		}
		return DateUtil.getFormatDate(paymentTime, "yyyy-MM-dd HH:mm:ss");
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getVisitDateStr() {
		if(visitDate == null){
			return null;
		}
		return DateUtil.getFormatDate(visitDate, "yyyy-MM-dd");
	}
	public Long getSettlementQueueItemId() {
		return settlementQueueItemId;
	}
	public void setSettlementQueueItemId(Long settlementQueueItemId) {
		this.settlementQueueItemId = settlementQueueItemId;
	}
	public Long getSettlementPrice() {
		if(settlementPrice == null){
			return 0L;
		}
		return settlementPrice;
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	// 结算单价（元）
	public double getSettlementPriceYuan() {
		if(settlementPrice == null){
			return 0.00;
		}
		return settlementPrice / 100.00;
	}
	public String getSettlementPriceYuanStr() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(getSettlementPriceYuan());
	}
	public Long getRealSettlementPrice() {
		if(realSettlementPrice == null){
			return 0L;
		}
		return realSettlementPrice;
	}
	//实际结算单价（元）
	public double getRealSettlementPriceYuan() {
		if(realSettlementPrice == null){
			return 0.00;
		}
		return realSettlementPrice	/ 100.00;
	}
	public String getRealSettlementPriceYuanStr() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(getRealSettlementPriceYuan());
	}
	
	public void setRealSettlementPrice(Long realSettlementPrice) {
		this.realSettlementPrice = realSettlementPrice;
	}
	//实际结算总价 = 实际结算价 * 打包数量 * 购买数量
	public Long getRealSettlementAmountMoney(){
		if(this.totalSettlementPrice!=null &&  this.totalSettlementPrice > 0 ){
			return this.totalSettlementPrice;
		}
		if(realSettlementPrice == null || productQuantity == null || quantity == null){
			return 0L;
		}
		return realSettlementPrice * productQuantity * quantity;
	}
	//实际结算总价（元） = 实际结算价 * 打包数量 * 购买数量
	public double getRealSettlementAmountMoneyYuan(){
		return this.getRealSettlementAmountMoney()/100d;
	}
	public String getRealSettlementAmountMoneyYuanStr(){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(getRealSettlementAmountMoneyYuan());
	}
	
	public Date getSuggestionPayTime() {
		return suggestionPayTime;
	}
	//格式化suggestionPayTime
	public String getSuggestionPayTimeStr() {
		if(suggestionPayTime == null){
			return null;
		}
		return com.lvmama.finance.base.util.DateUtil.dateToString(suggestionPayTime, "yyyy-MM-dd");
	}
	public void setSuggestionPayTime(Date suggestionPayTime) {
		this.suggestionPayTime = suggestionPayTime;
	}
	
	public String getStatus() {
		return status;
	}
	//状态显示名称
	public String getStatusName() {
		if("NORMAL".equals(status)){
			return "正常";
		}else if("PAUSE".equals(status)){
			return "缓结";
		}else if("NEVER".equals(status)){
			return "不结";
		}
		return null;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPayedAmount() {
		if(payedAmount == null){
			return 0L;
		}
		return payedAmount;
	}
	public String getPayedAmountYuanStr(){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return String.valueOf(nf.format(getPayedAmount() / 100.00));
	}
	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}
	
	
	/**
	 * 页面显示的行ID。订单子子项ID + 结算队列项ID
	 * @return
	 */
	public String getRowId(){
		String s = "";
		if(orderItemMetaId != null){
			s = s + orderItemMetaId;
		}
		if(settlementQueueItemId != null){
			s = s  + "-" + String.valueOf(settlementQueueItemId);
		}
		return s + "-[" + UUID.randomUUID().toString() + "]";
	}
	public Long getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}
	public Long getSubSettlementId() {
		return subSettlementId;
	}
	public void setSubSettlementId(Long subSettlementId) {
		this.subSettlementId = subSettlementId;
	}
	public String getPaymentTarget() {
		return paymentTarget;
	}
	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public String getBranchTypeName() {
		return branchTypeName;
	}
	public void setBranchTypeName(String branchTypeName) {
		this.branchTypeName = branchTypeName;
	}
	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}
	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}
}
