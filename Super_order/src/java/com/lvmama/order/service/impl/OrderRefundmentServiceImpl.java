package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.op.FinGroupSettlement;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.REFUNDMENT_TYPE;
import com.lvmama.op.dao.GroupBudgetDAO;
import com.lvmama.op.dao.OpTravelGroupDAO;
import com.lvmama.ord.dao.OrdSaleServiceDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderRefundmentDAO;
import com.lvmama.order.service.OrderRefundmentService;

/**
 * OrdRefundment服务实现类.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 * @see com.lvmama.ord.po.OrdRefundment
 * @see com.lvmama.order.dao.OrderRefundmentDAO
 * @see com.lvmama.order.service.OrderRefundmentService
 */
public final class OrderRefundmentServiceImpl extends OrderServiceImpl implements OrderRefundmentService {
	
	private static Logger LOG = Logger.getLogger(OrderRefundmentServiceImpl.class);
	
	/**
	 * ordRefundmentDAO.
	 */
	private transient OrderRefundmentDAO orderRefundmentDAO;
	
	private OrderItemMetaDAO orderItemMetaDAO;
	
	private OpTravelGroupDAO opTravelGroupDAO;
	
	private GroupBudgetDAO groupBudgetDAO;
	
	private TopicMessageProducer orderMessageProducer;
	
	private transient OrdSaleServiceDAO ordSaleServiceDao;
	
	private transient OrderDAO orderDAO;
	
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
	private boolean updateNeedSaleService(String needSaleService, Long orderId, String operatorId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId.toString());
		params.put("needSaleService", needSaleService);
		int row = orderDAO.updateByParamMap(params);
		if(row == 1){
			insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
					"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
					"设置订单需要售后服务为" + Constant.TRUE_FALSE.getCnName(needSaleService));
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 根据订单ID和状态查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            状态
	 * @param gatewayTradeNo
	 *            网关的交易号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByOrderIdAndStatus(
			final Long orderId, final String status, final String gatewayTradeNo) {
		return orderRefundmentDAO.queryOrdRefundmentByOrderIdAndStatus(orderId,
				status, gatewayTradeNo);
	}

