package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderAuditDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.service.OrderAuditService;

/**
 * 领单服务实现类.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.com.po.ComAudit
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.vo.Constant
 * @see com.lvmama.order.dao.OrderAuditDAO
 * @see com.lvmama.order.dao.OrderDAO
 * @see com.lvmama.order.dao.OrderItemMetaDAO
 * @see com.lvmama.order.service.OrderAuditService
 */
public class OrderAuditServiceImpl extends OrderServiceImpl implements
		OrderAuditService {
	private OrderDAO orderDAO;
	private OrderAuditDAO orderAuditDAO;
	private OrderItemMetaDAO orderItemMetaDAO;

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setOrderAuditDAO(OrderAuditDAO orderAuditDAO) {
		this.orderAuditDAO = orderAuditDAO;
	}

	public OrdOrder getOrderByOrderItemMetaId(Long orderItemMetaId) {
		OrdOrderItemMeta itemMeta = orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
		OrdOrder order=orderDAO.selectByPrimaryKey(itemMeta.getOrderId());
		return order;
	}
	
	/**
	 * 根据订单类型OrdOrder领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderType
	 *            订单类型
	 * @return Audit
	 */
	public OrdOrder makeOrdOrderAudit(String operatorId, String orderType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderStatus", Constant.ORDER_STATUS.NORMAL.name());
		params.put("infoApproveStatus", Constant.INFO_APPROVE_STATUS.UNVERIFIED.name());
		params.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		params.put("orderType", orderType);
		List<OrdOrder> results = orderDAO.selectForAuditOrder(params);

		OrdOrder order = null;
		if (results == null || results.size() == 0)
			return null;
		else
			order = (OrdOrder) results.get(0);

		order.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
		order.setTakenOperator(operatorId);
		order.setDealTime(new Date());
		orderDAO.updateByPrimaryKey(order);

		ComAudit comAudit = new ComAudit();
		comAudit.setObjectId(order.getOrderId());
		comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
		comAudit.setOperatorName(operatorId);
		comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
		orderAuditDAO.insert(comAudit);

		insertLog(order.getOrderId(), "ORD_ORDER", null, null, operatorId, 
				"信息审核领单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(), "信息审核领单，操作者：" + operatorId );
		
		return order;
	}
	
	/**
	 * 根据订单id领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 *            订单id
	 * @return Audit
	 * 
	 * @author zhushuying
	 */
	public OrdOrder makeOrdOrderAuditById(String operatorId, Long orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderStatus", Constant.ORDER_STATUS.NORMAL.name());
		params.put("infoApproveStatus", Constant.INFO_APPROVE_STATUS.UNVERIFIED.name());
		params.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		params.put("orderId", orderId.toString());
		OrdOrder order = orderDAO.selectForAuditOrderByOrderId(params);

		if(order!=null){
			order.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
			order.setTakenOperator(operatorId);
			order.setDealTime(new Date());
			orderDAO.updateByPrimaryKey(order);
	
			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(orderId);
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
			comAudit.setOperatorName(operatorId);
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			orderAuditDAO.insert(comAudit);
	
			insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
					"信息审核领单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(), "信息审核领单，操作者：" + operatorId );
		}
		return order;
	}
	
	/**
	 * 根据订单编号OrdOrder领单（分单）.
	 * 
	 * @author luoyinqi
	 *
	 * @param operator
	 *            处理人（被分单人）
	 * @param orderId
	 * 			  订单编号           
	 * @param assignUser
	 *  		  分单人
	 */
	public boolean makeOrdOrderAuditByOrderId(String operator, Long orderId, String assignUser ) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderStatus", Constant.ORDER_STATUS.NORMAL.name());
		params.put("infoApproveStatus", Constant.INFO_APPROVE_STATUS.UNVERIFIED.name());
		params.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		params.put("orderId", orderId.toString());
		OrdOrder order = orderDAO.selectForAuditOrderByOrderId(params);

		if(order!=null){
			order.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
			order.setTakenOperator(assignUser);
			order.setDealTime(new Date());
			orderDAO.updateByPrimaryKey(order);
			
			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(order.getOrderId());
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
			comAudit.setOperatorName(assignUser);
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			comAudit.setAssignUserId(operator);
			comAudit.setIsRecycle("true");
			orderAuditDAO.insert(comAudit);

			insertLog(order.getOrderId(), "ORD_ORDER", null, null, operator, 
					"信息审核分单", Constant.COM_LOG_ORDER_EVENT.orderAuditDeliver.name(), "信息审核分单,"+operator+"分配给：" + assignUser );
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 根据订单编号分配无需审核的订单
	 */
	public boolean makeOrdOrderConfirmAuditByOrderId(String operator,
			Long orderId, String assignUser) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderStatus", Constant.ORDER_STATUS.NORMAL.name());
		params.put("approveStatus", Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		params.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		params.put("orderId", orderId.toString());
		OrdOrder order = orderDAO.selectForAuditOrderByOrderId(params);

		if(order!=null){
			order.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
			order.setTakenOperator(assignUser);
			order.setDealTime(new Date());
			orderDAO.updateByPrimaryKey(order);
			
			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(order.getOrderId());
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
			comAudit.setOperatorName(assignUser);
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			comAudit.setAssignUserId(operator);
			comAudit.setIsRecycle("true");
			orderAuditDAO.insert(comAudit);

			insertLog(order.getOrderId(), "ORD_ORDER", null, null, operator, 
					"无需资源审核分单", Constant.COM_LOG_ORDER_EVENT.orderAuditConfirmDeliver.name(), "无需资源审核分单," + operator + "分配给：" + assignUser );
			
			return true;
		}
		
		return false;
	}

	/**
	 * 根据销售产品类型OrdOrderItemMeta领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param productType
	 *            销售产品类型
	 * @return Audit
	 */
	public OrdOrderItemMeta makeOrdOrderItemMetaAudit(String operatorId,
			String productType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("resourceConfirm", "true");
		params.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		params.put("productType", productType);
		List<OrdOrderItemMeta> results = orderItemMetaDAO
				.selectForAuditOrderItemMeta(params);

		OrdOrderItemMeta item = null;
		if (results == null || results.size() == 0)
			return null;
		else
			item = (OrdOrderItemMeta) results.get(0);

		item.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
		orderItemMetaDAO.updateByPrimaryKey(item);

		ComAudit comAudit = new ComAudit();
		comAudit.setObjectId(item.getOrderItemMetaId());
		comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER_ITEM_META
				.name());
		comAudit.setOperatorName(operatorId);
		comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
		orderAuditDAO.insert(comAudit);

		insertLog(item.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", item.getOrderId(), "ORD_ORDER", operatorId, 
				"资源审核领单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(), "资源审核领单，操作者：" + operatorId );
		return item;
	}
	

	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	public List<OrdOrderItemMeta> makeOrdOrderItemMetaListToAudit(String operatorId,List<Long> orderItemMetaIdList) {
		if (orderItemMetaIdList == null || orderItemMetaIdList.size() == 0)
			return new ArrayList<OrdOrderItemMeta>();
/*
 *   orderInfo.setTakenOperator(orderInfo.getOperatorId());
				orderInfo.setDealTime(now);
 * */
		//未被领单的子项列表
		List<OrdOrderItemMeta> workOrderItemMetaList = new ArrayList<OrdOrderItemMeta>() ;
		for (Long ordOrderItemMetaId : orderItemMetaIdList) {
			OrdOrderItemMeta item = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);

			this.setTakenOperatorAndDealTime(item.getOrderId(), operatorId);
			
			if (Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name()
					.equals(item.getTaken())){
				item.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
				orderItemMetaDAO.updateByPrimaryKey(item);

				ComAudit comAudit = new ComAudit();
				comAudit.setObjectId(item.getOrderItemMetaId());
				comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER_ITEM_META
						.name());
				comAudit.setOperatorName(operatorId);
				comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
				orderAuditDAO.insert(comAudit);

				insertLog(item.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", item.getOrderId(), "ORD_ORDER", operatorId, 
						"资源审核领单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(), "资源审核领单，操作者：" + operatorId );
			    continue;
			}
			workOrderItemMetaList.add(item);
		}
		return workOrderItemMetaList;
	}

	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	public boolean makeOrdOrderItemMetaToAuditByAssignUser(String operatorId,String assignUserId,OrdOrderItemMeta item) {
		if (item == null )
			return false;

		this.setTakenOperatorAndDealTime(item.getOrderId(), assignUserId);
		
		if (Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name()
				.equals(item.getTaken())){
			item.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
			orderItemMetaDAO.updateByPrimaryKey(item);

			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(item.getOrderItemMetaId());
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER_ITEM_META
					.name());
			comAudit.setOperatorName(assignUserId);
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			comAudit.setAssignUserId(operatorId);
			comAudit.setIsRecycle("true");
			orderAuditDAO.insert(comAudit);

			insertLog(item.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", item.getOrderId(), "ORD_ORDER", operatorId, 
					"资源审核领单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(), "资源审核领单，" + operatorId + "分配给：" + assignUserId );
			return true;
		}
		return false;
	}
	
	/**
	 * 设置指定订单号的初审人和初审时间.
	 * @param orderId 订单号.
	 * @param operatorId 初审人.
	 */
	private void setTakenOperatorAndDealTime(Long orderId,String operatorId) {
		OrdOrder ordOrder = this.orderDAO.selectByPrimaryKey(orderId);
		Map<String,Object> ordOrderMap = new HashMap<String,Object>();
		if (ordOrder.getTakenOperator() == null || ordOrder.getTakenOperator().trim().equals("")) {
			ordOrder.setTakenOperator(operatorId);
			ordOrderMap.put("takenOperator",operatorId);
		}
		if (ordOrder.getDealTime() == null) {
			ordOrder.setDealTime(new Date());
			ordOrderMap.put("dealTime",new Date());
		}
		ordOrderMap.put("orderId","" + orderId);
		this.orderDAO.updateByParamMap2(ordOrderMap);
	}

	/**
	 * 取消OrdOrder领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * <code>true</code>代表取消领单成功，<code>false</code>代表取消领单失败
	 * </pre>
	 */
	public boolean cancelOrdOrderAudit(String operatorId, Long orderId) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (operatorId.equalsIgnoreCase(order.getTakenOperator())) {
			Map<String, String> orderParam = new HashMap<String, String>();
			orderParam.put("orderId", Long.toString(orderId));
			orderParam.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
			orderParam.put("takenOperator", "");
			orderDAO.updateByParamMap(orderParam);
			
			insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
					"信息审核退单", Constant.COM_LOG_ORDER_EVENT.orderAuditGoBack.name(), "信息审核退单，操作者：" + operatorId );
			
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 回收订单
	 * 
	 * @author luoyinqi
	 * 
	 */
	public boolean cancelOrdOrderAuditByOrderId(String operator, Long orderId) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if(Constant.ORDER_APPROVE_STATUS.UNVERIFIED.name().equals(order.getInfoApproveStatus()) || "false".equals(order.getResourceConfirm())){
			Map<String, String> orderParam = new HashMap<String, String>();
			orderParam.put("orderId", Long.toString(orderId));
			orderParam.put("taken", Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
			orderParam.put("takenOperator", "");
			orderDAO.updateByParamMap(orderParam);
			
			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(order.getOrderId());
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
			comAudit.setOperatorName("");
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			comAudit.setAssignUserId(operator);
			comAudit.setIsRecycle("false");
			orderAuditDAO.insert(comAudit);
			
			insertLog(orderId, "ORD_ORDER", null, null, operator, 
					"信息审核回收", Constant.COM_LOG_ORDER_EVENT.orderAuditGoBack.name(), "信息审核回收，操作者：" + operator );
			
			return true;
		}
		return false;
	}

	/**
	 * 取消OrdOrderItemMeta领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param ordOrderItemMetaId
	 *            OrdOrderItemMeta的ID
	 * @return <pre>
	 * <code>true</code>代表取消领单成功，<code>false</code>代表取消领单失败
	 * </pre>
	 */
	public boolean cancelOrdOrderItemMetaAudit(String operatorId,
			Long ordOrderItemMetaId) {
		Map<String, String> auditParam = new HashMap<String, String>();
		auditParam.put("userId", operatorId);
		auditParam.put("objectId", Long.toString(ordOrderItemMetaId));
		auditParam.put("objectType",
				Constant.AUDIT_OBJECT_TYPE.ORD_ORDER_ITEM_META.name());
		auditParam.put("auditStatus", Constant.AUDIT_STATUS.AUDIT_GOING.name());
		List<ComAudit> results = orderAuditDAO
				.selectComAuditByParamMap(auditParam);

		ComAudit comAudit = null;
		if (results == null || results.size() == 0)
			return false;
		else
			comAudit = (ComAudit) results.get(0);

		comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOBACK.name());
		orderAuditDAO.updateByPrimaryKey(comAudit);
		
		OrdOrderItemMeta orderItemMeta = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);
		orderItemMeta.setTaken(Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);

		insertLog(orderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", orderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
				"资源审核退单", Constant.COM_LOG_ORDER_EVENT.orderItemMetaAuditGoBack.name(), "资源审核退单，操作者：" + operatorId );
		return true;
	}

	public boolean canGoingBack(Map<String, String> params){
		return orderAuditDAO.canGoingBack(params);
	}
	
	public boolean canRecycle(Map<String, String> params){
		return orderAuditDAO.canRecycle(params);
	}

	@Override
	public List<ComAudit> selectComAuditByParam(Map<String, String> params) {
		return orderAuditDAO.selectComAuditByParamMap(params);
	}
	@Override
	public Long selectComAuditCountByParams(Map<String, Object> params){
		return  orderAuditDAO.selectComAuditCountByParams(params);
	}
}
