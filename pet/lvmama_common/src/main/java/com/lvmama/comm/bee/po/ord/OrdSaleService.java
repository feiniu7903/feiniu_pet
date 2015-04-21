package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

/**
 * 售后服务.
 *
 * <pre>
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 *
 */
public final class OrdSaleService implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 87138902902119100L;
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
	private OrdOrder ordOrder;
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
	 * 一城卡支付标识.
	 */
	private boolean oneCityOneCardFlag;
	/**
	 * 一城卡支付否定标识.
	 */
	private boolean oneCityOneCardNotFlag;
	
	/**
	 * 领单者:客服号.
	 */
	private String takenOperator;
	
	/**
	 * 分配时间
	 */
	private Date takenTime;
	
	/**
	 * 处理结束时间
	 * */
	private Date finishTime;
	
	private String message;  //当申请售后服务类型是投诉时，添加提醒信息
	
	/**
	 * 业务系统编码
	 */
	private String sysCode;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

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

	/**
	 * getOrdOrder.
	 *
	 * @return 订单
	 */
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	/**
	 * setOrdOrder.
	 *
	 * @param ordOrder
	 *            订单
	 */
	public void setOrdOrder(final OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

	public String getServiceTypeName() {
		return Constant.SERVICE_TYPE.getCnName(serviceType);
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
		return Constant.ORD_SALE_STATUS.getCnName(status);
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
		return Constant.ORD_SALE_REASON.getCnName(reason);
	}

	/**
	 * isOneCityOneCardFlag.
	 * 
	 * @return 一城卡支付标识
	 */
	public boolean isOneCityOneCardFlag() {
		return oneCityOneCardFlag;
	}

	/**
	 * setOneCityOneCardFlag.
	 * 
	 * @param oneCityOneCardFlag
	 *            一城卡支付标识
	 */
	public void setOneCityOneCardFlag(final boolean oneCityOneCardFlag) {
		this.oneCityOneCardFlag = oneCityOneCardFlag;
	}

	/**
	 * isOneCityOneCardNotFlag.
	 * 
	 * @return 一城卡支付否定标识
	 */
	public boolean isOneCityOneCardNotFlag() {
		return !oneCityOneCardFlag;
	}

	/**
	 * setOneCityOneCardNotFlag.
	 * 
	 * @param oneCityOneCardNotFlag
	 *            一城卡支付否定标识
	 */
	public void setOneCityOneCardNotFlag(final boolean oneCityOneCardNotFlag) {
		this.oneCityOneCardFlag = !oneCityOneCardNotFlag;
	}


	public String getTakenOperator() {
		return takenOperator;
	}

	public Date getTakenTime() {
		return takenTime;
	}

	public void setTakenTime(Date takenTime) {
		this.takenTime = takenTime;
	}

	public void setTakenOperator(String takenOperator) {
		this.takenOperator = takenOperator;
	}
	
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public String getSysCodeCnName() {
		if (StringUtils.isEmpty(this.sysCode)) {
			return "";
		}
		return Constant.COMPLAINT_SYS_CODE.getCnName(this.sysCode);
	}

	public String getZhTakenTime() {
		if(this.getTakenTime() != null) {
			if(this.getStatus() != null) {
				//结束状态处理时长，结束时间-分配时间
				if("FINISH".equalsIgnoreCase(this.getStatus())) {
					if(this.getFinishTime() != null) {
						long min = (this.getFinishTime().getTime() - this.getTakenTime().getTime())/1000/60;
						String res = min + "m";
						if(min >= 60) {
							res = min/60 + "h " + (min-(min/60)*60) + "m";
						}
						return res;
					}
				}
			}
			//正常状态处理时长：当前时间-分配时间
			long min = (new Date().getTime() - this.getTakenTime().getTime())/1000/60;
			String res = min + "m";
			if(min >= 60) {
				res = min/60 + "h " + (min-(min/60)*60) + "m";
			}
			return res;
		}
		return "";
	}
}