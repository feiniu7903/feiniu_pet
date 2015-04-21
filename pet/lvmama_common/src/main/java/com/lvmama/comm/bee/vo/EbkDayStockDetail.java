package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author fangweiquan
 */
public class EbkDayStockDetail implements Serializable {
	private static final long serialVersionUID = 4195665976238338435L;
	/**
	 * 采购产品名称
	 */
	private String metaProductName;
	/**
	 * 类别名称
	 */
	private String metaBranchName;
	/**
	 * 类别ID
	 */
	private Long metaBranchId;
	/**
	 * 日总库存数量
	 */
	private Long totalDayStock;	

	/**
	 * 剩余日库存数量
	 */
	private Long dayStock;	
	/**
	 * 售出日库存数量
	 */
	private Long soldStock;	
	/**
	 * 增减库存数量
	 */
	private Long addOrMinusStock;	
	/**
	 * 是否增加库存
	 * <br>增加True，减少False，直接库存量null
	 */
	private String isAddDayStock;
	/**
	 * 是否超卖
	 */
	private String isOverSale;
	/**
	 * 占位数
	 */
	private Long seatOccupiedQuantity;	
	/**
	 * 出发时间
	 */
	private Date specDate;
	/**
	 * 是否库存清零,即关班
	 */
	private String isStockZero;
	
	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public Long getTotalDayStock() {
		return totalDayStock;
	}
	public void setTotalDayStock(Long totalDayStock) {
		this.totalDayStock = totalDayStock;
	}
	public Long getDayStock() {
		return dayStock;
	}
	public void setDayStock(Long dayStock) {
		this.dayStock = dayStock;
	}
	public Long getSoldStock() {
		return soldStock;
	}
	public void setSoldStock(Long soldStock) {
		this.soldStock = soldStock;
	}
	public Long getSeatOccupiedQuantity() {
		return seatOccupiedQuantity;
	}
	public void setSeatOccupiedQuantity(Long seatOccupiedQuantity) {
		this.seatOccupiedQuantity = seatOccupiedQuantity;
	}
	public Date getSpecDate() {
		return specDate;
	}
	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	public String getMetaBranchName() {
		return metaBranchName;
	}
	public void setMetaBranchName(String metaBranchName) {
		this.metaBranchName = metaBranchName;
	}
	public Long getAddOrMinusStock() {
		return addOrMinusStock;
	}
	public void setAddOrMinusStock(Long addOrMinusStock) {
		this.addOrMinusStock = addOrMinusStock;
	}
	public String getIsOverSale() {
		return isOverSale;
	}
	public void setIsOverSale(String isOverSale) {
		this.isOverSale = isOverSale;
	}
	public String getIsAddDayStock() {
		return isAddDayStock;
	}
	public void setIsAddDayStock(String isAddDayStock) {
		this.isAddDayStock = isAddDayStock;
	}
	public String getIsStockZero() {
		return isStockZero;
	}
	public void setIsStockZero(String isStockZero) {
		this.isStockZero = isStockZero;
	}


}
