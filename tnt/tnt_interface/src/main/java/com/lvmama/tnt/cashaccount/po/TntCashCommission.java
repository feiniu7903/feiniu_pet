package com.lvmama.tnt.cashaccount.po;

import java.util.Date;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;

/**
 * 返佣bean
 * @author wangxicheng
 *
 */
public class TntCashCommission implements java.io.Serializable{

	
	private Long cashCommissionId;
	private Long cashAccountId;
	/**产品类型*/
	private String productType;
	/**履行开始时间*/
	private Date performBeginDate;
	/**履行结束时间*/
	private Date performEndDate;
	/**订单数*/
	private Long orderCount;
	/**总金额*/
	private Long totalAmount;
	/**返佣比率*/
	private Long commisRate;
	/**返佣金额*/
	private Long commisAmount;
	/**创建时间*/
	private Date createTime;
	
	private TntUser tntUser = new TntUser();
	
	public Long getCashCommissionId() {
		return cashCommissionId;
	}
	public void setCashCommissionId(Long cashCommissionId) {
		this.cashCommissionId = cashCommissionId;
	}
	public Long getCashAccountId() {
		return cashAccountId;
	}
	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Date getPerformBeginDate() {
		return performBeginDate;
	}
	public void setPerformBeginDate(Date performBeginDate) {
		this.performBeginDate = performBeginDate;
	}
	public Date getPerformEndDate() {
		return performEndDate;
	}
	public void setPerformEndDate(Date performEndDate) {
		this.performEndDate = performEndDate;
	}
	
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getCommisAmount() {
		return commisAmount;
	}
	public void setCommisAmount(Long commisAmount) {
		this.commisAmount = commisAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCommisRate() {
		return commisRate;
	}
	public void setCommisRate(Long commisRate) {
		this.commisRate = commisRate;
	}
	
	public void setTotalAmountY(String totalAmountY) {
		this.setTotalAmount(PriceUtil.convertToFen(totalAmountY));
	}
	public String getTotalAmountY() {
		if(this.getTotalAmount()==null){
			return "";
		}
		return ""+PriceUtil.convertToYuan(this.getTotalAmount());
	}
	
	public Float getTotalAmountToYuan() {
		return PriceUtil.convertToYuan(this.getTotalAmount());
	}
	
	public void setCommisAmountY(String commisAmountY) {
		this.setCommisAmount(PriceUtil.convertToFen(commisAmountY));
	}
	public String getCommisAmountY() {
		if(this.getCommisAmount()==null){
			return "";
		}
		return ""+PriceUtil.convertToYuan(this.getCommisAmount());
	}
	
	public Float getCommisAmountToYuan() {
		return PriceUtil.convertToYuan(this.getCommisAmount());
	}
	public String getCnProductType() {
		return TntConstant.PRODUCT_TYPE.getCnName(productType);
	}
	public String getCnCreateTime(){
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}
	public String getCnPerformBeginDate(){
		return TntUtil.formatDate(this.performBeginDate, "yyyy-MM-dd");
	}
	public String getCnPerformEndDate(){
		return TntUtil.formatDate(this.performEndDate, "yyyy-MM-dd");
	}
	public TntUser getTntUser() {
		return tntUser;
	}
	public void setTntUser(TntUser tntUser) {
		this.tntUser = tntUser;
	}
	
	
	
}
