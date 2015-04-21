package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.CertificateItemVo;

public class EbkTask implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4953463984742786926L;
	private Long ebkTaskId;
    private Long orderId;
    private Long ordItemMetaId;
    private String confirmUser;
    private Date confirmTime;
    private String followUser;
    private Date followTime;
    private Date createTime;
    private Long ebkCertificateId;
	private Date orderCreateTime;
	private String travellerName;
	private String paymentStatus;
	private String orderStatus;
	private Long roomQuantity;
	private String resourceConfirm;
	private EbkCertificate ebkCertificate;
	/***修复数据*/
	private String taskType;
	private String status;
	private String memo;
	private String reason;
	private String groupWordStatus;
	
	public String getGroupWordStatusDesc() {
		if (StringUtils.isNotBlank(groupWordStatus)) {
			if ("NEEDSEND".equals(groupWordStatus) || 
					"UPLOADED_NOT_SENT".equals(groupWordStatus)) {
				return "未发送";
			} else if ("SENT_NOTICE".equals(groupWordStatus) ||
					"SENT_NO_NOTICE".equals(groupWordStatus) ||
					"MODIFY_NOTICE".equals(groupWordStatus)|| 
					"MODIFY_NO_NOTICE".equals(groupWordStatus)) {
				return "已发送";
			}
		}
		return "--";
	}
	
	public String getGroupWordStatus() {
		return groupWordStatus;
	}

	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}

	public String getZhOrderStatus(){
		return Constant.ORDER_STATUS.getCnName(orderStatus);
	}
	
	public Long getEbkTaskId() {
		return ebkTaskId;
	}
	public void setEbkTaskId(Long ebkTaskId) {
		this.ebkTaskId = ebkTaskId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getZhConfirmTime() {
		return DateUtil.getFormatDate(confirmTime, "yyyy-MM-dd HH:mm:ss");
	}
	public String getFollowUser() {
		return followUser;
	}
	public void setFollowUser(String followUser) {
		this.followUser = followUser;
	}
	public Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
	public String getZhFollowTime(){
		return DateUtil.getFormatDate(followTime, "yyyy-MM-dd HH:mm:ss");
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getEbkCertificateId() {
		return ebkCertificateId;
	}
	public void setEbkCertificateId(Long ebkCertificateId) {
		this.ebkCertificateId = ebkCertificateId;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public String getZhOrderCreateTime(){
		return DateUtil.getFormatDate(orderCreateTime, "yyyy-MM-dd HH:mm:ss");
	}
	public String getTravellerName() {
		return travellerName;
	}
	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getRoomQuantity() {
		return roomQuantity;
	}
	public void setRoomQuantity(Long roomQuantity) {
		this.roomQuantity = roomQuantity;
	}
	public String getResourceConfirm() {
		return resourceConfirm;
	}
	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}
	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}
	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}

	public String getZhPaymentStatus() {
		return Constant.PAYMENT_STATUS.getCnName(paymentStatus);
	}
	public long getConsumeTime() {
		if(this.ebkCertificate!=null&&"false".equals(this.ebkCertificate.getValid())) {
			return 0;
		}
		Date d = this.confirmTime;
		if(d == null) {
			d = new Date();
		}
		return ((d.getTime())-this.createTime.getTime())/1000/60;
	}
	public String getRealProductName() {
		String realProductName="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			realProductName = this.ebkCertificate.getEbkCertificateItemList().get(0).getRealProductName();
		}
		return realProductName;
	}

	public Long getMetaProductId() {
		Long metaProductId=null;
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			metaProductId = this.ebkCertificate.getEbkCertificateItemList().get(0).getMetaProductId();
		}
		return metaProductId;	
	}
	public String getMetaProductIdStrList() {
		String metaProductIdStrList="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			int length = this.ebkCertificate.getEbkCertificateItemList().size();
			for(int i=0;i<length;i++){
				metaProductIdStrList += String.valueOf(this.ebkCertificate.getEbkCertificateItemList().get(0).getMetaProductId());
				if(i<length-1){
					metaProductIdStrList+="/";
				}
			}
		}
		return metaProductIdStrList;	
	}
	
	public String getMetaProductName() {
		String metaProductName="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			metaProductName = this.ebkCertificate.getEbkCertificateItemList().get(0).getMetaProductName();
		}
		return metaProductName;	
	}

	public String getMetaProductNameStrList() {
		String metaProductNameStrList="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			int length = this.ebkCertificate.getEbkCertificateItemList().size();
			for(int i=0;i<length;i++){
				metaProductNameStrList += this.ebkCertificate.
						getEbkCertificateItemList().get(i).getMetaProductName();
				if(i<length-1){
					metaProductNameStrList+="/";
				}
			}
		}
		return metaProductNameStrList;	
	}
	
	public String getTotalAdultQuantityStrList() {
		String totalAdultQuantityStrList="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateData().get("items")!=null){
			@SuppressWarnings("unchecked")
			List<CertificateItemVo> itemVoList= (List<CertificateItemVo>)(this.ebkCertificate.getEbkCertificateData().get("items"));
			int length = itemVoList.size();
			for(int i=0;i<length;i++){
				totalAdultQuantityStrList += String.valueOf(itemVoList.get(i).getBaseInfo().get("adultQuantity"));
				if(i<length-1){
					totalAdultQuantityStrList+="/";
				}
			}
		}
		return totalAdultQuantityStrList;	
	}	
	public String getTotalChildQuantityStrList() {
		String totalChildQuantityStrList="";
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateData().get("items")!=null){
			@SuppressWarnings("unchecked")
			List<CertificateItemVo> itemVoList= (List<CertificateItemVo>)(this.ebkCertificate.getEbkCertificateData().get("items"));
			int length = itemVoList.size();
			for(int i=0;i<length;i++){
				totalChildQuantityStrList += String.valueOf(itemVoList.get(i).getBaseInfo().get("childQuantity"));
				if(i<length-1){
					totalChildQuantityStrList+="/";
				}
			}
		}
		return totalChildQuantityStrList;	
	}
	public Long getQuantity() {
		Long quantity=0L;
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			quantity = this.ebkCertificate.getEbkCertificateItemList().get(0).getQuantity();
		}
		return quantity;	
	}
	
	public Long getTotalAdultQuantity() {
		Long totalAdultQuantity=0L;
		if(this.ebkCertificate!=null&&this.ebkCertificate.getOrdOrderItemMetaList()!=null){
			totalAdultQuantity = this.ebkCertificate.getOrdOrderItemMetaList().get(0).getTotalAdultQuantity();
		}
		return totalAdultQuantity;	
	}	
	public Long getTotalChildQuantity() {
		Long totalChildQuantity=0L;
		if(this.ebkCertificate!=null&&this.ebkCertificate.getOrdOrderItemMetaList()!=null){
			totalChildQuantity = this.ebkCertificate.getOrdOrderItemMetaList().get(0).getTotalChildQuantity();
		}
		return totalChildQuantity;	
	}
	public Float getSettlementPriceYuan()
	{
		Float settlementPriceYuan=0F;
		if(this.ebkCertificate!=null&&this.ebkCertificate.getEbkCertificateItemList()!=null){
			settlementPriceYuan = this.ebkCertificate.getEbkCertificateItemList().get(0).getSettlementPriceYuan();
		}
		return settlementPriceYuan;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getOrdItemMetaId() {
		return ordItemMetaId;
	}

	public void setOrdItemMetaId(Long ordItemMetaId) {
		this.ordItemMetaId = ordItemMetaId;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//订单号
		sb.append(orderId + "\t");
		//订单状态
		sb.append(getZhOrderStatus() + "\t");
		//类型
		sb.append(ebkCertificate.getZhEbkCertificateType() + "\t");
		//确认状态
		sb.append(ebkCertificate.getZhCertificateTypeStatus() + "\t");
		//支付状态
		sb.append(getZhPaymentStatus() + "\t");
		//产品ID
		sb.append(getMetaProductIdStrList() + "\t");
		//产品名称
		sb.append(getMetaProductNameStrList() + "\t");
		//游客姓名
		sb.append(travellerName + "\t");
		//出发时间
		sb.append(ebkCertificate.getVisitTime() + "\t");
		//成人数
		sb.append(getTotalAdultQuantityStrList() + "\t");
		//儿童数
		sb.append(getTotalChildQuantityStrList() + "\t");
		//订购份数
		sb.append(getQuantity() + "\t");
		//结算单价
		sb.append(getSettlementPriceYuan() + "\t");
		//结算总价
		sb.append(ebkCertificate.getTotalSettlementPriceYuan() + "\t");
		//供应商确认人
		if (null == confirmUser) {
			sb.append("\t");
		} else {
			sb.append(confirmUser + "\t");
		}
		//确认时间
		if (null != confirmTime) {
			sb.append(confirmTime + "\t");
		}
		return sb.toString();
	}
}