package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ReplaceEnter;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;

/**
 * 移动端专用 - 产品模型
 * @author 
 *
 */
public class MobileProduct implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long productId; // 产品id
	private String productName; // 产品名称
	private Long marketPrice; // 市场价格
	private Long sellPrice; // 驴妈妈销售价格
	private Float avgScore;// 评价分数
	private int cmtNum;// 评论总数
	private boolean hasIn = false;// 是否收藏，默认false  
	private String managerRecommend;//产品经理推荐
	private String smallImage; // 小图片
	private Long branchId; // 分支id
	/**
	 *  产品类型：TRAFFIC("大交通"), TICKET("门票"), 
	 *  HOTEL("酒店"), HOTEL_FOREIGN("境外酒店"), 
	 *  ROUTE("线路"), OTHER("其它");
	 */
	private String productType; 
	private List<String> imageList; // 图片列表 
	private List<PlaceCmtScoreVO> placeCmtScoreList;
	private String announcement;
	
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
	 * 是否支持多定多惠，早定早惠
	 */
	private boolean hasBusinessCoupon;
	
	/**
	 * 主题 
	 */
	private String subProductType;
	
	
	
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
	public List<PlaceCmtScoreVO> getPlaceCmtScoreList() {
		return placeCmtScoreList;
	}
	public void setPlaceCmtScoreList(List<PlaceCmtScoreVO> placeCmtScoreList) {
		this.placeCmtScoreList = placeCmtScoreList;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public String getProductName() {
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
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getManagerRecommend() {
		if(StringUtils.isEmpty(managerRecommend))return "";
		return "★" + StringUtil.filterOutHTMLTags(managerRecommend).replaceAll("\n", "\n★");
	}
	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}
	
	public String getConverManagerRecommend() {
		if(StringUtils.isEmpty(getManagerRecommend()))return "";
		return ReplaceEnter.replaceEnterRn(getManagerRecommend(), "");
	}

	public String getMoreDetailUrl() {
		return "/html5/index.htm?productId="+productId;
	}
	
	public Long getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}
	public int getCmtNum() {
		return cmtNum;
	}
	public void setCmtNum(int cmtNum) {
		this.cmtNum = cmtNum;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAbsoluteSmallImageUrl() {
		return StringUtils.isEmpty(this.getSmallImage()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getSmallImage();
	}
	
	public float getSellPriceYuan() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
	}

	public float getMarketPriceYuan() {
		return marketPrice == null ? 0 : PriceUtil.convertToYuan(marketPrice);
	}
	
	public boolean isHasIn() {
		return hasIn;
	}
	public void setHasIn(boolean hasIn) {
		this.hasIn = hasIn;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Float getAvgScore() {
		return avgScore;
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


	public Long getMaxCashRefund() {
		return null==maxCashRefund?0l:maxCashRefund;
	}


	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
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

	
	public String getPreferentialTags() {
		return preferentialTags;
	}
	public void setPreferentialTags(String preferentialTags) {
		this.preferentialTags = preferentialTags;
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

	
	public String getZhSubProductType() {
		if(StringUtils.isEmpty(subProductType)) {
			return "";
		}
		return Constant.SUB_PRODUCT_TYPE.getCnName(subProductType);
	}
	
	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
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
	public void setFavourableDesc(String favourableDesc) {
		this.favourableDesc = favourableDesc;
	}
	
}
