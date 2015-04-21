/**
 * 
 */
package com.lvmama.jinjiang.vo.order;

import com.lvmama.comm.utils.PriceUtil;

/**
 * @author yangbin
 *
 */
public class OrderBaseInfo {
	
	private Long externalOrderNo;
	private Long orderTotalAmount;
	private Long orderFavorAmount;
	private Long adultSaleProxyPrice;
	private Long childrenSalePrice;
	private Long infantSalePrice;
	private Long bookAccompanyPrice;
	private String priceArithmetic;
	private String orderState="HK";//正常预订
	public Long getExternalOrderNo() {
		return externalOrderNo;
	}
	public String getOrderTotalAmount() {
		return  ""+PriceUtil.convertToYuan(orderTotalAmount);
	}
	public String getOrderFavorAmount() {
		return ""+PriceUtil.convertToYuan(orderFavorAmount);
	}
	public String getAdultSaleProxyPrice() {
		return  ""+PriceUtil.convertToYuan(adultSaleProxyPrice);
	}
	public String getChildrenSalePrice() {
		return  ""+PriceUtil.convertToYuan(childrenSalePrice);
	}
	public String getInfantSalePrice() {
		return ""+PriceUtil.convertToYuan(infantSalePrice);
	}
	public String getBookAccompanyPrice() {
		return  ""+PriceUtil.convertToYuan(bookAccompanyPrice);
	}
	public String getPriceArithmetic() {
		return priceArithmetic;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setExternalOrderNo(Long externalOrderNo) {
		this.externalOrderNo = externalOrderNo;
	}
	public void setOrderTotalAmount(Long orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}
	public void setOrderFavorAmount(Long orderFavorAmount) {
		this.orderFavorAmount = orderFavorAmount;
	}
	public void setAdultSaleProxyPrice(Long adultSaleProxyPrice) {
		this.adultSaleProxyPrice = adultSaleProxyPrice;
	}
	public void setChildrenSalePrice(Long childrenSalePrice) {
		this.childrenSalePrice = childrenSalePrice;
	}
	public void setInfantSalePrice(Long infantSalePrice) {
		this.infantSalePrice = infantSalePrice;
	}
	public void setBookAccompanyPrice(Long bookAccompanyPrice) {
		this.bookAccompanyPrice = bookAccompanyPrice;
	}
	public void setPriceArithmetic(String priceArithmetic) {
		this.priceArithmetic = priceArithmetic;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Long getLongOrderTotalAmount() {
		return orderTotalAmount==null?0l:orderTotalAmount;
	}

	public Long getLongOrderFavorAmount() {
		return orderFavorAmount==null?0l:orderFavorAmount;
	}

	public Long getLongAdultSaleProxyPrice() {
		return adultSaleProxyPrice==null?0l:adultSaleProxyPrice;
	}

	public Long getLongChildrenSalePrice() {
		return childrenSalePrice==null?0l:childrenSalePrice;
	}

	public Long getLongInfantSalePrice() {
		return infantSalePrice==null?0l:infantSalePrice;
	}

	public Long getLongBookAccompanyPrice() {
		return bookAccompanyPrice==null?0l:bookAccompanyPrice;
	}
	
	
}
