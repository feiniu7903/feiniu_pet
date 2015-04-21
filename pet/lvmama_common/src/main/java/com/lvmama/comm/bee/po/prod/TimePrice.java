package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;

public class TimePrice implements Serializable {
	private static final long serialVersionUID = -1626861179409481857L;
	protected long timePriceId;
	protected long productId;
	protected long prodBranchId;
	protected long metaBranchId;
	protected Date specDate;

	protected long price;

	protected long marketPrice;
	protected long settlementPrice;
	protected long dayStock = -2;
	/**
	 * 是否增加库存
	 * <br>增加True，减少False，直接库存量null
	 */
	private String isAddDayStock;

	private Date beginDate;
	private Date endDate;
	private String close = "false";

	private String weekOpen;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;

	private String priceF;
	private long marketPriceF;
	private String settlementPriceF;

	private String resourceConfirm = "true";

	private String overSale = "false";

	private boolean onlyForLeave = false;
	
	// 默认为未更新
	private boolean stockUpdated = false;
	private boolean overSaleUpdated = false;
	private boolean resourceConfirmUpdated = false;
	// 标记：某种采购产品可超卖&&库存为0
	private boolean overSaleFlagFirst = false;
	// 标记：某种采购产品不可超卖&&库存!=0
	private boolean overSaleFlagSecond = false;
	
	//最晚修改或取消小时数
	private Long cancelHour;
	
	//提前预订小时数
	private Long aheadHour;
	/**价格类型**/
	private String priceType;
	/**比较价格**/
	private Long ratePrice;
	/**固定加价**/
	private Long fixedAddPrice;
	private String fixedAddPriceF;
	//早餐份数
	private Long breakfastCount;
	//建议售价
	private Long suggestPrice;
	//总库存数量
	private Long totalDayStock;

	/**
	 * 自动清库存时间
	 * 为空值时不做清除处理
	 */
	private Long zeroStockHour;
	
	//最早入园/使用时间
	private String earliestUseTime;
	
	//最晚入园/使用时间
	private String latestUseTime;
	//对最晚入园时间做逻辑使用，不存入数据库当中
	private Date latestUseTimeDate;
	private Date earliestUseTimeDate;
	private String cancelStrategy;
	
	/**
	 * 优惠展示信息
	 */
	private String favorJsonParams;
	//是否支付给驴妈妈
	private String payToLvmama;
	//最短换票间隔小时数 
	private Long lastTicketTime;
	//最晚换票前多少小时数可售
	private Long lastPassTime;
	//是否支持手机当天订
	private String todayOrderAble;
	//最晚预定时间
	private Date lastReserveTime;
	 
	//展示'促'优惠标签
	private long cuCouponFlag = 0;
	
	private Long multiJourneyId;
	
	private String multiJourneyName;
	
	//标记 库存不足 邮件是否发送过
	private String stockFlag;
	
