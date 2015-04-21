package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.annotation.JSONField;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 团购
 * 
 */
public class MobileGroupOn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String managerRecommend;
	private Long productId;
	private String productName;
	//成团人数
	private String minGroupSize;
	private String offlineTime;
	private Float marketPriceYuan;
	private Float sellPriceYuan;
	private String smallImage; // 小图片 
	private String largeImage; // 大图
	//团购人数
	private String orderCount;
	
	private String productType;
	
	private String subProductType;
	
	private String branchId;
	
	private List<String> imageList;

    // 剩余时间 
	private String remainTime;

	// v3.1
	/**
	 * 优惠信息 
	 */
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
	 * 是否需要签证 
	 */
	private boolean visa;
	
	/**
	 * 优惠信息 
	 */
	private String payTarget;// 支付方式
	private Float avgScore;// 评价分数
	private  String zhPaymentTarget;
    
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
	

	/**
	 * 抵扣信息  
	 */
	private String deductionDesc="";
	/**带内容的Content**/
	private Set<MobileGrouponContent> viewContents = new TreeSet<MobileGrouponContent>();
	/**带tag的Content**/
	private Set<MobileGrouponContent> tagContents = new TreeSet<MobileGrouponContent>();
	/**行程说明**/
	private List<ViewJourney> viewJourneys = new ArrayList<ViewJourney>();
	/**秒杀状态**/
	private String viewJourneyUrl;
	/**秒杀状态**/
	private String seckillStatus;
	/**秒杀开始时间**/
	private String seckillStartTime;
	/**秒杀结束时间**/
	private String seckillEndTime;
	/**距离秒杀开始时间时长**/
	private long seckillMillis;

	public void setFavourableDesc(String favourableDesc) {
		this.favourableDesc = favourableDesc;
	}
	/**
	 * 优惠信息 
	 */
	private String favourableDesc=""; 

	/**
	 * 优惠链接url . 
	 * @return string
	 */
	public String getPreferentUrl() {
		return "/html5/help.htm?pageName=preferent&helpTags="+preferentialTags+"&cashRefundYuan="+this.getCashRefundY()+"&mobileCashRefundYuan="+this.getMobileCashRefund();
	}
	public String getDiscount(){
		if(null!=sellPriceYuan&&null!=marketPriceYuan){
			return PriceUtil.formatDecimal((sellPriceYuan/marketPriceYuan)*10);
		}else{
			return PriceUtil.formatDecimal(0);
		}
	}
	
	public Float getSaved(){
		if(null!=sellPriceYuan&&null!=marketPriceYuan){
			return marketPriceYuan-sellPriceYuan;
		}else{
			return 0f;
		}
	}
	
	public String getManagerRecommend() {
		return managerRecommend;
	}
	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}
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
	public String getMinGroupSize() {
		return minGroupSize;
	}
	public void setMinGroupSize(String minGroupSize) {
		this.minGroupSize = minGroupSize;
	}
	public String getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
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
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	
	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	/**
	 * 剩余时间. 
	 * @return
	 */
	public String getRemainTime(){
		return this.remainTime;
	}
	
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
	
	public String getMoreDetailUrl() {
		return "/html5/index.htm?productId="+productId;
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
		return maxCashRefund;
	}
	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}
	public boolean isVisa() {
		return visa;
	}
	public void setVisa(boolean visa) {
		this.visa = visa;
	}
	
	public String getPayTarget() {
		return payTarget;
	}
	public void setPayTarget(String payTarget) {
		this.payTarget = payTarget;
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
	
	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}
	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}
	
	 /** 是否支持返现 
	  * @return
	  */
	public boolean isCashRefund() {
		// 是否支持返现 
		return this.maxCashRefund!=0;
	}
	
	 /**
	  * @return
	  */
	public Float getCashRefundY() {
		if(!isCashRefund()) {
			return 0f;
		} 
		return RefundUtils.getMobileRefundYuan(maxCashRefund, productType);
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
	
	public String getShareContent(){
		String content = "";
		try {
			if(Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
				content =String.format("我在@驴妈妈旅游网 上看到“%s”这个景点不错，驴妈妈评分4.5分，门票%s元起。 ", 
						com.lvmama.clutter.utils.ClientUtils.subProductName(this.getProductName()),this.getSellPriceYuan());
			} else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
				content =String.format("我在@驴妈妈旅游网 上看中了旅游线路 “%s”，%s折，仅售%s 。",
						com.lvmama.clutter.utils.ClientUtils.subProductName(this.getProductName()),
						PriceUtil.formatDecimal(this.getSellPriceYuan()/this.getMarketPriceYuan()*10),this.getSellPriceYuan());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return content;
	}
	
	public String getShareContentTitle(){
		if(this.getMarketPriceYuan()==null || null == this.getSellPriceYuan()){
			return null;
		}
			
		String contentTitle =String.format(ClutterConstant.SHARE_CONTENT,this.getProductName(),PriceUtil.formatDecimal(this.getSellPriceYuan()/this.getMarketPriceYuan()*10));
		return contentTitle;
	}
	
	
	public String getWapUrl(){
		return "http://m.lvmama.com/clutter/groupbuy/"+getProductId()+"/"+getProductType();
	}
	public Float getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
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
	

	public String getDeductionDesc() {
		return deductionDesc;
	}
	public void setDeductionDesc(String deductionDesc) {
		this.deductionDesc = deductionDesc;
	}
	public String getFavourableDesc() {
		return favourableDesc;
	}
	public Set<MobileGrouponContent> getViewContents() {
		return viewContents;
	}
	public void setViewContents(Set<MobileGrouponContent> viewContents) {
		this.viewContents = viewContents;
	}
	public Set<MobileGrouponContent> getTagContents() {
		return tagContents;
	}
	public void setTagContents(Set<MobileGrouponContent> tagContents) {
		this.tagContents = tagContents;
	}
	public List<ViewJourney> getViewJourneys() {
		return viewJourneys;
	}
	public void setViewJourneys(List<ViewJourney> viewJourneys) {
		this.viewJourneys = viewJourneys;
	}
	public String getViewJourneyUrl() {
		return viewJourneyUrl;
	}
	public void setViewJourneyUrl(String viewJourneyUrl) {
		this.viewJourneyUrl = viewJourneyUrl;
	}
	public String getSeckillStatus() {
		return seckillStatus;
	}
	public void setSeckillStatus(String seckillStatus) {
		this.seckillStatus = seckillStatus;
	}
	public String getSeckillStartTime() {
		return seckillStartTime;
	}
	public void setSeckillStartTime(String seckillStartTime) {
		this.seckillStartTime = seckillStartTime;
	}
	public String getSeckillEndTime() {
		return seckillEndTime;
	}
	public void setSeckillEndTime(String seckillEndTime) {
		this.seckillEndTime = seckillEndTime;
	}
	public long getSeckillMillis() {
		return seckillMillis;
	}
	public void setSeckillMillis(long seckillMillis) {
		this.seckillMillis = seckillMillis;
	}
	
}
