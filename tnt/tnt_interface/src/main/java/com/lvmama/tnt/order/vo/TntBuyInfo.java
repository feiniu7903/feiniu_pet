package com.lvmama.tnt.order.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.prod.vo.TntProdProduct;

public class TntBuyInfo implements Serializable {

	private static final long serialVersionUID = 2518436939230200970L;

	private Long productId;// 产品Id

	private Long branchId;// 类别Id

	private String productType;

	private Date visitDate;// 游玩时间

	private String visitTime;

	private String distributor;

	private String isEContract;// 主产品是否是电子签单

	private String userMemo;// 对订单的需求

	private String isNeedResourceConfirm;// 是否需要资源审核

	private boolean isPayToLvmama;

	private Integer days;

	private TntProdProduct mainProdBranch;

	private Map<String, Integer> buyNum = new HashMap<String, Integer>();// 各类别订单数量

	private List<Date> visitDates;

	private Map<String, List<TntProductRelation>> additionalProduct;

	private Map<String, Object> data;

	private List<TntProdProduct> relatedProductList;

	private List<String> firstTravellerInfoOptions; // 第一游玩人的必填信息
	private List<String> travellerInfoOptions; // 游玩人的必填信息
	private List<String> contactInfoOptions; // 联系人的必填信息

	private List<TntProdAssemblyPoint> prodAssemblyPointList;

	private Map<String, String> reserveNoticeMap;

	private String isAperiodic = "false";

	/**
	 * 游玩人员
	 */
	private List<Person> travellers = new ArrayList<Person>();

	/**
	 * 紧急联系人
	 */
	private Person emergencyContact;

	/**
	 * 收件人
	 */
	private Person usrReceivers;

	/**
	 * 联系人
	 */
	private Person contact;

	/**
	 * 订单子项
	 */
	private List<Item> itemList;

	private Date validBeginTime;

	private Date validEndTime;

	private String physical;

	private ResultMessage result;

	private boolean productPreview = true;

	private String leaveTime;

	private Long orderId;// 订单号，订单常见后将orderId存入tntBuyInfo中

	private boolean submitOrder; // 判断是否订单提交的时候，如果是订单提交时/ajaxCheckPrice需要显示关联产品

	public Map<Long, Long> getOrdItemProdList() {
		Map<Long, Long> map = new HashMap<Long, Long>();
		Set<String> keySet = buyNum.keySet();
		for (String string : keySet) {
			if (string.matches("^product_[0-9]+$")) {
				Long productId = Long.valueOf(string.substring(8));
				Long value = Long.valueOf(buyNum.get(string));
				if (value > 0) {
					map.put(productId, value);
				}
			}
		}
		return map;
	}

	/**
	 * 订单子项.
	 */
	public static final class Item implements Serializable {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -1234855001195311037L;
		/**
		 * 销售产品ID.
		 */
		private long productId;

		/**
		 * 销售产品分支ID
		 */
		private long productBranchId;

		private Long journeyProductId;

		/**
		 * 数量.
		 */
		private int quantity;
		/**
		 * 游玩时间.
		 */
		private Date visitTime;
		/**
		 * 传真备注.
		 */
		private String faxMemo;

		/**
		 * 是否是默认类别.
		 */
		private String isDefault = "false";

		/**
		 * 成人数
		 */
		private Long adultQuantity;
		/**
		 * 儿童数
		 */
		private Long childQuantity;

		private String productType;

		private String subProductType;

		/**
		 * 不定期产品有效期
		 * */
		private Date validBeginTime;

		private Date validEndTime;

		/**
		 * 不定期不可游玩日期
		 * */
		private String invalidDate;
		/**
		 * 不可游玩日期描述信息
		 * */
		private String invalidDateMemo;

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

		/**
		 * getProductId.
		 * 
		 * @return 销售产品ID
		 */
		public long getProductId() {
			return productId;
		}

		/**
		 * setProductId.
		 * 
		 * @param productId
		 *            销售产品ID
		 */
		public void setProductId(final long productId) {
			this.productId = productId;
		}

		/**
		 * 销售产品分支ID
		 * 
		 * @return
		 */
		public long getProductBranchId() {
			return productBranchId;
		}

		/**
		 * 销售产品分支ID
		 * 
		 * @param productBranchId
		 */
		public void setProductBranchId(long productBranchId) {
			this.productBranchId = productBranchId;
		}

		/**
		 * getQuantity.
		 * 
		 * @return 数量
		 */
		public int getQuantity() {
			return quantity;
		}

		/**
		 * setQuantity.
		 * 
		 * @param quantity
		 *            数量
		 */
		public void setQuantity(final int quantity) {
			this.quantity = quantity;
		}

		/**
		 * getVisitTime.
		 * 
		 * @return 游玩时间
		 */
		public Date getVisitTime() {
			return visitTime;
		}

		/**
		 * setVisitTime.
		 * 
		 * @param visitTime
		 *            游玩时间
		 */
		public void setVisitTime(final Date visitTime) {
			this.visitTime = visitTime;
		}

		/**
		 * getFaxMemo.
		 * 
		 * @return 传真备注
		 */
		public String getFaxMemo() {
			return faxMemo;
		}

		/**
		 * setFaxMemo.
		 * 
		 * @param faxMemo
		 *            传真备注
		 */
		public void setFaxMemo(final String faxMemo) {
			this.faxMemo = faxMemo;
		}

		/**
		 * 该数据做为map的key使用.
		 * 
		 * @return
		 */
		public long getKey() {
			StringBuffer sb = new StringBuffer();
			sb.append(productId);
			sb.append("_");
			sb.append(productBranchId);
			sb.append("_");
			sb.append(TntUtil.formatDate(visitTime));
			return sb.toString().hashCode();
		}