	public String getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}

	public long getTimePriceId() {
		return timePriceId;
	}

	public void setTimePriceId(long timePriceId) {
		this.timePriceId = timePriceId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getWeekOpen() {
		return weekOpen;
	}

	public void setWeekOpen(String weekOpen) {
		this.weekOpen = weekOpen;
	}

	public String getMonday() {
		return monday;
	}

	public void setMonday(String monday) {
		this.monday = monday;
	}

	public String getTuesday() {
		return tuesday;
	}

	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}

	public String getWednesday() {
		return wednesday;
	}

	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}

	public String getThursday() {
		return thursday;
	}

	public void setThursday(String thursday) {
		this.thursday = thursday;
	}

	public String getFriday() {
		return friday;
	}

	public void setFriday(String friday) {
		this.friday = friday;
	}

	public String getSaturday() {
		return saturday;
	}

	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}

	public String getSunday() {
		return sunday;
	}

	public void setSunday(String sunday) {
		this.sunday = sunday;
	}

	public long getMarketPriceF() {
		return marketPriceF;
	}

	public void setMarketPriceF(long marketPriceF) {
		this.marketPriceF = marketPriceF;
		this.marketPrice = PriceUtil.convertToFen(marketPriceF);
	}

	public void plusMarketPrice(long p) {
		if (marketPrice != 0) {
			marketPrice += p;
		} else {
			marketPrice = p;
		}
	}

	public long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(long marketPrice) {
		this.marketPrice = marketPrice;
		this.marketPriceF = (long) PriceUtil.convertToYuan(marketPrice);
	}

	public String getSettlementPriceF() {
		return settlementPriceF;
	}

	public void setSettlementPriceF(String settlementPriceF) {
		this.settlementPriceF = settlementPriceF;
		/**
		 * 采购产品时间价格表,修改选项选中 ‘只修改库存’ 或者结算价选中设置为‘0’时，此时表单提交settlementPriceF为空
		 * 不加此判断直接做类型转换会出现NumberFormatException
		 * */
		if(!StringUtil.isEmptyString(settlementPriceF)){
			this.settlementPrice = PriceUtil.convertToFen(new BigDecimal(settlementPriceF));			
		}
	}

	public void plusSettlementPrice(long p) {
		if (settlementPrice != 0) {
			settlementPrice += p;
		} else {
			settlementPrice = p;
		}
	}

	public long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(long settlementPrice) {
		this.settlementPrice = settlementPrice;
		this.settlementPriceF = String.valueOf(PriceUtil.convertToYuan(settlementPrice));
	}

	public long getDayStock() {
		return dayStock;
	}

	public void setDayStock(long dayStock) {
		this.dayStock = dayStock;
	}

	public String getPriceF() {
		return priceF;
	}

	public String getPriceInt() {
		if (this.priceF != null) {
			BigDecimal p = new BigDecimal(this.priceF);
			return (p.divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP).intValue()) + "";
		}
		return "";
	}

	public void setPriceF(String priceF) {
		this.priceF = priceF;
		/**
		 * 销售产品时间价格表,修改选项选中 ‘修改最晚取消小时数’ 或者驴妈妈价选中设置为‘0’时，此时表单提交priceF为空
		 * 不加此判断直接做类型转换会出现NumberFormatException
		 * */
		if(!StringUtil.isEmptyString(priceF)){
			this.price = PriceUtil.convertToFen(new BigDecimal(priceF));		
		}
		
	}

	public float getSellPriceFloat() {
		return PriceUtil.convertToYuan(this.price);
	}
	public float getMarketPriceFloat() {
		return PriceUtil.convertToYuan(this.marketPrice);
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
		this.priceF = String.valueOf(PriceUtil.convertToYuan(price));
	}

	public Date getSpecDate() {
		return specDate;
	}

	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}

	public String getResourceConfirm() {
		return resourceConfirm;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	public boolean isNeedResourceConfirm() {
		return "true".equalsIgnoreCase(this.checkResourceConfirm());
	}

	public String getOverSale() {
		return overSale;
	}

	public void setOverSale(String overSale) {
		this.overSale = overSale;
	}

	public Long getCancelHour() {
		return cancelHour;
	}
	
	public Float getCancelHourFloat() {
		if(cancelHour != null) {
			return DateUtil.convertToHours(cancelHour);
		}
		return 0f;
	}

	public void setCancelHour(Long cancelHour) {
		this.cancelHour = cancelHour;
	}

	public Long getAheadHour() {
		return aheadHour;
	}

	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}
	
	public Float getAheadHourFloat() {
		if(aheadHour != null) {
			return DateUtil.convertToHours(aheadHour);
		}
		return 0f;
	}
	
	public boolean isSpecday(int dayInWeek) {
		if ("true".equalsIgnoreCase(weekOpen)) {
			switch (dayInWeek) {
			case 1:
				return "true".equalsIgnoreCase(sunday);
			case 2:
				return "true".equalsIgnoreCase(monday);
			case 3:
				return "true".equalsIgnoreCase(tuesday);
			case 4:
				return "true".equalsIgnoreCase(wednesday);
			case 5:
				return "true".equalsIgnoreCase(thursday);
			case 6:
				return "true".equalsIgnoreCase(friday);
			case 7:
				return "true".equalsIgnoreCase(saturday);
			}
			return false;
		} else {
			return true;
		}
	}

	public String getDateStr() {
		String result = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(specDate);
		if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
			result = DateUtil.MONTHS[cal.get(Calendar.MONTH)];
		}
		return result + DateUtil.getFormatDate(specDate, "d");
	}

	public String getDayStockStr() {
		if (dayStock == -1) {
			return "库:不限";
		} else if (dayStock == -2){
			return "库:";
		}else {
			return "库:" + dayStock;
		}
	}

	public String getPriceStr() {
		String str = "售:";
		if (this.timePriceId > 0) {
			str += (float) price / 100;
		}
		return str;
	}

	public String getSettlementPriceStr() {
		return "结:" + (float) settlementPrice / 100;
	}

	public String getMarketPriceStr() {
		return "市:" + marketPrice / 100;
	}

	public String getResourceConfirmStr() {
		return "资源:" + ("true".equalsIgnoreCase(resourceConfirm) ? "是" : "否");
	}

	public String getOverSaleStr() {
		return "超卖:" + ("true".equalsIgnoreCase(overSale) ? "是" : "否");
	}

	public String getCancelHourStr() {
		if(cancelHour==null){
			return "";
		}
		return "最晚取消小时数:" + getCancelHourFloat();
	}
	
	public String getAheadHourStr() {
		if(aheadHour==null){
			return "";
		}
		return "提前预订小时数:" + getAheadHourFloat();
	}
	
	/**
	 * 修改销售产品的"是否需要资源确认".
	 * 
	 * @param theResourceConfirm
	 *            是否需要资源确认
	 */
	public void updateResourceConfirm(String theResourceConfirm) {
		if (!resourceConfirmUpdated) {
			resourceConfirmUpdated = true;
			this.resourceConfirm = theResourceConfirm;
		} else if ("true".equalsIgnoreCase(theResourceConfirm)) {
			this.resourceConfirm = "true";
		}
	}

	public void updateOverSale(String theOverSale) {
		if (!overSaleUpdated) {
			overSaleUpdated = true;
			this.overSale = theOverSale;
		} else if ("false".equalsIgnoreCase(theOverSale)) {
			this.overSale = "false";
		}
	}

	/**
	 * 修改销售产品的"是否可超卖".
	 * 
	 * @param theOverSale
	 *            是否可超卖
	 * @param stock
	 *            库存
	 */
	public void updateOverSale(String theOverSale, long stock) {
		// 标记：某种采购产品可超卖&&库存为0
		if ("true".equalsIgnoreCase(theOverSale) && stock == 0) {
			overSaleFlagFirst = true;
		}
		// 标记：某种采购产品不可超卖&&库存!=0
		if ("false".equalsIgnoreCase(theOverSale) && stock != 0) {
			overSaleFlagSecond = true;
		}
		// 第一次，通过接受传过来的参数来初始化
		if (!overSaleUpdated) {
			overSaleUpdated = true;
			this.overSale = theOverSale;
			return;
		}

		// 当某销售产品由多种采购产品组成时，存在A采购产品可超卖、库存为0 B采购产品不可超卖、库存>0时，需要将销售产品设置为可超卖
		if (overSaleFlagFirst && overSaleFlagSecond) {
			this.overSale = "true";
		} else if (!(overSaleFlagFirst && overSaleFlagSecond)) {
			if ("false".equalsIgnoreCase(theOverSale)) {
				this.overSale = "false";
			}
		} else if ("true".equalsIgnoreCase(theOverSale) && overSale == "true") {
			this.overSale = "true";
		}

	}

	public void updateDayStock(long stock, boolean totalDecrease) {
		if (totalDecrease && !stockUpdated) {
			dayStock = stock;
			stockUpdated = true;
		} else {
			priUpdate(stock);
		}
	}

	private void priUpdate(long stock) {
		if ((dayStock == -2 && stock == -1L) || dayStock == -1 || dayStock == -2) { // 无论如何都用传入值代替
			dayStock = stock;
			stockUpdated = true;
			return;
		}
		if (dayStock == 0) { // 如果原库存是0，则保持0
			stockUpdated = true;
			return;
		}
		if (dayStock > stock && stock >= 0) { // 如果传入库存小于原库存，则使用新传入库存
			stockUpdated = true;
			dayStock = stock;
			return;
		}
	}

	/**
	 * 是否可超卖.
	 * 
	 * @return boolean
	 */
	public boolean isOverSaleAble() {
		return "true".equalsIgnoreCase(overSale);
	}

	/**
	 * 是否限库存. -1为不限库存 -2为初始值，表示未初始化
	 * 
	 * @return boolean
	 */
	public boolean isLimitDayStock() {
		return dayStock != -1;
	}

	/**
	 * 是否需要纯资源确认. 只包含资源确认这一个字段的判断.
	 * 
	 * @return boolean
	 */
	public boolean isPureNeedResourceConfirm() {
		return "true".equalsIgnoreCase(resourceConfirm);
	}

	public boolean isClosed() {
		return "true".equalsIgnoreCase(close);
	}

	public boolean isSpecifiedDateDuration() {
		return beginDate != null && endDate != null;
	}

	public boolean isSellable(long needStock) {
		if (this.dayStock == -1) {
			return true;
		}
		if (this.dayStock > 0 && dayStock >= needStock) {
			return true;
		}
		if (this.dayStock == 0) {
			return isOverSaleAble();
		}
		return false;
	}

	public String checkResourceConfirm() {
		// 当不需要资源确认，库存为0时,设置为资源需确认
		if (this.dayStock == 0 && "false".equals(this.resourceConfirm) && this.isOverSaleAble()) {
			this.resourceConfirm = "true";
		}
		return resourceConfirm;
	}

	public boolean isOnlyForLeave() {
		return onlyForLeave;
	}

	public void setOnlyForLeave(boolean onlyForLeave) {
		this.onlyForLeave = onlyForLeave;
	}
 
	public long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public long getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	/**
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	/**
	 * @return the ratePrice
	 */
	public Long getRatePrice() {
		return ratePrice;
	}

	/**
	 * @param ratePrice the ratePrice to set
	 */
	public void setRatePrice(Long ratePrice) {
		this.ratePrice = ratePrice;
	}

	/**
	 * @return the fixedAddPrice
	 */
	public Long getFixedAddPrice() {
		return fixedAddPrice;
	}

	/**
	 * @param fixedAddPrice the fixedAddPrice to set
	 */
	public void setFixedAddPrice(Long fixedAddPrice) {
		this.fixedAddPrice = fixedAddPrice;
		this.fixedAddPriceF=String.valueOf(PriceUtil.convertToYuan(fixedAddPrice));
	}
	
	public Long getBreakfastCount() {
		return breakfastCount;
	}

	public void setBreakfastCount(Long breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

	public Long getSuggestPrice() {
		if(this.suggestPrice==null){
			return null;
		}
		return suggestPrice;
	}
	
	public Float getSuggestPriceF(){
		if(this.suggestPrice==null){
			return null;
		}
		return PriceUtil.convertToYuan(suggestPrice);					
	}
	
	public void setSuggestPriceF(Float sp){
		if(sp==null){
			suggestPrice = null;
		}else {
			suggestPrice = PriceUtil.convertToFen(sp);		
		}
					
	}
	
	public String getSuggestPriceStr(){
		if(this.suggestPrice==null){
			return "";
		}
		return  "建："+getSuggestPriceF();
	}

	public void setSuggestPrice(Long suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public Long getTotalDayStock() {
		return totalDayStock;
	}

	public void setTotalDayStock(Long totalDayStock) {
		this.totalDayStock = totalDayStock;
	}

	/**
	 * @return the fixedAddPriceF
	 */
	public String getFixedAddPriceF() {
		return fixedAddPriceF;
	}

	/**
	 * @param fixedAddPriceF the fixedAddPriceF to set
	 */
	public void setFixedAddPriceF(String fixedAddPriceF) {
		this.fixedAddPriceF = fixedAddPriceF;
		if(StringUtils.isNotEmpty(fixedAddPriceF)){
			this.fixedAddPrice = PriceUtil.convertToFen(new BigDecimal(fixedAddPriceF));
		}
	}

	/**
	 * 读取价格模式
	 * @return
	 */
	public String getPriceDesc(){
		StringBuffer sb=new StringBuffer();
		if(Constant.PRICE_TYPE.RATE_PRICE.name().equals(priceType)){
			sb.append("比例加价模式<br/>X");
			sb.append(1+PriceUtil.convertToYuan(ratePrice));
		}else if(Constant.PRICE_TYPE.FIXED_PRICE.name().equals(priceType)){
			sb.append("固定价格模式");
		}else if(Constant.PRICE_TYPE.FIXED_ADD_PRICE.name().equals(priceType)){
			sb.append("固定加价模式<br/>+");
			sb.append(PriceUtil.convertToYuan(fixedAddPrice));
		}
		
		return sb.toString();
	}
	
	public String getZeroStockStr(){
		if(zeroStockHour!=null){
			return "自动清库存小时数:"+zeroStockHour;
		}else{
			return "";
		}
	}
	
    //最晚预定时间
	public Date getLatestScheduledTime(){
		 Date latestScheduledTime=this.specDate;
		 if(this.aheadHour!=null && this.aheadHour!=0){
			 latestScheduledTime=DateUtils.addMinutes(this.specDate,-this.aheadHour.intValue());
		 }
	     return latestScheduledTime;
	}
	
	public Long getZeroStockHour() {
		return zeroStockHour;
	}

	public void setZeroStockHour(Long zeroStockHour) {
		this.zeroStockHour = zeroStockHour;
	}

	public String getIsAddDayStock() {
		return isAddDayStock;
	}

	public void setIsAddDayStock(String isAddDayStock) {
		this.isAddDayStock = isAddDayStock;
	}

	@Override
	public String toString() {
		return "TimePrice [productId=" + productId + ", prodBranchId=" + prodBranchId + ", metaBranchId=" + metaBranchId + ", specDate=" + specDate + ", price=" + price + ", marketPrice=" + marketPrice + ", settlementPrice=" + settlementPrice
				+ ", dayStock=" + dayStock + "]";
	}

	public String getEarliestUseTime() {
		return earliestUseTime;
	}

	public void setEarliestUseTime(String earliestUseTime) {
		this.earliestUseTime = earliestUseTime;
	}

	public String getLatestUseTime() {
		return latestUseTime;
	}

	public void setLatestUseTime(String latestUseTime) {
		this.latestUseTime = latestUseTime;
	}

	public void setAheadHourFloat(Float aheadHourFloat) {
		if(aheadHourFloat != null) {
			this.aheadHour = DateUtil.convertToMinutes(aheadHourFloat);
		}
	}

	public void setCancelHourFloat(Float cancelHourFloat) {
		if(cancelHourFloat != null) {
			this.cancelHour = DateUtil.convertToMinutes(cancelHourFloat);
		}
	}

	public String getFavorJsonParams() {
		return favorJsonParams;
	}

	public void setFavorJsonParams(String favorJsonParams) {
		this.favorJsonParams = favorJsonParams;
	}

	/**
	 * 在属性不存在时直接计算
	 * @return
	 */
	public Date getLatestUseTimeDate(){
		if(latestUseTimeDate==null){
			latestUseTimeDate = TimePriceUtil.converTime(specDate, latestUseTime);
		}
		return latestUseTimeDate;
	}
	
	public void updateLatesUseDate(Date date){
		if(date==null){
			return;
		}
		if(latestUseTimeDate==null){
			latestUseTimeDate = new Date(date.getTime());//为空时直接使用
		}else if(date.before(latestUseTimeDate)){//不为空时比较谁早
			latestUseTimeDate = new Date(date.getTime());
		}
	}

	public Date getEarliestUseTimeDate() {
		if(earliestUseTimeDate==null){
			earliestUseTimeDate = TimePriceUtil.converTime(specDate, earliestUseTime);
		}
		return earliestUseTimeDate;
	}
	
	public void updateEarliestUseTimeDate(Date date){
		if(date==null){
			return;
		}
		if(earliestUseTimeDate==null){
			earliestUseTimeDate = new Date(date.getTime());
		}else if(date.after(earliestUseTimeDate)){
			earliestUseTimeDate = new Date(date.getTime());
		}
		
	}
	
	public String getLatestUseTimeStr() {
		if(this.latestUseTime == null) {
			return "";
		} 
		return "最晚换票/使用时间：" + latestUseTime;
	}
	
	public String getEarliestUseTimeStr() {
		if(this.earliestUseTime == null) {
			return "";
		} 
		return "最早换票/使用时间：" + earliestUseTime;
	}

	public String getCancelStrategy() {
		return cancelStrategy;
	}

	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}
	
	public String getZhCancelStrategy() {
		if(isAble()) {
			return "可退改";
		} else if(isForbid()) {
			return "不退不改";
		} else {
			return "人工确认退改";
		}
	}
	
	public String getPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}


	public Long getLastTicketTime() {
		return lastTicketTime;
	}

	public void setLastTicketTime(Long lastTicketTime) {
		this.lastTicketTime = lastTicketTime;
	}

	public Long getLastPassTime() {
		return lastPassTime;
	}

	public void setLastPassTime(Long lastPassTime) {
		this.lastPassTime = lastPassTime;
	}

	public String getTodayOrderAble() {
		return todayOrderAble;
	}

	public void setTodayOrderAble(String todayOrderAble) {
		this.todayOrderAble = todayOrderAble;
	}

	public Date getLastReserveTime() {
		return lastReserveTime;
	}

	public void setLastReserveTime(Date lastReserveTime) {
		this.lastReserveTime = lastReserveTime;
	}

	/**
	 * 退改策略
	 * */
	public boolean isAble() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.ABLE.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	
	public boolean isManual() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.MANUAL.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	
	public boolean isForbid() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	/**
	 * 提前小时数
	 */
	public String getAheadHourByPayKind(){
		if(lastTicketTime==null){
			return null;
		}
		if("true".equals(payToLvmama)){
			return "提前"+DateUtil.convertToHours(lastTicketTime+30)+"小时";
		}else{
			return "提前"+DateUtil.convertToHours(lastTicketTime)+"小时";
		}
	}
	/**
	 * 最晚预定时间HH:mm
	 */
	public String getLastReserveHourByPayKind(){
		//如果是支付给驴妈妈的就在减30分钟
		if(lastReserveTime!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastReserveTime);
			if("true".equals(payToLvmama)){
				calendar.add(Calendar.MINUTE, -30);
			}
			return DateUtil.formatDate(calendar.getTime(), "HH:mm");
		}
		return null;
	}
	/**
	 * 更新预定时间
	 * @param date
	 * @param lastPassTime
	 * @param lastTicketTime
	 */
	public void updateLastReserveTime(Date date,Long lastPassTime,Long lastTicketTime){
		if(date!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(lastTicketTime!=null){
				calendar.add(Calendar.MINUTE, -(lastTicketTime.intValue())
				);
			}
			if(lastPassTime!=null){
				calendar.add(Calendar.MINUTE, -(lastPassTime.intValue())
				);
			}
			if(lastReserveTime==null){
				lastReserveTime = calendar.getTime();//为空时直接使用
			}else if(calendar.getTime().before(lastReserveTime)){//不为空时比较谁早
				lastReserveTime =calendar.getTime();
			}
		}
		this.updateLastPassTicketTime(lastPassTime,lastTicketTime);
	}
	/**
	 * 更新最短多少时间提前时间,最短换票间隔小时数
	 * @param lastTicketTime
	 */
	public void updateLastPassTicketTime(Long lastPassTime,Long lastTicketTime){
		if(lastPassTime!=null){
			if(this.lastPassTime==null){
				this.lastPassTime = lastPassTime;
			}else if(this.lastPassTime<lastPassTime){
				this.lastPassTime = lastPassTime;
			}
		}
		if(lastTicketTime!=null){
			if(this.lastTicketTime==null){
				this.lastTicketTime = lastTicketTime;
			}else if(this.lastTicketTime<lastTicketTime){
				this.lastTicketTime = lastTicketTime;
			}
		}
	}

	public long getCuCouponFlag() {
		return cuCouponFlag;
	}

	public void setCuCouponFlag(long cuCouponFlag) {
		this.cuCouponFlag = cuCouponFlag;
	}

	public Long getMultiJourneyId() {
		return multiJourneyId;
	}

	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}

	public String getMultiJourneyName() {
		return multiJourneyName;
	}

	public void setMultiJourneyName(String multiJourneyName) {
		this.multiJourneyName = multiJourneyName;
	}

}
