package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 购买信息.
 * 
 * <pre>
 * 创建订单时使用
 * </pre>
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class BuyInfo implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1131048306805956198L;

	/**
	 * 支付目标.
	 */
	private String paymentTarget;
	/**
	 * 用户备注.
	 */
	private String userMemo;
	/**
	 * 订票人ID.
	 */
	private String userId;
	
	/**
	 * 订票人ID的number类型
	 */
	private Long userNo;
	/**
	 * 下单渠道.
	 */
	private String channel;
	/**
	 * 支付等待时间.
	 */
	private Long waitPayment;
	/**
	 * 资源审核状态.
	 */
	private String resourceConfirmStatus;
	/**
	 * 是否需要发票.
	 * */
	private String needInvoice;
	
	private String redail;
	/**
	 * {@link Item}列表.
	 */
	private List<Item> itemList;
	/**
	 * {@link Person}列表.
	 */
	private List<Person> personList;
	/**
	 * {@link Invoice}列表.
	 */
	private List<Invoice> invoiceList;
	/**
	 * {@link Coupon}列表.
	 */
	private List<Coupon> couponList;
	/**
	 * {@link paymentChannel}支付渠道.
	 */
	private String paymentChannel;
	/**
	 * 发票送货地址
	 */
	private Person invoiceAddress;
	
	private String selfPack;
	
	private ClientOrderReport clientOrderReport;
	
	private OrdOrderChannel ordOrderChannel;
	
	/**
	 * 主产品的产品类型
	 */
	private String mainProductType;
	/**
	 * 主产品的产品子类型
	 */
	private String mainSubProductType;
	/**
	 * 优惠策略列表
	 */
	private FavorResult favorResult;

	/**
	 * 从Cart中复制来的订单备注
	 */
	private List<OrdOrderMemo> memoList;
	
	/**
	 * 当天预订订单
	 */
	private boolean todayOrder=false;
	
	/**
	 * 是否为不定期产品
	 * */
	private String isAperiodic = "false";
	
	/**
	 * 不定期产品有效期
	 * */
	private Date validBeginTime;
	/**
	 * 客户端点评多返的奖金额度
	 */
	private Long clientCommentRefundAmount;
	
	private Date validEndTime;
	
	private String localCheck;//默认需要检查

  /**
   * 使用奖金账户金额
   */
  private Float cashValue;

	/**
	 * 订单是否属于秒杀
	 * @author zenglei
	 */
	private Long seckillId;
	/**
	 * getPaymentChannel.
	 * 
	 * @return 支付渠道
	 */
	public String getPaymentChannel() {
		return paymentChannel;
	}

	/**
	 * setPaymentChannel.
	 * 
	 * @return 支付渠道
	 */
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	/**
	 * getPaymentTarget.
	 * 
	 * @return 支付目标
	 */
	public String getPaymentTarget() {
		return paymentTarget;
	}

	/**
	 * setPaymentTarget.
	 * 
	 * @param paymentTarget
	 *            支付目标
	 */
	public void setPaymentTarget(final String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	/**
	 * getUserMemo.
	 * 
	 * @return 用户备注
	 */
	public String getUserMemo() {
		return userMemo;
	}

	/**
	 * setUserMemo.
	 * 
	 * @param userMemo
	 *            用户备注
	 */
	public void setUserMemo(final String userMemo) {
		this.userMemo = userMemo;
	}

	/**
	 * getUserId.
	 * 
	 * @return 订票人ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * setUserId.
	 * 
	 * @param userId
	 *            订票人ID
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * getChannel.
	 * 
	 * @return 下单渠道
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * setChannel.
	 * 
	 * @param channel
	 *            下单渠道
	 */
	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/**
	 * getWaitPayment.
	 * 
	 * @return 支付等待时间
	 */
	public Long getWaitPayment() {
		return waitPayment;
	}

	/**
	 * setWaitPayment.
	 * 
	 * @param waitPayment
	 *            支付等待时间
	 */
	public void setWaitPayment(final Long waitPayment) {
		this.waitPayment = waitPayment;
	}

	/**
	 * @return the resourceConfirmStatus
	 */
	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	/**
	 * @param resourceConfirmStatus
	 *            the resourceConfirmStatus to set
	 */
	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	/**
	 * getItemList.
	 * 
	 * @return {@link Item}列表
	 */
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * setItemList.
	 * 
	 * @param itemList
	 *            {@link Item}列表
	 */
	public void setItemList(final List<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * getPersonList.
	 * 
	 * @return {@link Person}列表
	 */
	public List<Person> getPersonList() {
		return personList;
	}

	/**
	 * setPersonList.
	 * 
	 * @param personList
	 *            {@link Person}列表
	 */
	public void setPersonList(final List<Person> personList) {
		this.personList = personList;
	}

	/**
	 * @return {@link Invoice}列表
	 */
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	/**
	 * @param invoiceList
	 *            {@link Invoice}列表
	 */
	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	/**
	 * @return {@link Coupon}列表
	 */
	public List<Coupon> getCouponList() {
		return couponList;
	}

	/**
	 * @param couponList
	 *            {@link Coupon}列表
	 */
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

	/**
	 * 优惠和活动.
	 */
	public static final class Coupon implements Serializable {
		private Long couponId;
		private String code;
		private String checked = "false";

		public Long getCouponId() {
			return couponId;
		}

		public void setCouponId(Long couponId) {
			this.couponId = couponId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getChecked() {
			return checked;
		}

		public void setChecked(String checked) {
			this.checked = checked;
		}
	}

	/**
	 * 酒店类型时间区间支持
	 * 
	 * @author dengcheng
	 * 
	 */
	public static final class OrdTimeInfo implements Serializable {
		private Long productId;
		
		private Long productBranchId;
		/**
		 * 时间
		 */
		private Date visitTime;
		/**
		 * 数量
		 */
		private Long quantity;
		/**
		 * 市场价
		 * */
		private Long marketPrice;
		/**
		 * 销售价格
		 * */
		private Long sellPrice;

		public Date getVisitTime() {
			return visitTime;
		}

		public void setVisitTime(Date visitTime) {
			this.visitTime = visitTime;
		}

		public Long getQuantity() {
			return quantity;
		}

		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Long getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(Long marketPrice) {
			this.marketPrice = marketPrice;
		}

		public Long getSellPrice() {
			return sellPrice;
		}

		public void setSellPrice(Long sellPrice) {
			this.sellPrice = sellPrice;
		}

		public float getSellPriceYuan() {
			return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
		}

		public float getMarketPriceYuan() {
			return marketPrice == null ? 0 : PriceUtil
					.convertToYuan(marketPrice);
		}

		public String getZhVisitTime() {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(visitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		/**
		 * @return the productBranchId
		 */
		public Long getProductBranchId() {
			return productBranchId;
		}

		/**
		 * @param productBranchId the productBranchId to set
		 */
		public void setProductBranchId(Long productBranchId) {
			this.productBranchId = productBranchId;
		}
		
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
		private String isDefault="false";
		
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
		 * 选择的时间范围
		 */
		private List<OrdTimeInfo> timeInfoList;

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
		 * @return
		 */
		public long getProductBranchId() {
			return productBranchId;
		}

		/**
		 * 销售产品分支ID
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

		public List<OrdTimeInfo> getTimeInfoList() {
			return timeInfoList;
		}
		

		public void setTimeInfoList(List<OrdTimeInfo> timeInfoList) {
			this.timeInfoList = timeInfoList;
		}
		
		/**
		 * 该数据做为map的key使用.
		 * @return
		 */
		public long getKey(){
			StringBuffer sb=new StringBuffer();
			sb.append(productId);
			sb.append("_");
			sb.append(productBranchId);
			sb.append("_");
			sb.append(DateUtil.getFormatDate(visitTime,"yyyy-MM-dd"));
			
			return sb.toString().hashCode();
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

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public Date calcVisitTime() {
		Date visitTime = null;
		for (BuyInfo.Item item : itemList) {
			if (visitTime == null
					|| visitTime.getTime() > item.getVisitTime().getTime()) {
				visitTime = item.getVisitTime();
			}
		}
		return visitTime;
	}

	public List<OrdOrderItemProd> initialOrdItemProdList() {
		List<OrdOrderItemProd> ordOrderItemProds = new ArrayList<OrdOrderItemProd>(
				itemList.size());
		for (BuyInfo.Item item : itemList) {
			OrdOrderItemProd itemProd = new OrdOrderItemProd();
			itemProd.setProductId(item.getProductId());
			itemProd.setProdBranchId(item.getProductBranchId());
			itemProd.setQuantity(new Long(item.getQuantity()));
			itemProd.setVisitTime(item.getVisitTime());
			itemProd.setFaxMemo(item.getFaxMemo());
			/**
			 * 设置采购产品的时间信息 没有就为空
			 */
			itemProd.setTimeInfoList(item.getTimeInfoList());
			if (item.getQuantity() > 0) {
				ordOrderItemProds.add(itemProd);
			}
		}
		return ordOrderItemProds;
	}

	public String convertResourceConfirmStatus() {
		if ("true".equalsIgnoreCase(resourceConfirmStatus)) {
			return Constant.ORDER_RESOURCE_STATUS.AMPLE.name();
		} else {
			return Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED.name();
		}
	}

	public Coupon getCheckedCoupon() {

		if (this.couponList == null) {
			return null;
		}
		for (Coupon coupon : this.getCouponList()) {
			if ("true".equals(coupon.getChecked())) {
				return coupon;
			}
		}
		return null;
	}

	public String getRedail() {
		return redail;
	}

	public void setRedail(String redail) {
		this.redail = redail;
	}

	/**
	 * @return the invoiceAddress
	 */
	public Person getInvoiceAddress() {
		return invoiceAddress;
	}

	/**
	 * @param invoiceAddress the invoiceAddress to set
	 */
	public void setInvoiceAddress(Person invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	/**
	 * @return the selfPack
	 */
	public String getSelfPack() {
		return selfPack;
	}

	/**
	 * @param selfPack the selfPack to set
	 */
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}

	public boolean hasSelfPack(){
		return StringUtils.equals("true", selfPack);
	}

	public ClientOrderReport getClientOrderReport() {
		return clientOrderReport;
	}

	public void setClientOrderReport(ClientOrderReport clientOrderReport) {
		this.clientOrderReport = clientOrderReport;
	}

	public OrdOrderChannel getOrdOrderChannel() {
		return ordOrderChannel;
	}

	public void setOrdOrderChannel(OrdOrderChannel ordOrderChannel) {
		this.ordOrderChannel = ordOrderChannel;
	}

	public List<OrdOrderMemo> getMemoList() {
		return memoList;
	}

	public void setMemoList(List<OrdOrderMemo> memoList) {
		this.memoList = memoList;
	}

	public boolean isTodayOrder() {
		return todayOrder;
	}

	public void setTodayOrder(boolean todayOrder) {
		this.todayOrder = todayOrder;
	}

	public String getMainProductType() {
		return mainProductType;
	}

	public void setMainProductType(String mainProductType) {
		this.mainProductType = mainProductType;
	}

	public String getMainSubProductType() {
		return mainSubProductType;
	}

	public void setMainSubProductType(String mainSubProductType) {
		this.mainSubProductType = mainSubProductType;
	}

	public FavorResult getFavorResult() {
		return favorResult;
	}

	public void setFavorResult(FavorResult favorResult) {
		this.favorResult = favorResult;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
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

	public Long getClientCommentRefundAmount() {
		return clientCommentRefundAmount;
	}

	public void setClientCommentRefundAmount(Long clientCommentRefundAmount) {
		this.clientCommentRefundAmount = clientCommentRefundAmount;
	}

	public String getLocalCheck() {
		return localCheck;
	}

	public void setLocalCheck(String localCheck) {
		this.localCheck = localCheck;
	}
	
	public boolean hasNotLocalCheck(){
		return "false".equals(localCheck);
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}


	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

  public Float getCashValue() {
      return cashValue;
  }

  public void setCashValue(Float cashValue) {
      this.cashValue = cashValue;
  }

}
