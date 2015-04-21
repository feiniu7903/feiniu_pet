package com.lvmama.comm.vst.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品时间价格表（销售类别）
 * @author ranlongfei 2013-12-20
 * @version
 */
public class VstProdGoodsTimePriceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2563711112637722166L;
	/**
	 * 销售产品类型ID（商品ID）
	 */
	private Long goodsId;
	/**
	 * 指定天
	 */
    private Date specDate;
    /**
     * 售价
     */
    private Long price;
    /**
     * 结算价
     */
    private Long settlementPrice;
    /**
     * 库存
     */
    private Long dayStock;
    /**
     * 资源确认Y要，N不要
     */
    private String resourceConfirm;
    /**
     * 是否可超卖Y是N否
     */
    private String overSaleFlag;
    /**
     * 提前预订时间（分）
     */
    private Long aheadHour;
    /**
     * 最晚取消时间（分）
     */
	private Long cancelHour;
    /**
     * 取消策略
     */
    private String cancelStrategy;
    /**
     * 是否禁售Y是N不是
     */
    private String forbiddenSell;
    /**
     * 早餐数
     */
    private Integer breakfastCount;
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Date getSpecDate() {
		return specDate;
	}
	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Long getDayStock() {
		return dayStock;
	}
	public void setDayStock(Long dayStock) {
		this.dayStock = dayStock;
	}
	public String getResourceConfirm() {
		return resourceConfirm;
	}
	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}
	public String getOverSaleFlag() {
		return overSaleFlag;
	}
	public void setOverSaleFlag(String overSaleFlag) {
		this.overSaleFlag = overSaleFlag;
	}
	public Long getAheadHour() {
		return aheadHour;
	}
	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}
	public Long getCancelHour() {
		return cancelHour;
	}
	public void setCancelHour(Long cancelHour) {
		this.cancelHour = cancelHour;
	}
	public String getCancelStrategy() {
		return cancelStrategy;
	}
	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}
	public String getForbiddenSell() {
		return forbiddenSell;
	}
	public void setForbiddenSell(String forbiddenSell) {
		this.forbiddenSell = forbiddenSell;
	}
	public Integer getBreakfastCount() {
		return breakfastCount;
	}
	public void setBreakfastCount(Integer breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

}
