package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class MobileProductTitle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long productId;
	public String productName;
	
	public Float marketPriceYuan;
	public Float sellPriceYuan;
	public String smallImage;
	public String cmtAvgScore;
	public boolean clientOnly;
	
	/******** V3.1 add *******/
	/**
	 * 点评数据 
	 */
	private Long cmtNum=0l;
	
	/**
	 * 主题 
	 */
	private String subProductType;
	
	/**
	 * 游玩天数
	 */
	public String visitDay; 

	/**
	 * 点评返现金额，单位分
	 */
	private Long maxCashRefund=0l;

	/**
	 * 是否支持多定多惠，早定早惠
	 */
	private boolean hasBusinessCoupon;
	
	/************** V5.0 标签*******************/
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
	

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Float getSellPriceYuan() {
		return sellPriceYuan;
	}
	public void setSellPriceYuan(Float sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getCmtAvgScore() {
		return cmtAvgScore;
	}
	public void setCmtAvgScore(String cmtAvgScore) {
		this.cmtAvgScore = cmtAvgScore;
	}
	public Float getMarketPriceYuan() {
		return marketPriceYuan;
	}
	public void setMarketPriceYuan(Float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}

	public String getAbsoluteRecommendImageUrl() {
		return StringUtils.isEmpty(this.getSmallImage()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getSmallImage();
	}
	public boolean isClientOnly() {
		return clientOnly;
	}
	public void setClientOnly(boolean clientOnly) {
		this.clientOnly = clientOnly;
	}
	public String getVisitDay() {
		return visitDay;
	}
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}
	public Long getCmtNum() {
		return cmtNum;
	}
	public void setCmtNum(Long cmtNum) {
		this.cmtNum = cmtNum;
	}
	
	public String getSubProductType() {
		return null==subProductType?"":subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	
	public String getZhSubProductType() {
		if(StringUtils.isEmpty(subProductType)) {
			return "";
		}
		return Constant.SUB_PRODUCT_TYPE.getCnName(subProductType);
	}
	
	public Long getMaxCashRefund() {
		return maxCashRefund;
	}
	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}
	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}
	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}
	
	 /**
	  * 多返金额   
	  * @return
	  */
	public Float getCashRefundY() {
		if(!isCashRefund()) {
			return 0f;
		}
		return RefundUtils.getRouteMobileRefundYuan(maxCashRefund);
		//return PriceUtil.convertToYuan(getMaxCashRefund()+ClutterConstant.getMobileRouteRefund());
	}
	
	
	/**
	 * 手机预订多返金额 
	 * @return
	 */
	public float getMobileCashRefund() {
		if(!isCashRefund()) {
			return 0f;
		}
		return RefundUtils.getRouteMoreMobileRefundYuan(maxCashRefund);
		//return ClutterConstant.getMobileRouteRefundYuan();
	}
	
	
	/**
	 * 是否支持返现 
	 * @return
	 */
	public boolean isCashRefund() {
		Long clientCashRefund = this.maxCashRefund;
		/*Long clientCashRefund = this.maxCashRefund + ClutterConstant.getMobileRouteRefund();*/
		// 是否支持返现 
		return clientCashRefund!=0;
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
	
	public boolean isCanDeduction() {
		return canDeduction;
	}
	public void setCanDeduction(boolean canDeduction) {
		this.canDeduction = canDeduction;
	}
}
