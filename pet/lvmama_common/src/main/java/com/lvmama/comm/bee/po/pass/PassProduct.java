package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;
/**
 * 第三方产品信息
 * @author chenlinjun
 *
 */
public class PassProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2228765564864290533L;
	private Long passProdId;
	private Long objectId;
	private String productIdSupplier;
	private String productTypeSupplier;
	private String provider;
	private String providerName;
	private Date startDate;
	private Date endDate;
	private String isWeekend;
	private String merchantType;
	private String extId;
	private String productName;// 产品名称
	private String scenicName;// 景区名称
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getIsWeekend() {
		return isWeekend;
	}

	public void setIsWeekend(String isWeekend) {
		this.isWeekend = isWeekend;
	}

	public Long getPassProdId() {
		return passProdId;
	}

	public void setPassProdId(Long passProdId) {
		this.passProdId = passProdId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getProductIdSupplier() {
		return productIdSupplier;
	}

	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}

	public String getProductTypeSupplier() {
		return productTypeSupplier;
	}

	public void setProductTypeSupplier(String productTypeSupplier) {
		this.productTypeSupplier = productTypeSupplier;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

}
