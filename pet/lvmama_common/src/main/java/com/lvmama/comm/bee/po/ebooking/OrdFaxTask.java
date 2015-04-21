package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 传真任务. <br/>
 */
public final class OrdFaxTask implements Serializable {

	private static final long serialVersionUID = 8068742610939212096L;
	// 主键.
	private Long faxTaskId;
	// 订单ID，冗余字段
	private Long orderId;
	// 订单.
	private OrdOrder ordOrder;
	// 订单的最晚取消时间
	private Date ordLastCancelTime;
	// 凭证对象ID.
	private Long targetId;
	// 订单子子项ID.
	private Long orderItemMetaId;
	// 传真状态.
	// 取值为com.lvmama.common.vo.Constant.CODE_TYPE=FAX_STATUS,即COM_CODE表中SET_CODE=FAX_STATUS记录列表.
	private String taskStatus;
	// 创建时间.
	private Date createTime;
	// 发送时间.
	private Date sendTime;
	// 发送次数.
	private Long sendCount;
	// 计划发送时间.
	private Date planTime;
	// 是否需要关注
	private String needCare;
	// 传真备注（不同于ordOrderItemMeta中的，客服沟通用）.
	private String faxMemo;
	// 是否通过定时任务自动发送,是否需要资源审核通过后发送传真.
	private String isAutoSend;

	public Date getOrdLastCancelTime() {
		return ordLastCancelTime;
	}

	public void setOrdLastCancelTime(Date ordLastCancelTime) {
		this.ordLastCancelTime = ordLastCancelTime;
	}

	public String getIsAutoSend() {
		return isAutoSend;
	}

	public void setIsAutoSend(String isAutoSend) {
		this.isAutoSend = isAutoSend;
	}

	/**
	 * getFaxTaskId.
	 * 
	 * @return 主键
	 */
	public Long getFaxTaskId() {
		return faxTaskId;
	}

	/**
	 * setFaxTaskId.
	 * 
	 * @param faxTaskId
	 *            主键
	 */
	public void setFaxTaskId(Long faxTaskId) {
		this.faxTaskId = faxTaskId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * getTargetId.
	 * 
	 * @return 凭证对象ID
	 */
	public Long getTargetId() {
		return targetId;
	}

	/**
	 * setTargetId.
	 * 
	 * @param targetId
	 *            凭证对象ID
	 */
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * getOrderItemMetaId.
	 * 
	 * @return 采购产品订单子项ID
	 */
	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	/**
	 * setOrderItemMetaId.
	 * 
	 * @param orderItemMetaId
	 *            采购产品订单子项ID
	 */
	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	/**
	 * getTaskStatus.
	 * 
	 * @return 传真状态
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * setTaskStatus.
	 * 
	 * @param taskStatus
	 *            传真状态
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * getSendTime.
	 * 
	 * @return 发送时间
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 设置传真发送时间. <br/>
	 * 注:<br/>
	 * 自动发送的发送时间为最近一次传真服务器发送完毕后的回调super后台的时间; <br/>
	 * 人工发送的发送时间为最近一次下载传真模板的时间.
	 * 
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * getSendCount.
	 * 
	 * @return 发送次数
	 */
	public Long getSendCount() {
		return sendCount;
	}

	/**
	 * setSendCount.
	 * 
	 * @param sendCount
	 *            发送次数
	 */
	public void setSendCount(Long sendCount) {
		this.sendCount = sendCount;
	}

	/**
	 * getPlanTime.
	 * 
	 * @return 计划发送时间
	 */
	public Date getPlanTime() {
		return planTime;
	}

	/**
	 * setPlanTime.
	 * 
	 * @param planTime
	 *            计划发送时间
	 */
	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
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
	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

	 

	public OrdOrderItemMeta getOrdOrderItemMeta() {
		if (ordOrder != null) {
			List<OrdOrderItemMeta> list1 = ordOrder.getAllOrdOrderItemMetas();
			List<OrdOrderItemProd> list2 = ordOrder.getOrdOrderItemProds();
			for (OrdOrderItemMeta itemMeta : list1) {
				if (this.orderItemMetaId.equals(itemMeta.getOrderItemMetaId())) {
					for (OrdOrderItemProd ordOrderItemProd : list2) {
						if (ordOrderItemProd == null) {
							continue;
						}

						if (itemMeta.getOrderItemId().equals(ordOrderItemProd.getOrderItemProdId())) {
							itemMeta.setRelateOrdOrderItemProd(ordOrderItemProd);
						}
					}
					return itemMeta;
				}
			}
		}
		return null;
	}

	public OrdOrderItemProd getOrdOrderItemProd() {
		if (ordOrder != null) {
			List<OrdOrderItemProd> list = ordOrder.getOrdOrderItemProds();
			for (OrdOrderItemProd ordOrderItemProd : list) {
				OrdOrderItemMeta meta = this.getOrdOrderItemMeta();
				if (meta.getOrderItemId().equals(ordOrderItemProd.getOrderItemProdId())) {
					return ordOrderItemProd;
				}
			}
		}
		return null;
	}

	public String getZhTaskStatus() {
		return Constant.FAX_STATUS.getCnName(taskStatus);
	}

	public String getOrderMonitorUrl() {
		return ".../ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=" + orderId;
	}

	public String getPersonListString() {
		String res = "";
		for (OrdPerson person : this.getOrdOrder().getPersonList()) {
			if (person.getName() != null
					&& Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(person.getPersonType())) {
				res += person.getName() + "(" + person.getCertNo() + ")" + " ";
			}
		}
		return res.replace("(null)", "");
	}

	public String getNeedCare() {
		return needCare;
	}

	public void setNeedCare(String needCare) {
		this.needCare = needCare;
	}

	public String getFaxMemo() {
		return faxMemo;
	}

	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
	}

