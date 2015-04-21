package com.lvmama.comm.abroad.po;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.vo.Constant;

/**
 * 售后服务.
 *
 */
public final class AhotelOrdSaleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -995259781681635098L;
	/**
	 * 主键.
	 */
	private Long saleServiceId;
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 服务类型.
	 */
	private String serviceType;
	/**
	 * 申请内容.
	 */
	private String applyContent;
	/**
	 * 操作人登录名.
	 */
	private String operatorName;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 订单.
	 */
	private ReservationsOrder ordOrder;
	/**
	 * 服务类型名称.
	 */
	private String serviceTypeName;
	/**
	 * 订单态售后服务状态(NORMAL/FINISH).
	 */
	private String status;
	/**
	 * 显示状态的中文名称.
	 */
	private String statusName;
	/**
	 * 售后查询中的申请处理,当申请状态为正常时，才出现申请处理链接.
	 */
	private String visible= "false";
	/**
	 * 售后结束原因.
	 */
	private String reason;
	/**
	 * 售后结束原因（中文）.
	 */
	private String ZkReason;
	
	/**
	 * 是否有退款
	 */
	private String hasRefund = "false";


	/**
	 * getSaleServiceId.
	 *
	 * @return 主键
	 */
	public Long getSaleServiceId() {
		return saleServiceId;
	}

	/**
	 * setSaleServiceId.
	 *
	 * @param saleServiceId
	 *            主键
	 */
	public void setSaleServiceId(final Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	/**
	 * getOrderId.
	 *
	 * @return 订单ID
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * setOrderId.
	 *
	 * @param orderId
	 *            订单ID
	 */
	public void setOrderId(final Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * getServiceType.
	 *
	 * @return 服务类型
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * setServiceType.
	 *
	 * @param serviceType
	 *            服务类型
	 */
	public void setServiceType(final String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * getApplyContent.
	 *
	 * @return 申请内容
	 */
	public String getApplyContent() {
		return applyContent;
	}

	/**
	 * setApplyContent.
	 *
	 * @param applyContent
	 *            申请内容
	 */
	public void setApplyContent(final String applyContent) {
		this.applyContent = applyContent;
	}

	/**
	 * getOperatorName.
	 *
	 * @return 操作人登录名
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * setOperatorName.
	 *
	 * @param operatorName
	 *            操作人登录名
	 */
	public void setOperatorName(final String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * getCreateTime.
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * setCreateTime.
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public String getServiceTypeName() {
		return Constant.SERVICE_TYPE.getCnName(this.serviceType);
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return Constant.ORD_SALE_STATUS.getCnName(this.status);
	}
	public String getVisible() {
		if ("NORMAL".equals(this.status)){
			this.visible = "true";
		}
		return visible;
	}
	public String getNotVisible() {
		if ("NORMAL".equals(this.status)){
			return "false";
		}
		return "true";		
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHasRefund() {
		return hasRefund;
	}

	public void setHasRefund(String hasRefund) {
		this.hasRefund = hasRefund;
	}
	
	public void setHasRefund(boolean hasRefund) {
		if (hasRefund) {
			this.hasRefund = "true";
		} else {
			this.hasRefund = "false";
		}
	}

	public String getZkReason() {
		return Constant.ORD_SALE_REASON.getCnName(this.reason);
	}

	public ReservationsOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(ReservationsOrder ordOrder) {
		this.ordOrder = ordOrder;
	}
}