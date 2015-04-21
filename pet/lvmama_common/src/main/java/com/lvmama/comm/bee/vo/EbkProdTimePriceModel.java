package com.lvmama.comm.bee.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class EbkProdTimePriceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2507438895483711217L;
	
	private Date beginDate;
	private Date endDate;
	private List<Integer> weeks = new ArrayList<Integer>();
	private String days;
	private String forbiddenSell;
	private Integer breackfastCount;
	private Integer aheadHourDay;
	private Integer aheadHour;
	private Integer aheadHourSecend;
	private String cancelStrategy;
	private List<EbkProdTimePrice> priceStockSimples = new ArrayList<EbkProdTimePrice>();
	private String ebkProductViewType;
	private String isShareStock;
	private Integer shareStockNum;
	private List<Long> shareBranch = new ArrayList<Long>();
	public static enum MODEL_TYPE{
		NEW_PRICE_STOCK(0),
		UPDATE_PRICE(1),
		UPDATE_STOCK(2),
		UDPATE_PRICE_STOCK(3);
		private Integer type;
		MODEL_TYPE(Integer type){
			this.type = type;
		}
		public Integer getType(){
			return this.type;
		}
	}
	public String validate(Integer type){
		if (days == null) {
			if(null==beginDate){
				return "beginDate";
			}
			if(null==endDate){
				return "endDate";
			}
			if(null==weeks || (null!=weeks && weeks.isEmpty())){
				return "weeks";
			}
		}
		
		if(MODEL_TYPE.UPDATE_PRICE.getType()!=type){
			if(null==forbiddenSell){
				return "forbiddenSell";
			}
		}
		if(MODEL_TYPE.UPDATE_STOCK.getType()!=type){
			if(null==aheadHourDay){
				aheadHourDay = 0;
			}
			if(null==aheadHour){
				aheadHour = 0;
			}
			if(null==aheadHourSecend){
				aheadHourSecend = 0;
			}
			if((aheadHourDay+aheadHour+aheadHourSecend)==0){
				return "aheadHour";
			}
			if(StringUtil.isEmptyString(cancelStrategy)){
				return "cancelStrategy";
			}
		}
		if(Constant.EBK_PRODUCT_VIEW_TYPE.HOTEL.name().equalsIgnoreCase(ebkProductViewType) && null==breackfastCount && MODEL_TYPE.UPDATE_PRICE.getType()!=type){
			return "breackfastCount";
		}
		if(null==priceStockSimples || (null!=priceStockSimples && priceStockSimples.isEmpty())){
			return "priceStockSimples";
		}
		return null;
	}
	public Map<String,Object> validatePriceAndStock(){
		Map<String,Object> validMap = new HashMap<String,Object>();
		for(int i=0;i<priceStockSimples.size();i++){
			EbkProdTimePrice priceStockSimple = priceStockSimples.get(i);
			if(!Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(priceStockSimple.getBranchType())){
				if(null==priceStockSimple.getSettlementPrice()){
					validMap.put("index", i);
					validMap.put("column", "settlementPrice");
				}
				if(Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equalsIgnoreCase(ebkProductViewType)){
					if(null==priceStockSimple.getPrice()){
						validMap.put("index", i);
						validMap.put("column", "price");
					}
				}
				if(null==priceStockSimple.getMarketPrice()){
					validMap.put("index", i);
					validMap.put("column", "marketPrice");
				}
			}
			
			if(null==priceStockSimple.getStockType()){
				validMap.put("index", i);
				validMap.put("column", "stockType");
			}
			if(null==priceStockSimple.getDayStock() && !EbkProdTimePrice.STOCK_TYPE.UNLIMITED_STOCK.name().equalsIgnoreCase(priceStockSimple.getStockType())){
				validMap.put("index", i);
				validMap.put("column", "dayStock");
			}
			if(null==priceStockSimple.getResourceConfirm()){
				validMap.put("index", i);
				validMap.put("column", "resourceConfirm");
			}
			if(null==priceStockSimple.getOverSale()){
				validMap.put("index", i);
				validMap.put("column", "overSale");
			}
		}
		return validMap.isEmpty()?null:validMap;
	}
	public Map<String,Object> validateStock(){
		Map<String,Object> validMap = new HashMap<String,Object>();
		for(int i=0;i<priceStockSimples.size();i++){
			EbkProdTimePrice priceStockSimple = priceStockSimples.get(i);
			if(null==priceStockSimple.getStockType()){
				validMap.put("index", i);
				validMap.put("column", "stockType");
			}
			if(null==priceStockSimple.getDayStock()&& !EbkProdTimePrice.STOCK_TYPE.UNLIMITED_STOCK.name().equalsIgnoreCase(priceStockSimple.getStockType())){
				validMap.put("index", i);
				validMap.put("column", "dayStock");
			}
			if(null==priceStockSimple.getResourceConfirm()){
				validMap.put("index", i);
				validMap.put("column", "resourceConfirm");
			}
			if(null==priceStockSimple.getOverSale()){
				validMap.put("index", i);
				validMap.put("column", "overSale");
			}
		}
		return validMap.isEmpty()?null:validMap;
	}
	public Map<String,Object> validatePrice(){
		Map<String,Object> validMap = new HashMap<String,Object>();
		for(int i=0;i<priceStockSimples.size();i++){
			EbkProdTimePrice priceStockSimple = priceStockSimples.get(i);
			if(!Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(priceStockSimple.getBranchType())){
				if(null==priceStockSimple.getSettlementPrice()){
					validMap.put("index", i);
					validMap.put("column", "settlementPrice");
				}
				if(Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equalsIgnoreCase(ebkProductViewType)){
					if(null==priceStockSimple.getPrice()){
						validMap.put("index", i);
						validMap.put("column", "price");
					}
				}
				if(null==priceStockSimple.getMarketPrice()){
					validMap.put("index", i);
					validMap.put("column", "marketPrice");
				}
			}
		}
		return validMap.isEmpty()?null:validMap;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<Integer> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<Integer> weeks) {
		this.weeks = weeks;
	}
	public String getForbiddenSell() {
		return forbiddenSell;
	}
	public void setForbiddenSell(String forbiddenSell) {
		this.forbiddenSell = forbiddenSell;
	}
	public Integer getBreackfastCount() {
		return breackfastCount;
	}
	public void setBreackfastCount(Integer breackfastCount) {
		this.breackfastCount = breackfastCount;
	}
	public Integer getAheadHourDay() {
		if(null==aheadHourDay){
			return 0;
		}
		return aheadHourDay;
	}
	public void setAheadHourDay(Integer aheadHourDay) {
		this.aheadHourDay = aheadHourDay;
	}
	public Integer getAheadHour() {
		if(null==aheadHour){
			return 0;
		}
		return aheadHour;
	}
	public void setAheadHour(Integer aheadHour) {
		this.aheadHour = aheadHour;
	}
	public Integer getAheadHourSecend() {
		if(null==aheadHourSecend){
			return 0;
		}
		return aheadHourSecend;
	}
	public void setAheadHourSecend(Integer aheadHourSecend) {
		this.aheadHourSecend = aheadHourSecend;
	}
	public String getCancelStrategy() {
		return cancelStrategy;
	}
	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}
	public List<EbkProdTimePrice> getPriceStockSimples() {
		if(null==priceStockSimples){
			priceStockSimples = new ArrayList<EbkProdTimePrice>();
		}
		return priceStockSimples;
	}
	public void setPriceStockSimples(List<EbkProdTimePrice> priceStockSimples) {
		this.priceStockSimples = priceStockSimples;
	}
	public String getEbkProductViewType() {
		return ebkProductViewType;
	}
	public void setEbkProductViewType(String ebkProductViewType) {
		this.ebkProductViewType = ebkProductViewType;
	}
	
	private String getWeeksCh(){
		Map<Integer,String> weekMap =new HashMap<Integer,String>();
		weekMap.put(1, "周日");
		weekMap.put(2, "周一");
		weekMap.put(3, "周二");
		weekMap.put(4, "周三");
		weekMap.put(5, "周四");
		weekMap.put(6, "周五");
		weekMap.put(7, "周六");
		StringBuffer weekch = new StringBuffer();
		for(Integer week:weeks){
			weekch.append(weekMap.get(week)+"　");
		}
		return weekch.toString();
	}
	public String toStringCh(Long prodBranchId,Integer type) {
		StringBuffer info = new StringBuffer();
		info.append("设置时间价格 [");
		if ((null == beginDate || null == endDate) && null != days) {
			String[] dayss = days.split(",");
			for (int i = 0; i < dayss.length; i++) {
				info.append(dayss[i]);
				if (i < dayss.length -1 ) {
					info.append(",");
				}
			}
		} else {
			info.append("开始时间="+DateUtil.formatDate(beginDate, "yyyy-MM-dd"));
			info.append(", 结束时间="+DateUtil.formatDate(endDate, "yyyy-MM-dd"));
		}
		
		info.append("], 星期="+getWeeksCh());
		info.append(", 禁售="+("true".equalsIgnoreCase(forbiddenSell)?"是":"否"));
		if(Constant.EBK_PRODUCT_VIEW_TYPE.HOTEL.name().equalsIgnoreCase(ebkProductViewType) && null==breackfastCount){
			info.append(", 早餐份数="+breackfastCount);
		}
		for(EbkProdTimePrice price:priceStockSimples){
			if(price.getProdBranchId().longValue()==prodBranchId.longValue()){
				if(MODEL_TYPE.NEW_PRICE_STOCK.getType()==type || MODEL_TYPE.UPDATE_PRICE.getType()==type){
					info.append(", 提前预订 ="+aheadHourDay+"天"+aheadHour+"小时"+aheadHourSecend+"分");
					info.append(", 退改策略="+Constant.CANCEL_STRATEGY.getCnName(cancelStrategy));
					info.append(", 门市价="+price.getMarketPrice());
					info.append(", 销售价="+price.getPrice());
					info.append(", 结算价="+price.getSettlementPrice());
				}
				if(MODEL_TYPE.NEW_PRICE_STOCK.getType()==type || MODEL_TYPE.UPDATE_STOCK.getType()==type){
					info.append(", 库存增减类型="+EbkProdTimePrice.STOCK_TYPE.getCnName(price.getStockType()));
					info.append(", 库存数量="+price.getDayStock());
				}
				if(MODEL_TYPE.NEW_PRICE_STOCK.getType()==type || MODEL_TYPE.UPDATE_STOCK.getType()==type){
					info.append(", 资源审核="+("true".equalsIgnoreCase(price.getResourceConfirm())?"是":"否"));
					info.append(", 超卖="+("true".equalsIgnoreCase(price.getOverSale())?"是":"否"));
				}
			}
		}
		return info.toString();
	}
	@Override
	public String toString() {
		return "EbkProdTimePriceModel [beginDate=" + beginDate + ", endDate="
				+ endDate + ", weeks=" + weeks + ", forbiddenSell="
				+ forbiddenSell + ", breackfastCount=" + breackfastCount
				+ ", aheadHourDay=" + aheadHourDay + ", aheadHour=" + aheadHour
				+ ", aheadHourSecend=" + aheadHourSecend + ", cancelStrategy="
				+ cancelStrategy + ", priceStockSimples=" + priceStockSimples
				+ ", ebkProductViewType=" + ebkProductViewType + "]";
	}
	public String getIsShareStock() {
		return isShareStock;
	}
	public void setIsShareStock(String isShareStock) {
		this.isShareStock = isShareStock;
	}
	public Integer getShareStockNum() {
		return shareStockNum;
	}
	public void setShareStockNum(Integer shareStockNum) {
		this.shareStockNum = shareStockNum;
	}
	public List<Long> getShareBranch() {
		return shareBranch;
	}
	public void setShareBranch(List<Long> shareBranch) {
		this.shareBranch = shareBranch;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
}
