package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 移动端专用 from v3 - 订单
 * 
 * @author
 */
public class MobileOrder implements Serializable {

	// 具体需要哪些字段 ，待改 .
	private Long orderId;
	private String productName;

	private String orderViewStatus;

	private String amount;

	private String visitTime;
	/**
	 * 离开时间 酒店类型产品有
	 */
	private String leaveTime;

	private String jieshen;

	private String quantity;

	private boolean isTodayOrder;

	private String zhOrderViewState;

	private String zhPaymentTarget;

	private boolean isEContractConfirmed;

	private boolean isNeedEContract;

	private String latestPassTime;

	private String earliestPassTime;

	private String mainProductType;

	private String mainSubProductType;

	private List<MobilePersonItem> listPerson;
	private List<MobileOrderItem> orderItem;

	private String createdTime;

	private boolean needResourceConfirm;

	private String resourceConfirmStatus;

	private List<String> additionList;

	/**
	 * 用户订单备注
	 */
	private String userMemo;

	/**
	 * 是否能提交支付
	 */
	private boolean canToPay;

	private boolean canCancel;
	/**
	 * 支付宝手机支付
	 */
	private String alipayAppUrl;
	/**
	 * 支付宝Wap支付
	 */
	private String alipayWapUrl;
	
	/**
	 * 支付渠道
	 */
	private String paymentChannel;

	/**
	 * upomp支付地址
	 */
	private String upompPayUrl;

	/**
	 * 图片地址
	 */
	private String imgUrl;

	/**
	 * 人工推改
	 */
	private boolean isManual;
	/**
	 * 是否不退不改
	 */
	private boolean isForbid;

	/**
	 * 去签约
	 */
	private boolean gotoSign;

	private String descUrl; // 行程url

	private Float couponUsageAmount;

	// v3.1 add
	/**
	 * 订单已使用奖金支付金额(以分为单位)
	 */
	private Long bonusPaidAmount = 0L;
	/**
	 * 订单最大可用奖金金额(以分为单位)
	 */
	private Long bonus = 0L;
	/**
	 * 订单实际可用金额(以分为单位)
	 */
	private Long actBonus = 0L;
	/**
	 * 用户奖金账户余额(以分为单位)
	 */
	private Long bonusBalance = 0L;

	/**
	 * 是否可重发验证码 .
	 */
	private boolean canSendCert;
	/**
	 * 产品id ，
	 */
	private Long productId;
	/**
	 * 门票id ，
	 */
	private Long placeId;

	/**
	 * 订单联系人手机号
	 */
	private String contactPersonMobile = "";

	/**
	 * 支付方式
	 */
	private String payTarget;

	/**
	 * 合同.
	 * 
	 * @return
	 */

	private List<MobilePayment> paymentChannels = new ArrayList<MobilePayment>();

	private boolean isGroupProduct;

	/**
	 * 订单类型 对应 SUB_PRODUCT_TYPE
	 */
	// 20130827
	private String orderType;

	public boolean physical;

	private String shareContent;

	private String shareContentTitle = "";

	/***/
	/**
	 * 出发站中文名
	 */
	private String departureStationName;
	/**
	 * 到达站中文名
	 */
	private String arrivalStationName;

	private String seatType; // 座位名称

	private Long departureTime; // 出发时间

	/**
	 * 用于三星钱包
	 */
	private Double baiduLongitude = 0d; // 经度
	private Double baiduLatitude = 0d; // 维度
	private Double googleLongitude = 0d; // 经度
	private Double googleLatitude = 0d; // 维度
	private String address;//地址
	private String scenicOpenTime;//开园时间
	private String fromPlaceName;//出发地
	private String destPlaceName;//目的地
	private String cityName;//目的地所在城市

	/**************V5.0.0*******************/
	private boolean tempCloseCashAccountPay; // 现金支付是否临时关闭 false：没关闭 ，true：关闭
	
	private boolean hasGroupAdviceNote; // 是否可以下载出团通知书 
	private boolean hasDownloadTravel; //是否可以下载行程单
	
	private float maxPayMoney; // 可用金额 ，可以支付金额 
	
	private boolean mobileCanChecked; // 是否绑定； true：未绑定； false：已绑定
	
	private boolean cashAccountValid; // 是否冻结 ； true:是；false：否 
	
	/**
	 * 实付金额.单位元 
	 */
	private float actualPay; 
	
