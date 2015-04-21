/**
 * 
 */
package com.lvmama.comm.bee.po.meta;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.TimePrice;

/**
 * 采购产品类别.
 * @author yangbin
 *
 */
public class MetaProductBranch implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5746795320614861225L;
	//采购产品类别ID.
	/** The meta branch id. */
	private Long metaBranchId;
	//采购产品ID.
	/** The meta product id. */
	private Long metaProductId;
	//采购产品类型. 取值为TICKET、ROUTE、HOTEL.
	/** The product type. */
	private String productType;
	//采购产品类别类型. 取值为COM_CODE表中SET_CODE值为TICKET_BRANCH、ROUTE_BRANCH、OTHER_BRANCH、SELF_PACK_BRANCH.
	/** The branch type. */
	private String branchType;
	//采购产品类别名字.
	/** The branch name. */
	private String branchName;
	//成人数.
	/** The adult quantity. */
	private Long adultQuantity;
	//儿童数.
	/** The child quantity. */
	private Long childQuantity;

	/** 原总库存，现为非买断库存. @author zuoxiaoshuai */
	private Long totalStock;
	
	//是否使用总库存, 取值为true、false.
	/** The total decrease. */
	private String totalDecrease;
	//创建时间.
	/** The create time. */
	private Date createTime;
	
	/** The description. */
	private String description;
	
	/** 代理产品编号. */
	private String productIdSupplier;
	
	/** 代理产品类型. */
	private String productTypeSupplier;
 	
	 /** 天库存，由销售产品类别所关联的采购类别计算. */
	protected long dayStock = -2;
	
	/** The additional. */
	private String additional;
	//是否需要单独创建传真.
	/** The send fax. */
	private String sendFax;
 	
	 /** The valid. */
	 private String valid;
 	
 	/** The meta product name. */
	 private String metaProductName;
 	
	 /** 是否虚拟产品 true/false. */
 	private String virtual;
 	
 	/** 舱位类别. */
 	private String berth;
 	
 	/** The station station id. */
	 private Long stationStationId;
 	
 	/** 对接第三方库存使用*. */
 	private String checkStockHandle;
 	
	/**
	 * Gets the meta product id.
	 *
	 * @return the metaProductId
	 */
	public Long getMetaProductId() {
		return metaProductId;
	}

	/**
	 * Sets the meta product id.
	 *
	 * @param metaProductId the metaProductId to set
	 */
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * Gets the meta branch id.
	 *
	 * @return the metaBranchId
	 */
	public Long getMetaBranchId() {
		return metaBranchId;
	}

	/**
	 * Sets the meta branch id.
	 *
	 * @param metaBranchId the metaBranchId to set
	 */
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	/**
	 * Gets the branch type.
	 *
	 * @return the branchType
	 */
	public String getBranchType() {
		return branchType;
	}

	/**
	 * Sets the branch type.
	 *
	 * @param branchType the branchType to set
	 */
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	/**
	 * Gets the branch name.
	 *
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * Sets the branch name.
	 *
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * Gets the adult quantity.
	 *
	 * @return the adultQuantity
	 */
	public Long getAdultQuantity() {
		return adultQuantity;
	}

	/**
	 * Sets the adult quantity.
	 *
	 * @param adultQuantity the adultQuantity to set
	 */
	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	/**
	 * Gets the child quantity.
	 *
	 * @return the childQuantity
	 */
	public Long getChildQuantity() {
		return childQuantity;
	}

	/**
	 * Sets the child quantity.
	 *
	 * @param childQuantity the childQuantity to set
	 */
	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	/**
	 * Gets the total stock.
	 *
	 * @return the totalStock
	 */
	public Long getTotalStock() {
		return totalStock;
	}

	/**
	 * Sets the total stock.
	 *
	 * @param totalStock the totalStock to set
	 */
	
	public void setTotalStock(Long totalStock) {
		this.totalStock = totalStock;
	}
	

	/**
	 * Gets the creates the time.
	 *
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	/**
	 * Gets the total decrease.
	 *
	 * @return the totalDecrease
	 */
	public String getTotalDecrease() {
		return totalDecrease;
	}

	/**
	 * Sets the total decrease.
	 *
	 * @param totalDecrease the totalDecrease to set
	 */
	public void setTotalDecrease(String totalDecrease) {
		this.totalDecrease = totalDecrease;
	}

	/**
	 * Gets the product id supplier.
	 *
	 * @return the productIdSupplier
	 */
	public String getProductIdSupplier() {
		return productIdSupplier;
	}

	/**
	 * Sets the product id supplier.
	 *
	 * @param productIdSupplier the productIdSupplier to set
	 */
	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}

	/**
	 * Gets the product type supplier.
	 *
	 * @return the productTypeSupplier
	 */
	public String getProductTypeSupplier() {
		return productTypeSupplier;
	}

	/**
	 * Sets the product type supplier.
	 *
	 * @param productTypeSupplier the productTypeSupplier to set
	 */
	public void setProductTypeSupplier(String productTypeSupplier) {
		this.productTypeSupplier = productTypeSupplier;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the valid.
	 *
	 * @return the valid
	 */
	public String getValid() {
		return valid;
	}

	/**
	 * Sets the valid.
	 *
	 * @param valid the valid to set
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	/**
	 * Checks if is total decrease.
	 *
	 * @return true, if is total decrease
	 */
	public boolean isTotalDecrease() {
		return "true".equals(totalDecrease);
	}

	/**
	 * Checks for valid.
	 *
	 * @return true, if successful
	 */
	public boolean hasValid(){
		return "Y".equals(valid);
	}
	
	/**
	 * 改为相反的值.
	 */
	public void changeValid(){
		valid=hasValid()?"N":"Y";
		
	}
	
	
	
	/**
	 * Gets the additional.
	 *
	 * @return the additional
	 */
	public String getAdditional() {
		return additional;
	}

	/**
	 * Sets the additional.
	 *
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}

	/**
	 * 判断产品的能否销售.
	 *
	 * @param needStock the need stock
	 * @param timePrice the time price
	 * @return true, if is sellable
	 */
	public boolean isSellable(long needStock, TimePrice timePrice) {
		if (this.getTotalStock() == -1) {
			return true;
		}
		if (this.getTotalStock() > 0 && this.getTotalStock() >= needStock) {
			return true;
		}
		if (this.getTotalStock() == 0) {
			return timePrice.isOverSaleAble();
		}
		return false;
	}
	
	/**
	 * Checks if is additional.
	 *
	 * @return true, if is additional
	 */
	public boolean isAdditional(){
		return "true".equals(this.additional);
	}

	/**
	 * Gets the send fax.
	 *
	 * @return the sendFax
	 */
	public String getSendFax() {
		return sendFax;
	}

	/**
	 * Sets the send fax.
	 *
	 * @param sendFax the sendFax to set
	 */
	public void setSendFax(String sendFax) {
		this.sendFax = sendFax;
	}
	

	/**
	 * 是否需要单独创建传真.
	 *
	 * @return true, if successful
	 */
	public boolean hasSendFax(){
		return "true".equals(sendFax);
	}

	/**
	 * Gets the meta product name.
	 *
	 * @return the meta product name
	 */
	public String getMetaProductName() {
		return metaProductName;
	}

	/**
	 * Sets the meta product name.
	 *
	 * @param metaProductName the new meta product name
	 */
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}

	/**
	 * Gets the virtual.
	 *
	 * @return the virtual
	 */
	public String getVirtual() {
		return virtual;
	}

	/**
	 * Sets the virtual.
	 *
	 * @param virtual the new virtual
	 */
	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

	/**
	 * Gets the berth.
	 *
	 * @return the berth
	 */
	public String getBerth() {
		return berth;
	}

	/**
	 * Sets the berth.
	 *
	 * @param berth the new berth
	 */
	public void setBerth(String berth) {
		this.berth = berth;
	}
	
	/**
	 * Gets the product type.
	 *
	 * @return the product type
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * Sets the product type.
	 *
	 * @param productType the new product type
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * Gets the station station id.
	 *
	 * @return the station station id
	 */
	public Long getStationStationId() {
		return stationStationId;
	}

	/**
	 * Sets the station station id.
	 *
	 * @param stationStationId the new station station id
	 */
	public void setStationStationId(Long stationStationId) {
		this.stationStationId = stationStationId;
	}

	/**
	 * Gets the check stock handle.
	 *
	 * @return the check stock handle
	 */
	public String getCheckStockHandle() {
		return checkStockHandle;
	}

	/**
	 * Sets the check stock handle.
	 *
	 * @param checkStockHandle the new check stock handle
	 */
	public void setCheckStockHandle(String checkStockHandle) {
		this.checkStockHandle = checkStockHandle;
	}
}
