package com.lvmama.order.service.impl;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_STATUS;
import com.lvmama.order.dao.*;
import com.lvmama.order.logic.ProductStockLogic;
import com.lvmama.order.service.OrderUpdateService;
import com.lvmama.order.service.Query;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProductResourceConfirmLogic;
import com.lvmama.prd.logic.ProductSellableLogic;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 订单更改服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.comm.jms.MessageFactory
 * @see com.lvmama.comm.jms.TopicMessageProducer
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.comm.vo.Constant
 * @see com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS
 * @see com.lvmama.order.dao.OrderDAO
 * @see com.lvmama.order.dao.OrderItemMetaDAO
 * @see com.lvmama.order.service.OrderUpdateService
 */
public class OrderUpdateServiceImpl extends OrderServiceImpl implements
		OrderUpdateService {
	private static Logger logger = Logger.getLogger(OrderUpdateServiceImpl.class);

	private OrderDAO orderDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private ProductStockLogic productStockLogic;
	private ProductSellableLogic productSellableLogic;
	private OrderRefundmentDAO orderRefundmentDAO;
	private OrderAmountItemDAO orderAmountItemDAO;
	
	private MetaProductDAO metaProductDAO;	
	private MetaTimePriceDAO metaTimePriceDAO;
	private OrdOrderItemMetaTimeDAO ordOrderItemMetaTimeDAO;
	private ProductResourceConfirmLogic productResourceConfirmLogic;
	private PerformTargetService performTargetService;
	/**
	 * 销售产品时间价格DAO.
	 */
	protected ProdTimePriceDAO prodTimePriceDAO;
	
	/**
	 * 采购类别DAO
	 */
	private MetaProductBranchDAO metaProductBranchDAO;
	/**
	 * 查询服务.
	 */
	private transient Query queryService;
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 优惠日志服务
	 */
	private FavorOrderService favorOrderService;
	
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
	 * setOrderRefundmentDAO.
	 *
	 * @param orderRefundmentDAO
	 *            orderRefundmentDAO
	 */
	public void setOrderRefundmentDAO(
			final OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setProductStockLogic(ProductStockLogic productStockLogic) {
		this.productStockLogic = productStockLogic;
	}

	public void setProductSellableLogic(ProductSellableLogic productSellableLogic) {
		this.productSellableLogic = productSellableLogic;
	}

	/**
	 * 修改订单下单人userId.
	 *
	 * @param orderId
	 *            订单ID
	 *
	 * @param userId
	 *            下单人ID
	 *
	 * @param operatorName
	 *            操作人登录名
	 * @return <pre>
	 * <code>true</code>代表修改成功，<code>false</code>代表修改失败
	 * </pre>
	 */
	public boolean updateUserIdForOrder(Long orderId, String userId, String operatorName) {
		// 通过订单服务综合查询，会自动填充此订单所有的采购项目列表
		final CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getOrderIdentity().setOrderId(orderId);
		OrdOrder order = orderServiceProxy.compositeQueryOrdOrder(
				compositeQuery).get(0);
		if (order == null) {
			throwException("updateUserIdForOrder fail: Not found order with orderId = " + orderId, logger);
		}
		order.setUserId(userId);
		orderDAO.updateByPrimaryKey(order);
		insertLog(orderId, "ORD_ORDER", null, null, operatorName, 
				"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), "修改订单用户所有者，操作者：" + operatorName +",用户id为" + userId );
		return true;
	}

	@Override
	public List<OrdOrder> getToAutoApproveOrder() {
		return orderDAO.getToAutoApproveOrder();
	}
	
	@Override
	public List<OrdOrder> getToAutoCancelOrder() {
		return orderDAO.getToAutoCancelOrder();
	}
	@Override
	public int autoFinishOrder() {
		return orderDAO.autoFinishOrder();
	}
	@Override
	public List<OrdOrderItemMeta> getToAutoPerformOrderItemMeta() {
		return orderItemMetaDAO.getToAutoPerformOrderItemMeta();
	}
	/**
	 * 修改订单.
	 */
	public void updateOrdOrderByPrimaryKey(final OrdOrder order){
		orderDAO.updateByPrimaryKey(order);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 预授权支付完成的,修改订单的支付状态.
	 * @param order
	 * @param iskey
	 * @return
	 */
	@Override
	public boolean updateOrdOrderByPrePay(final OrdOrder order,final boolean iskey){
		boolean  isSuccess = false;
		if(iskey){
			order.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
			order.setOrderViewStatus(Constant.PAYMENT_STATUS.PAYED.name());	
			order.setPaymentFinishTime(new Date());
			updateOrdOrderByPrimaryKey(order);
			isSuccess = true;
		}
	    return 	isSuccess;
	}
	
	/**
	 * 取消订单.
	 *
	 * @param orderId
	 *            订单ID
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param cancelReorderReason
     * @return <pre>
	 * <code>true</code>代表取消成功，<code>false</code>代表取消失败，orderViewStatus由消息OrderStatusProcesser处理,这里不需要设置
	 * </pre>
	 */
	public boolean cancelOrder(Long orderId, String reason, String operatorId, String cancelReorderReason) {
		OrdOrder order = queryService.queryOrdOrder(orderId);
		if (order == null)
		{
			throwException("cancelOrder fail: Not found order with orderId = " + orderId, logger);
		}

		if (Constant.ORDER_STATUS.CANCEL.name().equalsIgnoreCase(order.getOrderStatus()))
		{
			throwException("cancelOrder fail: The order with orderId = " + orderId + " has been already cancel.operatorId:"+operatorId+" reason:"+reason, logger);
		}
		
		order.setOrderId(orderId);
		order.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		
		order.setCancelTime(new Date());
		order.setCancelReason(reason);
		order.setCancelOperator(operatorId);
        order.setCancelReorderReason(cancelReorderReason);
		productStockLogic.restoreStock(order);
		if (order.getUserId().equals(operatorId)){
			order.setCancelOperator("SELF");
			insertLog(order.getOrderId(), "ORD_ORDER", null, null, operatorId, 
					"用户取消", Constant.COM_LOG_ORDER_EVENT.userCancel.name(), "用户取消，操作者：" + operatorId + " " + reason );
		}else {
			order.setCancelOperator(operatorId);
			insertLog(order.getOrderId(), "ORD_ORDER", null, null, operatorId, 
					"后台取消", Constant.COM_LOG_ORDER_EVENT.userCancel.name(), "后台取消，操作者：" + operatorId + " " + reason );
		}
		
		orderDAO.updateByPrimaryKey(order);

		return true;
	}
	
	
	/**
	 * 恢复订单.
	 *
	 * @param orderId
	 *            订单ID
	 *
	 * @param operatorName
	 *            操作人登录用户名
	 *
	 * @return ResponseMessage
	 *            订单服务返回的应答消息
	 */
	@Override
	public ResponseMessage restoreOrder(Long orderId, String operatorName)
	{
		ResponseMessage responseMessage = new ResponseMessage();
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (order == null) {
			logger.error("restore Order fail: Not found order with orderId = " + orderId);
			responseMessage.setSuccessFlag(false);
			responseMessage.setResponse("通过订单号" + orderId + "找不到订单");
			return responseMessage;
		}

		if (!(Constant.ORDER_STATUS.CANCEL.name().equalsIgnoreCase(order.getOrderStatus()))) {
			logger.error("restore Order fail: OrderStatus of the order with orderId = " +
					orderId + " is not " + Constant.ORDER_STATUS.CANCEL.name());
			responseMessage.setSuccessFlag(false);
			responseMessage.setResponse("订单" + orderId + "未被取消");
			return responseMessage;
		}

		if (Constant.ORD_CANCEL_REASON.CANCEL_TO_CREATE_NEW.name().equalsIgnoreCase(order.getCancelReason())) {
			logger.error("restore Order fail: CancelReason of the order with orderId = " +
					orderId + " is " + Constant.ORD_CANCEL_REASON.CANCEL_TO_CREATE_NEW.name());
			responseMessage.setSuccessFlag(false);
			responseMessage.setResponse("您好，此订单已经修改为新订单，请查看订单日志并以新订单为准");
			return responseMessage;
		}
		//存在退款单且状态不为reject的才能恢复
		final List<OrdRefundment> ordRefundmentList = orderRefundmentDAO
				.queryOrdRefundmentByOrderId(orderId);
		boolean flag = false;
		Long refundmentId= null;
		
		for(OrdRefundment orf : ordRefundmentList){
			//恢复订单时 检查当前订单是否有退款单（其中不包括：REJECTED、CANCEL、FAIL 这3种状态的退款单）
			if (!Constant.REFUNDMENT_STATUS.REJECTED.name().equals(orf.getStatus()) && !Constant.REFUNDMENT_STATUS.CANCEL.name().equals(orf.getStatus())
					&& !Constant.REFUNDMENT_STATUS.FAIL.name().equals(orf.getStatus())) {
				refundmentId = orf.getRefundmentId();
				flag=true;
				break;
			}
		}
		if (flag) {
			logger.error("restore Order fail: the ordRefundment of order has existed");
			responseMessage.setSuccessFlag(false);
			responseMessage.setResponse("您好，此订单已经存在退款单"
					+ refundmentId);
			return responseMessage;
		}
//		// 检查库存
//		List<OrdOrderItemProd> prodItems = orderItemProdDAO.selectByOrderId(orderId);
//		for (OrdOrderItemProd itemProd : prodItems) {
//			if (!productSellableLogic.isProductSellable(itemProd.getProductId(), itemProd.getQuantity(), itemProd.getVisitTime())) {
//				logger.error("restore Order fail: Product is OverStock with orderId = " + orderId);
//				responseMessage.setSuccessFlag(false);
//				responseMessage.setResponse("你好，此订单中关联的产品当日无库存，请与产品经理联系处理");
//				return responseMessage;
//			}
//		}

//		productStockLogic.minusStock(order);

		order.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		long createTimeMillis = order.getCreateTime().getTime();
		long currentTimeMillis = System.currentTimeMillis();
		// 当前时间 - 订单建立时间 + 1 （小时数）
		long waitPayment = (currentTimeMillis - createTimeMillis) / (3600 * 1000) + 1;
		long createZeroTimeMillis = getZeroTimeMillis(createTimeMillis);
		long currentZeroTimeMillis = getZeroTimeMillis(currentTimeMillis);
		if((currentTimeMillis - currentZeroTimeMillis) < (createTimeMillis - createZeroTimeMillis))
		{
			waitPayment += 1;
		}
		order.setWaitPayment(waitPayment * 60);
		orderDAO.updateByPrimaryKey(order);
		
		insertLog(orderId, "ORD_ORDER", null, null, operatorName, 
				"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
				"修改订单内容" + ",订单状态为：" + Constant.ORDER_STATUS.NORMAL.name() );

		responseMessage.setSuccessFlag(true);
		responseMessage.setResponse("恢复订单成功");
		return responseMessage;
	}

	private long getZeroTimeMillis(long timeMillis)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 更改订单需重播.
	 *
	 * @param redail
	 *            要更改的订单需重播
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单需重播成功，<code>false</code>代表更改订单需重播失败
	 * </pre>
	 */
	public boolean updateRedail(String redail, Long orderId, String operatorId) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (order == null)
			return false;

		if (redail.equals("false")) {
			order.setRedail(null);
		}else{
			order.setRedail(redail);
		}
		orderDAO.updateByPrimaryKey(order);

		insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
				"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
				"修改订单内容" + ",是否需重拨为" + redail );

		return true;
	}

	/**
	 * 更改订单支付等待时间.
	 *
	 * @param waitPayment
	 *            更改后的订单支付等待时间，以分为单位
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单支付等待时间成功，<code>false</code>代表更改订单支付等待时间失败
	 * </pre>
	 */
	public boolean updateWaitPayment(Long waitPayment, Long orderId,
			String operatorId) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (order == null)
			return false;

		Long oldWaitPayment = order.getWaitPayment();
		order.setWaitPayment(waitPayment);
		orderDAO.updateByPrimaryKey(order);
		insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
				"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
				"操作人" + operatorId + "，将支付等待时间 " + oldWaitPayment + "延长" + waitPayment + "分钟");
		
		return true;
	}

	/**
	 * 更改OrderItemMeta的传真备注.
	 *
	 * @param memo
	 *            更改后的OrderItemMeta的传真备注
	 * @param ordOrderItemMetaId
	 *            OrderItemMeta的ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改OrderItemMeta的传真备注成功，<code>false</code>
	 *         代表更改OrderItemMeta的传真备注失败
	 * </pre>
	 */
	public boolean updateOrderItemMetaFaxMemo(String memo,
			Long ordOrderItemMetaId, String operatorId) {
		OrdOrderItemMeta record = orderItemMetaDAO
				.selectByPrimaryKey(ordOrderItemMetaId);
		if (record == null)
			return false;

		record.setFaxMemo(memo);
		orderItemMetaDAO.updateByPrimaryKey(record);
		
		insertLog(record.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", record.getOrderId(), "ORD_ORDER", operatorId, 
				"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), "修改订单子子项内容，操作者：" + operatorId +",传真备注为" + memo );

		return true;
	}

	/**
	 * 更新订单审核状态.
	 *
	 * @param orderId
	 *            订单ID
	 * @param orderApproveStatus
	 *            更改后的订单审核状态
	 * @param operatorId
	 *            操作人ID
	 * @param approveTime 审核时间
	 * @return <pre>
	 * <code>true</code>代表更新订单审核状态成功，<code>false</code>
	 *         代表更新订单审核状态失败
	 * </pre>
	 */
	@Override
	public boolean updateOrdOrderApproveStatus(final Long orderId,
			final String infoApproveStatus,
			final String operatorId) {
		OrdOrder ord = orderDAO.selectByPrimaryKey(orderId);
		if(!ord.isApproveInfoPass()) {
			Map params = new HashMap();
			params.put("orderId", orderId);
			params.put("infoApproveStatus", infoApproveStatus.toString());
			orderDAO.updateByParamMap(params);
			insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
					"修改订单内容", Constant.COM_LOG_ORDER_EVENT.approvePass.name(), 
					"修改订单内容" + ",审核状态为" + Constant.INFO_APPROVE_STATUS.getCnName(infoApproveStatus.toString()));
		}

		return true;
	}

	
	
	@Override
	public boolean updateOrderSettlementStatus(final List<Long> orderItemMetaIds,final Constant.SETTLEMENT_STATUS settlementStatus,final String operatorName) {
		//更新订单子子项的结算状态
		this.orderItemMetaDAO.updateSettlementStatus(settlementStatus, orderItemMetaIds);
		for(Long orderItemMetaId : orderItemMetaIds){
			insertLog(orderItemMetaId, Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER_ITEM_META.name(), null, null, operatorName, 
					"更新结算状态", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
					"更新订单子子项结算状态为"+settlementStatus.getCnName());
		}
		List<Long> orderIdList = this.orderItemMetaDAO.selectOrderIdByOrderItemMetaId(orderItemMetaIds);
		if(Constant.SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlementStatus.name()) || 
				Constant.SETTLEMENT_STATUS.NOSETTLEMENT.name().equals(settlementStatus.name()) || 
				Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name().equals(settlementStatus.name())){
			for (Long orderId : orderIdList) {
				List<OrdOrderItemMeta> allOrdOrderItemMeta = this.orderItemMetaDAO.selectByOrderId(orderId);
				Constant.SETTLEMENT_STATUS orderSettlementStatus = this.getOrderSettlementStatusByOrderItemMetaStatus(allOrdOrderItemMeta);
				Map params = new HashMap();
				params.put("orderId", orderId);
				params.put("settlementStatus", orderSettlementStatus.name());
				this.orderDAO.updateByParamMap2(params);
				insertLog(orderId, "ORD_ORDER", null, null, operatorName, 
						"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
						"修改订单内容" + ",结算状态为" + Constant.SETTLEMENT_STATUS.getCnName(orderSettlementStatus.toString()));
			}
		}else if(Constant.SETTLEMENT_STATUS.SETTLEMENTING.name().equals(settlementStatus.name())){
			for (Long orderId : orderIdList) {
				Map params = new HashMap();
				params.put("orderId", orderId);
				params.put("settlementStatus", settlementStatus.name());
				this.orderDAO.updateByParamMap2(params);
				insertLog(orderId, "ORD_ORDER", null, null, operatorName, 
						"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
						"修改订单内容" + ",结算状态为" + Constant.SETTLEMENT_STATUS.getCnName(settlementStatus.toString()));
			}
		}
	
		return true;
	}
	/**
	 * 通过订单子子项的结算状态获取订单的结算状态.
	 * @param allOrdOrderItemMeta 订单子子项列表.
	 * @return	返回订单的结算状态.
	 */
	private SETTLEMENT_STATUS getOrderSettlementStatusByOrderItemMetaStatus(List<OrdOrderItemMeta> allOrdOrderItemMeta) {
		if (allOrdOrderItemMeta == null || allOrdOrderItemMeta.isEmpty()) {
			return SETTLEMENT_STATUS.UNSETTLEMENTED;
		}
		SETTLEMENT_STATUS result = SETTLEMENT_STATUS.UNSETTLEMENTED;
		//是否有-未结算项 
		boolean hasUnsettlemented = false;
		//是否有-结算中项
		boolean hasSettlementing = false;
		//是否有-已结算项
		boolean hasSettlemented = false;
		//是否有-不结算项
		boolean hasNoSettlement = false;
		for (OrdOrderItemMeta ooim : allOrdOrderItemMeta) {
			if(SETTLEMENT_STATUS.UNSETTLEMENTED.name().equals( ooim.getSettlementStatus()) && ooim.getActualSettlementPriceYuan() == 0f && ooim.getProductName().indexOf("库存") != -1){
				//排序虚拟库存 
				continue;
			}
//			//订单结算状态的评定,排除'房差'、'保险'、'其它'这三种子产品类型.
//			if (Constant.SUB_PRODUCT_TYPE.FANGCHA.equals(ooim.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.INSURANCE.equals(ooim.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.OTHER.equals(ooim.getSubProductType())) {
//				continue;
//			}
			//订单子子项结算状态为:未结算、false、争议单、null，订单结算状态为未结算.
			if (SETTLEMENT_STATUS.UNSETTLEMENTED.name().equals(ooim.getSettlementStatus()) || "false".equals(ooim.getSettlementStatus()) || (null == ooim.getSettlementStatus())) {
				hasUnsettlemented = true;
			} else if (SETTLEMENT_STATUS.SETTLEMENTING.name().equals(ooim.getSettlementStatus())) {
				hasSettlementing = true;
			} else if (SETTLEMENT_STATUS.SETTLEMENTED.name().equals(ooim.getSettlementStatus())) {
				hasSettlemented  = true;
			} else if (SETTLEMENT_STATUS.NOSETTLEMENT.name().equals(ooim.getSettlementStatus())) {
				hasNoSettlement = true;
			}
		}
		if (hasUnsettlemented && !hasSettlementing && !hasSettlemented && !hasNoSettlement) {
			result = SETTLEMENT_STATUS.UNSETTLEMENTED;
		} else if (hasSettlementing || (hasUnsettlemented && hasSettlemented)) {
			result = SETTLEMENT_STATUS.SETTLEMENTING;
		} else if (!hasUnsettlemented && !hasSettlementing && !hasSettlemented && hasNoSettlement) {//没有不结算，没有结算中，没有已结算 全是不结，订单结算状态为不结
			result = SETTLEMENT_STATUS.NOSETTLEMENT;
		} else if (!hasUnsettlemented && !hasSettlementing && hasSettlemented ) {//没有不结算，没有结算中，有已结算，订单结算状态为已结算
			result = SETTLEMENT_STATUS.SETTLEMENTED;
		}
		return result;
	}
	
	/**
	 * 
	 * @param ordOrder
	 */
	public boolean orderAutoApprovePass(OrdOrder ordOrder) {
		ordOrder.setApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		ordOrder.setInfoApproveStatus(Constant.INFO_APPROVE_STATUS.INFOPASS.name());
		ordOrder.setApproveTime(new Date());
		if (ordOrder.isNeedResourceConfirm()) {
			ordOrder.setResourceConfirmStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE.name());
		}
		this.orderDAO.updateByPrimaryKey(ordOrder);
		
		List<OrdOrderItemMeta> itemMetas = orderItemMetaDAO.selectByOrderId(ordOrder.getOrderId());
		for (OrdOrderItemMeta ordOrderItemMeta : itemMetas) {
			if (ordOrderItemMeta.isNeedResourceConfirm()) {
				ordOrderItemMeta.setResourceStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE.name());
			}
			orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
		}
		
		insertLog(ordOrder.getOrderId(), "ORD_ORDER", null, null, "SYSTEM", 
				"系统自动审核通过", Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name(), 
				"系统自动审核通过" + ",审核状态为" + ORDER_APPROVE_STATUS.VERIFIED.name());
		return true;
	}
	
	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 *
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	@Override
	public boolean updateFaxMemo(final Long ordOrderItemMetaId,
			final String faxMemo, final String operatorId) {
		return updateFaxMemo(ordOrderItemMetaId, faxMemo, operatorId, false);
	}

	private boolean updateFaxMemo(final Long ordOrderItemMetaId,
			final String faxMemo, final String operatorId, boolean isBatchUpdate){
		final OrdOrderItemMeta ordOrderItemMeta = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);

		if(isBatchUpdate)
		{
			String tmp = ordOrderItemMeta.getFaxMemo();
			if(tmp == null)
				tmp = "";
			ordOrderItemMeta.setFaxMemo(tmp + " " + faxMemo);
		}
		else
			ordOrderItemMeta.setFaxMemo(faxMemo);

		orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
		insertLog(ordOrderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", ordOrderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
				"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
				"修改订单子子项内容" + ",传真备注为" + ordOrderItemMeta.getFaxMemo());
		return true;
	}

	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 *
	 * @param ordOrderItemMetaIdList
	 *            采购产品订单子项ID列表
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	@Override
	public boolean updateFaxMemoByorderItemMetaIdList(final List<Long> ordOrderItemMetaIdList,
			final String faxMemo, final String operatorId) {
		for(Long ordOrderItemMetaId : ordOrderItemMetaIdList)
		{
			updateFaxMemo(ordOrderItemMetaId, faxMemo, operatorId, true);
		}
		return true;
	}

	/**
	 * 订单返现.
	 *
	 * @param orderId
	 *            订单ID
	 * @param commentId
	 *            点评ID
	 * @param cash
	 *            返现金额，以分为单位，100代表1元
	 * @return <pre>
	 * <code>true</code>代表订单返现成功，<code>false</code>
	 *         代表订单返现失败
	 * </pre>
	 */
	@Override
	public boolean cashOrder(final Long orderId, final Long cash) {
		return orderDAO.cashOrder(orderId, cash);
	}

	/**
	 * 更改订单子子项的退款标志.
	 *
	 * @param refund
	 *            要更改的订单子子项的退款标志
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的退款标志成功，<code>false</code>代表更改订单子子项的退款标志失败
	 * </pre>
	 */
	@Override
	public boolean updateOrderItemMetaRefund(String refund, Long ordOrderItemMetaId, String operatorId)
	{
		OrdOrderItemMeta record = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);
		if (record == null)
			return false;

		record.setRefund(refund);
		orderItemMetaDAO.updateByPrimaryKey(record);
		insertLog(record.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", record.getOrderId(), "ORD_ORDER", operatorId, 
				"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
				"修改退款标志" + ",是否有退款申请：" + refund);

		return true;
	}

	/**
	 * 更改订单子子项的实际结算价.
	 *
	 * @param actualSettlementPrice
	 *            要更改的订单子子项的实际结算价
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的实际结算价成功，<code>false</code>代表更改订单子子项的实际结算价失败
	 * </pre>
	 */
	@Override
	public boolean updateActualSettlementPrice(Long actualSettlementPrice, Long ordOrderItemMetaId, String operatorId)
	{
		OrdOrderItemMeta record = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);
		if (record == null)
			return false;

		record.setActualSettlementPrice(actualSettlementPrice);
		orderItemMetaDAO.updateByPrimaryKey(record);
		insertLog(record.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", record.getOrderId(), "ORD_ORDER", operatorId, 
				"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
				"修改实际结算价格为" + actualSettlementPrice);
		return true;
	}

	public boolean resetSellPrice(Long orderId, String operatorId)
	{
		List<OrdOrderItemProd> orderItemProdList = orderItemProdDAO.selectByOrderId(orderId);

		if(orderItemProdList == null || orderItemProdList.size() == 0)
		{
			throwException("update SellPrice fail: OrdOrderItemProd is not found with orderId = " + orderId, logger);
		}

		for(OrdOrderItemProd orderItemProd : orderItemProdList)
		{
			Long orderItemId = orderItemProd.getOrderItemProdId();
			long orderItemProdPrice = orderItemProd.getPrice();
			long sumActualSettlementPrice = orderItemMetaDAO.sumTotalActualSettlementPriceByOrderItemId(orderItemId);
			List<OrdOrderItemMeta> orderItemMetaList = orderItemMetaDAO.selectByOrderItemId(orderItemId);
			if(orderItemMetaList == null || orderItemMetaList.size() == 0)
			{
				throwException("update SellPrice fail: OrdOrderItemMeta is not found with orderItemId = " + orderItemId + " and orderId = " + orderId, logger);
			}

			for(OrdOrderItemMeta orderItemMeta : orderItemMetaList)
			{
				long sellPrice = Math.round((orderItemMeta.getActualSettlementPrice().longValue() * orderItemProdPrice) / sumActualSettlementPrice);
				orderItemMeta.setSellPrice(sellPrice);
				orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
				insertLog(orderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", orderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
						"修改订单子子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
						"修改销售价为" + sellPrice);
			}
		}

		return true;
	}

	/**
	 * 更改订单售后服务标志.
	 *
	 * @param needSaleService
	 *            订单售后服务标志
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单售后服务标志成功，<code>false</code>代表更改订单售后服务标志失败
	 * </pre>
	 */
	@Override
	public boolean updateNeedSaleService(String needSaleService, Long orderId, String operatorId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId.toString());
		params.put("needSaleService", needSaleService);
		int row = orderDAO.updateByParamMap(params);

		if(row == 1) {
			insertLog(orderId, "ORD_ORDER", null,null, operatorId,
					"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
					"设置订单需要售后服务：" + needSaleService);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 订单电子合同的状态更改为已确认
	 * @param orderId 订单号
	 * @return 更改结果
	 */
	public boolean updateOrdEContractStatusToConfirmed(final Long orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId.toString());
		params.put("eContractStatus", Constant.ECONTRACT_STATUS.CONFIRM.name());
		int row = orderDAO.updateByParamMap(params);

		if(row == 1) {
			insertLog(orderId, "ORD_ORDER", null,null, "",
					"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
					"设置订单电子合同状态为" + Constant.ECONTRACT_STATUS.CONFIRM.getCnName());
			return true;
		}
		else
			return false;
	}

	public void setQueryService(Query queryService) {
		this.queryService = queryService;
	}
	
	/**
	 * 更新订单金额 和 优惠券的处理 .
	 * @param orderInfo
	 * @return
	 */
	public boolean updateOrderPriceByCoupon(final OrderInfoDTO orderInfo,final  String operatorId){
		this.saveMarkLog(orderInfo);//记录优惠券LOG
		int row = updateOrder(orderInfo);
		orderAmountItemDAO.insert(orderInfo.getCouponAmountItem());
		if(row == 1){
			insertLog(orderInfo.getOrderId(), "ORD_ORDER", null,null, operatorId,
					"修改订单价格", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
					"使用优惠金额 "+orderInfo.getCouponAmountItem().getAmountYuan());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 记录优惠券LOG
	 * @param orderInfo
	 */
	private void saveMarkLog(final OrderInfoDTO orderInfo) {
		
		FavorResult favorResult = orderInfo.getBuyInfo().getFavorResult();
		//记录优惠日志
		List<OrderFavorStrategy> favorStrategyList = favorResult.getFavorOrderResult().getFavorStrategyList();
		if(favorStrategyList != null && favorStrategyList.size() > 0){
			long total_discountAmount = 0;
			for(int i = 0; i < favorStrategyList.size(); i++){
				long currentFavorDiscount = favorStrategyList.get(i).getDiscountAmount(orderInfo, total_discountAmount);
				total_discountAmount += currentFavorDiscount;
				MarkCouponUsage markCouponUsage = new MarkCouponUsage();
				markCouponUsage.setObjectId(orderInfo.getOrderId());
				markCouponUsage.setCreateTime(new Date());
				markCouponUsage.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
				markCouponUsage.setCouponCodeId(favorStrategyList.get(i).getMarkCouponCode().getCouponCodeId());
				markCouponUsage.setStrategy(favorResult.getFavorOrderResult().getFavorStrategyList().get(i).getFavorType());
				markCouponUsage.setAmount(total_discountAmount);
				favorOrderService.saveCouponUsage(markCouponUsage);
			}
		}
	}
	
	
	/**
	 * 更新order
	 * @param orderInfo
	 * @return
	 */
	private int updateOrder(final OrderInfoDTO orderInfo){
		FavorResult favorResult = orderInfo.getBuyInfo().getFavorResult();
		List<OrderFavorStrategy> favorStrategyList = favorResult.getFavorOrderResult().getFavorStrategyList();
		if(favorStrategyList != null && favorStrategyList.size() > 0){
			long total_discountAmount = 0;
			for(int i = 0; i < favorStrategyList.size(); i++){
				long currentFavorDiscount = favorStrategyList.get(i).getDiscountAmount(orderInfo, total_discountAmount);
				total_discountAmount += currentFavorDiscount;
			}
			//应付金额减去优惠金额
			if(total_discountAmount!=0){
				orderInfo.setOughtPay(orderInfo.getOughtPay() - total_discountAmount);
			}
			logger.info("orderInfo oughtPay: "+orderInfo.getOughtPay()+" used mark coupon: "+total_discountAmount+"orderInfo orderId: "+ orderInfo.getOrderId());
		}
		return orderDAO.updateByPrimaryKey(orderInfo);
	}
	
/**
 * 保存优惠券.
 * @param orderInfo
 */
//	private void saveCoupon(final OrderInfoDTO orderInfo) {
//		// 更新B类优惠券
//		updateBcouponUsed(orderInfo.getMarkCouponCode(),
//				couponLogic.isBCouponCode(orderInfo.getMarkCoupon()));
//		// 保存A类优惠券
//		this.couponLogic.saveCouponUsage(orderInfo.getMarkCoupon(), orderInfo,
//				orderInfo.getMarkCouponCode());
//	}	
	
	/**
	 * 更新B类优惠券.
	 * 
	 * @param markCouponCode
	 *            markCouponCode
	 * @param flag
	 *            判断标识
	 */
//	private void updateBcouponUsed(final MarkCouponCode markCouponCode,
//			final boolean flag) {
//		if (flag) {
//			this.couponLogic.updateBcouponUsed(markCouponCode);
//		}
//	}
	/**
	 * 订单子子项DAO.
	 * @param orderAmountItemDAO
	 */
	public void setOrderAmountItemDAO(OrderAmountItemDAO orderAmountItemDAO) {
		this.orderAmountItemDAO = orderAmountItemDAO;
	}
	
	
	/**
	 * 修改订单金额,由于支付给景区的可能需要修改入园人数,针对二维码景区.
	 * @param orderId
	 * @param adult
	 * @param child
	 * @return
	 */
	public boolean editOrder(final Long orderId, final Long adult,
			final Long child) {
		boolean isEditOrder=false;
		
		if ((adult > 0 && child == 0) || (adult == 0 && child > 0)) {
			OrdOrder order = this.queryService.queryOrdOrder(orderId);
			if(!order.isPayToSupplier()){
				logger.error("editOrderError: orderId="+orderId+",adult:"+adult+",child="+child);
				return false;
			}
			// 判断是不是单独的订单子项和订单子子项.
			if (order.getOrdOrderItemProds()!=null
					&& order.getOrdOrderItemProds().size() == 1
					&& order.getAllOrdOrderItemMetas()!=null
					&& order.getAllOrdOrderItemMetas().size() == 1
					&& (order.getAllOrdOrderItemMetas().get(0).getAdultQuantity()+order.getAllOrdOrderItemMetas().get(0).getChildQuantity())==1L) {
				logger.info("editOrder: orderId="+orderId+",adult:"+adult+",child="+child);
				Long orderAmount = 0L;
				// 销售产品
				OrdOrderItemProd orderItemProd = order.getOrdOrderItemProds()
						.get(0);
				// 采购产品
				OrdOrderItemMeta orderItemMeta = order.getAllOrdOrderItemMetas().get(0);

				if (adult > 0 && child == 0) {
					orderItemProd.setQuantity(adult);
					//orderItemMeta.setAdultQuantity(adult);
					orderItemMeta.setQuantity(adult);
					orderAmount = orderItemProd.getPrice() * adult;
				} else if (adult == 0 && child > 0) {
					orderItemProd.setQuantity(child);
					//orderItemMeta.setChildQuantity(child);
					orderItemMeta.setQuantity(child);
					orderAmount = orderItemProd.getPrice() * child;
				}
				orderItemMeta.setTotalSettlementPrice(orderItemMeta.getSettlementPrice()* orderItemMeta.getProductQuantity()*orderItemMeta.getQuantity());
				order.setOrderPay(orderAmount);
				order.setOughtPay(orderAmount);
				orderItemProd.setSumSettlementPrice(orderItemMeta.getSettlementPrice()* orderItemMeta.getProductQuantity()*orderItemMeta.getQuantity());
				logger.info("editOrder  orderItemProd: "+orderItemProd.getOrderItemProdId());
				orderItemProdDAO.updateByPrimaryKey(orderItemProd);
				orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
				orderDAO.updateByPrimaryKey(order);
				
				OrdOrderAmountItem  ordOrderAmountItem = new OrdOrderAmountItem();
				List<OrdOrderAmountItem> amountList = orderAmountItemDAO.querOrderAmountItemByOrderId(orderId);
				if(amountList!=null && amountList.size()>0){
					ordOrderAmountItem = amountList.get(0);
					ordOrderAmountItem.setItemAmount(orderAmount);
					orderAmountItemDAO.updateByPrimaryKey(ordOrderAmountItem);
				}
				isEditOrder=true;
			}
		}
		return isEditOrder;
	}
	

	@Override
	public boolean updateNeedInvoice(Long orderId, String val) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId",orderId);
		map.put("needInvoice", val);
		orderDAO.updateByParamMap2(map);
		return true;
	}
	
	@Override
	public boolean updateCashRefund(Long orderId, Long cashRefund) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("cashRefund", cashRefund);
		orderDAO.updateByParamMap2(map);
		return true;
	}
	public ProdTimePriceDAO getProdTimePriceDAO() {
		return prodTimePriceDAO;
	}
	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
	 
	@Override
	public OrdOrder getToAutoApproveOrder(Long orderId) {
		return orderDAO.selectByPrimaryKey(orderId);
	}

	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 *
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	@Override
	public boolean updateSettPrice(Long ordItemId, Long price, String operatorId) {
		boolean flag = updateSettlementPrice(ordItemId, price, operatorId);
		
		return flag;
	}

	// 修改结算价并添加日志
	private boolean updateSettlementPrice(Long ordItemId, Long price, String operatorId){
		// 根据订单子子项id查询订单子子项信息
		OrdOrderItemMeta ordOrderItemMeta = orderItemMetaDAO.selectByPrimaryKey(ordItemId);
		// 设置修改后的实际结算价
		ordOrderItemMeta.setActualSettlementPrice(price);

		orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
		// 修改结算价成功之后在日志表里添加记录 
		insertLog(ordItemId, "ORD_ORDER_ITEM_META",ordOrderItemMeta.getOrderId(), "ORD_ORDER", operatorId,
				"修改订单子项内容", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
				"修改为：" + Double.parseDouble(ordOrderItemMeta.getActualSettlementPrice().toString())/100);
		return true;
	}
	
	
	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 *
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	@Override
	public boolean updateBatchPrice(String ordItemIds, Long price, String operatorId) {
		boolean flag = updateBatchSettlementPrice(ordItemIds, price, operatorId);
		
		return flag;
	}

	// 修改结算价并添加日志
	private boolean updateBatchSettlementPrice(String ordItemIds, Long price, String operatorId){
		String[] idArray = ordItemIds.split(",");
		try{
			for(int i=0; i<idArray.length; i++){
				Long ordItemId = Long.parseLong(idArray[i]);
	
				// 根据订单子子项id查询订单子子项信息
				OrdOrderItemMeta ordOrderItemMeta = orderItemMetaDAO.selectByPrimaryKey(ordItemId);
				// 设置修改后的实际结算价
				ordOrderItemMeta.setActualSettlementPrice(price);
	
				orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
				// 修改结算价成功之后在日志表里添加记录
				insertLog(ordOrderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", ordOrderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
						"修改结算价", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), 
						"修改为：" + Double.parseDouble(ordOrderItemMeta.getActualSettlementPrice().toString())/100);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	@Override
	public ResultHandle updateOrderItemMeta(Long orderItemMetaId,
			Long metaBranchId,SupBCertificateTarget bCertificateTarget, String operatorId) {
		ResultHandle handle=new ResultHandle();
		try{
			OrdOrderItemMeta orderItemMeta=orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
			if(orderItemMeta==null){
				throw new Exception("订单子子项不存在");
			}
			if(!Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED.name().equals(orderItemMeta.getResourceStatus())){
				throw new Exception("当前状态不可以做资源修改");
			}
			
			
			MetaProductBranch metaBranch=metaProductBranchDAO.selectBrachByPrimaryKey(metaBranchId);
			if(metaBranch==null){
				throw new Exception("替换的采购类别不存在");
			}
			if(metaBranch.getMetaBranchId().equals(orderItemMeta.getMetaBranchId())){
				throw new Exception("替换的采购类别不能与当前订单资源是同一资源");
			}
			MetaProduct metaProduct=metaProductDAO.getMetaProductByPk(metaBranch.getMetaProductId());
			if(metaProduct==null){
				throw new Exception("采购产品不存在");
			}
			
			if(!Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(metaProduct.getPaymentTarget())){
				throw new Exception("被修改采购产品支付对象必须是支付给驴妈妈");
			}
			
			if(!StringUtils.equals(orderItemMeta.getProductType(),metaProduct.getProductType())
				||!StringUtils.equals(orderItemMeta.getSubProductType(), metaProduct.getSubProductType())
				||!orderItemMeta.getAdultQuantity().equals(metaBranch.getAdultQuantity())
				||!orderItemMeta.getChildQuantity().equals(metaBranch.getChildQuantity())
				||!StringUtils.equals(orderItemMeta.getBranchType(), metaBranch.getBranchType())){
				throw new Exception("替换的采购产品不符合：产品类型、产品子类型、成人数、儿童数、类别类型一置的条件");				
			}
			
			StringBuffer log=new StringBuffer();
			log.append("修改了订单子子项ID:"+orderItemMetaId+",旧值为“");
			log.append(orderItemMeta.getProductName());
			log.append("”,采购产品ID：");
			log.append(orderItemMeta.getMetaProductId());
			log.append(",采购类别ID：");
			log.append(orderItemMeta.getMetaBranchId());
			
			OrdOrderItemProd itemProd=orderItemProdDAO.selectByPrimaryKey(orderItemMeta.getOrderItemId());
			boolean hotel_single_room=Constant.PRODUCT_TYPE.HOTEL.name().equals(
					itemProd.getProductType())
					&& Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(
							itemProd.getSubProductType());
			long days=0;
			if (hotel_single_room) {//酒店单房型产品				
				days=ordOrderItemMetaTimeDAO.selectCountByOrderMeta(orderItemMeta.getOrderItemMetaId());
				for(int i=0;i<days;i++){
					Date visitTime=DateUtils.addDays(orderItemMeta.getVisitTime(), i);
					if (!productSellableLogic.isMetaProductSellable(metaBranch,
							orderItemMeta.getProductQuantity(), orderItemMeta
									.getVisitTime())) {
						throw new Exception("替换的类别不存在 或"+DateFormatUtils.format(visitTime, "yyyy-MM-dd")+"库存不足");
					}
				}
			}else{
				if (!productSellableLogic.isMetaProductSellable(metaBranch,
						orderItemMeta.getProductQuantity(), orderItemMeta
								.getVisitTime())) {
					throw new Exception("替换的类别不存 在库存或库存不足");
				}
				
			}
			
			productStockLogic.restoreStock(orderItemMeta);
			orderItemMeta.setFilialeName(metaProduct.getFilialeName());
			orderItemMeta.setPaymentTarget(metaProduct.getPaymentTarget());
			orderItemMeta.setMetaProductId(metaProduct.getMetaProductId());
			orderItemMeta.setMetaBranchId(metaBranch.getMetaBranchId());
			orderItemMeta.setIsResourceSendFax(metaProduct.getIsResourceSendFax());
			orderItemMeta.setValidDays(metaProduct.getValidDays());
			orderItemMeta.setSupplierId(metaProduct.getSupplierId());
			orderItemMeta.setProductName(metaProduct.getProductName()+"("+metaBranch.getBranchName()+")");
			orderItemMeta.setProductIdSupplier(metaBranch.getProductIdSupplier());
			orderItemMeta.setProductTypeSupplier(metaBranch.getProductTypeSupplier());
			//是否虚拟
			orderItemMeta.setVirtual(metaBranch.getVirtual());
			//由凭证对象为传真，并且采购类别设为需要单独创建传真来决定sendFax是否为true
			if (bCertificateTarget != null
					&& BooleanUtils.toBoolean(bCertificateTarget.isSendFax())
					&& metaBranch.hasSendFax()) {
				orderItemMeta.setSendFax("true");
			} else {
				orderItemMeta.setSendFax("false");
			}	
			//履行对象ID
			List<MetaPerform> metaPerformLst=performTargetService.getMetaPerformByMetaProductId(metaProduct.getMetaProductId());
			if(metaPerformLst!=null && metaPerformLst.size()>0){
				orderItemMeta.setPerformTargetId(metaPerformLst.get(0).getTargetId());
			}
			//由凭证对象为供应商资源确认
			//不是虚拟产品
			if (bCertificateTarget != null && !"true".equals(metaBranch.getVirtual())
					&& bCertificateTarget.hasSupplier()) {
				orderItemMeta.setSupplierFlag("true");
			} else {
				orderItemMeta.setSupplierFlag("false");
			}
			boolean resourceConfirm = false;

			//早餐数
			Long breakfastCount = 0L;
			//结算价计算
			if(hotel_single_room){		
				List<OrdOrderItemMetaTime> items=ordOrderItemMetaTimeDAO.selectAllByOrderMetaId(orderItemMetaId);
				Map<Date,OrdOrderItemMetaTime> itemMap=getItemMap(items);
				
				ordOrderItemMetaTimeDAO.deleteAllByOrderMetaId(orderItemMetaId);
				Long sumQuantity=0L;
				Long sumMarketPrice=0L;
				Long sumSettlementPrice=0L;
				for(long i=0;i<days;i++){
					TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranch.getMetaBranchId(), DateUtils.addDays(orderItemMeta.getVisitTime(),(int)i));
					OrdOrderItemMetaTime oldTime=itemMap.get(timePrice.getSpecDate());
					
					OrdOrderItemMetaTime orderItemMetaTime=new OrdOrderItemMetaTime();
					orderItemMetaTime.setMarketPrice(timePrice.getMarketPrice());
					orderItemMetaTime.setSuggestPrice(timePrice.getSuggestPrice());
					orderItemMetaTime.setBreakfastCount(timePrice.getBreakfastCount());
					orderItemMetaTime.setSettlementPrice(timePrice.getSettlementPrice());
					orderItemMetaTime.setQuatity(oldTime.getQuatity());
					
					orderItemMetaTime.setOrderItemMetaId(orderItemMeta.getOrderItemMetaId());
					orderItemMetaTime.setVisitTime(timePrice.getSpecDate());
					
					if(!resourceConfirm){
						resourceConfirm = productResourceConfirmLogic.isResourceConfirm(
								metaBranch, timePrice.getSpecDate());
					}	
					
					sumQuantity += orderItemMetaTime.getQuatity()*orderItemMeta.getProductQuantity();
					breakfastCount += orderItemMetaTime.getBreakfastCount() == null ? 0 : orderItemMetaTime.getBreakfastCount();
					sumMarketPrice += timePrice.getMarketPrice()
							* orderItemMetaTime.getQuatity()
							* orderItemMeta.getProductQuantity();
					
					sumSettlementPrice += timePrice.getSettlementPrice()
							* orderItemMetaTime.getQuatity()
							* orderItemMeta.getProductQuantity();
									
					ordOrderItemMetaTimeDAO.insert(orderItemMetaTime);
				}	
				Long avgMarketPrice=sumMarketPrice/sumQuantity;
				Long avgSettlementPrice=sumSettlementPrice/sumQuantity;
				
				orderItemMeta.setSettlementPrice(avgSettlementPrice);
				orderItemMeta.setTotalSettlementPrice(sumSettlementPrice);
				orderItemMeta.setActualSettlementPrice(avgSettlementPrice);
				orderItemMeta.setMarketPrice(avgMarketPrice);
			}else{
				TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranch.getMetaBranchId(), orderItemMeta.getVisitTime());
				orderItemMeta.setSettlementPrice(timePrice.getSettlementPrice());
				orderItemMeta.setActualSettlementPrice(timePrice.getSettlementPrice());
				orderItemMeta.setMarketPrice(timePrice.getMarketPrice());
				orderItemMeta.setTotalSettlementPrice(orderItemMeta.getSettlementPrice()*orderItemMeta.getQuantity()*orderItemMeta.getProductQuantity());
				breakfastCount = timePrice.getBreakfastCount();
				resourceConfirm = productResourceConfirmLogic.isResourceConfirm(
						metaBranch, orderItemMeta.getVisitTime());
			}
			orderItemMeta.setBreakfastCount(breakfastCount);
			orderItemMeta.setResourceConfirm(String.valueOf(resourceConfirm));
			orderItemMeta.setResourceStatus(BuildOrderInfoDTO
					.makeResourceConfirmStatus(resourceConfirm));
			
			productStockLogic.minusStock(orderItemMeta);
			
			log.append(",新值:“");
			log.append(orderItemMeta.getProductName());
			log.append("”,采购产品ID：");
			log.append(orderItemMeta.getMetaProductId());
			log.append(",采购类别ID：");
			log.append(orderItemMeta.getMetaBranchId());
			
			orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
			
			insertLog(orderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", orderItemMeta.getOrderId(), "ORD_ORDER", operatorId, 
					"订单资源替换", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), log.toString() );
		}catch(Exception ex){			
			//ex.printStackTrace();
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	@Override
	public ResultHandle updateOrderItemMetaByPrimaryKey(OrdOrderItemMeta orderItemMeta,String operatorId) {
		ResultHandle handle=new ResultHandle();
		try{
			
			orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
			
			StringBuffer log=new StringBuffer();
			log.append("修改了订单子子项ID:"+orderItemMeta.getOrderItemMetaId()+",游玩时间修改为");
			log.append(orderItemMeta.getVisitTime());
			
			insertLog(orderItemMeta.getOrderItemMetaId(), "ORD_ORDER_ITEM_META", orderItemMeta.getOrderId(), "ORD_ORDER",operatorId, 
					"游玩时间修改", Constant.COM_LOG_ORDER_EVENT.updateOrderItemMeta.name(), log.toString() );
		}catch(Exception ex){			
			//ex.printStackTrace();
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	
	@Override
	public ResultHandle updateOrderItemProdByPrimaryKey(OrdOrderItemProd itemProd,String operatorId) {
		ResultHandle handle=new ResultHandle();
		try{
			
			orderItemProdDAO.updateByPrimaryKey(itemProd);
			
			StringBuffer log=new StringBuffer();
			log.append("修改了订单子项ID:"+itemProd.getOrderItemProdId()+",游玩时间修改为");
			log.append(itemProd.getVisitTime());
			
			insertLog(itemProd.getOrderItemProdId(), "ORD_ORDER_ITEM_PROD", itemProd.getOrderId(), "ORD_ORDER",operatorId, 
					"游玩时间修改", Constant.COM_LOG_ORDER_EVENT.updateOrderItemProd.name(), log.toString() );
		}catch(Exception ex){			
			//ex.printStackTrace();
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	@Override
	public boolean updateRefundedAmount(Long orderId, Long refundmentId,Long amount) {
		if (null == orderId || null == amount || null == refundmentId) {
			return false;
		} else {
			orderDAO.updateProdRefundedAmount(orderId, refundmentId);
			return orderDAO.updateRefundedAmount(orderId, amount);
		}
	}

	
	private Map<Date,OrdOrderItemMetaTime> getItemMap(final List<OrdOrderItemMetaTime> list){
		Map<Date,OrdOrderItemMetaTime> map=new HashMap<Date, OrdOrderItemMetaTime>();
		for(OrdOrderItemMetaTime t:list){
			map.put(t.getVisitTime(), t);
		}
		return map;
	}
	
	/**
	 * @param ordOrderItemMetaTimeDAO the ordOrderItemMetaTimeDAO to set
	 */
	public void setOrdOrderItemMetaTimeDAO(
			OrdOrderItemMetaTimeDAO ordOrderItemMetaTimeDAO) {
		this.ordOrderItemMetaTimeDAO = ordOrderItemMetaTimeDAO;
	}
	/**
	 * @param metaProductDAO the metaProductDAO to set
	 */
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
	/**
	 * @param metaTimePriceDAO the metaTimePriceDAO to set
	 */
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	
	/**
	 * @param productResourceConfirmLogic the productResourceConfirmLogic to set
	 */
	public void setProductResourceConfirmLogic(
			ProductResourceConfirmLogic productResourceConfirmLogic) {
		this.productResourceConfirmLogic = productResourceConfirmLogic;
	}
 
	/**
	 * @param metaProductBranchDAO the metaProductBranchDAO to set
	 */
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	@Override
	public List<OrdOrder> getNeedForPaymentOrderList() {
		return orderDAO.selectForPaymentOrderList();
	}
	
	@Override
	public List<OrdOrder> getNeedSendWorkOrderOrderList(Map<String,Object> params) {
		return orderDAO.selectForPaymentOrderListByCondition(params);
	}
	
	@Override
	public List<OrdOrderItemProd> selectOrderItemProdByOrderId(Long orderId) {
		return orderItemProdDAO.selectByOrderId(orderId);
	}
	
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}
	@Override
	public void updateCertificateStatusAndTypeOrConfirmChannel(
			Long ordItemMetaId, String certificateStatus,
			String ebkCertificateType, String confirmChannel) {
		 this.orderItemMetaDAO.updateCertificateStatusAndTypeOrConfirmChannel(
				ordItemMetaId, certificateStatus,ebkCertificateType,confirmChannel);
	}
	@Override
	public boolean updateIsCashRefundByOrderId(OrdOrder order) {
		return orderDAO.updateIsCashRefundByOrdId(order);
	}
	
}
