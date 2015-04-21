package com.lvmama.comm.bee.po.group;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

public class GroupDream implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5139600768232246018L;
	private Long dreamId;
	private String productName;
	private String productType;
	private String dest;
	private Long enjoyCount;
	private Long notEnjoyCount;
	private String dreamYearMonth;
	private String introduction;
	private String picUrl;
	private Long lowDreamPrice = new Long(0);
	private Long highDreamPrice= new Long(0);
	private Long marketPrice   = new Long(0);
	private Date createTime;
	private Date updateTime;
	private String operator;
	private String lowDreamPriceDou;
	private String highDreamPriceDou;

	private Long joinTotalCount;
	private String valid;
	public Long getDreamId() {
		return dreamId;
	}

	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}
	public Long getEnjoyCount() {
		return enjoyCount;
	}

	public void setEnjoyCount(Long enjoyCount) {
		this.enjoyCount = enjoyCount;
	}

	public Long getNotEnjoyCount() {
		return notEnjoyCount;
	}

	public void setNotEnjoyCount(Long notEnjoyCount) {
		this.notEnjoyCount = notEnjoyCount;
	}

	public String getDreamYearMonth() {
		return dreamYearMonth;
	}

	public void setDreamYearMonth(String dreamYm) {
		this.dreamYearMonth = dreamYm;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getLowDreamPrice() {
		return lowDreamPrice;
	}

	public void setLowDreamPrice(Long lowDreamPrice) {
		this.lowDreamPrice = lowDreamPrice;
		
	}

	public Long getHighDreamPrice() {
		return highDreamPrice;
	}

	public void setHighDreamPrice(Long highDreamPrice) {
		this.highDreamPrice = highDreamPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getLowDreamPriceDou() {
		return PriceUtil.moneyConvertStr(lowDreamPrice);
	}

	public void setLowDreamPriceDou(String lowDreamPriceDou) {
		this.lowDreamPriceDou = lowDreamPriceDou;
		this.lowDreamPrice = PriceUtil.convertToFen(Float.valueOf(lowDreamPriceDou));
	}

	public String getHighDreamPriceDou() {
		
		return PriceUtil.moneyConvertStr(highDreamPrice);
	}

	public void setHighDreamPriceDou(String highDreamPriceDou) {
		this.highDreamPriceDou = highDreamPriceDou;
		this.highDreamPrice = PriceUtil.convertToFen(Float.valueOf(highDreamPriceDou));
	}
	public String getMarketPriceDou() {
		
		return PriceUtil.moneyConvertStr(this.marketPrice);
	}

	public void setMarketPriceDou(String marketPriceDou) {
		
		this.marketPrice = PriceUtil.convertToFen(Float.valueOf(marketPriceDou));
	}
	public Long getJoinTotalCount() {
		return joinTotalCount;
	}
	
	public void setJoinTotalCount(Long joinTotalCount) {
		this.joinTotalCount = joinTotalCount;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
}
