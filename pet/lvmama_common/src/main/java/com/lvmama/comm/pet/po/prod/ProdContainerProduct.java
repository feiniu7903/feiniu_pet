package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdContainerProduct implements Serializable {
	private static final long serialVersionUID = -2649314627834157418L;

	private Long id;
	private String containerName;
	private Integer recommendSeq;
	private Integer defaultSeq;
	private Long productId;
	private String productName;
	private Long sellPrice;
	private String fromPlaceName;
	private String toPlaceName;
	private String isValid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setContainerId(Long containerId){
		this.id=containerId;
	}
	public Long getContainerId() {
		return id;
	}
	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public Integer getRecommendSeq() {
		return recommendSeq;
	}

	public void setRecommendSeq(Integer recommendSeq) {
		this.recommendSeq = recommendSeq;
	}

	public Integer getDefaultSeq() {
		return defaultSeq;
	}

	public void setDefaultSeq(Integer defaultSeq) {
		this.defaultSeq = defaultSeq;
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

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public String getToPlaceName() {
		return toPlaceName;
	}

	public void setToPlaceName(String toPlaceName) {
		this.toPlaceName = toPlaceName;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Integer getSellPriceYuan() {
		Integer price = 0;
		if (this.sellPrice != null) {
			price = Integer.valueOf(sellPrice.toString());
		}
		return price / 100;
	}
}
