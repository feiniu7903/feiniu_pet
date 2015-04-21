package com.lvmama.comm.bee.vo.ord;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 履行明细.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class PerformDetail extends AbstractDetail {
	/**
	 * 复选框-是否选中订单.
	 */
	private boolean isChecked;
	/**
	 * 复选框-是否通关订单.
	 */
	private boolean isDisabledPass;
	/**new_eplace*****************************/
	/**
	 * 订购数量
	 */
	private Long quantity;

	private Long singleAdultQuantity;
	
	private Long singleChildQuantity;
	
	private Long multipleAdultQuantity;
	
	private Long multipleChildQuantity;
	
	private Long metaBrandId;
	
	private Long metaProductId;
	
	private String userMemo;
	
	private String performMemo;
	
	private String contactAddress;
	
	//不定期密码券
	private String isAperiodic;

	//不定期密码券
	private String passwordCertificate;
	
	/**
	 * 不定期密码券有效开始日期
	 * */
	private Date validBeginTime;

	/**
	 * 不定期密码券有效结束日期
	 * */
	private Date validEndTime;

	private Long sellPrice;
	private Long productQuantity;
	private Date visitTime;
	private String performStatus;
	private String invalidDateMemo;
	private Long settlementPrice;
	private Long totalSettlementPrice;
	/**new_eplace*****************************/
	public Long getSingleAdultQuantity() {
		return singleAdultQuantity;
	}

	public void setSingleAdultQuantity(Long singleAdultQuantity) {
		this.singleAdultQuantity = singleAdultQuantity;
	}

	public Long getSingleChildQuantity() {
		return singleChildQuantity;
	}

	public void setSingleChildQuantity(Long singleChildQuantity) {
		this.singleChildQuantity = singleChildQuantity;
	}
	public String getPerformMemo() {
		return performMemo;
	}

	public void setPerformMemo(String performMemo) {
		this.performMemo = performMemo;
	}
	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getMetaBrandId() {
		return metaBrandId;
	}

	public void setMetaBrandId(Long metaBrandId) {
		this.metaBrandId = metaBrandId;
	}

	public Long getMultipleAdultQuantity() {
		return multipleAdultQuantity;
	}

	public void setMultipleAdultQuantity(Long multipleAdultQuantity) {
		this.multipleAdultQuantity = multipleAdultQuantity;
	}

	public Long getMultipleChildQuantity() {
		return multipleChildQuantity;
	}

	public void setMultipleChildQuantity(Long multipleChildQuantity) {
		this.multipleChildQuantity = multipleChildQuantity;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getZhOrderStatus(){
		return Constant.ORDER_STATUS.getCnName(getOrderStatus());
	}
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	/**
	 * 销售价<br/><br/>
	 * <strong><font color=red>订单子项打包 N 个子子项</font></strong>
	 * @return
	 */
	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getSellPriceYuan(){
		if (sellPrice==null || sellPrice <= 0L) {
			return "0";
		} else {
			return String.valueOf(PriceUtil.convertToYuan(sellPrice));
		}
	}
	public String getZhPaymentTarget(){
		return Constant.PAYMENT_TARGET.getCnName(getPaymentTarget());
	}
	public String getZhPassStatus(){
		String zhPassStatus="已游玩";
		if(!getIsPass())zhPassStatus="未游玩";
		return zhPassStatus;
	}
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6790881592310654057L;

	public String getStrVisitTime() {
		if (this.visitTime != null) {
			return DateFormatUtils.format(this.visitTime, "yyyy-MM-dd");
		}
		return "";
	}
	
	public String getStrDeadlineTime() {
		if (this.getDeadlineTime() != null) {
			return DateFormatUtils.format(this.getDeadlineTime(), "yyyy-MM-dd HH:mm");
		}
		return "";
	}

	public String getStrOrderCreateTime() {
		if (this.getDeadlineTime() != null) {
			return DateFormatUtils.format(this.getOrderCreateTime(), "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	public boolean getIsNotPass() {
		return !this.getIsPass();
	}

	public boolean getIsShowPass() {
		return !isDisabledPass();
	}
	
	public boolean isDisabledPass() {
		if((!this.getIsPass()&&this.isPayToSupplier()&&!this.isCanceled())||
				(!this.getIsPass()&&this.isPayToLvmama()&&!this.isCanceled()&&!Constant.CCERT_TYPE.DIMENSION.name().equals(this.getCertificateType()))	
			){
			return false;
		}else if(this.getIsPass()){
			return  true;
		}
//		this.isPayToSupplier()&&!this.isNotPass()&&!this.isCanceled()&&!this.getIsPass();
		return true;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getPerformStatus() {
		return performStatus;
	}

	public String getPasswordCertificate() {
		return passwordCertificate;
	}

	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}
	
	public String getValidBeginTime() {
		if (this.validBeginTime != null) {
			return DateFormatUtils.format(this.validBeginTime, "yyyy-MM-dd");
		}
		return "";
	}

	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}
	
	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public String getValidEndTime() {
		if (this.validEndTime != null) {
			return DateFormatUtils.format(this.validEndTime, "yyyy-MM-dd");
		}
		return "";
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}

	public void setDisabledPass(boolean isDisabledPass) {
		this.isDisabledPass = isDisabledPass;
	}

	/**
	 * 实际成人取票数
	 */
	public Long getRealAdultQuantity() {
		Long quantity=0L;
		if(this.getIsPass() && !this.isCanceled()){
			quantity=this.getSingleAdultQuantity()+this.getMultipleAdultQuantity();
//			if(quantity==0){
//				quantity=this.getAdultQuantity();
//			}
		}
		return quantity;
	}
	/**
	 * 实际儿童取票数
	 * @return
	 */
	public Long getRealChildQuantity() {
		Long quantity=0L;
		if(this.getIsPass() && !this.isCanceled()){
			quantity=this.getSingleChildQuantity()+this.getMultipleChildQuantity();
//			if(quantity==0){
//				quantity=this.getChildQuantity();
//			}
		}
		return quantity;
	}
	/**
	 * 得到实际张数
	 * 
	 * @author: ranlongfei 2013-2-28 下午1:44:08
	 * @return
	 */
	public Long getRealQuantity() {
		return (getRealAdultQuantity() + getRealChildQuantity()) / ((getAdultQuantity() + getChildQuantity())/getQuantity());
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	
	public String getSettlementPriceYuan(){
		if (settlementPrice==null || settlementPrice <= 0L) {
			return "0";
		} else {
			return String.valueOf(PriceUtil.convertToYuan(settlementPrice));
		}
	}

	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}

	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}
	
	public String getTotalSettlementPriceYuan(){
		if (totalSettlementPrice==null || totalSettlementPrice <= 0L) {
			return "0";
		} else {
			return String.valueOf(PriceUtil.convertToYuan(totalSettlementPrice));
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//订单号
		sb.append(super.getOrderId() + "\t");
		//订单子号
		sb.append(super.getOrderItemMetaId() + "\t");
		//订单状态
		sb.append(this.getZhOrderStatus() + "\t");
		//游玩时间
		sb.append(this.getStrDeadlineTime() + "\t");
		//取票人姓名
		sb.append(this.getContactName() + "\t");
		//取票人手机号
		sb.append(this.getContactMobile() + "\t");
		//支付方式
		sb.append(this.getZhPaymentTarget() + "\t");
		//产品名称
		sb.append(super.getMetaProductName() + "\t");
		//销售单价
		if ("TOLVMAMA".equals(this.getPaymentTarget())) {
			sb.append("\t");
		} else {
			sb.append(this.getSellPriceYuan() + "\t");
		}
		//结算单价
		sb.append(this.getSettlementPriceYuan() + "\t");
		//结算总价
		sb.append(this.getTotalSettlementPriceYuan() + "\t");
		//订购票数
		sb.append(this.getQuantity() + "\t");
		//实际取票数
		if (this.getIsPass()) {
			if ("TOLVMAMA".equals(this.getPaymentTarget())) {
				sb.append(this.getQuantity() + "\t");
			} else {
				sb.append(this.getRealAdultQuantity()
						+ this.getRealChildQuantity() + "\t");
			}

		} else {
			sb.append("\t");
		}
		//游玩状态
		sb.append(this.getZhPassStatus() + "\t");
		//预计游玩人数
		sb.append(this.getAdultQuantity() + this.getChildQuantity() + "\t");
		//成人
		sb.append(this.getAdultQuantity() + "\t");
		//儿童
		sb.append(this.getChildQuantity() + "\t");
		//实际游玩人数
		sb.append(this.getRealAdultQuantity() + this.getRealChildQuantity() + "\t");
		//第一位游客身份证
		if (null == this.getContactCertNo()) {
			sb.append("\t");
		} else {
			sb.append("'" + this.getContactCertNo() + "\t");
		}
		return sb.toString();
	}
}
