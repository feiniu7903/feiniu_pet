package com.lvmama.back.sweb.phoneorder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.vo.Constant;

public class Cart {

	private Long productId;
	
	private Long oldOrderId;

	private List<OrdOrderMemo> memoList;

	private OrdInvoice ordInvoice;

	private UsrReceivers usrReceiver;

	private Date visitDate;

	private Date leaveDate;

	private ProdProduct product;

	private String paymentTarget;

	private String paymentWaitTime;

	private String needInvoice;

	private String resourceConfirm;

	private String needRedail;

	private String userId;
	
	private String orderChannel = Constant.CHANNEL.BACKEND.name();
	
	private Coupon coupon;

	private String paymentChannel;

	private String channel;
	
	private Long singleSellPriceTotal;
	
	private Long singleMarketPriceTotal;
	
	private Map<UsrReceivers, String> visitorList;
	
	private List<OrdTimeInfo> timeInfoList;

	private Map<ProdProductBranch, Long> prodBranchItemList;

	private Map<ProdProductRelation, Long> relationProductList;
	/**
	 * 紧急联系人
	 */
	private UsrReceivers emergencyContact;

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public List<OrdOrderMemo> getMemoList() {
		return memoList;
	}

	public void setMemoList(List<OrdOrderMemo> memoList) {
		this.memoList = memoList;
	}

	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

	public void setOrdInvoice(OrdInvoice ordInvoice) {
		this.ordInvoice = ordInvoice;
	}

	public UsrReceivers getUsrReceiver() {
		return usrReceiver;
	}

	public void setUsrReceiver(UsrReceivers usrReceiver) {
		this.usrReceiver = usrReceiver;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public String getPaymentWaitTime() {
		return paymentWaitTime;
	}

	public void setPaymentWaitTime(String paymentWaitTime) {
		this.paymentWaitTime = paymentWaitTime;
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getResourceConfirm() {
		return resourceConfirm;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	public String getNeedRedail() {
		return needRedail;
	}

	public void setNeedRedail(String needRedail) {
		this.needRedail = needRedail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<ProdProductBranch, Long> getProdBranchItemList() {
		return prodBranchItemList;
	}

	public void setProdBranchItemList(Map<ProdProductBranch, Long> prodBranchItemList) {
		this.prodBranchItemList = prodBranchItemList;
	}

	public Map<ProdProductRelation, Long> getRelationProductList() {
		return relationProductList;
	}

	public void setRelationProductList(Map<ProdProductRelation, Long> relationProductList) {
		this.relationProductList = relationProductList;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<OrdTimeInfo> getTimeInfoList() {
		return timeInfoList;
	}

	public void setTimeInfoList(List<OrdTimeInfo> timeInfoList) {
		this.timeInfoList = timeInfoList;
	}

	public Long getSingleSellPriceTotal() {
		return singleSellPriceTotal;
	}

	public void setSingleSellPriceTotal(Long singleSellPriceTotal) {
		this.singleSellPriceTotal = singleSellPriceTotal;
	}

	public Long getSingleMarketPriceTotal() {
		return singleMarketPriceTotal;
	}

	public void setSingleMarketPriceTotal(Long singleMarketPriceTotal) {
		this.singleMarketPriceTotal = singleMarketPriceTotal;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Map<UsrReceivers, String> getVisitorList() {
		return visitorList;
	}

	public void setVisitorList(Map<UsrReceivers, String> visitorList) {
		this.visitorList = visitorList;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public UsrReceivers getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(UsrReceivers emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public Long getOldOrderId() {
		return oldOrderId;
	}

	public void setOldOrderId(Long oldOrderId) {
		this.oldOrderId = oldOrderId;
	}
}
