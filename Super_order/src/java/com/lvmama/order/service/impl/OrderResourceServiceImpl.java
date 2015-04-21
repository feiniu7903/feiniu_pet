package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS;
import com.lvmama.comm.vo.Constant.ORDER_RESOURCE_STATUS;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.service.OrderRefundmentService;
import com.lvmama.order.service.OrderResourceService;

/**
 * 订单资源服务实现类.
 *
 * <pre>
 * 封装订单资源更改
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.com.dao.ComLogDAO
 * @see com.lvmama.com.po.ComLog
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.vo.Constant.ORDER_RESOURCE_STATUS
 * @see com.lvmama.order.dao.OrderDAO
 * @see com.lvmama.order.dao.OrderItemMetaDAO
 * @see com.lvmama.order.service.OrderResourceService
 */
public final class OrderResourceServiceImpl extends OrderServiceImpl implements OrderResourceService {
	/**
	 * 订单DAO接口.
	 */
	private transient OrderDAO orderDAO;
	/**
	 * 采购产品订单子项DAO接口.
	 */
	private transient OrderItemMetaDAO orderItemMetaDAO;
	/**
	 * 支付记录DAO.
	 */
	private transient PayPaymentService payPaymentService;
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * OrdRefundment服务.
	 */
	private transient OrderRefundmentService orderRefundmentService;
	/**
	 * setOrderRefundmentService.
	 * 
	 * @param orderRefundmentService
	 *            OrdRefundment服务
	 */
	public void setOrderRefundmentService(
			final OrderRefundmentService orderRefundmentService) {
		this.orderRefundmentService = orderRefundmentService;
	}

	/**
	 * setOrderServiceProxy.
	 * 
	 * @param orderServiceProxy
	 *            订单服务
	 */
	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	/**
	 * setOrderDAO.
	 *
	 * @param orderDAO
	 *            订单DAO接口
	 */
	public void setOrderDAO(final OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	/**
	 * setOrderItemMetaDAO.
	 *
	 * @param orderItemMetaDAO
	 *            采购产品订单子项DAO接口
	 */
	public void setOrderItemMetaDAO(final OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}
		
	/**
	 * 更改采购产品订单子项资源状态.
	 *
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param resourceStatus
	 *            更改后的采购产品订单子项资源状态
	 * @param operatorId
	 *            操作人ID
	 * @param retentionTime 资源保留时间
	 *            <pre>
	 * 当所有需要资源确认的采购产品订单子项资源状态一致时，订单资源状态也随之更改
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表更改采购产品订单子项资源状态成功，<code>false</code>代表更改采购产品订单子项资源状态失败
	 * </pre>
	 */
	@Override
	public boolean updateOrderItemResource(final Long orderItemId,
			final String resourceStatus, final String operatorId, Date retentionTime,final String resourceLackReason) {
		final OrdOrderItemMeta orderItemMeta = orderItemMetaDAO
				.selectByPrimaryKey(orderItemId);
		orderItemMeta.setResourceStatus(resourceStatus.toString());
		orderItemMeta.setRetentionTime(retentionTime);
		orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
		
		insertLog(orderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", orderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
				"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), "资源审核领单，操作者：" + operatorId +",资源状态为" + resourceStatus.toString() );
		updateOrderResource(orderItemMeta.getOrderId(), resourceStatus, operatorId, resourceLackReason);
		return Boolean.TRUE;
	}
	
	/**
	 * 更改订单资源状态.
	 *
	 * @param orderId
	 *            订单ID
	 * @param resourceStatus
	 *            更改后的订单资源状态
	 * @param operatorId
	 *            操作人ID
	 *
	 * <pre>
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表更改订单资源状态成功，<code>false</code>代表更改订单资源状态失败
	 * </pre>
	 */
	private boolean updateOrderResource(final Long orderId,
			final String resourceStatus, final String operatorId, final String resourceLackReason) 
	{
		final OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		LOG.info("resourceStatus: " + resourceStatus);
		boolean update = false;
		if (resourceStatus.equals(ORDER_RESOURCE_STATUS.AMPLE.name())) {
			Long countResource = orderItemMetaDAO.countNoAmpleResource(orderId, ORDER_RESOURCE_STATUS.AMPLE);
			LOG.info("countResource: " + countResource);
			if (countResource == 0L){
				order.setResourceConfirmStatus(ORDER_RESOURCE_STATUS.AMPLE.name());
				order.setApproveStatus(ORDER_APPROVE_STATUS.VERIFIED.name());
				
				Date date = new Date();
				order.setApproveTime(date);
				Long waitPayment = order.getWaitPayment();
				Date theDate = null;
				List<OrdOrderItemMeta> list = orderItemMetaDAO.selectByOrderId(orderId);
				for (OrdOrderItemMeta itemMeta : list) {
					Date rt = itemMeta.getRetentionTime();
					if (rt!=null && itemMeta.isApproveResourceAmple()) {
						if ( theDate==null) {
							theDate = rt;
						}else if(theDate.after(rt)) {
							theDate = rt;
						}
					}
				}
				if (theDate!=null && theDate.after(date)) {
					waitPayment = (theDate.getTime()/1000/60)-(date.getTime()/1000/60);
				}
				if (order.getLastCancelTime() != null) {
					Long tempLastTime = order.getLastCancelTime().getTime();
					if ((date.getTime() + waitPayment * 60 * 1000) > tempLastTime) {
						waitPayment = (tempLastTime - date.getTime()) / 1000 / 60;
					}
				}
				order.setWaitPayment(waitPayment);
				update = true;
				orderDAO.updateByPrimaryKey(order);
			}
		} else if (resourceStatus.equals(ORDER_RESOURCE_STATUS.LACK.name())) {
			order.setResourceConfirmStatus(ORDER_RESOURCE_STATUS.LACK.name());
			order.setApproveStatus(ORDER_APPROVE_STATUS.RESOURCEFAIL.name());
			order.setResourceLackReason(resourceLackReason);//添加资源不满足原因.add by liuboen
			update = true;
			orderDAO.updateByPrimaryKey(order);
		} else if (resourceStatus.equals(ORDER_RESOURCE_STATUS.BEFOLLOWUP.name())) {
			//待跟进,更新审核信息,需要再次审核
			order.setApproveStatus(ORDER_APPROVE_STATUS.BEFOLLOWUP.name());
			order.setResourceConfirmStatus(ORDER_RESOURCE_STATUS.BEFOLLOWUP.name());
			update = true;
			orderDAO.updateByPrimaryKey(order);
		}
		if (update) {
			insertLog(order.getOrderId(), "ORD_ORDER", null, null, operatorId, 
				"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
				"修改订单内容，操作者：" + operatorId +",审核状态为" 
				+ Constant.ORDER_APPROVE_STATUS.getCnName(order.getApproveStatus()) + ",资源确认状态为" + Constant.ORDER_RESOURCE_STATUS.getCnName(order.getResourceConfirmStatus()) );
		}
		return true;
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	
}
