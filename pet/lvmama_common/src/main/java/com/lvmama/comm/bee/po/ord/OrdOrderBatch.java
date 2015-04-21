package com.lvmama.comm.bee.po.ord;

import java.util.Date;

import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;


public class OrdOrderBatch implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7547194100506598599L;
	private long batchId = 0;
	private long productBranchId = 0;
	private String productBranchName;
	private long productId = 0;
	private String productName;
	private String contacts;
	private String contactsPhone;
	private int batchCount;
	private String status;
	private String reson;
	private long creator;
	private String creatorName;
	private String creatorPhone;
	private DistributorTuanInfo distributorTuanInfo = new DistributorTuanInfo();
	private Date createtime;
	
	private Long orderId;
	private Date invalidDate;
	private Date validBeginDate;
	private Date validEndDate;
	private String addCode;
	private String isValid;
	private String operatorName;
	
	private String orderStatus;
	
	private String performStatus;
	
	private String performStatusCnName;

	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public long getProductBranchId() {
		return productBranchId;
	}
	public void setProductBranchId(long productBranchId) {
		this.productBranchId = productBranchId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getContactsPhone() {
		return contactsPhone;
	}
	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getProductBranchName() {
		return productBranchName;
	}
	public void setProductBranchName(String productBranchName) {
		this.productBranchName = productBranchName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getAddCode() {
		return addCode;
	}
	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	public int getBatchCount() {
		return batchCount;
	}
	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorPhone() {
		return creatorPhone;
	}
	public void setCreatorPhone(String creatorPhone) {
		this.creatorPhone = creatorPhone;
	}
	public String getOrderStatus() {
		return Constant.ORDER_STATUS.getCnName(orderStatus);
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPerformStatus() {
		return this.performStatus;
	}
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}
	
	public Date getValidBeginDate() {
		return validBeginDate;
	}
	public void setValidBeginDate(Date validBeginDate) {
		this.validBeginDate = validBeginDate;
	}
	public Date getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}
	
	public DistributorTuanInfo getDistributorTuanInfo() {
		return distributorTuanInfo;
	}
	public void setDistributorTuanInfo(DistributorTuanInfo distributorTuanInfo) {
		this.distributorTuanInfo = distributorTuanInfo;
	}
	
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getStrValidBeginDate(){
		if(this.validBeginDate!=null){
			return DateUtil.formatDate(validBeginDate, "yyyy-MM-dd");
		}
		return "";
	}
	public String getStrValidEndDate(){
		if(this.validEndDate!=null){
			return DateUtil.formatDate(validEndDate, "yyyy-MM-dd");
		}
		return "";
	}
	
	
	public void setPerformStatusCnName(String performStatusCnName) {
		this.performStatusCnName = performStatusCnName;
	}
	public String getPerformStatusCnName() {
		if(Constant.PASSCODE_USE_STATUS.USED.name().equals(this.performStatus)){
			return "已履行";
		}else if(Constant.PASSCODE_USE_STATUS.UNUSED.name().equals(this.performStatus)){
			return "未履行";
		}else if(Constant.PASSCODE_USE_STATUS.DESTROYED.name().equals(this.performStatus)){
			return "已废除";
		}
		return "未知";
	}
	
	
}