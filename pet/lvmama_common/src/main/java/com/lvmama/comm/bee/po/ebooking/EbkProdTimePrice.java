package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @since 2013-09-24
 */
public class EbkProdTimePrice implements Serializable {

    private static final long serialVersionUID = 138001285715572111L;

    /**
     * column EBK_PROD_TIME_PRICE.TIME_PRICE_ID
     */
    private Long timePriceId;

    /**
     * column EBK_PROD_TIME_PRICE.SPEC_DATE
     */
    private Date specDate;

    /**
     * column EBK_PROD_TIME_PRICE.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_TIME_PRICE.PROD_BRANCH_ID
     */
    private Long prodBranchId;

    /**
     * column EBK_PROD_TIME_PRICE.PRICE
     */
    private Long price;

    /**
     * column EBK_PROD_TIME_PRICE.MARKET_PRICE
     */
    private Long marketPrice;

    /**
     * column EBK_PROD_TIME_PRICE.SETTLEMENT_PRICE
     */
    private Long settlementPrice;

    /**
     * column EBK_PROD_TIME_PRICE.DAY_STOCK
     */
    private Long dayStock;

    /**
     * column EBK_PROD_TIME_PRICE.RESOURCE_CONFIRM
     */
    private String resourceConfirm;

    /**
     * column EBK_PROD_TIME_PRICE.OVER_SALE
     */
    private String overSale;

    /**
     * column EBK_PROD_TIME_PRICE.DAY_STOCK_NUMBER
     */
    private String stockType;

    /**
     * column EBK_PROD_TIME_PRICE.AHEAD_HOUR
     */
    private Long aheadHour;

    /**
     * column EBK_PROD_TIME_PRICE.CANCEL_STRATEGY
     */
    private String cancelStrategy;

    /**
     * 是否仅售，Y/N
     * column EBK_PROD_TIME_PRICE.FORBIDDEN_SELL 
     */
    private String forbiddenSell;
    
    /**
     * 类别类型
     */
    private String branchType;

    /**
     * 是否与其他类别共享库存
     */
    private boolean haveShareStock = false;
    
    /**
     * 共享库存数
     */
    private String shareStockNum;
    
    private Integer breakfastCount;
    
    private String operateStatus;
    
    public static enum STOCK_TYPE{
    	ADD_STOCK("增加","+"),
    	MINUS_STOCK("减少","-"),
    	FIXED_STOCK("固定",""),
    	UNLIMITED_STOCK("不限","");
    	STOCK_TYPE(String cnName,String shortName){
    		this.cnName = cnName;
    		this.shortName = shortName;
    	};
    	private String cnName;
    	private String shortName;
    	public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public String getShortName(){
			return this.shortName;
		}
		public static String getCnName(String code){
			for(STOCK_TYPE item:STOCK_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public static String getShortName(String code){
			for(STOCK_TYPE item:STOCK_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getShortName();
				}
			}
			return code;
		}
    }
    public static enum OPERATE_STATUS{
    	ADD_OPERATE("增加"),
    	UPDATE_OPERATE("更新");
    	OPERATE_STATUS(String cnName){
    		this.cnName = cnName;
    	};
    	private String cnName;
    	public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(OPERATE_STATUS item:OPERATE_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
    }
    public Long getMarketPriceFen(){
    	return initPriceFen(marketPrice);
    }
    public Long getSettlementPriceFen(){
    	return initPriceFen(settlementPrice);
    }
    public Long getPriceFen(){
    	return initPriceFen(price);
    }
    public Long getMarketPriceYuan(){
    	return initPriceYuan(marketPrice);
    }
    public Long getSettlementPriceYuan(){
    	return initPriceYuan(settlementPrice);
    }
    public Long getPriceYuan(){
    	return initPriceYuan(price);
    }
    public String getStockTypeCh(){
    	return STOCK_TYPE.getCnName(stockType);
    }
    public String getStockTypeShort(){
    	return STOCK_TYPE.getShortName(stockType);
    }
    public boolean getGlCurrentDate(){
    	return  DateUtil.compareDateLessOneDayMore(DateUtil.accurateToDay(specDate),DateUtil.dsDay_Date(DateUtil.accurateToDay(new Date()),-1));
    }
    public String getDateStr() {
		return DateUtil.getFormatDate(specDate, "d");
	}
    public String getMonthStr() {
		String result = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(specDate);
		if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
			result = DateUtil.MONTHS[cal.get(Calendar.MONTH)];
		}
		return result;
	}
    public String getSpecDateCh(){
    	String result = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(specDate);
		if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
			result = DateUtil.MONTHS[cal.get(Calendar.MONTH)];
		}
		return result + DateUtil.getFormatDate(specDate, "yyyy-MM-dd");
    }
    
    public EbkProdTimePrice() {
        super();
    }

