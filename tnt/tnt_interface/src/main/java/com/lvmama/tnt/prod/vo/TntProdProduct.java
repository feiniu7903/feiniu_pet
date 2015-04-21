package com.lvmama.tnt.prod.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;

public class TntProdProduct implements Serializable {

	private static final long serialVersionUID = 2518436939230200970L;

	private Long branchId;// 类别Id

	private String branchName;// 类别名称

	private String branchStatus;// 类别状态

	private Long productId;// 产品Id

	private String productName;// 产品名称

	private String productType;// 产品类型

	private String productStatus;// 产品状态

	private String branchType;

	private Long placeId;// 景区Id

	private String placeName;// 景区名称

	private String payType;// 支付方式

	private String channelType;// 分销渠道类型

	private String description;

	private String subProductType;

	private Long minimum = 0L;

	private Long maximum;

	private Long stock;

	private Long adultQuantity;

	private Long childQuantity;

	// 不定期产品有效开始日期
	private Date validBeginTime;

	// 不定期产品有效结束日期
	private Date validEndTime;

	private String invalidDateMemo;

	private Long sellPrice;// 销售价

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public boolean isAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic) ? true : false;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	private String isAperiodic;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchStatus() {
		return branchStatus;
	}

	public void setBranchStatus(String branchStatus) {
		this.branchStatus = branchStatus;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public void trim() {
		if (productName != null)
			setProductName(productName.trim());
	}

	// 是否返佣
	public boolean isRebate() {
		return isPayToLvmama();
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

	public boolean isTicket() {
		return TntConstant.PRODUCT_TYPE.TICKET.name().equals(productType);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
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

	public String getStringValidBeginTime() {
		return TntUtil.formatDate(validBeginTime);
	}

	public String getStringValidEndTime() {
		return TntUtil.formatDate(validEndTime);
	}

	public float getSellPriceYuan() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
	}

	public int getSellPriceInt() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuanInt(sellPrice);
	}

	public String getDescriptionHtml() {
		if (description != null) {
			return description.replace("<div class=\'xtext\'>", "")
					.replace("</div>", "").replace("<h4>", "")
					.replace("</h4>", "").replace("<p>", "").replace("\n", "")
					.replace("\t", "").replace("</p>", "</br>");
		}
		return null;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
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

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public boolean isPayToLvmama() {
		return TntConstant.PRODUCT_PAY_TYPE.isPayToLvmama(payType);
	}

}