		/**
		 * @return the journeyProductId
		 */
		public Long getJourneyProductId() {
			return journeyProductId;
		}

		/**
		 * @param journeyProductId
		 *            the journeyProductId to set
		 */
		public void setJourneyProductId(Long journeyProductId) {
			this.journeyProductId = journeyProductId;
		}

		public Long getAdultQuantity() {
			return adultQuantity;
		}

		public void setAdultQuantity(Long adultQuantity) {
			this.adultQuantity = adultQuantity;
		}

		public Long getChildQuantity() {
			return childQuantity;
		}

		public void setChildQuantity(Long childQuantity) {
			this.childQuantity = childQuantity;
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

		public String getSubProductType() {
			return subProductType;
		}

		public void setSubProductType(String subProductType) {
			this.subProductType = subProductType;
		}

		public String getProductType() {
			return productType;
		}

		public void setProductType(String productType) {
			this.productType = productType;
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

	}

	public Map<String, List<TntProductRelation>> getAdditionalProduct() {
		return additionalProduct;
	}

	public void setAdditionalProduct(
			Map<String, List<TntProductRelation>> additionalProduct) {
		this.additionalProduct = additionalProduct;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public Map<String, Integer> getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Map<String, Integer> buyNum) {
		this.buyNum = buyNum;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.visitTime = visitTime.trim();
		try {
			if (StringUtils.isNotEmpty(this.visitTime)) {
				this.visitDate = sdf.parse(this.visitTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TntProdProduct getMainProdBranch() {
		return mainProdBranch;
	}

	public void setMainProdBranch(TntProdProduct mainProdBranch) {
		this.mainProdBranch = mainProdBranch;
	}

	public String getIsEContract() {
		return isEContract;
	}

	public void setIsEContract(String isEContract) {
		this.isEContract = isEContract;
	}

	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	public List<Date> getVisitDates() {
		return visitDates;
	}

	public void setVisitDates(List<Date> visitDates) {
		this.visitDates = visitDates;
	}

	public String getIsNeedResourceConfirm() {
		return isNeedResourceConfirm;
	}

	public void setIsNeedResourceConfirm(String isNeedResourceConfirm) {
		this.isNeedResourceConfirm = isNeedResourceConfirm;
	}

	public List<Person> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<Person> travellers) {
		this.travellers = travellers;
	}

	public Person getContact() {
		return contact;
	}

	public void setContact(Person contact) {
		this.contact = contact;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String isAperiodic() {
		return isAperiodic;
	}

	public void setAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
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

	public List<String> getContactInfoOptions() {
		return contactInfoOptions;
	}

	public void setContactInfoOptions(List<String> contactInfoOptions) {
		this.contactInfoOptions = contactInfoOptions;
	}

	public List<String> getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}

	public void setFirstTravellerInfoOptions(
			List<String> firstTravellerInfoOptions) {
		this.firstTravellerInfoOptions = firstTravellerInfoOptions;
	}

	public List<String> getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public void setTravellerInfoOptions(List<String> travellerInfoOptions) {
		this.travellerInfoOptions = travellerInfoOptions;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<TntProdAssemblyPoint> getProdAssemblyPointList() {
		return prodAssemblyPointList;
	}

	public void setProdAssemblyPointList(
			List<TntProdAssemblyPoint> prodAssemblyPointList) {
		this.prodAssemblyPointList = prodAssemblyPointList;
	}

	public Map<String, String> getReserveNoticeMap() {
		return reserveNoticeMap;
	}

	public void setReserveNoticeMap(Map<String, String> reserveNoticeMap) {
		this.reserveNoticeMap = reserveNoticeMap;
	}

	public List<TntProdProduct> getRelatedProductList() {
		return relatedProductList;
	}

	public void setRelatedProductList(List<TntProdProduct> relatedProductList) {
		this.relatedProductList = relatedProductList;
	}

	public ResultMessage getResult() {
		return result;
	}

	public void setResult(ResultMessage result) {
		this.result = result;
	}

	public boolean getIsPayToLvmama() {
		return isPayToLvmama;
	}

	public void setIsPayToLvmama(boolean isPayToLvmama) {
		this.isPayToLvmama = isPayToLvmama;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public Person getUsrReceivers() {
		return usrReceivers;
	}

	public void setUsrReceivers(Person usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public Person getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(Person emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public void setDays(Integer days) {
		if (days != null) {
			leaveTime = leaveTime == null ? getSpecifyDate(days) : leaveTime;
		}
		this.days = days;
	}

	public Integer getDays() {
		return days;
	}

	public String getSpecifyDate(int day) {
		if (this.visitDate != null) {
			Date date = DateUtils.addDays(visitDate, day);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date.getTime());
		}
		return null;
	}

	public Integer getBuyNumValue(String key) {
		Integer value = this.buyNum.get(key);
		if (value != null) {
			return value;
		}
		return 0;
	}

	public boolean isProductPreview() {
		return productPreview;
	}

	public void setProductPreview(boolean productPreview) {
		this.productPreview = productPreview;
	}

	public String getLeaveTime() {
		if (this.days != null) {
			leaveTime = leaveTime == null ? getSpecifyDate(days) : leaveTime;
		}
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public boolean isSubmitOrder() {
		return submitOrder;
	}

	public void setSubmitOrder(boolean submitOrder) {
		this.submitOrder = submitOrder;
	}

	public boolean getContactContainsCardNumber() {
		boolean flag = false;
		if (contactInfoOptions != null) {
			for (String s : contactInfoOptions) {
				if ("CARD_NUMBER".equals(s)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
