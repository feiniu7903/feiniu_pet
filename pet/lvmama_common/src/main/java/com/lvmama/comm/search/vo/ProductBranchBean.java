package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdTag;

/*
 * 类别产品属性类： 类别产品是属于产品下面的子类型，继承产品的属性，通常是在产品细节和服务上做变化.
 * 2012-5-31 huangzhi
 * */
public class ProductBranchBean implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6841462640546576332L;
	private Long prodBranchId;// 主键
	private String createTime;
	private String updateTime;
	private String branchName;
	private String bedType;
	private String description;
	private String broadBand;
	private String cashRefund;
	private String breakfast;
	private Long marketPrice;
	private Long sellPrice;
	private String icon;
	private String additional;
	private String onLine;
	private String valid;
	private String productAllPlaceIds;
	private String productAlltoPlaceContent;
	private String productId;// 产品ID,外键
	private String visible;
	private String defaultBranch;// 是否为默认类别产品,一个产品下面必须有一个默认类别产品
	private String channel;// 显示频道：线上，客户端，手机端
	private String subProudctType; // 产品子类型
	private Date validBeginTime;//期票有效期的开始时间
	private Date validEndTime;//期票有效期的结束时间
	private String invalidDateMemo; //期票不可售日期描述信息
	
	private String shareweixin; //微信分享标识
	
	private String productName;
	private String tagsGroup;
	/**
	 * 标签描述,多个标签以 , 分隔
	 * */
	private String tagsDescript;
	/**
	 * 标签Class样式名称,多个标签以 , 分隔
	 * */
	private String tagsCss;
	private String isTicket;// 产品类型
	private String tagsName;
	private String productUrl;
	
	private Map<String, List<ProdTag>> tagGroupMap = new HashMap<String, List<ProdTag>>();
	
	private List<ProdTag> tagList = new ArrayList<ProdTag>();
	
	public String getProductUrl() {
		if (null != productUrl) {
			return productUrl;
		} else {
			return "";
		}
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	
	public String getTagsDescript() {
		return tagsDescript;
	}

	public void setTagsDescript(String tagsDescript) {
		this.tagsDescript = tagsDescript;
	}

	public String getTagsCss() {
		return tagsCss;
	}

	public void setTagsCss(String tagsCss) {
		this.tagsCss = tagsCss;
	}

	public List<ProdTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ProdTag> tagList) {
		this.tagList = tagList;
	}

	public String getTagsName() {
		return tagsName;
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public Map<String, List<ProdTag>> getTagGroupMap() {
		return tagGroupMap;
	}

	public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
		this.tagGroupMap = tagGroupMap;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTagsGroup() {
		return tagsGroup;
	}

	public void setTagsGroup(String tagsGroup) {
		this.tagsGroup = tagsGroup;
	}

	public String getIsTicket() {
		return isTicket;
	}

	public void setIsTicket(String isTicket) {
		this.isTicket = isTicket;
	}

	public Long getProdBranchId() {
		if (null != prodBranchId) {
			return prodBranchId;
		} else {
			return 0L;
		}
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public String getCreateTime() {
		if (null != createTime) {
			return createTime;
		} else {
			return "";
		}
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		if (null != updateTime) {
			return updateTime;
		} else {
			return "";
		}
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBranchName() {
		if (null != branchName) {
			return branchName;
		} else {
			return "";
		}
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBedType() {
		if (null != bedType) {
			return bedType;
		} else {
			return "";
		}
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getDescription() {
		if (null != description) {
			return description;
		} else {
			return "";
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBroadBand() {
		if (null != broadBand) {
			return broadBand;
		} else {
			return "";
		}
	}

	public void setBroadBand(String broadBand) {
		this.broadBand = broadBand;
	}

	public String getCashRefund() {
		if (null != cashRefund) {
			return cashRefund;
		} else {
			return "";
		}
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public String getBreakfast() {
		if (null != breakfast) {
			return breakfast;
		} else {
			return "";
		}
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public Float getMarketPrice() {
		if(null == marketPrice){
			marketPrice = 0L;
		}
		BigDecimal bd1 = new BigDecimal(this.marketPrice);
		BigDecimal bd2 = BigDecimal.valueOf(100L);
		bd1 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_DOWN);
		return bd1.floatValue();
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Float getSellPrice() {
		if(null == sellPrice){
			sellPrice = 0L;
		}
		BigDecimal bd1 = new BigDecimal(this.sellPrice);
		BigDecimal bd2 = BigDecimal.valueOf(100L);
		bd1 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_DOWN);
		return bd1.floatValue();
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getIcon() {
		if (null != icon) {
			return icon;
		} else {
			return "";
		}
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAdditional() {
		if (null != additional) {
			return additional;
		} else {
			return "";
		}
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public String getOnLine() {
		if (null != onLine) {
			return onLine;
		} else {
			return "";
		}
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getValid() {
		if (null != valid) {
			return valid;
		} else {
			return "";
		}
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getProductAllPlaceIds() {
		if (null != productAllPlaceIds) {
			return productAllPlaceIds;
		} else {
			return "";
		}
	}

	public void setProductAllPlaceIds(String productAllPlaceIds) {
		this.productAllPlaceIds = productAllPlaceIds;
	}

	public String getProductAlltoPlaceContent() {
		if (null != productAlltoPlaceContent) {
			return productAlltoPlaceContent;
		} else {
			return "";
		}
	}

	public void setProductAlltoPlaceContent(String productAlltoPlaceContent) {
		this.productAlltoPlaceContent = productAlltoPlaceContent;
	}

	public String getProductId() {
		if (null != productId) {
			return productId;
		} else {
			return "";
		}
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVisible() {
		if (null != visible) {
			return visible;
		} else {
			return "";
		}
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getDefaultBranch() {
		if (null != defaultBranch) {
			return defaultBranch;
		} else {
			return "";
		}
	}

	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	public String getChannel() {
		if (null != channel) {
			return channel;
		} else {
			return "";
		}
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubProudctType() {
		if (null != subProudctType) {
			return subProudctType;
		} else {
			return "";
		}
	}

	public void setSubProudctType(String subProudctType) {
		this.subProudctType = subProudctType;
	}

	/** sellPrice排序比较器 **/
	public static class comparatorSellPrice implements
			Comparator<ProductBranchBean> {
		public int compare(ProductBranchBean o1, ProductBranchBean o2) {
			ProductBranchBean s1 = (ProductBranchBean) o1;
			ProductBranchBean s2 = (ProductBranchBean) o2;
			int result = (int) (s1.sellPrice - s2.sellPrice);
			return result;
		}
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

	public String getInvalidDateMemo() {
		if (null != invalidDateMemo) {
			return invalidDateMemo;
		} else {
			return "";
		}
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public String getShareweixin() {
		return shareweixin;
	}

	public void setShareweixin(String shareweixin) {
		this.shareweixin = shareweixin;
	}

	
}
