package com.lvmama.comm.bee.po.prod;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.ReplaceEnter;
import com.lvmama.comm.vo.BonusConfigData;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProdProduct implements Serializable {
	private static final long serialVersionUID = 73743886319838963L;
	
	private Long productId;
	private String bizcode;
	private String productName;
	private String description;
	private Date createTime;
	private String additional = "false";
	private Date onlineTime;
	private Date offlineTime;
	private String productType;
	private String smsContent;
	private String wrapPage;
	private String payToLvmama = "true";
	private String payToSupplier = "false";
	private Long stock;
	private Long marketPrice;
	private Long sellPrice;

	private Long orgId;
	private Long managerId;
	private Long placeId;//临时字段，景区ID
	private String placeName;
	private String realName;
	private boolean isChecked;
	private String departmentName;
	private String departmentId;
	//一句话推荐
	private String recommendInfoFirst;
	private String recommendInfoSecond;
	private String recommendInfoThird;
	/**
	 * 已购买数量
	 */
	private Long orderCount = 0L;
	/**
	 * 产品经理推荐
	 */
	private String managerRecommend;
	/**
	 * 团购产品图片id
	 */
	private Long pageId;
	/**
	 * 计价单位
	 */
	private String priceUnit;
	/**
	 * 子类型.
	 */
	private String subProductType;

	/** 产品默认图片 */
	private String smallImage;
    /**
     * 审核状态
     */
    private String auditingStatus;
	/**
	 * 是否需要物流
	 */
	private String physical = "false";

	/**
	 */
	private String sendSms = "true";

	/**
	 */
	private Date nearDate;

	/**
	 * 
	 */
	private String valid;
	
	/**
	 */
	private String canPayByBonus="Y";

	/**
	 * 是否可以返现Y,N
	 */
	private String isRefundable="Y";
	
	/**
	 * 是否为境外
	 */
	private String isForegin="N";
	
	/**
	 */
	private String regionName;
	/**
	 * 分公司名称
	 */
	private String filialeName;
	/**
	 * 游客必填信息
	 * 
	 * @return
	 */
	private String travellerInfoOptions;

	private Long groupMin = 0L;
	private String saleNumType;// 销售数量类型,只在读取关联销售产品时使用
	
	/**
	 */
	private Long payDeposit=0L;
	/**
	 * 产品是否可售
	 */
	private String onLine;

	private String couponAble="true";
	/**
	 */
	private String couponActivity="true";
	
	private Place toPlace;
	
	private List<Place> PlaceList;

	private List<ProdProductBranch> prodBranchList;
	/**
	 */
	private List<ProdJourneyProduct> prodJourneyProductList;
	
	/**
	 */
	private Long maxCashRefund;
	
	/**
	 */
	private String isManualBonus = "N";
	
	/**
	 */
	private Integer showSaleDays=180;
	
	/**
	 * */
	private boolean interiorExist;
	
	private String hasSensitiveWord;
	
	/**
	 * @deprecated 周年庆后应该删除
	 */
	private String isAnniveraryProduct;

    /**
     * 	保险适用行程天数下限
     */
    private Long applicableTravelDaysLimit;
    /**
     * 	保险适用行程天数上限
     */
    private Long applicableTravelDaysCaps;
    /**
     * 	保险适用产品子类型
     */
    private String applicableSubProductType;

    private List<String> applicableSubProductTypeList;
    
    /**********************************/
    
    /**
     * 返现比例
     */
    private String bounsScale;
    
    /**
     * 投放原因
     */
    private String bounsReason;
    
    /**
     * 投放时长-开始
     */
    private Date bounsStart;
    
    /**
     * 投放时长-结束
     */
    private Date bounsEnd;
    
    /**
     * 投放金额
     */
    private Long bounsLimit;
    
    /**
     * 返现比是否超过设置值
     */
    private String isMoreThanConf = "N";
    
    /**********************************/
    
	/**
	 * @deprecated 周年庆后应该删除
	 * @return
	 */
	public String getIsAnniveraryProduct() {
		return isAnniveraryProduct;
	}
	/**
	 * @deprecated 周年庆后应该删除
	 * @param isAnniveraryProduct
	 */
	public void setIsAnniveraryProduct(final String isAnniveraryProduct) {
		this.isAnniveraryProduct = isAnniveraryProduct;
	}
	/**
	 * @deprecated 周年庆后应该删除
	 * @return 是否是周年庆产品
	 */
	public boolean isAnniveraryProduct() {
		return "Y".equalsIgnoreCase(isAnniveraryProduct);
	}

	/**
	 * */
	private String isAperiodic = "false";
	
	/**
	 * */
	private Long aheadBookingDays;
	/**
	 * 是否使用预授权支付("Y", "N")
	 */
	private String prePaymentAble;
	
	
	private String productContactInfoOptions;
	
	/**
	 * 产品是否可以发起随时退
	 */
	private String freeBackable = "N";
	
	/**
	 * @return the maxCashRefund
	 */
	public Long getMaxCashRefund() {
		return maxCashRefund;
	}

	/**
	 * @param maxCashRefund the maxCashRefund to set
	 */
	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}

	public String getOnLine() {
		return onLine;
	}

	public String getCanPayByBonus() {
		return canPayByBonus;
	}

	public void setCanPayByBonus(String canPayByBonus) {
		this.canPayByBonus = canPayByBonus;
	}

	public String getIsRefundable() {
		return isRefundable;
	}

	public void setIsRefundable(String isRefundable) {
		this.isRefundable = isRefundable;
	}

	public String getIsForegin() {
		return isForegin;
	}

	public void setIsForegin(String isForegin) {
		this.isForegin = isForegin;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getStrOnLine() {
		if (onLine == null) {
			onLine = "";
		}
		if ("true".equals(this.onLine)) {
			return "上线";
		} else {
			return "下线";
		}
	}

	public String getButtonOnLine() {
		if (onLine == null) {
			onLine = "";
		}
		if ("true".equals(this.onLine)) {
			return "下线";
		} else {
			return "上线";

		}
	}
	
	public String getOnlineTimeStr() {
		if (null == onlineTime) {
			return "";
		}
		return DateUtil.formatDate(onlineTime, "yyyy-MM-dd");
	}
	
	public String getOfflineTimeStr() {
		if (null == offlineTime) {
			return "";
		}
		return DateUtil.formatDate(offlineTime, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @author lipengcheng
	 * @return
	 */
	public String getViewPage() {
		return Constant.getInstance().getProductViewPageUrl() + "?id="
				+ this.getProductId();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRelatePlace() {
		return false;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getProductName() {
		return productName;
	}

    public String getFirstTitle (){
        if(productName.indexOf("*")>0){
            String firstTitle = productName.substring(0,productName.indexOf("*"));
            return firstTitle;
        }
        return productName;
    }
    
    public String getNextTitle (){
        if(productName.indexOf("*")>0){
            String nextTitle = productName.substring(productName.indexOf("*")+1,productName.length());
            return nextTitle;
        }
        return "";
    }
    
	public void setProductName(String productName) {
		this.productName = productName;
	}
 
	public String getDescription() {
		return description;
	}

	public String getConvertDescription() {
		return ReplaceEnter.replaceEnterRn(description, "");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isAdditional() {
		return Boolean.parseBoolean(additional);
	}

	public String getAdditional() {
		return additional == null ? "false" : additional;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}
 
	public String getViewOnlineTime() {
		return onlineTime == null ? "" : DateUtil.getFormatDate(onlineTime,
				"yyyy-MM-dd");
	}
	
	public String getBounsStartTime() {
		return bounsStart == null ? "" : DateUtil.getFormatDate(bounsStart,
				"yyyy-MM-dd");
	}
	
	public String getBounsEndTime() {
		return bounsEnd == null ? "" : DateUtil.getFormatDate(bounsEnd,
				"yyyy-MM-dd");
	}
	
	public String getBounsLimitYuan() {
		if (bounsLimit == null) {
			return null;
		}
		return (bounsLimit * 1.0 / 100) + "";
	}
	
	public void setBounsLimitYuan(Float arg) {
		if (arg == null) {
			return;
		}
		setBounsLimit(PriceUtil.convertToFen(arg));
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getViewOfflineTime() {
		return offlineTime == null ? "" : DateUtil.getFormatDate(offlineTime,
				"yyyy-MM-dd");
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}
	/**
	 * @return
	 * @author:nixianjun 2013-6-9
	 */
    public long getOfflineTimeLength(){
    	long offlineTime = 0L;
		if (this.getOfflineTime() != null) {
			offlineTime = this.getOfflineTime().getTime();
		} else {
			offlineTime = -1;
		}
		return offlineTime - System.currentTimeMillis();
    }
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
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
	
	public int getIntSellPrice(){
		if(this.sellPrice==null)
			return 0;
		return ((Long)(this.sellPrice/100)).intValue();
	}

	public int getIntMarketPrice(){
        if(this.marketPrice==null)
            return 0;
        return ((Long)(this.marketPrice/100)).intValue();
    }
	
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public float getSellPriceFloat() {
		if (this.sellPrice != null)
			return PriceUtil.convertToYuan(this.sellPrice);
		return 0;
	}

	public float getSellPriceYuan() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
	}

	public float getMarketPriceYuan() {
		return marketPrice == null ? 0 : PriceUtil.convertToYuan(marketPrice);
	}
	

	public float getDiscount() {
		if (this.getMarketPrice() > 0) {
			float dis = (float) this.sellPrice / (float) this.marketPrice;
			DecimalFormat df = new DecimalFormat("#.#");
			return new Float(df.format(dis * 10));
		} else {
			return 0;
		}
	}

	public String getWrapPage() {
		return wrapPage;
	}

	public void setWrapPage(String wrapPage) {
		this.wrapPage = wrapPage;
	}

	public boolean isPaymentToLvmama() {
		return "true".equalsIgnoreCase(payToLvmama);
	}

	public String getPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}

	public boolean isPaymentToSupplier() {
		return "true".equalsIgnoreCase(payToSupplier);
	}

	public String getPayToSupplier() {
		return payToSupplier;
	}

	public void setPayToSupplier(String payToSupplier) {
		this.payToSupplier = payToSupplier;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(final String subProductType) {
		this.subProductType = subProductType;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSellable() {
		if (onlineTime != null && offlineTime != null) {
			Date now = new Date();
			if (now.after(onlineTime) && now.before(offlineTime)
					&& this.sellPrice != null) {
				return true;
			}
		}
		return false;
	}

	public boolean isDisabled() {
		if ("N".equalsIgnoreCase(valid)) {
			return true;
		}
		return false;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ProdProduct) {
			ProdProduct cc = (ProdProduct) obj;
			if (productId == null) {
				return cc.getProductId() == null;
			} else {
				return productId.equals(cc.getProductId());
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (productId != null)
			return productId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (productId != null)
			return "ProdProduct" + productId.toString();
		else
			return "ProdProduct_null";
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Date getNearDate() {
		return nearDate;
	}

	/**
	 * 
	 * @return
	 */
	public boolean canBindingCoupon() {
		return isPaymentToLvmama();
	}

	public void setNearDate(Date nearDate) {
		this.nearDate = nearDate;
	}

	public String getAbsoluteSmallImageUrl() {
		return StringUtils.isEmpty(this.smallImage) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.smallImage;
	}

	public String getZhSubProductType() {
		//return Constant.getInstance().getChineseStr(Constant.CODE_TYPE.SUB_PRODUCT_TYPE, subProductType);
		return ProductUtil.getSubProdTypeByCode(this.subProductType).getName();
	}

	public String getFilialeName() {
		return filialeName;
	}
	
	/**
	 * 只显示地区
	 * @return
	 */
	public String getZhFilialeName(){
		String f=Constant.FILIALE_NAME.getCnName(filialeName);
		if(StringUtils.isNotEmpty(f)){
			f=f.replaceAll("总部|分部", "");
		}
		return f;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public void setTravellerInfoOptions(String travellerInfoOptions) {
		this.travellerInfoOptions = travellerInfoOptions;
	}

	/**
	 * 是否为route
	 */
	public boolean isRoute() {
		return Constant.PRODUCT_TYPE.ROUTE.name().equals(productType);
	}
	
	/**
	 * 是否为other
	 * */
	public boolean isOther() {
		return Constant.PRODUCT_TYPE.OTHER.name().equals(productType);
	}

	/**
	 * 是否为Ticket
	 * 
	 * @return
	 */

	public boolean isTicket() {
		return Constant.PRODUCT_TYPE.TICKET.name().equals(productType);
	}

	/**
	 * 是否为Hotel
	 * 
	 * @return
	 */

	public boolean isHotel() {
		return Constant.PRODUCT_TYPE.HOTEL.name().equals(productType);
	}
	
	public boolean isTraffic(){
		return Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType);
	}
	public boolean isFlight(){
		return isTraffic()&&Constant.SUB_PRODUCT_TYPE.FLIGHT.name().equals(subProductType);
	}

	public boolean isSingleRoom(){
		return Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(subProductType);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isFreeness() {
		return Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(subProductType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(
						subProductType);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isForeign() {
		if (subProductType == null) {
			return false;
		}
		return Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(
				subProductType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(
						subProductType);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isGroup() {
		return Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(subProductType)
				|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(
						subProductType)
				|| Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(
						subProductType);
	}

	public Long getGroupMin() {
		if (groupMin == null) {
			return 0L;
		}
		return groupMin;
	}

	public void setGroupMin(Long groupMin) {
		this.groupMin = groupMin;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEContract() {
		return false;
	}

	/**
	 */
	public List<String> getTravellerInfoOptionsList() {
		if (null == this.travellerInfoOptions) {
			return null;
		}
		String[] travellerInfoOptionsArray = travellerInfoOptions.split(",");
		if (null != travellerInfoOptionsArray && travellerInfoOptionsArray.length > 0) {
			List<String> travellerInfoOptionsList = new ArrayList<String>();
			for (String options : travellerInfoOptionsArray) {
				if (!options.startsWith("C_") && !options.startsWith("F_")) {
					travellerInfoOptionsList.add(options);
				}
			}
			return travellerInfoOptionsList.isEmpty() ? null : travellerInfoOptionsList;
		} else {
			return null;
		}
	}
	
	/**
	 * @return
	 */
	public List<String> getContactInfoOptionsList() {
		if (null == this.travellerInfoOptions) {
			return null;
		}
		String[] travellerInfoOptionsArray = travellerInfoOptions.split(",");
		if (null != travellerInfoOptionsArray && travellerInfoOptionsArray.length > 0) {
			List<String> contactInfoOptionsList = new ArrayList<String>();
			for (String options : travellerInfoOptionsArray) {
				if (options.startsWith("C_")) {
					contactInfoOptionsList.add(options.substring(2));
				}
			}
			return contactInfoOptionsList.isEmpty() ? null : contactInfoOptionsList;
		} else {
			return null;
		}		
	}
	
	/**
	 * @return
	 */
	public List<String> getFirstTravellerInfoOptionsList() {
		if (null == this.travellerInfoOptions) {
			return null;
		}
		String[] travellerInfoOptionsArray = travellerInfoOptions.split(",");
		if (null != travellerInfoOptionsArray && travellerInfoOptionsArray.length > 0) {
			List<String> firstTravellerInfoOptionsList = new ArrayList<String>();
			for (String options : travellerInfoOptionsArray) {
				if (options.startsWith("F_")) {
					firstTravellerInfoOptionsList.add(options.substring(2));
				}
			}
			return firstTravellerInfoOptionsList.isEmpty() ? null : firstTravellerInfoOptionsList;
		} else {
			return null;
		}		
	}	

	/**
	 * @return
	 */
	public boolean isOnlyFirstTravellerInfoOptionNotEmpty() {
		if(this.getTravellerInfoOptionsList()==null||this.getTravellerInfoOptionsList().isEmpty()){
			if(getFirstTravellerInfoOptionsList()!=null&&!this.getFirstTravellerInfoOptionsList().isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public boolean isAllTravellerInfoOptionNotEmpty() {
		return getFirstTravellerInfoOptionsList()!=null&&!getFirstTravellerInfoOptionsList().isEmpty()
				&&getTravellerInfoOptionsList()!=null&&!getTravellerInfoOptionsList().isEmpty();
	}
	

	
	public String getSaleNumType() {
		return saleNumType;
	}

	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isOnLine() {
		return "true".equals(onLine);
	}
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCouponAble() {
		return couponAble;
	}

	public void setCouponAble(String couponAble) {
		this.couponAble = couponAble;
	}
   /**
    * 获取第一次支付金额.
    * @return
    */
	public Long getPayDeposit() {
		return payDeposit;
	}
   /**
    * 设置第一次支付金额.
    * @param payDeposit
    */
	public void setPayDeposit(Long payDeposit) {
		this.payDeposit = payDeposit;
	}

	/**
	 * 团号字段
	 * @return
	 */
	public String getTravelGroupCode() {
		return "";
	}

	public String getRecommendInfoFirst() {
		return recommendInfoFirst;
	}

	public void setRecommendInfoFirst(String recommendInfoFirst) {
		this.recommendInfoFirst = recommendInfoFirst;
	}

	public String getRecommendInfoSecond() {
		return recommendInfoSecond;
	}

	public void setRecommendInfoSecond(String recommendInfoSecond) {
		this.recommendInfoSecond = recommendInfoSecond;
	}

	public String getRecommendInfoThird() {
		return recommendInfoThird;
	}

	public void setRecommendInfoThird(String recommendInfoThird) {
		this.recommendInfoThird = recommendInfoThird;
	}

	public boolean hasSelfPack(){
		return false;
	}

	/**
	 * @return the prodBranchList
	 */
	public List<ProdProductBranch> getProdBranchList() {
		return prodBranchList;
	}

	/**
	 * @param prodBranchList the prodBranchList to set
	 */
	public void setProdBranchList(List<ProdProductBranch> prodBranchList) {
		this.prodBranchList = prodBranchList;
	}

	/**
	 * @return the prodJourneyProductList
	 */
	public List<ProdJourneyProduct> getProdJourneyProductList() {
		return prodJourneyProductList;
	}

	/**
	 * @param prodJourneyProductList the prodJourneyProductList to set
	 */
	public void setProdJourneyProductList(
			List<ProdJourneyProduct> prodJourneyProductList) {
		this.prodJourneyProductList = prodJourneyProductList;
	}
	
	/**
	 * 是否是保险
	 * @return
	 */
	public boolean isInsurance(){
		return Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(subProductType);
	}

	public String getSubProductTypeStr(){
		if(StringUtils.equals(subProductType, Constant.SUB_PRODUCT_TYPE.INSURANCE.name())){
			return "保险";
		}else if(StringUtils.equals(subProductType, Constant.SUB_PRODUCT_TYPE.VISA.name())){
			return "签证";
		}else{
			return "其它";
		}
	}

	public Place getToPlace() {
		return toPlace;
	}

	public void setToPlace(Place toPlace) {
		this.toPlace = toPlace;
	}

	public List<Place> getPlaceList() {
		return PlaceList;
	}

	public void setPlaceList(List<Place> PlaceList) {
		this.PlaceList = PlaceList;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public String getManagerRecommend() {
		return managerRecommend;
	}

	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	 /**
	  * @return
	  */
	public Long getCashRefundY() {
		if(getMaxCashRefund()==0) {
			return 0L;
		} else {
			return getMaxCashRefund()/100;
		}
	}
	
	 /**
	  * @return
	  */
	@Deprecated
	public Long getCashRefundF() {
		Long cashRefundF = 0L;
		if (this.isPaymentToLvmama()&&"Y".equals(this.getIsRefundable())) {
			if("Y".equals(this.getIsManualBonus())&&this.getMaxCashRefund()!=null&&this.getMaxCashRefund().longValue()>0){
				cashRefundF = this.getMaxCashRefund();
			}else{
				if (this.getSellPrice() != null) {
					cashRefundF=BonusConfigData.getBonusByProfit(getSellPrice(),BonusConfigData.ORDER_PROFIT_ISSUE);//发放奖金
				}
			}
		}
		return (long) (Math.floor(cashRefundF/100)*100);
	}

	public String getCouponActivity() {
		return couponActivity;
	}

	public void setCouponActivity(String couponActivity) {
		this.couponActivity = couponActivity;
	}

	public String getIsManualBonus() {
		return isManualBonus;
	}

	public void setIsManualBonus(String isManualBonus) {
		this.isManualBonus = isManualBonus;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	
	public String getZhIsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?"不定期":"非不定期";
	}
	
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
	}

	public Long getAheadBookingDays() {
		return aheadBookingDays;
	}

	public void setAheadBookingDays(Long aheadBookingDays) {
		this.aheadBookingDays = aheadBookingDays;
	}
	public boolean isTrain(){
		return isTraffic() && Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(subProductType);
	}
	public String getPrePaymentAble() {
		return prePaymentAble;
	}
	public void setPrePaymentAble(String prePaymentAble) {
		this.prePaymentAble = prePaymentAble;
	}
	public Integer getShowSaleDays() {
		return showSaleDays;
	}
	public void setShowSaleDays(Integer showSaleDays) {
		this.showSaleDays = showSaleDays;
	}
	public boolean isInteriorExist() {
		return interiorExist;
	}
	public void setInteriorExist(boolean interiorExist) {
		this.interiorExist = interiorExist;
	}

    public String getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(String auditingStatus) {
        this.auditingStatus = auditingStatus;
    }
	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}
	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}

    public Long getApplicableTravelDaysLimit() {
        return applicableTravelDaysLimit;
    }

    public void setApplicableTravelDaysLimit(Long applicableTravelDaysLimit) {
        this.applicableTravelDaysLimit = applicableTravelDaysLimit;
    }

    public Long getApplicableTravelDaysCaps() {
        return applicableTravelDaysCaps;
    }

    public void setApplicableTravelDaysCaps(Long applicableTravelDaysCaps) {
        this.applicableTravelDaysCaps = applicableTravelDaysCaps;
    }

    public String getApplicableSubProductType() {
        return applicableSubProductType;
    }

    public void setApplicableSubProductType(String applicableSubProductType) {
        this.applicableSubProductType = applicableSubProductType;
    }

    public List<String> getApplicableSubProductTypeList() {
        if (applicableSubProductTypeList == null) {
            applicableSubProductTypeList = new ArrayList<String>();
            if (StringUtils.isNotBlank(applicableSubProductType)) {
                applicableSubProductTypeList.addAll(Arrays.asList(applicableSubProductType.split(",")));
            }
        }

        return applicableSubProductTypeList;
    }
	public String getProductContactInfoOptions() {
		return productContactInfoOptions;
	}
	public void setProductContactInfoOptions(String productContactInfoOptions) {
		this.productContactInfoOptions = productContactInfoOptions;
	}
	public String getBounsScale() {
		return bounsScale;
	}
	
	public double getBounsScaleDouble(){
		if (this.bounsScale == null) {
			return 0;
		}
		return Double.valueOf(this.bounsScale) / 100;
	}
	
	public void setBounsScale(String bounsScale) {
		this.bounsScale = bounsScale;
	}
	public String getBounsReason() {
		return bounsReason;
	}
	public void setBounsReason(String bounsReason) {
		this.bounsReason = bounsReason;
	}
	public Date getBounsStart() {
		return bounsStart;
	}
	public void setBounsStart(Date bounsStart) {
		this.bounsStart = bounsStart;
	}
	public Date getBounsEnd() {
		return bounsEnd;
	}
	public void setBounsEnd(Date bounsEnd) {
		this.bounsEnd = bounsEnd;
	}
	public Long getBounsLimit() {
		return bounsLimit;
	}
	public void setBounsLimit(Long bounsLimit) {
		this.bounsLimit = bounsLimit;
	}
	public String getIsMoreThanConf() {
		return isMoreThanConf;
	}
	public void setIsMoreThanConf(String isMoreThanConf) {
		this.isMoreThanConf = isMoreThanConf;
	}
	public String getFreeBackable() {
		if (freeBackable == null) {
			freeBackable = "N";
		}
		return freeBackable;
	}
	public void setFreeBackable(String freeBackable) {
		this.freeBackable = freeBackable;
	}
}
