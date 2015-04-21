package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;



public class ViewClientOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -621116669125590184L;

	private Long orderId;
	
	private Long mianProductId;
	
	private String productName;
	
	private String paymentTarget;
	
	private String createTime;
	
	private String orderViewStatus;
	
	private String titleName;

	private String amount;
	
	private String orderType;
	
	private String visitTime;
	/**
	 * 离开时间 酒店类型产品有
	 */
	private String leaveTime;
	
	private String mainProductType;
	
	private String jieshen;
	
	private String quantity;
	
	private boolean isTodayOrder;

	private String zhOrderViewState;
	private String zhPaymentTarget;
	private String orderStatus;
	private String approveStatus;
	private boolean isEContractConfirmed;
	private String upompPayUrl;
	private boolean isNeedEContract;
	private String latestPassTime;
	private String earliestPassTime;
	private List<ViewOrdPerson> listPerson;
	/**
	 * 采购产品的状态.
	 */
	private String resourceConfirmStatus;
	/**
	 * 是否资源需确认
	 */
	private boolean needResourceConfirm;
	/**
	 * 招商支付地址
	 */
	
	private List<String> additionList;
	
	/**
	 * 能否发送短信凭证
	 */
	private boolean canSendCert;
	
	/**
	 * 是否能提交支付
	 */
	private boolean canToPay;
	
	
	private String payUrl;
	
	/** 
	* 支付宝手机支付 
	*/ 
	private String alipayAppUrl; 
	/** 
	* 支付宝Wap支付 
	*/ 
	private String alipayWapUrl;
	
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

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<ViewOrdPerson> getListPerson() {
		return listPerson;
	}

	public void setListPerson(List<ViewOrdPerson> listPerson) {
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getMainProductType() {
		return mainProductType;
	}

	public void setMainProductType(String mainProductType) {
		this.mainProductType = mainProductType;
	}

	public String getJieshen() {
		return jieshen;
	}

	public void setJieshen(String jieshen) {
		this.jieshen = jieshen;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	public boolean isNeedResourceConfirm() {
		return needResourceConfirm;
	}

	public void setNeedResourceConfirm(boolean needResourceConfirm) {
		this.needResourceConfirm = needResourceConfirm;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
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


	public String getShareContent () {
		String _productName = StringUtil.subStringStr(this.getProductName(), 60);
		String content = String.format("#驴行狠划算#我刚在@驴妈妈旅游网，预订了“%s”，一共省了%s元，产品", _productName,this.getJieshen());
		return content;
	}
	
	public String getShareUrl (){
	 return Constant.WWW_HOST+"/product/"+this.getMianProductId();
	}

	public Long getMianProductId() {
		return mianProductId;
	}

	public void setMianProductId(Long mianProductId) {
		this.mianProductId = mianProductId;
	}
	
	public boolean isCanToPay() {
		return canToPay;
	}

	public void setCanToPay(boolean canToPay) {
		this.canToPay = canToPay;
	}

	public boolean isCanSendCert() {
		return canSendCert;
	}

	public void setCanSendCert(boolean canSendCert) {
		this.canSendCert = canSendCert;
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

}