	/**
	 * 是否需要加注红色显示
	 * 
	 * @return
	 */
	public boolean isRed() {
		return !"true".equals(needCare);
	}

	public String getFaxMemoString() {
		if ("".equals(faxMemo) || faxMemo == null) {
			return "无备注内容";
		}
		return faxMemo;
	}

	@Override
	public String toString() {
		return "OrdFaxTask [faxTaskId=" + faxTaskId + ", orderId=" + orderId
				+ ", ordLastCancelTime=" + ordLastCancelTime + ", targetId=" + targetId
				+ ", orderItemMetaId=" + orderItemMetaId + ", taskStatus=" + taskStatus
				+ ", createTime=" + createTime + ", sendTime=" + sendTime + ", sendCount="
				+ sendCount + ", planTime=" + planTime + ", needCare=" + needCare + ", faxMemo="
				+ faxMemo + ", isAutoSend=" + isAutoSend + "]";
	}

	/**
	 * 获取传真任务的Key.
	 * 
	 * @return
	 */
	public OrdFaxTaskKey getOrdFaxTaskKey() {
		return new OrdFaxTaskKey();
	}

	/**
	 * 传真任务合并发送的条件:同一采购产品、同一凭证对象、同一计划发送时间、同一支付对象.
	 */
	public class OrdFaxTaskKey {
		// 采购产品ID.
		private Long metaProductId;
		// 凭证对象ID.
		private Long targetId;
		// 计划发送时间.
		private String planTime;
		// 支付对象.
		private String paymentTarget;

		public OrdFaxTaskKey() {
			OrdOrderItemMeta temp = OrdFaxTask.this.getOrdOrderItemMeta();
			this.metaProductId = temp.getMetaProductId();
			this.paymentTarget = temp.getPaymentTarget();
			this.targetId = OrdFaxTask.this.targetId;
			this.planTime = DateUtil.getDateTime("yyyy-MM-dd", OrdFaxTask.this.planTime);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((metaProductId == null) ? 0 : metaProductId.hashCode());
			result = prime * result + ((paymentTarget == null) ? 0 : paymentTarget.hashCode());
			result = prime * result + ((planTime == null) ? 0 : planTime.hashCode());
			result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrdFaxTaskKey other = (OrdFaxTaskKey) obj;
			if (metaProductId == null) {
				if (other.metaProductId != null)
					return false;
			} else if (!metaProductId.equals(other.metaProductId))
				return false;
			if (paymentTarget == null) {
				if (other.paymentTarget != null)
					return false;
			} else if (!paymentTarget.equals(other.paymentTarget))
				return false;
			if (planTime == null) {
				if (other.planTime != null)
					return false;
			} else if (!planTime.equals(other.planTime))
				return false;
			if (targetId == null) {
				if (other.targetId != null)
					return false;
			} else if (!targetId.equals(other.targetId))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "OrdOrderItemMetaKey [metaProductId=" + metaProductId + ", targetId=" + targetId
					+ ", planTime=" + planTime + ", paymentTarget=" + paymentTarget + "]";
		}
	}
}