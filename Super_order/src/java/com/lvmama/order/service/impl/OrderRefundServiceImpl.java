package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.com.dao.ComMessageDAO;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.REFUNDMENT_STATUS;
import com.lvmama.comm.vo.Constant.REFUND_TYPE;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderRefundmentDAO;

public class OrderRefundServiceImpl extends OrderServiceImpl implements OrderRefundService{
	
	private static final Logger LOG = Logger.getLogger(OrderRefundServiceImpl.class);
	
	private transient OrderRefundmentDAO orderRefundmentDAO;
	
	private OrderItemMetaDAO orderItemMetaDAO;

	private ComMessageDAO comMessageDAO;
	
	private OrderDAO orderDAO;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	private VstDistributorService vstDistributorService;
	private UserUserProxy userUserProxy;
	
	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}
	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}
	/**
	 * 修改退款单.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表修改退款单成功，<code>false</code> 代表修改退款单失败
	 */
	@Override
	public boolean updateRefund(final Long refundmentId,
			final List<Long> orderItemMetaIdList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName) {
		OrdRefundment ordRefundment = orderRefundmentDAO
				.queryOrdRefundmentById(refundmentId);
		if (REFUNDMENT_STATUS.REFUNDED.name().equalsIgnoreCase(
				ordRefundment.getStatus())) {
			LOG.error("UPDATE Refund fail: The Refundment has already been Refunded with the RefundmentId = "
					+ ordRefundment.getRefundmentId());
			return false;
		}
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(reason);
		if (REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundType)){
			ordRefundment.setRefundType(REFUND_TYPE.COMPENSATION.name());
		}else{
			ordRefundment.setRefundType(REFUND_TYPE.ORDER_REFUNDED.name());
		}
		ordRefundment.setStatus(refundStatus);
		orderRefundmentDAO.updateOrdRefundment(ordRefundment);
		
		insertLog(refundmentId, "ORD_REFUNDMENT", ordRefundment.getOrderId(), "ORD_ORDER", operatorName, 
				"修改退款单", Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), "修改退款单,价格为" + amount + ",类型为"
						+ Constant.REFUND_TYPE.getCnName(refundType) + ",状态为" + Constant.REFUNDMENT_STATUS.getCnName(refundStatus)
						+ ",原因为" + reason );
		

		int delRow = orderRefundmentDAO
				.deleteOrdRefundmentItemByRefundmentId(refundmentId);
		int resetRow = orderItemMetaDAO.resetRefundByOrderId(ordRefundment
				.getOrderId());
		if (delRow != resetRow) {
			throwException(
					"updateRefund fail : deleteOrdRefundmentItemByRefundmentId's row = "
							+ delRow + " resetRefundByOrderId's resetRow = "
							+ resetRow, LOG);
		}
		for (Long orderItemMetaId : orderItemMetaIdList) {
			OrdOrderItemMeta orderItemMeta = orderItemMetaDAO
					.selectByPrimaryKey(orderItemMetaId);
			orderItemMeta.setRefund("true");
			orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
			Map<String, Long> paramMap = new HashMap<String, Long>();
			paramMap.put("refundmentId", refundmentId);
			paramMap.put("orderItemMetaId", orderItemMetaId);
			orderRefundmentDAO.insertOrdRefundmentItem(paramMap);
		}
		return true;
	}
	/**
	 * 修改退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表申请原路退款成功，<code>false</code> 代表申请原路退款失败
	 */
	@Override
	public boolean updateRefundment(final Long orderId, final Long saleServiceId,
			final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName, 
			final List<OrdOrderItemMeta> allOrdOrderItemMetas, Long refundmentId, Long penaltyAmount) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		LOG.info("refundType: " + refundType);
		if (REFUND_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundType)) {

			// 表示订单未支付
			if(order.getActualPay() == 0){
				LOG.error("Apply Refund fail: ActualPay amount is 0 of the order with the orderId = " + orderId);
				return false;
			}
			if (amount > order.getActualPay()) {
				LOG.error("Apply Refund fail: amount(" + amount
						+ ") is larger than actual Pay(" + order.getActualPay()
						+ ") of the order with the orderId = " + orderId);
				return false;
			}
		}

		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setRefundmentId(refundmentId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(reason);
		ordRefundment.setPenaltyAmount(penaltyAmount);
		ordRefundment.setStatus(refundStatus);
		/**
		 *  生成退款单
		 */
		orderRefundmentDAO.updateRefundment(ordRefundment);
		
		/**
		 *  添加日志
		 */
		insertLog(refundmentId, "ORD_REFUNDMENT", ordRefundment.getOrderId(), "ORD_ORDER", operatorName, 
				"修改退款单", Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), "修改退款单,价格为" + amount + ",类型为"
						+ Constant.REFUND_TYPE.getCnName(refundType) + ",状态为" + Constant.REFUNDMENT_STATUS.getCnName(refundStatus)
						+ ",原因为" + reason );
		
		/**
		 * 生成退款单明细
		 * 		全额退款时，将所有的采购产品都生成退款明细，退款数量、违约金、供应商损失、退款金额、供应商承担都为空
		 * 		不为全额退款时，根据选中的采购产品ID来生成相应的退款明细
		 */
		if (null != allOrdOrderItemMetas && UtilityTool.isValid(allOrdOrderItemMetas) && allOrdOrderItemMetas.size() > 0) {
			for (OrdOrderItemMeta orderItemMeta : allOrdOrderItemMetas) {
				/**
				 *  将采购产品的退款状态改为：已退款
				 */
				orderItemMeta.setRefund("true");
				orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
				
				/**
				 *  生成退款单明细
				 */
				Map paramMap = new HashMap();
				paramMap.put("refundmentId", refundmentId);
				paramMap.put("orderItemMetaId", orderItemMeta.getOrderItemMetaId());
				paramMap.put("type", orderItemMeta.getAmountType());
				paramMap.put("amount", orderItemMeta.getAmountValue());
				if(orderItemMeta.getAmountValue()==null){
					paramMap.put("amount", 0L);
				}
				paramMap.put("memo", orderItemMeta.getMemo());
				paramMap.put("actualLoss", orderItemMeta.getActualLoss());
				if(orderItemMeta.getActualLoss()==null){
					paramMap.put("actualLoss", 0L);
				}
				orderRefundmentDAO.insertOrdRefundmentItem(paramMap);
			}
		}
		
		return true;
	}
	/**
	 * 修改退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表申请原路退款成功，<code>false</code> 代表申请原路退款失败
	 */
	@Override
	public boolean updateVstRefundment(final Long orderId, final Long saleServiceId,
			final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName, 
			final List<OrdOrderItemMeta> allOrdOrderItemMetas, Long refundmentId, Long penaltyAmount) {
		OrdOrder order = new OrdOrder();
		VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orderId);
		order.setActualPay(vstOrdOrderVo.getActualAmount());
		LOG.info("refundType: " + refundType);
		if (REFUND_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundType)) {

			// 表示订单未支付
			if(order.getActualPay() == 0){
				LOG.error("Apply Refund fail: ActualPay amount is 0 of the order with the orderId = " + orderId);
				return false;
			}
			if (amount > order.getActualPay()) {
				LOG.error("Apply Refund fail: amount(" + amount
						+ ") is larger than actual Pay(" + order.getActualPay()
						+ ") of the order with the orderId = " + orderId);
				return false;
			}
		}

		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setRefundmentId(refundmentId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(reason);
		ordRefundment.setPenaltyAmount(penaltyAmount);
		ordRefundment.setStatus(refundStatus);
		/**
		 *  生成退款单
		 */
		orderRefundmentDAO.updateRefundment(ordRefundment);
		
		/**
		 *  添加日志
		 */
		insertLog(refundmentId, "ORD_REFUNDMENT", ordRefundment.getOrderId(), "ORD_ORDER", operatorName, 
				"修改退款单", Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), "修改退款单,价格为" + amount + ",类型为"
						+ Constant.REFUND_TYPE.getCnName(refundType) + ",状态为" + Constant.REFUNDMENT_STATUS.getCnName(refundStatus)
						+ ",原因为" + reason );
		
		/**
		 * 生成退款单明细
		 * 		全额退款时，将所有的采购产品都生成退款明细，退款数量、违约金、供应商损失、退款金额、供应商承担都为空
		 * 		不为全额退款时，根据选中的采购产品ID来生成相应的退款明细
		 */
		if (null != allOrdOrderItemMetas && UtilityTool.isValid(allOrdOrderItemMetas) && allOrdOrderItemMetas.size() > 0) {
			for (OrdOrderItemMeta orderItemMeta : allOrdOrderItemMetas) {
				/**
				 *  将采购产品的退款状态改为：已退款
				 */
				//VST中OrdOrderItemMeta没有refund字段
//				orderItemMeta.setRefund("true");
//				orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
				
				/**
				 *  生成退款单明细
				 */
				Map paramMap = new HashMap();
				paramMap.put("refundmentId", refundmentId);
				paramMap.put("orderItemMetaId", orderItemMeta.getOrderItemMetaId());
				paramMap.put("type", orderItemMeta.getAmountType());
				paramMap.put("amount", orderItemMeta.getAmountValue());
				if(orderItemMeta.getAmountValue()==null){
					paramMap.put("amount", 0L);
				}
				paramMap.put("memo", orderItemMeta.getMemo());
				paramMap.put("actualLoss", orderItemMeta.getActualLoss());
				if(orderItemMeta.getActualLoss()==null){
					paramMap.put("actualLoss", 0L);
				}
				orderRefundmentDAO.insertOrdRefundmentItem(paramMap);
			}
		}
		
		return true;
	}
	/**
	 * 更新退款单审核状态.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param status
	 *            审核状态（拒绝/通过）
	 * @param memo
	 *            审核原因
	 * @param operatorName
	 *            操作人
	 * @return <code>true</code>代表更新退款单审核状态成功，<code>false</code> 代表更新退款单审核状态失败
	 */
	@Override
	public boolean updateOrderRefundmentApproveStatus(final Long refundmentId,
			final String status, final String memo, final String operatorName) {
		boolean successFlag = orderRefundmentDAO
				.updateOrdRefundmentStatusAndMemoById(refundmentId, status,
						memo, operatorName);
		if (successFlag) {
			OrdRefundment ordrefundment = this.orderRefundmentDAO
					.queryOrdRefundmentById(refundmentId);
			
			insertLog(refundmentId, "ORD_REFUNDMENT", ordrefundment.getOrderId(), "ORD_ORDER", operatorName, 
					"退款单审核", Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), "退款单审核,状态为" + Constant.REFUNDMENT_STATUS.getCnName(status) + " " + memo );

			if (Constant.REFUNDMENT_STATUS.REJECTED.name().equals(
					ordrefundment.getStatus())) {
				try {
					ComMessage msg = new ComMessage();
					msg.setContent("订单" + ordrefundment.getOrderId() + "退款被驳回");
					msg.setCreateTime(new Date());
					msg.setReceiver(ordrefundment.getOperatorName());
					msg.setSender(Constant.SYSTEM_USER);
					msg.setStatus("CREATE");
					this.comMessageDAO.insertComMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @deprecated
	 */
	@Override
	public Long queryRefundAmountSum(Long orderId){
		return  orderRefundmentDAO.queryRefundAmountSum(orderId);
	}
	@Override
	public Long queryRefundAmountSumByOrderIdAndSysCode(Long orderId, String sysCode) {
		return  orderRefundmentDAO.queryRefundAmountSumByOrderIdAndSysCode(orderId, sysCode);
	}
	
	/*
	 * @Override public Long queryMoneyDrawHistoryCount(CompositeQuery
	 * compositeQuery) { // TODO Auto-generated method stub Map queryParamMap =
	 * buildFincMoneyHisParam(compositeQuery); return
	 * (Long)this.fincMoneyDrawDAO
	 * .queryForObject("FINC_MONEY_DRAW.queryMoneyDrawHistoryCount",
	 * queryParamMap); }
	 */
	public boolean rejectCashRefundment(final Long refundmentId,
			final String memo, final String operatorName) {
		OrdRefundment orf = orderRefundmentDAO
				.queryOrdRefundmentById(refundmentId);
		if (orf == null) {
			return false;
		}
		orf.setOperatorName(operatorName);
		orf.setStatus(Constant.REFUNDMENT_STATUS.REJECTED.name());
		orf.setMemo(orf.getMemo()+";拒绝原因:"+memo);
		//拒绝后 将更改的退款网关改为空(即:不更改)
		orf.setRefundBank(null);
		orderRefundmentDAO.updateOrdRefundment(orf);
		
		insertLog(refundmentId, "ORD_REFUNDMENT", orf.getOrderId(), "ORD_ORDER", operatorName, 
				"拒绝退款", Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), "拒绝退款,状态为" + Constant.REFUNDMENT_STATUS.REJECTED.name() + " " + memo );
		
		return true;
	}
	
	@Override
	public boolean deteleRefund(Long orderId, Long refundmentId, String refundType) {
//		boolean flag = orderRefundmentDAO.deleteRefundment(refundmentId);
//		if(flag){
		boolean flag = orderRefundmentDAO.deleteRefundmentItem(refundmentId);
//		}
		return flag;
	}
	
	
	public Constant.APPLY_REFUNDMENT_RESULT validateRefundment(final Long orderId,final String refundType,final Long amount){
		LOG.info("OrderId: "+ orderId +" refundType: " + refundType);
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (REFUND_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundType)) {
			// 表示订单未支付
			if(order.getActualPay() == 0){
				LOG.error("Apply Refund fail: ActualPay amount is 0 of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_UNPAY;
			}
			//表示订单资金已转移
			if(Constant.PAYMENT_STATUS.TRANSFERRED.name().equalsIgnoreCase(order.getPaymentStatus())){
				LOG.error("Apply Refund fail: Money has been transferred of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_TRANSFERRED;
			}
			
			//TODO 退款金额过大需要比较
			long refundedAmount = order.getRefundedAmount() == null ? 0l : order.getRefundedAmount();
			if (amount > (order.getActualPay() - refundedAmount)) {
				LOG.error("Apply Refund fail: amount(" + amount
						+ ") is larger than actual Pay(" + order.getActualPay()
						+ ") of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.REFUND_AMOUNT2LARGE;
			}
			// 判断是否存在退款单
			List<OrdRefundment> ordRefundmentList = orderRefundmentDAO.findRefundOrdRefundmentByOrderId(orderId);
			if (ordRefundmentList != null && ordRefundmentList.size() > 0) {
				LOG.error("Apply Refund fail: OrdRefundment has already existed with the orderId = "
						+ orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.EXISTS_UNREFUNDED;
			}
			
			if(!order.getApproveStatus().equals(Constant.ORDER_APPROVE_STATUS.VERIFIED.name())){
				LOG.error("Apply Refund fail: Order approve status is not verified with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_STATUS_NOT_VERIFIED;
			}
		}
		return Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS;
	}
	
	public Constant.APPLY_REFUNDMENT_RESULT validateRefundmentVst(final Long orderId,final String refundType,final Long amount){
		LOG.info("OrderId: "+ orderId +" refundType: " + refundType);
		VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
		UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
		long tempRefundedAmount = this.getOrderServiceProxy().getRefundAmountByOrderId(Long.valueOf(orderId), Constant.COMPLAINT_SYS_CODE.VST.name());
		//设置订单来源渠道
//		if(vstOrdOrderVo.getDistributorId()!=null) {
//			vstOrdOrderVo.setDistributorName(vstDistributorService.getDistributorName(vstOrdOrderVo.getDistributorId()));	
//		}
		OrdOrder order = new OrdOrder().setProp(vstOrdOrderVo, userUser, tempRefundedAmount);
		if (REFUND_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundType)) {
			// 表示订单未支付
			if(order.getActualPay() == 0){
				LOG.error("Apply Refund fail: ActualPay amount is 0 of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_UNPAY;
			}
			//表示订单资金已转移
			if(Constant.PAYMENT_STATUS.TRANSFERRED.name().equalsIgnoreCase(order.getPaymentStatus())){
				LOG.error("Apply Refund fail: Money has been transferred of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_TRANSFERRED;
			}
			
			//TODO 退款金额过大需要比较
			long refundedAmount = order.getRefundedAmount() == null ? 0l : order.getRefundedAmount();
			if (amount > (order.getActualPay() - refundedAmount)) {
				LOG.error("Apply Refund fail: amount(" + amount
						+ ") is larger than actual Pay(" + order.getActualPay()
						+ ") of the order with the orderId = " + orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.REFUND_AMOUNT2LARGE;
			}
			// 判断是否存在退款单
			List<OrdRefundment> ordRefundmentList = orderRefundmentDAO.findRefundOrdRefundmentByOrderId(orderId);
			if (ordRefundmentList != null && ordRefundmentList.size() > 0) {
				LOG.error("Apply Refund fail: OrdRefundment has already existed with the orderId = "
						+ orderId);
				return Constant.APPLY_REFUNDMENT_RESULT.EXISTS_UNREFUNDED;
			}
			//新系统不需要判断" 订单审核状态 "
//			if(!order.getApproveStatus().equals(Constant.ORDER_APPROVE_STATUS.VERIFIED.name())){
//				LOG.error("Apply Refund fail: Order approve status is not verified with the orderId = " + orderId);
//				return Constant.APPLY_REFUNDMENT_RESULT.ORDER_STATUS_NOT_VERIFIED;
//			}
		}
		return Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS;
	}
	//------------新的退款-------------------------
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	@Override
	public Constant.APPLY_REFUNDMENT_RESULT applyRefund(final Long orderId, final Long saleServiceId,
			final List orderItemMetaList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName,
			final Long penaltyAmount) {
		//验证是否可以生成退款单
		Constant.APPLY_REFUNDMENT_RESULT validateRes = this.validateRefundment(orderId, refundType, amount);
		if(!Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS.equals(validateRes)){//如果验证结果不为NULL 说明不符合生成退款单的条件 返回验证结果
			return validateRes;
		}
		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setOrderId(orderId);
		ordRefundment.setSaleServiceId(saleServiceId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setMemo(reason);
		ordRefundment.setCreateTime(new Date());
		
		if (Constant.REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundType)){
			ordRefundment.setRefundType(Constant.REFUND_TYPE.COMPENSATION.name());
		}else{
			ordRefundment.setRefundType(REFUND_TYPE.ORDER_REFUNDED.name());
		}

		ordRefundment.setIfApply("Y");
		// 退款申请时不需要填写退款金额和违约金
		if(!"REFUND_APPLY".equalsIgnoreCase(refundStatus)){
			ordRefundment.setAmount(amount);
			ordRefundment.setPenaltyAmount(penaltyAmount);
			ordRefundment.setIfApply("N");
		}

		ordRefundment.setApproveTime(new Date());
		ordRefundment.setStatus(refundStatus);
		ordRefundment.setSysCode(Constant.COMPLAINT_SYS_CODE.SUPER.name());

		Long refundmentId = orderRefundmentDAO.saveOrdRefundment(ordRefundment);

		insertLog(refundmentId, "ORD_REFUNDMENT", ordRefundment.getOrderId(), "ORD_ORDER", operatorName, 
				"创建银行退款单", Constant.COM_LOG_CASH_EVENT.insertOrderRefundment.name(), "创建银行退款单" + reason );
		
		if (UtilityTool.isValid(orderItemMetaList) && orderItemMetaList.size() > 0) {
			for (int i=0; i<orderItemMetaList.size();i++) {
				OrdOrderItemMeta ordOrderItemMeta = (OrdOrderItemMeta)orderItemMetaList.get(i);
				OrdOrderItemMeta orderItemMeta = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemMetaId());
				orderItemMeta.setRefund("true");
				orderItemMetaDAO.updateByPrimaryKey(orderItemMeta);
				
				Map paramMap = new HashMap();
				paramMap.put("refundmentId", refundmentId);
				paramMap.put("orderItemMetaId", ordOrderItemMeta.getOrderItemMetaId());
				
				// 退款类型为补偿单时 退款明细的type只能是供应商承担金额
				if (REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundType)) {
					paramMap.put("type", "SUPPLIER_BEAR");
				}else{
					paramMap.put("type", ordOrderItemMeta.getAmountType());
				}
				Long amountval = ordOrderItemMeta.getAmountValue();
				paramMap.put("amount", amountval);
				paramMap.put("memo", ordOrderItemMeta.getMemo());
				paramMap.put("actualLoss", ordOrderItemMeta.getActualLoss());
				Long refundmentItemId =orderRefundmentDAO.insertOrdRefundmentItem(paramMap);
				
				insertLog(refundmentId, "ORD_REFUNDMENT_ITEM", refundmentId, "ORD_REFUNDMENT", operatorName, 
						"创建退款单明细", Constant.COM_LOG_CASH_EVENT.insertOrderRefundment.name(), "创建退款单明细，操作者：" + operatorName );
			}
		}
		return validateRes;
	}
	
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	@Override
	public Constant.APPLY_REFUNDMENT_RESULT applyRefundVst(final Long orderId, final Long saleServiceId,
			final List vstOrdOrderItemsList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName,
			final Long penaltyAmount) {
		//验证是否可以生成退款单
		Constant.APPLY_REFUNDMENT_RESULT validateRes = this.validateRefundmentVst(orderId, refundType, amount);
		if(!Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS.equals(validateRes)){//如果验证结果不为NULL 说明不符合生成退款单的条件 返回验证结果
			return validateRes;
		}
		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setOrderId(orderId);
		ordRefundment.setSaleServiceId(saleServiceId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setMemo(reason);
		ordRefundment.setCreateTime(new Date());
		
		if (Constant.REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundType)){
			ordRefundment.setRefundType(Constant.REFUND_TYPE.COMPENSATION.name());
		}else{
			ordRefundment.setRefundType(REFUND_TYPE.ORDER_REFUNDED.name());
		}

		ordRefundment.setIfApply("Y");
		// 退款申请时不需要填写退款金额和违约金
		if(!"REFUND_APPLY".equalsIgnoreCase(refundStatus)){
			ordRefundment.setAmount(amount);
			ordRefundment.setPenaltyAmount(penaltyAmount);
			ordRefundment.setIfApply("N");
		}

		ordRefundment.setApproveTime(new Date());
		ordRefundment.setStatus(refundStatus);
		ordRefundment.setSysCode(Constant.COMPLAINT_SYS_CODE.VST.name());

		Long refundmentId = orderRefundmentDAO.saveOrdRefundment(ordRefundment);

		insertLog(refundmentId, "ORD_REFUNDMENT", ordRefundment.getOrderId(), "ORD_ORDER", operatorName, 
				"创建银行退款单", Constant.COM_LOG_CASH_EVENT.insertOrderRefundment.name(), "创建银行退款单" + reason );
		
		if (UtilityTool.isValid(vstOrdOrderItemsList) && vstOrdOrderItemsList.size() > 0) {
			for (int i=0; i<vstOrdOrderItemsList.size();i++) {
				VstOrdOrderItem vstOrdOrderItem = (VstOrdOrderItem)vstOrdOrderItemsList.get(i);
				//TODO heyuxing 数据库需要添加字段
				//VstOrdOrderItem vstOrderItem = vstOrdOrderService.getVstOrdOrderItem(vstOrdOrderItem.getOrderItemId());
				//vstOrderItem.setRefund("true");
				//orderItemMetaDAO.updateByPrimaryKey(vstOrderItem);
				
				Map paramMap = new HashMap();
				paramMap.put("refundmentId", refundmentId);
				paramMap.put("orderItemMetaId", vstOrdOrderItem.getOrderItemId());
				
				// 退款类型为补偿单时 退款明细的type只能是供应商承担金额
				if (REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundType)) {
					paramMap.put("type", "SUPPLIER_BEAR");
				}else{
					paramMap.put("type", vstOrdOrderItem.getAmountType());
				}
				Long amountval = vstOrdOrderItem.getAmountValue();
				paramMap.put("amount", amountval);
				paramMap.put("memo", vstOrdOrderItem.getMemo());
				paramMap.put("actualLoss", vstOrdOrderItem.getActualLoss());
				Long refundmentItemId =orderRefundmentDAO.insertOrdRefundmentItem(paramMap);
				
				insertLog(refundmentId, "ORD_REFUNDMENT_ITEM", refundmentId, "ORD_REFUNDMENT", operatorName, 
						"创建退款单明细", Constant.COM_LOG_CASH_EVENT.insertOrderRefundment.name(), "创建退款单明细，操作者：" + operatorName );
			}
		}
		return validateRes;
	}
 

	public OrderRefundmentDAO getOrderRefundmentDAO() {
		return orderRefundmentDAO;
	}

	public void setOrderRefundmentDAO(OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}

	public OrderItemMetaDAO getOrderItemMetaDAO() {
		return orderItemMetaDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public ComMessageDAO getComMessageDAO() {
		return comMessageDAO;
	}

	public void setComMessageDAO(ComMessageDAO comMessageDAO) {
		this.comMessageDAO = comMessageDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public OrderService getOrderServiceProxy() {
		return (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	}
}
