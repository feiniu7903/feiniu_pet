package com.lvmama.comm.work.builder;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;

public interface WorkOrderSenderBiz {
	/**
	 * 发系统工单
	 * 
	 * @param ordersList
	 *            订单集合
	 * @param workOrderType
	 *            工单类型
	 * @param url
	 *            处理链接
	 * @param isRepeatSender
	 *            是否发送重复工单
	 * @param isJdGroup
	 *            是否计调组织
	 * @param sendGroupId
	 *            发送人组织id
	 * @param sendUserName
	 *            发送人
	 * @param receiveGroupId
	 *            接收人组织id
	 * @param receiveName
	 *            接收人
	 * */
	public void sendWorkOrder(List<OrdOrder> ordersList, String workOrderType,
			String url, boolean isRepeatSender, boolean isJdGroup,
			Long sendGroupId, String sendUserName, Long receiveGroupId,
			String receiveUserName);

	/**
	 * 发系统工单
	 * 
	 * @param order
	 *            订单
	 * @param workOrderType
	 *            工单类型
	 * @param url
	 *            处理链接
	 * @param isRepeatSender
	 *            是否发送重复工单
	 * @param isJdGroup
	 *            是否计调组织
	 * @param sendGroupId
	 *            发送人组织id
	 * @param sendUserName
	 *            发送人
	 * @param receiveGroupId
	 *            接收人组织id
	 * @param receiveName
	 *            接收人
	 * @param metaProductId
	 *            采购单ID
	 * @param isNotGetFitReceiveUser
	 *            是否要重新获取接收用户，true：不需要，false：需要重新获取
	 * */
	public void sendWorkOrder(OrdOrder order, String workOrderType, String url,
			boolean isRepeatSender, boolean isJdGroup, Long sendGroupId,
			String sendUserName, Long receiveGroupId, String receiveUserName,
			Long metaProductId, boolean isNotGetFitReceiveUser);

}
