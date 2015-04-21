package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderItemProd implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7456723495866897877L;

	/**
	 * 所有销售产品的时间信息
	 */
	private List<OrdOrderItemProdTime> allOrdOrderItemProdTime = new ArrayList<OrdOrderItemProdTime>();
	private ProdProduct product;
	private List<OrdOrderItemMeta> ordOrderItemMetas = new ArrayList<OrdOrderItemMeta>();
	private Long orderItemProdId;
	private Long orderId;
	private Long productId;
	private Long journeyProductId;
	private Date visitTime;

	private String productName;

	private String productType;

	private Long marketPrice;

	private Long price;

	private Long quantity;

	private String faxMemo;

	private String additional = "false";


	private String wrapPage;

	private String subProductType;

	private String sendSms = "true";

	private String needEContract;
	private String branchType;
	/**
	 * 是否是默认类别.
	 */
	private String isDefault;
	/**
	 * 不定期有效期开始日期
	 * */
	private Date validBeginTime;
	/**
	 * 不定期有效期结束日期
	 * */
	private Date validEndTime;
	
	/**
	 * 不定期不可游玩日期
	 * */
	private String invalidDate;
	/**
	 * 不定期不可游玩日期描述信息
	 * */
	private String invalidDateMemo;
	
	private Long multiJourneyId;
	/**
	 * 是否是默认类别.
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * 是否是默认类别.
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public boolean hasDefault(){
		return "true".equals(this.isDefault);
	}
	/**
	 * 短名称.
	 */
	private String shortName;
	/**
	 * 酒店单房型订单应付金额.<br>
	 * 为酒店单房型计算订单总金额使用
	 */
	private Long hotelOughtPay;
	/**
	 * 产品订单应付金额.<br>
	 * 为酒店单房型计算订单总金额使用
	 */
	private Long oughtPay;
	/**
	 * 订单资源状态.<br>
	 * 为酒店单房型计算订单总金额使用
	 */
	private String resourceStatus;
	/**
	 * 总结算价.<br>
	 * 为酒店单房型计算订单总金额使用
	 */
	private Long sumSettlementPrice;
	
	/**
	 * 最晚取消小时.
	 */
	private Long lastCancelHour;
	
	/**
	 * 提前预订小时数
	 */
	private Long aheadHour;
	
	/**
	 * 销售类别id
	 */
	private Long prodBranchId;
	
	private Long paidAmount;
	
	private Long refundedAmount;
	
	/**
	 * 只在订单生成当中使用
	 */
	private transient Date latestUseTimeDate;
	
	private transient Date earliestUseTimeDate;	
	private transient Long lastPassTime;
	private transient Long lastTicketTime;

	/**
	 * getHotelOughtPay.
	 * 
	 * @return 酒店单房型订单应付金额<br>
	 *         为酒店单房型计算订单总金额使用
	 */
	public Long getHotelOughtPay() {
		return hotelOughtPay;
	}

	/**
	 * setHotelOughtPay.
	 * 
	 * @param hotelOughtPay
	 *            酒店单房型订单应付金额<br>
	 *            为酒店单房型计算订单总金额使用
	 */
	public void setHotelOughtPay(final Long hotelOughtPay) {
		this.hotelOughtPay = hotelOughtPay;
	}

	/**
	 * getOughtPay.
	 * 
	 * @return 产品订单应付金额<br>
	 *         为酒店单房型计算订单总金额使用
	 */
	public Long getOughtPay() {
		return oughtPay;
	}

	/**
	 * setOughtPay.
	 * 
	 * @param oughtPay
	 *            产品订单应付金额<br>
	 *            为酒店单房型计算订单总金额使用
	 */
	public void setOughtPay(final Long oughtPay) {
		this.oughtPay = oughtPay;
	}

	/**
	 * getResourceStatus.
	 * 
	 * @return 订单资源状态<br>
	 *         为酒店单房型计算订单总金额使用
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}

	/**
	 * setResourceStatus.
	 * 
	 * @param resourceStatus
	 *            订单资源状态<br>
	 *            为酒店单房型计算订单总金额使用
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	/**
	 * getSumSettlementPrice.
	 * 
	 * @return 总结算价.<br>
	 *         为酒店单房型计算订单总金额使用
	 */
	public Long getSumSettlementPrice() {
		return sumSettlementPrice;
	}

	/**
	 * setSumSettlementPrice.
	 * 
	 * @param sumSettlementPrice
	 *            总结算价.<br>
	 *            为酒店单房型计算订单总金额使用
	 */
	public void setSumSettlementPrice(final Long sumSettlementPrice) {
		this.sumSettlementPrice = sumSettlementPrice;
	}
	public OrdOrderItemProd() {
		for (OrdOrderItemMeta itemMeta : ordOrderItemMetas) {
			if (itemMeta.getOrderItemId().equals(this.orderItemProdId)) {
				itemMeta.setRelateOrdOrderItemProd(this);
				break;
			}

		}
	}

	private List<OrdTimeInfo> timeInfoList;

	public ProdProduct getProduct() {
		return product;
	}

	public Long getOrderItemProdId() {
		return orderItemProdId;
	}

	public void setOrderItemProdId(Long orderItemProdId) {
		this.orderItemProdId = orderItemProdId;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getFaxMemo() {
		return faxMemo;
	}

	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
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

	

	public Long getLastPassTime() {
		return lastPassTime;
	}

	public void setLastPassTime(Long lastPassTime) {
		this.lastPassTime = lastPassTime;
	}

	public Long getLastTicketTime() {
		return lastTicketTime;
	}

	public void setLastTicketTime(Long lastTicketTime) {
		this.lastTicketTime = lastTicketTime;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Date getEarliestUseTimeDate() {
		return earliestUseTimeDate;
	}

	public void setEarliestUseTimeDate(Date earliestUseTimeDate) {
		this.earliestUseTimeDate = earliestUseTimeDate;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getWrapPage() {
		return wrapPage;
	}

	public void setWrapPage(String wrapPage) {
		this.wrapPage = wrapPage;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}

	public boolean isNeedSendSms() {
		return "true".equalsIgnoreCase(sendSms);
	}

	public List<OrdOrderItemMeta> getOrdOrderItemMetas() {
		return ordOrderItemMetas;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public float getPriceYuan() {
		return PriceUtil.convertToYuan(price);
	}

	public float getMarketPriceYuan() {
		return PriceUtil.convertToYuan(marketPrice);
	}

	public float getMarketAmountYuan() {
		return getMarketPriceYuan() * this.quantity;
	}

	public float getAmountYuan() {
		return this.getPriceYuan() * this.quantity;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public void setOrdOrderItemMetas(List<OrdOrderItemMeta> ordOrderItemMetas) {
		this.ordOrderItemMetas = ordOrderItemMetas;
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}

	public String getZhVisitTime() {
		return DateUtil.getFormatDate(visitTime, "yyyy-MM-dd");
	}

	public String getZhAdditional() {
		return additional.equals("true") ? "有" : "无";
	}

	public boolean isOtherProductType() {
		return Constant.PRODUCT_TYPE.OTHER.name().equals(productType);
	}

	public boolean isAdditionalProduct() {
		return "true".equals(additional);
	}
	
	/**
	 * 能最消下单的最后时间
	 * @return
	 */
	public Date getLastCancelTime(){
		if(lastCancelHour==null||isAdditionalProduct())
			return null;
		
		return DateUtils.addMinutes(visitTime, -lastCancelHour.intValue());
	}

	/**
	 * getShortName.
	 * 	
	 * @return 短名称
	 */
	public String getShortName() {
		if(this.shortName == null){
			return this.productName.substring(productName.lastIndexOf("(")+1, this.productName.length()-1);
		}
		return this.shortName;
	}

	/**
	 * setShortName.
	 * 
	 * @param shortName
	 *            短名称
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<OrdOrderItemProdTime> getAllOrdOrderItemProdTime() {
		return allOrdOrderItemProdTime;
	}

	public void setAllOrdOrderItemProdTime(
			List<OrdOrderItemProdTime> allOrdOrderItemProdTime) {
		this.allOrdOrderItemProdTime = allOrdOrderItemProdTime;
	}

	public List<OrdTimeInfo> getTimeInfoList() {
		return timeInfoList;
	}

	public void setTimeInfoList(List<OrdTimeInfo> timeInfoList) {
		this.timeInfoList = timeInfoList;
	}

	public String getNeedEContract() {
		return needEContract;
	}

	public void setNeedEContract(String needEContract) {
		this.needEContract = needEContract;
	}

	
	/**
	 * @return the lastCancelHour
	 */
	public Long getLastCancelHour() {
		return lastCancelHour;
	}

	/**
	 * @param lastCancelHour the lastCancelHour to set
	 */
	public void setLastCancelHour(Long lastCancelHour) {
		this.lastCancelHour = lastCancelHour;
	}

	public String getDateRange() {
		List<Date> dateList = new ArrayList<Date>();
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(this.productType)) {
			for (OrdOrderItemProdTime itemTime : this.allOrdOrderItemProdTime) {
				dateList.add(itemTime.getVisitTime());
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkInDate = sdf.format(this.visitTime);
		String checkDate = checkInDate;
		java.util.Collections.sort(dateList);
		if (!dateList.isEmpty()) {
			java.util.Collections.sort(dateList);
			Calendar c = Calendar.getInstance();
			c.setTime((Date) dateList.get(dateList.size() - 1));
			c.add(Calendar.DATE, 1);
			checkDate += "至" + sdf.format(c.getTime());
		}
		return checkDate;
	}

	public int getDays() {
		if (!this.allOrdOrderItemProdTime.isEmpty()) {
			return this.allOrdOrderItemProdTime.size();
		}
		return 1;
	}

	/**
	 * 获得酒店产品的入住房间数量和晚数
	 * 
	 * @return
	 */

	public String getHotelQuantity() {
		if (!this.allOrdOrderItemProdTime.isEmpty()) {
			return String.valueOf(this.allOrdOrderItemProdTime.get(0)
					.getQuantity())
					+ "间/"
					+ String.valueOf(this.allOrdOrderItemProdTime.size()) + "晚";
		}
		return String.valueOf(this.getQuantity());
	}
	
	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	/**
	 * @return the journeyProductId
	 */
	public Long getJourneyProductId() {
		return journeyProductId;
	}

	/**
	 * @param journeyProductId the journeyProductId to set
	 */
	public void setJourneyProductId(Long journeyProductId) {
		this.journeyProductId = journeyProductId;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public Long getAheadHour() {
		return aheadHour;
	}

	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}

	public Long getPaidAmount() {
		return paidAmount;
	}
	public Date getLatestUseTimeDate() {
		return latestUseTimeDate;
	}

	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Long getRefundedAmount() {
		return refundedAmount;
	}

	public void setRefundedAmount(Long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	public void setLatestUseTimeDate(Date latestUseTimeDate) {
		this.latestUseTimeDate = latestUseTimeDate;
	}

	/**
	 * 重新设置订单的游玩时间，以时分秒设置
	 * @param createTime
	 */
	public void makeVisitTime(Date createTime){
		long tmp = 0;
		if(lastTicketTime!=null){
			tmp+=lastTicketTime;
		}
		//用下单时间加上换票时间,之后再与最早入园时间之间最大值
		Date dt = DateUtils.addMinutes(createTime, (int)tmp);
		if(dt.after(getEarliestUseTimeDate())){
			visitTime = dt;
		}else{
			visitTime = getEarliestUseTimeDate();
		}
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

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}
	
	public String getZhBranchName(){
		if(StringUtils.isNotEmpty(productName)) {
			int start=productName.lastIndexOf("(");
			String sub=productName.substring(start+1);
			int count=StringUtils.countMatches(sub, ")");
			while(count-->1){
				start=productName.lastIndexOf("(",start-1);
			}
			if(start != -1){
				return productName.substring(start+1,productName.length()-1);
			}
		}
		return "";
	}

	public Long getMultiJourneyId() {
		return multiJourneyId;
	}

	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}
}