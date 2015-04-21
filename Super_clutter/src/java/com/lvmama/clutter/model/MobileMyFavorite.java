package com.lvmama.clutter.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 驴途 移动端 from3.0 - 我的收藏
 * @author qinzubo
 *
 */
public class MobileMyFavorite {

		private Long id;

		/**
		 * 被收藏对象的id .
		 */
	    private Long objectId; 

	    /**
		 * 被收藏对象的类型 .
		 * PLACE("标的"), 
		 * PRODUCT("产品"), 
		 * GUIDE("攻略"); 
		 */
	    private String objectType;

	    /**
		 * 收藏时间 .
		 */
	    private String createdTime;

	    /**
		 * 被收藏对象的图片链接地址 .
		 */
	    private String objectImageUrl;

	    /**
		 * 被收藏对象的名称 .
		 */
	    private String objectName;
		private Float marketPriceYuan; // 市场价格
		private Float sellPriceYuan; // 驴妈妈销售价格
		private Float avgScore;// 评价分数
		private String address;// 地址
		private boolean todayOrderAble = false; // 是否今日可定
		
		/********* V3.1 add**********/
		/**
		 * 对于place   主题 
		 * 对于router  产品类别
		 */
		private String subject;
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
		
		/**
		 * 评论数. 
		 */
		private Long cmtNum;

		
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
		
		public boolean isTodayOrderAble() {
			return todayOrderAble;
		}

		public void setTodayOrderAble(boolean todayOrderAble) {
			this.todayOrderAble = todayOrderAble;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getObjectId() {
	        return objectId;
	    }

	    public void setObjectId(Long objectId) {
	        this.objectId = objectId;
	    }

	    public String getObjectType() {
	        return objectType;
	    }

	    public void setObjectType(String objectType) {
	        this.objectType = objectType;
	    }

	    public String getCreatedTime() {
	        return createdTime;
	    }

	    public void setCreatedTime(String createdTime) {
	        this.createdTime = createdTime;
	    }

	    public String getObjectImageUrl() {
	        return null == objectImageUrl?"":objectImageUrl;
	    }

	    public void setObjectImageUrl(String objectImageUrl) {
	        this.objectImageUrl = objectImageUrl == null ? null : objectImageUrl.trim();
	    }

	    public String getObjectName() {
	        return objectName;
	    }

	    public void setObjectName(String objectName) {
	        this.objectName = objectName == null ? null : objectName.trim();
	    }

		public Float getAvgScore() {
			return avgScore;
		}

		public void setAvgScore(Float avgScore) {
			this.avgScore = avgScore;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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
		
		
	    public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
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
		
		
		public String getZhSubProductType() {
			if(StringUtils.isEmpty(subject)) {
				return "";
			}
			return Constant.SUB_PRODUCT_TYPE.getCnName(subject);
		}
		
		/**
		 * 是否支持返现 
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
			String productType = null;
			if("PLACE".equals(objectType)) {
				productType = Constant.PRODUCT_TYPE.TICKET.getCode();
			}else if("PRODUCT".equals(objectType)){// 线路 
				productType = Constant.PRODUCT_TYPE.ROUTE.getCode();
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
			
			String productType = null;
			if("PLACE".equals(objectType)) {
				productType = Constant.PRODUCT_TYPE.TICKET.getCode();
			}else if("PRODUCT".equals(objectType)){// 线路 
				productType = Constant.PRODUCT_TYPE.ROUTE.getCode();
			}
			return RefundUtils.getMoreMobileRefundYuan(maxCashRefund, productType);
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