	/**
	 * 根据 批次号查询{@link OrdRefundment}.
	 *
	 * @param refundmentBatchId
	 *            批次号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByRefundmentBatchId(
			final Long refundmentBatchId) {
		return orderRefundmentDAO
				.queryOrdRefundmentByRefundmentBatchId(refundmentBatchId);
	}

	/**
	 * 根据refundmentId更新status.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            status
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusById(final Long refundmentId,
			final String status) {
		return orderRefundmentDAO.updateOrdRefundmentStatusById(refundmentId,
				status);
	}

	public   void ordRefundment2UpdateSettlement(Long refundmentId, final PermUser user) {
		OrdRefundment or = this.orderRefundmentDAO
				.queryOrdRefundmentById(refundmentId);
		long orderId = or.getOrderId();
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		boolean orderSettlement = true;
		if(!StringUtil.isEmptyString(order.getTravelGroupCode())){
			String groupType = opTravelGroupDAO.selectGroupTypeByGroupCode(order.getTravelGroupCode());
			if(!"AGENCY".equals(groupType)){
				orderSettlement = false;
			}
		} 
		if (orderSettlement) {//订单结算
			orderMessageProducer.sendMsg(MessageFactory.newOrderRefundedSuccessMessage(refundmentId));
		} else {//团结算
			// 查询团信息
			OpTravelGroup group = opTravelGroupDAO.selectByGroupCode(order.getTravelGroupCode());
			// 查询退款明细
			List<OrdRefundMentItem> oriList = orderRefundmentDAO.queryOrdRefundmentItemById(or.getRefundmentId());
			for (OrdRefundMentItem ori : oriList) {
				OrdOrderItemMeta ooim = orderItemMetaDAO.selectByPrimaryKey(ori.getOrderItemMetaId());
				this.groupSetlementHandle(group, ooim, user.getUserId(), order.getTravelGroupCode(), ori, user.getUserName());
			}
		}

	}
	/**
	 * 实际成本表的单项总成本(全额退款、退款数量等于子子项总数)
	 * @param ossi
	 */
	private void groupSetlementHandle(OpTravelGroup group,OrdOrderItemMeta ooim,Long userId,String travelGroupCode,OrdRefundMentItem ori,String operatorName){
		OpGroupBudgetProd obp = groupBudgetDAO.getCostGroupBudgetProdByBranchId(ooim.getMetaBranchId(), travelGroupCode);
		Long new_settlementPrice = null;
		Long settlementPrice = ooim.getProductQuantity() * ooim.getQuantity() * ooim.getActualSettlementPrice() / 100;
		if ("VISITOR_LOSS".equals(ori.getType())) {//退款明细类型为游客损失
			new_settlementPrice = ori.getActualLoss() / 100;
		}else{
			new_settlementPrice = settlementPrice - ori.getAmount() / 100 ;
		}
		if(group!=null && "CHECKED".equals(group.getSettlementStatus())){//已核算，生成抵扣款
			FinGroupSettlement fgs = new FinGroupSettlement();
			fgs.setTravelGroupCode(travelGroupCode);
			fgs.setBudgetItemId(obp.getItemId());
			fgs.setBudgetItemName(obp.getPrdBranchName());
			fgs.setBudgetItemType("PROD");
			fgs.setSupplierId(ooim.getSupplierId());
			fgs.setTargetId(obp.getTargetId());
			fgs.setCurrency(obp.getCurrency());
			fgs.setExchangeRate(obp.getExchangeRate());
			fgs.setSubtotalCosts(0- (settlementPrice - new_settlementPrice) );
			fgs.setCreatetime(new Date());
			fgs.setCreator(userId);
			groupBudgetDAO.insertDeduction(fgs);
		}
		if (obp != null) {
			//更新实际成本表的单项总成本
			obp.setSubtotalCostsFc(obp.getSubtotalCostsFc() - settlementPrice + new_settlementPrice);//减去旧的结算价，加上新的结算价
			Double se1 =  settlementPrice * obp.getExchangeRate();
			Double se2 =  new_settlementPrice * obp.getExchangeRate();
			obp.setSubtotalCosts(obp.getSubtotalCosts() - se1 + se2);
			Map<String, Object> map = new HashMap<String, Object>();
			map = new HashMap<String, Object>();
			map.put("budgetItemType", "PRODUCT");
			map.put("budgetItemId", obp.getItemId());
			FinGroupSettlement fs = groupBudgetDAO.getFinGroupSettlement(map);
			if("PARTPAY".equals(obp.getPayStatus()) || "PAYED".equals(obp.getPayStatus())){ //成本项打款状态为部分支付或已打款
				if(obp.getPayAmount() < obp.getSubtotalCostsFc()){//已打款金额小于修改后的金额，打款状态变成部分支付
					obp.setPayStatus("PARTPAY");
					if("PAYED".equals(fs.getPaymentStatus()) && fs.getSubtotalCosts() < fs.getPayAmount()){
						fs.setSubtotalCosts(fs.getPayAmount());
					}
					if(fs.getPayAmount() < obp.getSubtotalCostsFc() && obp.getSubtotalCostsFc() < fs.getSubtotalCosts()){
						fs.setSubtotalCosts(obp.getSubtotalCostsFc());
						fs.setPaymentStatus("PARTPAY");
					}
				}else{//打款金额大于修改后的金额，打款状态变成已支付
					obp.setPayStatus("PAYED");
					fs.setSubtotalCosts(obp.getSubtotalCostsFc());
					fs.setPaymentStatus("PAYED");
				}
			}
			
			if("PARTREQPAY".equals(obp.getPayStatus()) || "REQPAY".equals(obp.getPayStatus())){
				if(obp.getSubtotalCostsFc() > fs.getSubtotalCosts()){
					obp.setPayStatus("PARTREQPAY");
				}else{
					fs.setSubtotalCosts(obp.getSubtotalCostsFc());//成本项已催款（未打款）时调低结算金额，需要把团单项结算记录的结算价更新
					obp.setPayStatus("REQPAY");
				}
			}
			if(fs!=null){
				groupBudgetDAO.updateFinGroupSettlement(fs);
			}
			groupBudgetDAO.updateGroupBudgetProd(obp);
		}
	}
	/**
	 * 更加refundmentId更新status和refundTime.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param refundTime
	 *            新refundTime
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusAndRefundTimeById(
			final Long refundmentId, final String status, final Date refundTime) {
		return orderRefundmentDAO.updateOrdRefundmentStatusAndRefundTimeById(
				refundmentId, status, refundTime);
	}
	
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	@Override
	public OrdRefundment queryOrdRefundmentById(final Long refundmentId) {
		return orderRefundmentDAO.queryOrdRefundmentById(refundmentId);
	}

	// 支付需要的接口-------------------------------------------------------
	/**
	 * 保存新的{@link OrdRefundment}.
	 *
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 新{@link OrdRefundment}的ID
	 */
	@Override
	public Long saveOrdRefundment(final OrdRefundment ordRefundment) {
		return orderRefundmentDAO.saveOrdRefundment(ordRefundment);
	}
	
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 * 
	 * @return {@link List<OrdRefundment>}
	 */
	@Override
	public List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId) {
		return orderRefundmentDAO.findValidOrdRefundmentByOrderId(orderId);
	}
	/**
	 * @deprecated
	 * 生成退款单.
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @return
	 */
	@Override
	public Long markOrdRefunment(boolean isFullrefund,final Long orderId,final String operatorName,final Long amount,final String mome,final boolean isKey){
		Long refundmentId = 0L;
		//1. 生成退款服务.
		OrdSaleService ordSevice=new OrdSaleService();
		ordSevice.setCreateTime(new Date());
		ordSevice.setOperatorName(operatorName);
		ordSevice.setOrderId(orderId);
		ordSevice.setApplyContent("退款");
		ordSevice.setServiceType(Constant.SERVICE_TYPE.NORMAL.name());
		ordSevice.setStatus(Constant.SERVICE_TYPE.NORMAL.name());
		Long saleId=ordSaleServiceDao.addOrdSaleService(ordSevice);
		//2. 更新订单是否需要售后服务.
	    updateNeedSaleService("true", orderId,operatorName);
		//3. 生成退款单.
		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setOrderId(orderId);
		ordRefundment.setSaleServiceId(saleId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(mome);
		ordRefundment.setCreateTime(new Date());
		ordRefundment.setRefundType(REFUNDMENT_TYPE.ORDER_REFUNDED.name());
		ordRefundment.setApproveTime(new Date());
		if(isKey){
			ordRefundment.setStatus(Constant.REFUNDMENT_STATUS.VERIFIED.name());
		}else{
			ordRefundment.setStatus(Constant.REFUNDMENT_STATUS.UNVERIFIED.name());
		}
		refundmentId = orderRefundmentDAO.saveOrdRefundment(ordRefundment);
		String type;
		if(isFullrefund){//如果是全额退款，退款单明细的类型为游客损失
			type =Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.name();
		}else{//如果是不是全额退款，退款单明细的类型为游客损失
			type = Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name();
		}
		orderRefundmentDAO.saveOrdRefundmentItemByOrderId(orderId, refundmentId, type, 0l);
		return refundmentId;
	}
	
	/**
	 * 生成退款单.
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @param sysCode
	 * @return
	 */
	@Override
	public Long markOrdRefunment(boolean isFullrefund,final Long orderId,final String operatorName,final Long amount,final String mome,final boolean isKey, String sysCode){
		Long refundmentId = 0L;
		//1. 生成退款服务.
		OrdSaleService ordSevice=new OrdSaleService();
		ordSevice.setCreateTime(new Date());
		ordSevice.setOperatorName(operatorName);
		ordSevice.setOrderId(orderId);
		ordSevice.setApplyContent("退款");
		ordSevice.setServiceType(Constant.SERVICE_TYPE.NORMAL.name());
		ordSevice.setStatus(Constant.SERVICE_TYPE.NORMAL.name());
		ordSevice.setSysCode(sysCode);
		Long saleId=ordSaleServiceDao.addOrdSaleService(ordSevice);
		//2. 更新订单是否需要售后服务.
		if(Constant.COMPLAINT_SYS_CODE.SUPER.name().equals(sysCode)) {
		    updateNeedSaleService("true", orderId,operatorName);	
		}
		//3. 生成退款单.
		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setOrderId(orderId);
		ordRefundment.setSaleServiceId(saleId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(mome);
		ordRefundment.setCreateTime(new Date());
		ordRefundment.setRefundType(REFUNDMENT_TYPE.ORDER_REFUNDED.name());
		ordRefundment.setApproveTime(new Date());
		if(isKey){
			ordRefundment.setStatus(Constant.REFUNDMENT_STATUS.VERIFIED.name());
		}else{
			ordRefundment.setStatus(Constant.REFUNDMENT_STATUS.UNVERIFIED.name());
		}
		ordRefundment.setSysCode(sysCode);
		refundmentId = orderRefundmentDAO.saveOrdRefundment(ordRefundment);
		String type;
		if(isFullrefund){//如果是全额退款，退款单明细的类型为游客损失
			type =Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.name();
		}else{//如果是不是全额退款，退款单明细的类型为游客损失
			type = Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name();
		}
		orderRefundmentDAO.saveOrdRefundmentItemByOrderId(orderId, refundmentId, type, 0l);
		return refundmentId;
	}
	
	public Long markOrdRefunmentBySupplierBear(final Long orderId,final Long orderItemMetaId,final String operatorName,final Long amount,final String memo){
		Long refundmentId = 0L;
		//1. 生成退款服务.
		OrdSaleService ordSevice=new OrdSaleService();
		ordSevice.setCreateTime(new Date());
		ordSevice.setOperatorName(operatorName);
		ordSevice.setOrderId(orderId);
		ordSevice.setApplyContent("退款");
		ordSevice.setServiceType(Constant.SERVICE_TYPE.NORMAL.name());
		ordSevice.setStatus(Constant.SERVICE_TYPE.NORMAL.name());
		Long saleId=ordSaleServiceDao.addOrdSaleService(ordSevice);
		//2. 更新订单是否需要售后服务.
	    updateNeedSaleService("true", orderId,operatorName);
		//3. 生成退款单.
		OrdRefundment ordRefundment = new OrdRefundment();
		ordRefundment.setOrderId(orderId);
		ordRefundment.setSaleServiceId(saleId);
		ordRefundment.setOperatorName(operatorName);
		ordRefundment.setAmount(amount);
		ordRefundment.setMemo(memo);
		ordRefundment.setCreateTime(new Date());
		ordRefundment.setRefundType(REFUNDMENT_TYPE.ORDER_REFUNDED.name());
		ordRefundment.setApproveTime(new Date());
		ordRefundment.setStatus(Constant.REFUNDMENT_STATUS.VERIFIED.name());
		
		refundmentId = orderRefundmentDAO.saveOrdRefundment(ordRefundment);
		OrdRefundMentItem item = new OrdRefundMentItem();
		item.setOrderItemMetaId(orderItemMetaId);
		item.setRefundmentId(refundmentId);
		item.setMemo(memo);
		item.setType(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name());
		item.setAmount(amount);
		item.setActualLoss(0L);
		orderRefundmentDAO.saveOrdRefunementItem(item);
		return refundmentId;
	}

	/**
	 * setOrdRefundmentDAO.
	 *
	 * @param orderRefundmentDAO
	 *            ordRefundmentDAO
	 */
	public void setOrderRefundmentDAO(final OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}
	
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrdSaleServiceDao(OrdSaleServiceDAO ordSaleServiceDao) {
		this.ordSaleServiceDao = ordSaleServiceDao;
	}



	@Override
	public boolean updateOrdRefundment(OrdRefundment ordRefundment) {
		return orderRefundmentDAO.updateOrdRefundment(ordRefundment);
	}



	public void setOpTravelGroupDAO(OpTravelGroupDAO opTravelGroupDAO) {
		this.opTravelGroupDAO = opTravelGroupDAO;
	}



	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}



	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}



	public void setGroupBudgetDAO(GroupBudgetDAO groupBudgetDAO) {
		this.groupBudgetDAO = groupBudgetDAO;
	}

}
