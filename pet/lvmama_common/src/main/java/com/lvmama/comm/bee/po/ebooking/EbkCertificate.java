package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class EbkCertificate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3088560237835628665L;

	private Long ebkCertificateId;

    private Long supplierId;

    private String supplierName;

    private Long targetId;

    private String targetName;

    private String ebkCertificateType;

    private String productType;
    
    private List<String> productTypeVo;

    private String certificateStatus;

    private Long oldCertificateId;

    private Date visitTime;
    private Date leaveTime;

    private Date retentionTime;

    private String memo;

    private String reason;

    private String faxStrategy;

    private String toFax;

    private String toName;

    private String toTel;

    private String confirmChannel;

    private Long version;
    
    private Long supplierLoss;
    
    private Long travellerLoss;
    
    private String paymentTarget;
    
    private String subProductType;
    
    private List<String> subProductTypeVo;
    
    private String filialeName;
    /**
     * 主产品ID
     */
    private Long mainMetaProductID;
    /**
     * 主产品名称
     */
    private String mainMetaProductName;
    /**
     * 凭证的所有特殊要求(取自子项)
     */
    private String allUserMemo;
    
    private String travellerName;
    
    private String mobile;
    
    private String createTask;
    
    private String valid;
    
    private List<EbkCertificateItem> ebkCertificateItemList;
    
    private List<EbkOrderDataRev> ebkOrderDataRevList;
    
    private List<OrdOrderItemMeta> ordOrderItemMetaList;
	private Map<String,Object> ebkCertificateData;
    private EbkTask ebkTask;
    private String userMemoStatus;
    /**
     * 是否资源审核通过后发送传真（取自子项）
     */
    private String isResourceSendFax;
    
    private String orderType;
    
    private List<String> orderTypeVo;
    private String messageType;
    
	/**
	 * 是否为不定期产品订单
	 * */
	private String isAperiodic = "false";
	
	/**
	 * 密码券
	 * */
	private String passwordCertificate;
	
  private String testOrder;

	/**
	 * 使用状态
	 * */
	private String useStatus;

	/**
	 * 有效开始日期
	 * */
	private Date validBeginTime;

	/**
	 * 有效结束日期
	 * */
	private Date validEndTime;
	
	/**
	 * 保存变更单备注栏信息(方便显示最近一次的变更单信息)
	 * */
	private String changeInfo;
    
		/**
     * 得到凭证关键字
     * 
     * @author: ranlongfei 2013-4-1 下午8:57:27
     * @return
     */
    public String getCertificateKey() {
    	String key;
    	if(Constant.PRODUCT_TYPE.ROUTE.name().equals(this.productType)){
			key = this.getSupplierId()+"_"+this.getTargetId()+"_"+this.getProductType()+"_"+this.getFaxStrategy()+"_"+this.getVisitTime();
		}else {
			key = this.getSupplierId()+"_"+this.getTargetId()+"_"+this.getProductType()+"_"+this.getVisitTime();
		}
    	return key;
    }
    
    public String getZhCertificateStatus(){
    	return Constant.EBK_TASK_STATUS.getCnName(certificateStatus);
    }
    /**
     * 组合后的凭证状态
     * 
     * @author: ranlongfei 2013-3-25 下午7:17:07
     * @return
     */
    public String getZhCertificateTypeStatus(){
    	if(Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.name().equals(this.certificateStatus)) {
    		return Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.getCnName();
    	}
    	return Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.getCnName(ebkCertificateType+"_"+certificateStatus);
    }
    public String getCertificateTypeStatus(){
    	if(Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.name().equals(this.certificateStatus)) {
    		return Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.name();
    	}
    	return ebkCertificateType+"_"+certificateStatus;
    }
    
    public String getZhConfirmChannel() {
    	return Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.getCnName(confirmChannel);
    }
    public EbkTask getEbkTask() {
		return ebkTask;
	}
    public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}
    public String getZhPaymentTarget() {
    	if(!StringUtil.isEmptyString(paymentTarget))
    	{
    		return Constant.PAYMENT_TARGET.getCnName(paymentTarget);
    	}
    	return Constant.PAYMENT_TARGET.TOLVMAMA.getCnName();
    }
	public void setEbkTask(EbkTask ebkTask) {
		this.ebkTask = ebkTask;
	}

	public Long getEbkCertificateId() {
        return ebkCertificateId;
    }

    public void setEbkCertificateId(Long ebkCertificateId) {
        this.ebkCertificateId = ebkCertificateId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    public String getEbkCertificateType() {
        return ebkCertificateType;
    }

    public void setEbkCertificateType(String ebkCertificateType) {
        this.ebkCertificateType = ebkCertificateType == null ? null : ebkCertificateType.trim();
    }
    
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus == null ? null : certificateStatus.trim();
    }

    public Long getOldCertificateId() {
        return oldCertificateId;
    }

    public void setOldCertificateId(Long oldCertificateId) {
        this.oldCertificateId = oldCertificateId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Date getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Date retentionTime) {
        this.retentionTime = retentionTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getFaxStrategy() {
        return faxStrategy;
    }

    public void setFaxStrategy(String faxStrategy) {
        this.faxStrategy = faxStrategy == null ? null : faxStrategy.trim();
    }
    
    /**
     * 是否需要订单合并
     * @param faxStrategy
     * @return
     */
    public boolean hasSingleOrderCert() {
        return this.faxStrategy.equalsIgnoreCase(Constant.FAX_STRATEGY.IMMEDIATELY.name())
        		|| this.faxStrategy.equalsIgnoreCase(Constant.FAX_STRATEGY.MANUAL_SEND.name());
    }

    public String getToFax() {
        return toFax;
    }

    public void setToFax(String toFax) {
        this.toFax = toFax == null ? null : toFax.trim();
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName == null ? null : toName.trim();
    }

    public String getToTel() {
        return toTel;
    }

    public void setToTel(String toTel) {
        this.toTel = toTel == null ? null : toTel.trim();
    }

    public String getConfirmChannel() {
        return confirmChannel;
    }

    public void setConfirmChannel(String confirmChannel) {
        this.confirmChannel = confirmChannel == null ? null : confirmChannel.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public boolean hasEbkCertificateTypeConfirm(){
    	return Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name().equals(this.ebkCertificateType);
    }
    public boolean hasEbkCertificateTypeEnquiry(){
    	return Constant.EBK_CERTIFICATE_TYPE.ENQUIRY.name().equals(this.ebkCertificateType);
    }
    public boolean hasEbkCertificateTypeChange(){
    	return Constant.EBK_CERTIFICATE_TYPE.CHANGE.name().equals(this.ebkCertificateType);
    }
    public boolean hasEbkCertificateTypeCancel(){
    	return Constant.EBK_CERTIFICATE_TYPE.CANCEL.name().equals(this.ebkCertificateType);
    }
    public boolean hasCertificateStatusCreate(){
    	return Constant.EBK_TASK_STATUS.CREATE.name().equals(this.certificateStatus);
    }
    
    public boolean hasCertificateStatusReject(){
    	return Constant.EBK_TASK_STATUS.REJECT.name().equals(this.certificateStatus);
    }
    public boolean hasCertificateStatusAccept(){
    	return Constant.EBK_TASK_STATUS.ACCEPT.name().equals(this.certificateStatus);
    }


	public List<EbkCertificateItem> getEbkCertificateItemList() {
		return ebkCertificateItemList;
	}

	public void setEbkCertificateItemList(
			List<EbkCertificateItem> ebkCertificateItemList) {
		this.ebkCertificateItemList = ebkCertificateItemList;
	}

	public List<OrdOrderItemMeta> getOrdOrderItemMetaList() {
		return ordOrderItemMetaList;
	}
	 
	public OrdOrderItemMeta getFaxOrdOrderItemMeta() {
		OrdOrderItemMeta ooitm = ordOrderItemMetaList.get(0);
		for(OrdOrderItemMeta m : ordOrderItemMetaList) {
			if(!m.isAdditionBranchMeta() && m.isNeedSendFax()) {
				ooitm = m;
				break;
			}
		}
		return ooitm;
	}
	public OrdOrderItemMeta getEbkTaskOrdOrderItemMeta() {
		OrdOrderItemMeta ooitm = ordOrderItemMetaList.get(0);
		for(OrdOrderItemMeta m : ordOrderItemMetaList) {
			if(!m.isAdditionBranchMeta() && m.hasSupplier()) {
				ooitm = m;
				break;
			}
		}
		return ooitm;
	}
	public void setOrdOrderItemMetaList(List<OrdOrderItemMeta> ordOrderItemMetaList) {
		this.ordOrderItemMetaList = ordOrderItemMetaList;
	}

	public void putOrdOrderItemMeta(OrdOrderItemMeta ordOrderItemMeta){
		if(this.ordOrderItemMetaList == null){
			ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
		}
		ordOrderItemMetaList.add(ordOrderItemMeta);
	}
	
	public void putEbkCertificateItem(EbkCertificateItem item){
		if(this.ebkCertificateItemList == null){
			ebkCertificateItemList = new ArrayList<EbkCertificateItem>();
		}
		ebkCertificateItemList.add(item);
	}
	
	public String getZhEbkCertificateType() {
		return Constant.EBK_CERTIFICATE_TYPE.getCnName(this.ebkCertificateType);
	}
	
	public Date getLeaveTime() {
		if(this.visitTime!=null && ebkCertificateItemList!= null && ebkCertificateItemList.size()>0){
			Long nights = ebkCertificateItemList.get(0).getNights();
			if(nights!=null&&nights.intValue()>0){
				this.leaveTime = DateUtil.getDateAfterDays(this.visitTime, nights.intValue());
			}else{
				this.leaveTime = this.visitTime;				
			}
		}
		return leaveTime;
	}
	public String getZhVisitTime() {
		return DateUtil.getFormatDate(visitTime, "yyyy-MM-dd");
	}
	public String getZhLeaveTime() {
		return DateUtil.getFormatDate(leaveTime, "yyyy-MM-dd");
	}
	public String getZhRetentionTime() {
		return DateUtil.getFormatDate(retentionTime, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 取所有凭证子项的订单确认号
	 */
	public String getSupplierNo()
	{
		String result="";
		for( EbkCertificateItem ebkCertificateItem:getEbkCertificateItemList())
		{
			result = ebkCertificateItem.getSupplierNo();
			break;
		}
		return result;
	}
	/**
	 * 获取所有凭证子项的传真特殊备注
	 * @return
	 */
	
	public String getAllFaxMemo()
	{
		String result=null;
		for( EbkCertificateItem ebkCertificateItem:getEbkCertificateItemList())
		{
			if(!StringUtil.isEmptyString(ebkCertificateItem.getFaxMemo()))
			{
				if(result==null) result=ebkCertificateItem.getFaxMemo();
				else result+=ebkCertificateItem.getFaxMemo();
			}
		}
		return result;
	}
	/**
	 * 计算结算总价
	 * @return
	 */
	public Long getTotalSettlementPrice() {
		Long result = 0L;
		for( EbkCertificateItem ebkCertificateItem:getEbkCertificateItemList())
		{
			result = result+ebkCertificateItem.getSettlementPrice()*ebkCertificateItem.getQuantity();
		}
		return result;
	}
	/**
	 * 计算结算总价(元为单位)
	 * @return
	 */
	public Float getTotalSettlementPriceYuan(){
		return PriceUtil.convertToYuan(this.getTotalSettlementPrice());
	}
	
	public Long getSupplierLoss() {
		return supplierLoss;
	}
	public void setSupplierLoss(Long supplierLoss) {
		this.supplierLoss = supplierLoss;
	}
	public Long getTravellerLoss() {
		return travellerLoss;
	}
	public void setTravellerLoss(Long travellerLoss) {
		this.travellerLoss = travellerLoss;
	}
	public List<EbkOrderDataRev> getEbkOrderDataRevList() {
		return ebkOrderDataRevList;
	}
	public void setEbkOrderDataRevList(List<EbkOrderDataRev> ebkOrderDataRevList) {
		this.ebkOrderDataRevList = ebkOrderDataRevList;
	}
	
	public boolean isTicket(){
		return Constant.PRODUCT_TYPE.TICKET.name().equals(productType);
	}
	public boolean isHotel(){
		return Constant.PRODUCT_TYPE.HOTEL.name().equals(productType);
	}
	public String getPaymentTarget() {
		return paymentTarget;
	}
	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}
	public Long getMainMetaProductID() {
		return mainMetaProductID;
	}
	public void setMainMetaProductID(Long mainMetaProductID) {
		this.mainMetaProductID = mainMetaProductID;
	}
	public String getMainMetaProductName() {
		return mainMetaProductName;
	}
	public void setMainMetaProductName(String mainMetaProductName) {
		this.mainMetaProductName = mainMetaProductName;
	}
	public String getAllUserMemo() {
		return allUserMemo;
	}
	public void setAllUserMemo(String allUserMemo) {
		this.allUserMemo = allUserMemo;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 是否已经生成任务
	 * @return
	 */
	public boolean hasCreateTask(){
		return "true".equalsIgnoreCase(createTask);
	}

	public String getCreateTask() {
		return createTask;
	}

	public void setCreateTask(String createTask) {
		this.createTask = createTask;
	}

	public List<String> getProductTypeVo() {
		return productTypeVo;
	}

	public void setProductTypeVo(List<String> productTypeVo) {
		this.productTypeVo = new ArrayList<String>();
		if(productTypeVo!=null){
			for(String str:productTypeVo){
				this.productTypeVo.add(str);
			}
		}
	}

	public List<String> getSubProductTypeVo() {
		return subProductTypeVo;
	}

	public void setSubProductTypeVo(List<String> subProductTypeVo) {
		this.subProductTypeVo = new ArrayList<String>();
		if(subProductTypeVo!=null){
			for(String str:subProductTypeVo){
				this.subProductTypeVo.add(str);
			}	
		}
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getUserMemoStatus() {
		return userMemoStatus;
	}

	public void setUserMemoStatus(String userMemoStatus) {
		this.userMemoStatus = userMemoStatus;
	}

	public Map<String, Object> getEbkCertificateData() {
		return ebkCertificateData;
	}

	public void setEbkCertificateData(Map<String, Object> ebkCertificateData) {
		this.ebkCertificateData = ebkCertificateData;
	}

	public String getIsResourceSendFax() {
		return isResourceSendFax;
	}

	public void setIsResourceSendFax(String isResourceSendFax) {
		this.isResourceSendFax = isResourceSendFax;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<String> getOrderTypeVo() {
		return orderTypeVo;
	}

	public void setOrderTypeVo(List<String> orderTypeVo) {
		this.orderTypeVo = new ArrayList<String>();
		if(orderTypeVo!=null){
			for(String str:orderTypeVo){
				this.orderTypeVo.add(str);
			}	
		}
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTestOrder() {
		return testOrder;
	}

	public void setTestOrder(String testOrder) {
		this.testOrder = testOrder;
	}
	
	 public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public String getPasswordCertificate() {
		return passwordCertificate;
	}

	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
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
	public String getChangeInfo() {
		return changeInfo;
	}

	public void setChangeInfo(String changeInfo) {
		this.changeInfo = changeInfo;
	}

	
	public String getStartDays() {
		String days = "";
		if(null != visitTime) {
			int cnt = DateUtil.getDaysBetween(Calendar.getInstance().getTime(), visitTime);
			days = String.valueOf(cnt);
		}
		return days;
	}
	
}