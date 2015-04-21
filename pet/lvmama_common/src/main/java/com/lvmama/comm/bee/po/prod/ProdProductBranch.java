package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class ProdProductBranch implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1343040035095642240L;

	private Long prodBranchId;

    private Date createTime;

    private String branchType;
    private String branchName;

    private Long adultQuantity;

    private Long childQuantity;
    private String additional;//附加属性
    private String defaultBranch;//="false";//产品的默认
    private Long sellPrice;
    private Long marketPrice;

    private Long minimum = 0L;

    private Long maximum;

    private String priceUnit;

    private String description;

    private Long productId;
    
    private ProdProduct prodProduct;
    
    private String online;
    
    private String breakfast;//早餐
    private String broadband;//宽带
    private String bedType;//床型
    private String icon;//小icon;
    private String extraBedAble;//能否加床
    private String visible;//default("false")是否前台可见
    private String valid;//是否有效    
    private List<TimePrice> timePriceList;
    
    private String distributor;
    private String blackDistrbuor;
    private int branchSerialNumber;
    /**
     * 该值在前台下单流程当中使用.
     */
    private long stock=0L;
    
    private String todayOrderAble = "false";//是否手机当天预定
    /**
     * 舱位
     */
    private String berth;
    
    //不定期产品有效开始日期
    private Date validBeginTime;
    
  //不定期产品有效结束日期
    private Date validEndTime;
    
    //当前类别享有优惠
    private boolean hasBusinessCoupon;
    
    private Long stationStationId;
    
    //不定期产品不可售日期
    private String invalidDate;
    
    //不定期产品不可售日期说明文字
    private String invalidDateMemo;
    
    private String weixinLijian = "N";
    
    public Long getProdBranchId() {
        return prodBranchId;
    }

    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
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

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit == null ? null : priceUnit.trim();
    }

    public String getDescription() {
        return description;
    }

    
    /**
	 * @return the branchType
	 */
	public String getBranchType() {
		return branchType;
	}

	/**
	 * @param branchType the branchType to set
	 */
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	/**
	 * @return the additional
	 */
	public String getAdditional() {
		return additional;
	}

	/**
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	
	public boolean hasAdditional(){
		return StringUtils.equals("true", additional);
	}

	

	/**
	 * @return the defaultBranch
	 */
	public String getDefaultBranch() {
		return defaultBranch;
	}

	/**
	 * @param defaultBranch the defaultBranch to set
	 */
	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	/**
	 * @return the online
	 */
	public String getOnline() {
		return online;
	}
	/**
	 * 判断是否是上线，不用is方法是保证反射的不会出现问题
	 * @return
	 */
	public boolean hasOnline(){
		return StringUtils.equalsIgnoreCase(online, "true");
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(String online) {
		this.online = online;
	}
    
	public boolean hasDefault(){
		return StringUtils.equalsIgnoreCase(defaultBranch, "true");
	}

	/**
	 * @return the prodProduct
	 */
	public ProdProduct getProdProduct() {
		return prodProduct;
	}

	/**
	 * @param prodProduct the prodProduct to set
	 */
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}

	/**
	 * @return the breakfast
	 */
	public String getBreakfast() {
		return breakfast;
	}

	/**
	 * @param breakfast the breakfast to set
	 */
	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	/**
	 * @return the broadband
	 */
	public String getBroadband() {
		return broadband;
	}

	/**
	 * @param broadband the broadband to set
	 */
	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	/**
	 * @return the bedType
	 */
	public String getBedType() {
		return bedType;
	}

	/**
	 * @param bedType the bedType to set
	 */
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the sellPrice
	 */
	public Long getSellPrice() {
		return sellPrice;
	}

	/**
	 * @param sellPrice the sellPrice to set
	 */
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * @return the marketPrice
	 */
	public Long getMarketPrice() {
		return marketPrice;
	}

	/**
	 * @param marketPrice the marketPrice to set
	 */
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * @return the stock
	 */
	public long getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(long stock) {
		this.stock = stock;
	}
	
	public String getVisible() {
		return visible;
	}

	public boolean hasVisible(){
		return StringUtils.equalsIgnoreCase(visible, "true");
	}
	
	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getFullName(){
		StringBuffer sb=new StringBuffer();
		if(prodProduct!=null){
			sb.append(prodProduct.getProductName());
		sb.append("(");
		}
		sb.append(branchName);
		if(prodProduct!=null){
			sb.append(")");
		}
		return sb.toString();
	}
 
	public Float getMarketPriceYuan(){
		return marketPrice==null?0f:PriceUtil.convertToYuan(marketPrice);
	}
	public Float getSellPriceYuan(){
		return sellPrice==null?0f:PriceUtil.convertToYuan(sellPrice);
	}
	
	public String getZhBreakfast() {
		if (this.breakfast != null) {
			return this.breakfast.equals("true") ? "有" : "无";
		}
		return "无";
	}

	public String getZhBroadband() {
		if (this.broadband != null) {
			if (this.broadband.equals("none")) {
				return "无";
			} else if (this.broadband.equals("free")) {
				return "免费";
			} else {
				return "收费";
			}
		}
		return "无";
	}

	/**
	 * @return the extraBedAble
	 */
	public String getExtraBedAble() {
		return extraBedAble;
	}

	/**
	 * @param extraBedAble the extraBedAble to set
	 */
	public void setExtraBedAble(String extraBedAble) {
		this.extraBedAble = extraBedAble;
	}
	
	/**
	 * 能否加床
	 * @return
	 */
	public boolean hasExtraBedAble(){
		return "true".equals(extraBedAble);
	}
	
	public String getZHQuantity(){
		return "成人数："+this.adultQuantity+"  "+"儿童数："+this.childQuantity;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getZhOnline(){
		String zhStr = "下线";
		if("true".equals(this.online)){
			zhStr = "上线";
		}
		return zhStr;
	}
	
	public String getProductAndBranchName(){
		if(this.prodProduct!=null){
			return this.prodProduct.getProductName() + this.branchName;
		}else{
			return null;
		}
	}
	
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	

	public List<TimePrice> getTimePriceList() {
		return timePriceList;
	}

	public void setTimePriceList(List<TimePrice> timePriceList) {
		this.timePriceList = timePriceList;
	}
	
	public int getBranchSerialNumber() {
		return branchSerialNumber;
	}

	public void setBranchSerialNumber(int branchSerialNumber) {
		this.branchSerialNumber = branchSerialNumber;
	}

	public String getBerth() {
		return berth;
	}

	public void setBerth(String berth) {
		this.berth = berth;
	}
	
	public String getZhBerth(){
		return Constant.TRAFFIC_BRANCH_1.getCnName(berth);
	}

	public String getTodayOrderAble() {
		return todayOrderAble;
	}

	public void setTodayOrderAble(String todayOrderAble) {
		this.todayOrderAble = todayOrderAble;
	}

	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}

	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}

	public Date getValidBeginTime() {
		return validBeginTime;
	}

	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public Long getStationStationId() {
		return stationStationId;
	}

	public void setStationStationId(Long stationStationId) {
		this.stationStationId = stationStationId;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public String getBlackDistrbuor() {
		return blackDistrbuor;
	}

	public void setBlackDistrbuor(String blackDistrbuor) {
		this.blackDistrbuor = blackDistrbuor;
	}
	
	public String getWithOutHtmlDescription() {
		String desc = this.getDescription();
		if (desc == null) {
			return null;
		}
		return desc.replace("<div class=\"xtext\">", "")
				.replace("</div>", "")
				.replace("<h4>", "")
				.replace("</h4>", "")
				.replace("<p>", "")
				.replace("\n", "")
				.replace("\t", "")
				.replace("</p>", "");
	}

	public String getWeixinLijian() {
		return weixinLijian;
	}

	public void setWeixinLijian(String weixinLijian) {
		this.weixinLijian = weixinLijian;
	}
}