	/**
	 * 现金账号 加密时用 
	 * @return
	 */
	public String getObjectType() {
		return Constant.OBJECT_TYPE.ORD_ORDER.name();
    }
	public String getBizType() {
		return Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name();
    }
	
	public String getPayMentType() {
		return Constant.PAYMENT_TYPE.ORDER.name();
    }
	public String getSigKey() {
		return PaymentConstant.SIG_PRIVATE_KEY;
    }
	
	public Long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}

	public String getZhDepartureTime() {
		if (null == departureTime) {
			return "";
		}
		return TimePriceUtil.formatTime(departureTime);
	}

	public String getOrderType() {
		if (null == orderType) {
			return "";
		}
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getContractUrl() {
		return "/html5/order/contract_info.htm?orderId=" + orderId;
	}

	public String getContractConfig() {
		return "/mobile/html5/order/contract_options.do?orderId=" + orderId;
	}

	public String getDescUrl() {
		return descUrl;
	}

	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<MobilePersonItem> getListPerson() {
		return listPerson;
	}

	public void setListPerson(List<MobilePersonItem> listPerson) {
		this.listPerson = listPerson;
	}

	public String getZhOrderViewState() {
		return zhOrderViewState;
	}

	public void setZhOrderViewState(String zhOrderViewState) {
		this.zhOrderViewState = zhOrderViewState;
	}

	public String getZhPaymentTarget() {
		return zhPaymentTarget;
	}

	public void setZhPaymentTarget(String zhPaymentTarget) {
		this.zhPaymentTarget = zhPaymentTarget;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getJieshen() {
		return jieshen;
	}

	public void setJieshen(String jieshen) {
		this.jieshen = jieshen;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public boolean isNeedResourceConfirm() {
		return needResourceConfirm;
	}

	public void setNeedResourceConfirm(boolean needResourceConfirm) {
		this.needResourceConfirm = needResourceConfirm;
	}

	public String getUpompPayUrl() {
		return upompPayUrl;
	}

	public void setUpompPayUrl(String upompPayUrl) {
		this.upompPayUrl = upompPayUrl;
	}

	public boolean getIsEContractConfirmed() {
		return isEContractConfirmed;
	}

	public void setIsEContractConfirmed(boolean isEContractConfirmed) {
		this.isEContractConfirmed = isEContractConfirmed;
	}

	public boolean isNeedEContract() {
		return isNeedEContract;
	}

	public void setNeedEContract(boolean isNeedEContract) {
		this.isNeedEContract = isNeedEContract;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public List<String> getAdditionList() {
		return additionList;
	}

	public void setAdditionList(List<String> additionList) {
		this.additionList = additionList;
	}

	public boolean isCanToPay() {
		return canToPay;
	}

	public void setCanToPay(boolean canToPay) {
		this.canToPay = canToPay;
	}

	public String getAlipayAppUrl() {
		return alipayAppUrl;
	}

	public void setAlipayAppUrl(String alipayAppUrl) {
		this.alipayAppUrl = alipayAppUrl;
	}

	public String getAlipayWapUrl() {
		return alipayWapUrl;
	}

	public void setAlipayWapUrl(String alipayWapUrl) {
		this.alipayWapUrl = alipayWapUrl;
	}

	public String getLatestPassTime() {
		return latestPassTime;
	}

	public void setLatestPassTime(String latestPassTime) {
		this.latestPassTime = latestPassTime;
	}

	public String getEarliestPassTime() {
		return earliestPassTime;
	}

	public void setEarliestPassTime(String earliestPassTime) {
		this.earliestPassTime = earliestPassTime;
	}

	public boolean isTodayOrder() {
		return isTodayOrder;
	}

	public void setTodayOrder(boolean isTodayOrder) {
		this.isTodayOrder = isTodayOrder;
	}

	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	public List<MobileOrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<MobileOrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public void setEContractConfirmed(boolean isEContractConfirmed) {
		this.isEContractConfirmed = isEContractConfirmed;
	}

	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public String getMainProductType() {
		return mainProductType;
	}

	public void setMainProductType(String mainProductType) {
		this.mainProductType = mainProductType;
	}

	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	public boolean isManual() {
		return isManual;
	}

	public boolean isGotoSign() {
		return gotoSign;
	}

	public void setGotoSign(boolean gotoSign) {
		this.gotoSign = gotoSign;
	}

	public Float getCouponUsageAmount() {
		return couponUsageAmount;
	}

	public void setCouponUsageAmount(Float couponUsageAmount) {
		this.couponUsageAmount = couponUsageAmount;
	}

	public boolean isForbid() {
		return isForbid;
	}

	public void setForbid(boolean isForbid) {
		this.isForbid = isForbid;
	}

	public boolean isCanSendCert() {
		return canSendCert;
	}

	public void setCanSendCert(boolean canSendCert) {
		this.canSendCert = canSendCert;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Float getBonusPaidAmount() {
		return PriceUtil.convertToYuan(bonusPaidAmount);
	}

	public void setBonusPaidAmount(Long bonusPaidAmount) {
		this.bonusPaidAmount = bonusPaidAmount;
	}

	public Float getBonus() {
		return PriceUtil.convertToYuan(bonus);
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public Float getActBonus() {
		return PriceUtil.convertToYuan(actBonus);
	}

	public void setActBonus(Long actBonus) {
		this.actBonus = actBonus;
	}

	public Float getBonusBalance() {
		return PriceUtil.convertToYuan(bonusBalance);
	}

	public void setBonusBalance(Long bonusBalance) {
		this.bonusBalance = bonusBalance;
	}

	public String getContactPersonMobile() {
		return contactPersonMobile;
	}

	public void setContactPersonMobile(String contactPersonMobile) {
		this.contactPersonMobile = contactPersonMobile;
	}

	public String getPayTarget() {
		return payTarget;
	}

	public void setPayTarget(String payTarget) {
		this.payTarget = payTarget;
	}

	public boolean isGroupProduct() {
		return isGroupProduct;
	}

	public void setGroupProduct(boolean isGroupProduct) {
		this.isGroupProduct = isGroupProduct;
	}

	public List<MobilePayment> getPaymentChannels() {
		return paymentChannels;
	}

	public void setPaymentChannels(List<MobilePayment> paymentChannels) {
		this.paymentChannels = paymentChannels;
	}

	public String getMainSubProductType() {
		return mainSubProductType;
	}

	public void setMainSubProductType(String mainSubProductType) {
		this.mainSubProductType = mainSubProductType;
	}

	public boolean isPhysical() {
		return physical;
	}

	public void setPhysical(boolean physical) {
		this.physical = physical;
	}

	public void setShareContent(ProdProduct mainProduct, Place place,
			ProdProductBranch branch) {
		if (null != mainProduct) {
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(this.mainProductType)) {
				String content = String.format(
						"我在@驴妈妈旅游网上 订购了 旅游线路“%s”，%s折，仅售%s，推荐给你们。",
						com.lvmama.clutter.utils.ClientUtils
								.subProductName(this.getProductName()),
						PriceUtil.formatDecimal(mainProduct.getSellPrice()
								/ mainProduct.getMarketPriceYuan() * 10),
						mainProduct.getSellPriceYuan());
				this.shareContent = content;
				this.shareContentTitle = String.format(
						ClutterConstant.SHARE_CONTENT,
						this.getProductName(),
						PriceUtil.formatDecimal(mainProduct.getSellPriceYuan()
								/ mainProduct.getMarketPriceYuan() * 10));
			} else {
				if (null != branch && null != place) {
					String content = String.format(
							"我在@驴妈妈旅游网 上买了“%s“的%s，原价%s，现价%s，省了%s块！推荐给你们。",
							place.getName(),
							branch.getBranchName(),
							mainProduct.getMarketPriceYuan(),
							mainProduct.getSellPriceYuan(),
							mainProduct.getMarketPriceYuan()
									- mainProduct.getSellPriceYuan());
					;
					this.shareContent = content;
					this.shareContentTitle = String.format(
							ClutterConstant.SHARE_CONTENT, place.getName(),
							PriceUtil.formatDecimal(mainProduct
									.getSellPriceYuan()
									/ mainProduct.getMarketPriceYuan() * 10));

				}
			}

		}
	}

	public String getShareContent() {

		return this.shareContent;
	}

	public String getWapUrl() {
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(this.mainProductType)) {
			return "http://m.lvmama.com/clutter/route/" + productId;
		} else {
			return "http://m.lvmama.com/clutter/place/" + placeId;
		}
	}

	public String getShareContentTitle() {
		return shareContentTitle;
	}

	public void setShareContentTitle(String shareContentTitle) {
		this.shareContentTitle = shareContentTitle;
	}

	public String getDepartureStationName() {
		return departureStationName;
	}

	public void setDepartureStationName(String departureStationName) {
		this.departureStationName = departureStationName;
	}

	public String getArrivalStationName() {
		return arrivalStationName;
	}

	public void setArrivalStationName(String arrivalStationName) {
		this.arrivalStationName = arrivalStationName;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public Double getBaiduLongitude() {
		return baiduLongitude;
	}

	public void setBaiduLongitude(Double baiduLongitude) {
		this.baiduLongitude = baiduLongitude;
	}

	public Double getBaiduLatitude() {
		return baiduLatitude;
	}

	public void setBaiduLatitude(Double baiduLatitude) {
		this.baiduLatitude = baiduLatitude;
	}

	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScenicOpenTime() {
		return scenicOpenTime;
	}

	public void setScenicOpenTime(String scenicOpenTime) {
		this.scenicOpenTime = scenicOpenTime;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public String getDestPlaceName() {
		return destPlaceName;
	}

	public void setDestPlaceName(String destPlaceName) {
		this.destPlaceName = destPlaceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	

	public boolean isTempCloseCashAccountPay() {
		return tempCloseCashAccountPay;
	}
	public void setTempCloseCashAccountPay(boolean tempCloseCashAccountPay) {
		this.tempCloseCashAccountPay = tempCloseCashAccountPay;
	}
	
	public boolean isHasGroupAdviceNote() {
		return hasGroupAdviceNote;
	}
	public void setHasGroupAdviceNote(boolean hasGroupAdviceNote) {
		this.hasGroupAdviceNote = hasGroupAdviceNote;
	}
	
	/**
	 * 下载出团通知书
	 * <#if obj.mainProduct.productType?if_exists=="ROUTE"&& obj.paymentStatus?default("")=="PAYED">
					 	     <#if obj.orderRoute.groupWordStatus?if_exists=="SENT_NO_NOTICE"||obj.orderRoute.groupWordStatus?if_exists=="MODIFY_NO_NOTICE"||obj.orderRoute.groupWordStatus?default("")=="SENT_NOTICE" || obj.orderRoute.groupWordStatus?default("")=="MODIFY_NOTICE">
					         	<a href="${base}/groupAdviceNoteDownload/order.do?objectId=${obj.orderId?if_exists}&objectType=ORD_ORDER">下载出团通知书</a>
				 	         </#if>
				        </#if>
	 * @return
	 */
	public String getGroupAdviceNoteUrl() {
		if(this.hasGroupAdviceNote) {
			return "/mobile/html5/order/getGroupAdviceNote.do?orderId="+this.orderId+"&objectType=ORD_ORDER";
		}
		return "";
	}
	
	public boolean isHasDownloadTravel() {
		if(this.isNeedEContract() && this.isEContractConfirmed && this.canToPay) {
			
		}
		return hasDownloadTravel;
	}
	public void setHasDownloadTravel(boolean hasDownloadTravel) {
		this.hasDownloadTravel = hasDownloadTravel;
	}
	/**
	 * 下载行程单 
	 * <#if !obj.isCanceled() && "ROUTE"==obj.mainProduct.productType && obj.isNeedEContract() && obj.isEContractConfirmed() >
	   </#if>
	 * <a href="/ord/downloadTravel.do?productId=${obj.mainProduct.productId?if_exists}&orderId=${obj.orderId?if_exists}&productName=${obj.mainProduct.productName?if_exists}" target="_blank" rel="nofollow">下载行程</a>
	 * @return
	 */
	public String getDownLoadTralvelUrl() {
		if(this.hasDownloadTravel) {
			return "/mobile/html5/order/getDownLoadTralvel.do?productId="+this.getProductId()+"&orderId="+orderId+"&productName="+this.productName;
		}
		return "";
	}
	
	public float getMaxPayMoney() {
		return maxPayMoney;
	}
	public void setMaxPayMoney(float maxPayMoney) {
		this.maxPayMoney = maxPayMoney;
	}
	public boolean isMobileCanChecked() {
		return mobileCanChecked;
	}
	public void setMobileCanChecked(boolean mobileCanChecked) {
		this.mobileCanChecked = mobileCanChecked;
	}
	public boolean isCashAccountValid() {
		return cashAccountValid;
	}
	public void setCashAccountValid(boolean cashAccountValid) {
		this.cashAccountValid = cashAccountValid;
	}
	
	public float getActualPay() {
		return actualPay;
	}
	public void setActualPay(float actualPay) {
		this.actualPay = actualPay;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	
}
