package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 客户端v3.0 模型
 * @author dengcheng
 *
 */
public class MobileBranch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8363932428304688951L;
    private Long productId;// 产品id 
	private String shortName;

	private String description;
	private boolean canOrderToday;
	private boolean canOrderTodayCurrentTime;
	private Float marketPriceYuan;
	private Float sellPriceYuan;
	private Long minimum;
	private Long maximum;
	private Long branchId;
	private String saleNumType;
	private boolean additional;
	private Long adultQuantity;
	private Long childQuantity;
	private String todayOrderTips;
	private String porudctSubProductType;
	private Date todayOrderLastOrderTime;
	private Date newTodayOrderLastTime;//新最晚可定时间
	private boolean orderTodayAble;
	/**
	 * 是否支持分享微信
	 */
	private boolean shareWeixin=true;
	/**
	 * 是否已分享过了
	 */
	private boolean hasShareToWeixin=false;
	/**
	 * 附加产品 用 
	 */
	
	private String fullName; // 全名
	
	private String branchType;
	
	private String payToLvmama;
	
	private String largeImage;
	
	private String icon;
	

	// v3.1
	/**
	 * 优惠信息 
	 */
	private String payTarget;// 支付方式

	private  String zhPaymentTarget;
	
	private String preferentialInfo="";

	/**
	 * 优惠信息对应tag标签
	 * 奖金抵用1 早订早惠2 多订多惠3积分抵扣4
	 */
	private String preferentialTags="";
	
	/**
	 * 点评返现金额，单位分
	 */
	private Long maxCashRefund=0l;
	
	/**
	 * 产品类别
	 */
	private String subProductType;
	
	/**
	 * 产品类型
	 */
	private String productType;
	
	/**
	 * 是否支持多定多惠，早定早惠
	 */
	private boolean hasBusinessCoupon;
	

	/************V5.0***************/
	/**
	 * 活动标签 
	 */
	private  List<MobileProdTag> tagList ;
	
	/**
	 * 是否手机独享  product_search_info表中channel只为CLIENT时；
	 */
	private  boolean mobileAlone;
	
	/**
	 * 是否支持奖金抵扣或者积分抵用  true：是 ；false：否 
	 */
	private boolean canDeduction;
	

	/**
	 * 抵扣信息  
	 */
	private String deductionDesc="";

	
	/**
	 * 优惠信息 
	 */
	private String favourableDesc=""; 

	/**
	 * 优惠链接url . 
	 * @return string
	 */
	public String getPreferentUrl() {
		return "/html5/help.htm?pageName=preferent&helpTags="+preferentialTags+"&mobileCashRefundYuan="+getMobileCashRefund();//+"&cashRefundYuan="+getCashRefundY()
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isCanOrderToday() {
		return canOrderToday;
	}
	public void setCanOrderToday(boolean canOrderToday) {
		this.canOrderToday = canOrderToday;
	}
	public boolean isCanOrderTodayCurrentTime() {
		return canOrderTodayCurrentTime;
	}
	public void setCanOrderTodayCurrentTime(boolean canOrderTodayCurrentTime) {
		this.canOrderTodayCurrentTime = canOrderTodayCurrentTime;
	}
	public Float getMarketPriceYuan() {
		return marketPriceYuan;
	}
	public void setMarketPriceYuan(Float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}
	public Float getSellPriceYuan() {
		return sellPriceYuan;
	}
	public void setSellPriceYuan(Float sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}
	public Long getMinimum() {
		return minimum;
	}
	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}
	public Long getMaximum() {
		return maximum;
	}
	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}
	public boolean isAdditional() {
		return additional;
	}
	public void setAdditional(boolean additional) {
		this.additional = additional;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getAdultQuantity() {
		return adultQuantity;
	}
	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}
	public Long getChildQuantity() {
		return childQuantity;
	}
	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}
	public String getSaleNumType() {
		return saleNumType;
	}
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}
	public String getTodayOrderTips() {
		return todayOrderTips;
	}
	public void setTodayOrderTips(String todayOrderTips) {
		this.todayOrderTips = todayOrderTips;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public String getPorudctSubProductType() {
		return porudctSubProductType;
	}
	public void setPorudctSubProductType(String porudctSubProductType) {
		this.porudctSubProductType = porudctSubProductType;
	}

	public Date getTodayOrderLastOrderTime() {
		return todayOrderLastOrderTime;
	}
	public void setTodayOrderLastOrderTime(Date todayOrderLastOrderTime) {
		this.todayOrderLastOrderTime = todayOrderLastOrderTime;
	}

	
	public String getPayToLvmama() {
		return payToLvmama;
	}
	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}
	
	public String getZhPayToLvmama(){
		if("true".equals(payToLvmama)){
			return ClutterConstant.MOBILE_PAY_TARGET_LVMM;
		} else {
			return ClutterConstant.MOBILE_PAY_TARGET_SUPPLIER;
		}
	}
	
	/**
	 * 手机预订多返金额 
	 * @return
	 */
	public float getMobileCashRefund() {
		if(!isCashRefund()) {
			return 0f;
		}
		return RefundUtils.getMoreMobileRefundYuan(maxCashRefund, productType);
	}
	
	public String getPreferentialInfo() {
		return preferentialInfo;
	}

	public void setPreferentialInfo(String preferentialInfo) {
		this.preferentialInfo = preferentialInfo;
	}

	public String getPreferentialTags() {
		return preferentialTags;
	}

	public void setPreferentialTags(String preferentialTags) {
		this.preferentialTags = preferentialTags;
	}

	public Long getMaxCashRefund() {
		return null==maxCashRefund?0l:maxCashRefund;
	}


	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}

	public String getZhPaymentTarget() {
		if(Constant.PAYMENT_TARGET.TOLVMAMA.getCode().equals(payTarget)) {
			return ClutterConstant.MOBILE_PAY_TARGET_LVMM;
		} else {
			return ClutterConstant.MOBILE_PAY_TARGET_SUPPLIER;
		}
	}

	public void setZhPaymentTarget(String zhPaymentTarget) {
		this.zhPaymentTarget = zhPaymentTarget;
	}

	public String getPayTarget() {
		return payTarget;
	}

	public void setPayTarget(String payTarget) {
		this.payTarget = payTarget;
	}
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	 /**
	  * 总返现金额
	  * @return
	  */
	public Float getCashRefundY() {
		if(!isCashRefund()) {
			return 0f;
		} 
		return RefundUtils.getMobileRefundYuan(maxCashRefund, productType);
	}

	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}

	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}

	/**
	 * 是否支持返现 
	 * @return
	 */
	public boolean isCashRefund() {
		// 是否支持返现 
		return this.maxCashRefund!=0;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public boolean isCanDeduction() {
		return canDeduction;
	}

	public void setCanDeduction(boolean canDeduction) {
		this.canDeduction = canDeduction;
	}

	public List<MobileProdTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<MobileProdTag> tagList) {
		this.tagList = tagList;
	}

	public boolean isMobileAlone() {
		return mobileAlone;
	}

	public void setMobileAlone(boolean mobileAlone) {
		this.mobileAlone = mobileAlone;
	}
	public String getDeductionDesc() {
		return deductionDesc;
	}

	public void setDeductionDesc(String deductionDesc) {
		this.deductionDesc = deductionDesc;
	}

	public String getFavourableDesc() {
		return favourableDesc;
	}

	public void setFavourableDesc(String favourableDesc) {
		this.favourableDesc = favourableDesc;
	}

	public boolean isShareWeixin() {
		return shareWeixin;
	}

	public void setShareWeixin(boolean shareWeixin) {
		this.shareWeixin = shareWeixin;
	}

	public boolean isHasShareToWeixin() {
		return hasShareToWeixin;
	}

	public void setHasShareToWeixin(boolean hasShareToWeixin) {
		this.hasShareToWeixin = hasShareToWeixin;
	}

	public Date getNewTodayOrderLastTime() {
		return newTodayOrderLastTime;
	}

	public void setNewTodayOrderLastTime(Date newTodayOrderLastTime) {
		this.newTodayOrderLastTime = newTodayOrderLastTime;
	}

	public boolean isOrderTodayAble() {
		return orderTodayAble;
	}

	public void setOrderTodayAble(boolean orderTodayAble) {
		this.orderTodayAble = orderTodayAble;
	}

}