    public EbkProdTimePrice(Long timePriceId, Date specDate, Long productId, Long prodBranchId, Long price, Long marketPrice, Long settlementPrice, Long dayStock, String resourceConfirm, String overSale, String stockType, Long aheadHour, String cancelStrategy, String forbiddenSell) {
        this.timePriceId = timePriceId;
        this.specDate = specDate;
        this.productId = productId;
        this.prodBranchId = prodBranchId;
        this.price = price;
        this.marketPrice = marketPrice;
        this.settlementPrice = settlementPrice;
        this.dayStock = dayStock;
        this.resourceConfirm = resourceConfirm;
        this.overSale = overSale;
        this.stockType = stockType;
        this.aheadHour = aheadHour;
        this.cancelStrategy = cancelStrategy;
        this.forbiddenSell = forbiddenSell;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.TIME_PRICE_ID
     */
    public Long getTimePriceId() {
        return timePriceId;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.TIME_PRICE_ID
     * @param timePriceId
     */
    public void setTimePriceId(Long timePriceId) {
        this.timePriceId = timePriceId;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.SPEC_DATE
     */
    public Date getSpecDate() {
        return specDate;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.SPEC_DATE
     * @param specDate
     */
    public void setSpecDate(Date specDate) {
        this.specDate = specDate;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.PROD_BRANCH_ID
     */
    public Long getProdBranchId() {
        return prodBranchId;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.PROD_BRANCH_ID
     * @param prodBranchId
     */
    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.PRICE
     */
    public Long getPrice() {
        return price;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.PRICE
     * @param price
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.MARKET_PRICE
     */
    public Long getMarketPrice() {
        return marketPrice;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.MARKET_PRICE
     * @param marketPrice
     */
    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.SETTLEMENT_PRICE
     */
    public Long getSettlementPrice() {
        return settlementPrice;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.SETTLEMENT_PRICE
     * @param settlementPrice
     */
    public void setSettlementPrice(Long settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.DAY_STOCK
     */
    public Long getDayStock() {
        return dayStock;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.DAY_STOCK
     * @param dayStock
     */
    public void setDayStock(Long dayStock) {
        this.dayStock = dayStock;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.RESOURCE_CONFIRM
     */
    public String getResourceConfirm() {
        return resourceConfirm;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.RESOURCE_CONFIRM
     * @param resourceConfirm
     */
    public void setResourceConfirm(String resourceConfirm) {
        this.resourceConfirm = resourceConfirm;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.OVER_SALE
     */
    public String getOverSale() {
        return overSale;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.OVER_SALE
     * @param overSale
     */
    public void setOverSale(String overSale) {
        this.overSale = overSale;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.AHEAD_HOUR
     */
    public Long getAheadHour() {
        return aheadHour;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.AHEAD_HOUR
     * @param aheadHour
     */
    public void setAheadHour(Long aheadHour) {
        this.aheadHour = aheadHour;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.CANCEL_STRATEGY
     */
    public String getCancelStrategy() {
        return cancelStrategy;
    }
    public String getCancelStrategyZh() {
    	return Constant.EBK_CANCEL_STRATEGY.getCnName(this.cancelStrategy);
    }
    
    /**
     * setter for Column EBK_PROD_TIME_PRICE.CANCEL_STRATEGY
     * @param cancelStrategy
     */
    public void setCancelStrategy(String cancelStrategy) {
        this.cancelStrategy = cancelStrategy;
    }

    /**
     * getter for Column EBK_PROD_TIME_PRICE.FORBIDDEN_SELL
     */
    public String getForbiddenSell() {
        return forbiddenSell;
    }

    /**
     * setter for Column EBK_PROD_TIME_PRICE.FORBIDDEN_SELL
     * @param forbiddenSell
     */
    public void setForbiddenSell(String forbiddenSell) {
        this.forbiddenSell = forbiddenSell;
    }

	public Integer getBreakfastCount() {
		return breakfastCount;
	}

	public void setBreakfastCount(Integer breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

	@Override
	public String toString() {
		return "EbkProdTimePrice [timePriceId=" + timePriceId + ", specDate="
				+ specDate + ", productId=" + productId + ", prodBranchId="
				+ prodBranchId + ", price=" + price + ", marketPrice="
				+ marketPrice + ", settlementPrice=" + settlementPrice
				+ ", dayStock=" + dayStock + ", resourceConfirm="
				+ resourceConfirm + ", overSale=" + overSale
				+ ", stockType=" + stockType + ", aheadHour="
				+ aheadHour + ", cancelStrategy=" + cancelStrategy
				+ ", forbiddenSell=" + forbiddenSell + ", breakfastCount="
				+ breakfastCount + "]";
	}

	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getOperateStatus() {
		return operateStatus;
	}
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	private static Long initPriceFen(final Long price){
		if(null!=price && price.longValue()!=0){
			return price.longValue()*100;
		}
		return null;
	}
	private static Long initPriceYuan(final Long price){
		if(null!=price && price.longValue()!=0){
			return price.longValue()/100;
		}
		return null;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public boolean getHaveShareStock() {
		return haveShareStock;
	}
	public void setHaveShareStock(boolean haveShareStock) {
		this.haveShareStock = haveShareStock;
	}
	public String getShareStockNum() {
		return shareStockNum;
	}
	public void setShareStockNum(String shareStockNum) {
		this.shareStockNum = shareStockNum;
	}
	
	 public String getAheadHourStr() {
		 if (null != aheadHour) {
			 return String.valueOf(aheadHour);
		 }
		 return "";
	}
	
}