package com.lvmama.order.trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ord.ModifySettlementPriceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EVENT_TYPE;
import com.lvmama.comm.vo.Constant.OBJECT_TYPE;
import com.lvmama.comm.vo.Constant.ORDER_STATUS;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_REASON;
import com.lvmama.comm.vo.Constant.REFUNDMENT_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.op.dao.OpTravelGroupDAO;
import com.lvmama.order.dao.OrdOrderAmountApplyDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.dao.OrderRefundmentDAO;
import com.lvmama.order.dao.QueryDAO;
import com.lvmama.order.service.OrderUpdateService;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;

/**
 * 订单结算相关的Message处理
 * 
 * @author yanggan
 * 
 */
public class OrderSettleProcesser implements MessageProcesser {

	protected transient final Log logger = LogFactory.getLog(getClass());
	private OrderDAO orderDAO;

	private OrderItemMetaDAO orderItemMetaDAO;

	private OrderItemProdDAO orderItemProdDAO;

	private ProdProductDAO prodProductDAO;

	private OrderPersonDAO orderPersonDAO;

	private OrderUpdateService orderUpdateService;

	private SetSettlementItemService setSettlementItemService;

	private SettlementTargetService settlementTargetService;

	private OrderRefundmentDAO orderRefundmentDAO;

	private MetaProductBranchDAO metaProductBranchDAO;

	private ProdProductBranchDAO prodProductBranchDAO;

	private PassCodeDAO passCodeDAO;

	private MetaProductDAO metaProductDAO;

	private transient QueryDAO queryDAO;
	
	private OpTravelGroupDAO opTravelGroupDAO;
	/**
	 * 订单修改DAO.
	 */
	private OrdOrderAmountApplyDAO amountApplyDAO;
	
	private ModifySettlementPriceService modifySettlementPriceService;
	
	private OrderService orderServiceProxy;
	
	private MetaTimePriceDAO metaTimePriceDAO;
	
	private ComLogDAO comLogDAO;
	@Override
	public void process(Message message) {
		logger.info("OrderSettleProcesser revice message: " + message.toString());
		Map<Long,Long> countSettlePriceMap = new HashMap<Long,Long>();
		//如果是修复数据消息，解析些消息得到真实的消息数据
		if(message.isOrderSettleRepair()){
			message=parseMessage(message);
			if(null==message){
				logger.info("order settle repair out error,message is not parse!");
				return;
			}
		}
		if (message.isOrderSettle()) {// 订单结算的消息
			orderSettle(message);
		}else if(message.isOrderModifySettlementPrice()){//订单修改结算价 或 修改结算总价
			
			this.modifySettlementPrice(message,countSettlePriceMap);
			
		}  else if (message.isOrderApproveMsg() || message.isOrderPaymentMsg() || message.isOrderRetoreMsg() || message.isOrderCancelMsg()) {
			List<SetSettlementItem> ssItems = null;
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			//非不定期订单才进结算
			if(!order.IsAperiodic()) {
				boolean statusPass = false;
				if (message.isOrderCancelMsg()) {
					statusPass = true;
				} else {
					statusPass = ORDER_STATUS.NORMAL.name().equalsIgnoreCase(order.getOrderStatus()) ? true : ORDER_STATUS.FINISHED.name().equalsIgnoreCase(order.getOrderStatus());
				}
				if (order.isApprovePass() && order.isFullyPayed() && statusPass && !"true".equals(order.getTestOrderFlag())) {// 资源审核通过且全额支付且状态正常或完成(取消的消息不需要判断)
					ssItems = initItem(order,countSettlePriceMap,null);
				} else {
					logger.info("message type:" + message.getEventType() + "order:" + order.getOrderId() + " approve status:" + order.isApprovePass() + " fullpayed status:" + order.isFullyPayed() + " order status:" + order.getOrderStatus() + ", don't need to settlement!");
				}
				if (ssItems != null && ssItems.size() > 0) {
					this.setSettlementItemService.insertOrUpdateSettlementItem(ssItems, EVENT_TYPE.valueOf(message.getEventType()));
				}
			}
		} else if (message.isOrderRefundedMsg()) {// 订单退款消息
			Long refundmentId = message.getObjectId();
			List<SetSettlementItem> ssItems = new ArrayList<SetSettlementItem>();
			OrdRefundment refundment = this.orderRefundmentDAO.queryOrdRefundmentById(refundmentId);
			OrdOrder order = orderDAO.selectByPrimaryKey(refundment.getOrderId());
			if (order.isApprovePass() && order.isFullyPayed() && !"true".equals(order.getTestOrderFlag())) {// 资源审核通过且全额支付
				ssItems = initItem(refundmentId, order,countSettlePriceMap);
				if (ssItems != null && ssItems.size() > 0) {
					//非不定期
					if(!order.IsAperiodic()) {
						this.setSettlementItemService.insertOrUpdateSettlementItem(ssItems, EVENT_TYPE.valueOf(message.getEventType()));
					}
					for(SetSettlementItem item :ssItems){
						// 修改订单子子项的结算价
						this.modifySettlementPriceService.updateSettlementPrice(item.getOrderItemMetaId(), ORD_SETTLEMENT_PRICE_CHANGE_TYPE.TOTAL_PRICE, ORD_SETTLEMENT_PRICE_REASON.REFUND_SUCCESS, "退款成功修改结算总价", item.getTotalSettlementPrice(), "SYSTEM",true);
					}
				}
			} else {
				logger.info("message type:" + message.getEventType() + "order:" + order.getOrderId() + " approve status:" + order.isApprovePass() + " fullpayed status:" + order.isFullyPayed() + " order status:" + order.getOrderStatus() + ", don't need to settlement!");
			}
		} else if (message.isPasscodeApplySuccessMsg()) {// 订单申码成功的消息
			PassCode passCode = passCodeDAO.getPassCodeByCodeId(message.getObjectId());
			OrdOrder order = orderDAO.selectByPrimaryKey(passCode.getOrderId());
			//非不定期
			if(!order.IsAperiodic() && !"true".equals(order.getTestOrderFlag())) {
				if (order.isPaymentSucc() && order.isPassportOrder() && order.isPayToLvmama() && order.isApprovePass()) {
					List<SetSettlementItem> ssItems = null;
					if(this.setSettlementItemService.searchSettlementItemByOrderId(order.getOrderId()).size() == 0 ){//订单号
						ssItems = this.initItem(order,countSettlePriceMap,null);
					};
					
					if (OBJECT_TYPE.ORD_ORDER.name().equals(passCode.getObjectType())) {// 订单申码
						Long orderId = passCode.getObjectId();
						if( ssItems != null ){
							for (SetSettlementItem item : ssItems) {
								item.setPassCode(passCode.getCode());
								item.setPassSerialno(passCode.getSerialNo());
								item.setPassExtid(passCode.getExtId());
							}
						}else{
							ssItems = new ArrayList<SetSettlementItem>();
							List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(orderId);
							for (OrdOrderItemMeta itemMeta : metas) {
								if(initItemValidate(order,itemMeta)){
									SetSettlementItem item = new SetSettlementItem();
									item.setOrderItemMetaId(itemMeta.getOrderItemMetaId());
									item.setPassCode(passCode.getCode());
									item.setPassSerialno(passCode.getSerialNo());
									item.setPassExtid(passCode.getExtId());
									ssItems.add(item);
								}
							}
						}
					} else if (OBJECT_TYPE.ORD_ORDER_ITEM_META.name().equals(passCode.getObjectType())) {//订单子子项申码
						Long ordOrderItemMetaId = passCode.getObjectId();
						if( ssItems != null ){
							for (SetSettlementItem item : ssItems) {
								if(item.getOrderItemMetaId() == ordOrderItemMetaId.longValue()){
									item.setPassCode(passCode.getCode());
									item.setPassSerialno(passCode.getSerialNo());
									item.setPassExtid(passCode.getExtId());
								}
							}
						}else{
							ssItems = new ArrayList<SetSettlementItem>();
							OrdOrderItemMeta itemMeta = orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);
							if(initItemValidate(order,itemMeta)){
								SetSettlementItem item = new SetSettlementItem();
								item.setOrderItemMetaId(ordOrderItemMetaId);
								item.setPassCode(passCode.getCode());
								item.setPassSerialno(passCode.getSerialNo());
								item.setPassExtid(passCode.getExtId());
								ssItems.add(item);
							}
						}
					}
					if (ssItems != null && ssItems.size() > 0) {
						this.setSettlementItemService.insertOrUpdateSettlementItem(ssItems, EVENT_TYPE.valueOf(message.getEventType()));
					}
				} else {
					logger.info("message type:" + message.getEventType() + "order:" + order.getOrderId() + " isPayToLvmama:" + order.isPayToLvmama() + " fullpayed status:" + order.isFullyPayed() + " order status:" + order.getOrderStatus() + ", don't need to settlement!");
				}
			}
		}else if(message.isOrderItemMetaSettle()){
			Long orderItemMetaId = message.getObjectId();
			String operater = message.getAddition();
			List<SetSettlementItem> ssItems = new ArrayList<SetSettlementItem>();
			boolean statusPass = false;
			OrdOrderItemMeta ooim = orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
			OrdOrder order = orderDAO.selectByPrimaryKey(ooim.getOrderId());
			//非不定期
			if(!order.IsAperiodic()) {
				statusPass = ORDER_STATUS.NORMAL.name().equalsIgnoreCase(order.getOrderStatus())|| ORDER_STATUS.FINISHED.name().equalsIgnoreCase(order.getOrderStatus()) || ORDER_STATUS.CANCEL.name().equalsIgnoreCase(order.getOrderStatus());
				if (order.isApprovePass() && order.isFullyPayed() && statusPass) {// 资源审核通过且全额支付且状态正常或完成(取消的消息不需要判断)
					SetSettlementItem item = initItem(orderItemMetaId,
							countSettlePriceMap);
					if (null != item) {
						ssItems.add(item);
						setSettlementItemService
								.insertOrUpdateSettlementItem(ssItems,
										EVENT_TYPE.valueOf(message.getEventType()));
						insertLog(orderItemMetaId, "ORD_SETTLE_VILID_ERROR",
								item.getOrderId(), "SETTLE_ERROR", operater,
								"手动保存结算子项", "ERROR", "手动保存结算子项成功");
					}
				}else{
					insertLog(orderItemMetaId, "ORD_SETTLE_VILID_ERROR",
							order.getOrderId(), "SETTLE_ERROR", operater,
							"验证生成结算子项", "ERROR", "不能生成结算子项  订单状态："+order.getOrderStatus()+" 是否审核通过："+order.isApprovePass()+" 是否全额支付:"+order.isFullyPayed());
				}
			}
		}
		updateItemSettleCountAmount(countSettlePriceMap);
	}
	
	private boolean initItemValidate(OrdOrder order,OrdOrderItemMeta ooim){
		boolean groupPass = false;
		String errorMsg = null;
		String logName = "订单子子项结算数据验证";
		if(StringUtil.isEmptyString(order.getTravelGroupCode())||order.getIsShHolidayOrder() || order.getIsJinjiangOrder()){//团号为空时或者第三方供应商订单，结算类型为订单结算
			groupPass = true;
		}else{
			OpTravelGroup group = opTravelGroupDAO.selectByGroupCode(order.getTravelGroupCode());
			
			
			//如果团号不在重新生成一次
			if(null==group){
				order=orderServiceProxy.queryOrdOrderByOrderId(order.getOrderId());
				logger.info("make travel group code again,order id:"+order.getOrderId()+",travel group code:"+order.getTravelGroupCode());
				ProdProduct prodProduct = prodProductDAO.selectProductDetailByPrimaryKey(order.getMainProduct().getProductId());
				if(RouteUtil.hasTravelGroupProduct(prodProduct)){
					try {
						group=new OpTravelGroup();
						group.setTravelGroupCode(RouteUtil.makeTravelGroupCode(prodProduct, order.getVisitTime()));
						group.setProductId(prodProduct.getProductId());
						group.setProductName(prodProduct.getProductName());
						group.setVisitTime(order.getVisitTime());
						group.setOrgId(prodProduct.getOrgId());
						group.setSettlementPrice(ooim.getSettlementPrice());
						group.setTravelGroupStatus(Constant.TRAVEL_GROUP_STATUS.NORMAL.name());
						
						ProdRoute prodRoute = (ProdRoute) prodProduct;
						group.setInitialNum(prodRoute.getInitialNum()==null?0L:prodRoute.getInitialNum());
						group.setDays(prodRoute.getDays());
						
						OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ooim.getOrderItemId());
						group.setSellPrice(itemProd.getPrice());
						
						ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryProdBranchId(itemProd.getProdBranchId());
						TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(ooim.getMetaBranchId(), order.getVisitTime());
						if(timePrice.getDayStock()<0){
							group.setInitialGroupNum(-1);
						}else{
							group.setInitialGroupNum((prodProductBranch.getAdultQuantity()+prodProductBranch.getChildQuantity())*timePrice.getDayStock());					
						}
						opTravelGroupDAO.insert(group);
					} catch (Exception e) {
						logger.error("重新生成团号失败",e);
					}
				}
			}
			
			
			
			if(group == null){
				errorMsg = "不能生成结算子项 团号 "+order.getTravelGroupCode()+" 不能找到团信息";
				insertLog(ooim.getOrderItemMetaId(), "ORD_SETTLE_VILID_ERROR", ooim.getOrderId(), "SETTLE_ERROR", "SYSTEM", logName, "ERROR", errorMsg);
				logger.error("orderId:"+order.getOrderId()+" travelGroupCode:"+order.getTravelGroupCode()+" OP_TRAVEL_GROUP's data doesn't exist ");
				return false;
			}
			ProdRoute prodRoute = prodProductDAO.getProdRouteById(group.getProductId());
			if(Constant.GROUP_TYPE.AGENCY.name().equals(prodRoute.getGroupType())){
				groupPass = true;
			}
		}
		//虚拟库存不能进入结算系统，其它为0的，负数都可以进入结算系统
		if(Constant.TRUE_FALSE.TRUE.getCode().equalsIgnoreCase(ooim.getVirtual())){
			errorMsg = "不能生成结算子项  订单子子项的结算单价 虚拟库存";
		}else if(!groupPass && !StringUtil.isEmptyString(order.getTravelGroupCode()) && !SUB_PRODUCT_TYPE.INSURANCE.name().equals( ooim.getSubProductType())){
			errorMsg = "不能生成结算子项 团号:"+order.getTravelGroupCode()+(groupPass?"是":"不是")+"委托组团"+" 产品类型:"+ooim.getSubProductType();
		}
		if(null!=errorMsg){
			insertLog(ooim.getOrderItemMetaId(), "ORD_SETTLE_VILID_ERROR", ooim.getOrderId(), "SETTLE_ERROR", "SYSTEM", logName, "ERROR", errorMsg);
		}
		return  Constant.TRUE_FALSE.FALSE.getCode().equalsIgnoreCase(ooim.getVirtual()) && 
				(groupPass || StringUtil.isEmptyString(order.getTravelGroupCode()) || SUB_PRODUCT_TYPE.INSURANCE.name().equals( ooim.getSubProductType()));
	}
	private List<SetSettlementItem> initItem(Long refundmentId,OrdOrder order,Map countSettlePriceMap) {
		String refundMemo = null;
		if (order.getRefundedAmount() != null && order.getRefundedAmount() > 0) {// 存在退款，查询退款明细
			StringBuffer refundMemoBuff = new StringBuffer();
			List<OrdRefundment> refundmentList = orderRefundmentDAO.findOrderRefundmentByOrderIdStatus(order.getOrderId(), REFUNDMENT_STATUS.REFUNDED.name());
			for (OrdRefundment or : refundmentList) {
				refundMemoBuff.append("[").append(or.getMemo()).append("] ");
			}
			refundMemo = refundMemoBuff.toString();
		}
		List<OrdRefundMentItem> oriList = orderRefundmentDAO.queryOrdRefundmentItemById(refundmentId);
		List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(order.getOrderId());
		Map<String, Long> new_settlement_map = new HashMap<String, Long>();
		Map<String,Long> new_unit_settlement_map = new HashMap<String,Long>();
		Long countSettleAmount = 0L;
		for (OrdRefundMentItem ori : oriList) {
			OrdOrderItemMeta ooim = null;
			for(OrdOrderItemMeta meta:metas){
				if(meta.getOrderItemMetaId().longValue()==ori.getOrderItemMetaId().longValue()){
					ooim = meta;
				}
				if(initItemValidate(order,meta)){
					countSettleAmount +=meta.getTotalSettlementPrice();
				}
			}
			countSettlePriceMap.put(order.getOrderId(), countSettleAmount);
			//OrdOrderItemMeta ooim = orderItemMetaDAO.selectByPrimaryKey(ori.getOrderItemMetaId());
			//结算价不为0（虚拟库存的结算价为0），团号为空或者是保险,或者游客损失大于0
			if( initItemValidate(order,ooim) && (!(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.getCode().equals(ori.getType())&&ori.getAmount()<=0 && Constant.ORDER_STATUS.CANCEL.name().equalsIgnoreCase(order.getOrderStatus())))){ 
				String key = ori.getOrderItemMetaId().toString();
				Long new_settlementPrice = null;
				if (new_settlement_map.get(key) == null) {
					Long settlementPrice = ooim.getProductQuantity() * ooim.getQuantity() * ooim.getActualSettlementPrice();
					if (Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.getCode().equals(ori.getType())) {// 退款明细类型为游客损失
						new_settlementPrice = ori.getActualLoss();
					} else {
						new_settlementPrice = settlementPrice - ori.getAmount();
					}
				} else {
					Long settlementPrice = new_settlement_map.get(key);
					if (Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.getCode().equals(ori.getType())) {// 退款明细类型为游客损失
						new_settlementPrice = ori.getActualLoss();
					} else {
						new_settlementPrice = settlementPrice - ori.getAmount();
					}
				}
				new_settlement_map.put(key, new_settlementPrice);
				new_unit_settlement_map.put(key, Math.round(Double.longBitsToDouble(new_settlementPrice)/Double.longBitsToDouble((ooim.getProductQuantity() * ooim.getQuantity()))));
			}
		}
		List<SetSettlementItem> ssItems = new ArrayList<SetSettlementItem>();
		for (Map.Entry<String, Long> entry : new_settlement_map.entrySet()) {
			Long orderItemMetaId = Long.parseLong(entry.getKey());
			SetSettlementItem item = new SetSettlementItem();
			List<OrdRefundMentItem> refundmentItemList = orderRefundmentDAO.findOrderRefundMentItemByOrderItemMetaId(orderItemMetaId);
			if (refundmentItemList != null && refundmentItemList.size() > 0) {
				item.setOrderRefund(true);
			} else {
				item.setOrderRefund(false);
			}
			if (entry.getValue() != null && entry.getValue() > 0) {
				item.setStatus(SET_SETTLEMENT_ITEM_STATUS.NORMAL.name());
			}
			item.setRefundMemo(refundMemo);
			item.setOrderItemMetaId(orderItemMetaId);
			item.setTotalSettlementPrice(entry.getValue());
			item.setActualSettlementPrice(new_unit_settlement_map.get(entry.getKey()));
			item.setOrderRefund(true);
			Map<String,Object> apply = getOrderAmountApply(order.getOrderId());
			item.setAdjustmentAmount(null!=apply.get("amountApply")?(Long)apply.get("amountApply"):null);
			item.setAdjustmentRemark(null!=apply.get("remark")?(String)apply.get("remark"):null);
			item.setRefundedAmount(order.getRefundedAmount());
			item.setOughtPay(order.getOughtPay());
			item.setUpdateRemark(getUpdateRemark(item.getOrderItemMetaId()));
			item.setCountSettleAmount(countSettleAmount);
			ssItems.add(item);
		}
		return ssItems;
	}

	/**
	 * 获取订单的优惠券金额
	 * 
	 * @param orderId
	 *            订单号
	 * @return 优惠券使用金额
	 */
	private Long getMarkCouponAmount(final Long orderId) {
		long amount = 0l;
		try {
			// 查询订单优惠券使用情况
			List<OrdOrderAmountItem> listAmountItem =queryDAO.queryOrdOrderAmountItem(orderId,"ALL");
			if (null != listAmountItem && !listAmountItem.isEmpty()) {
				for (OrdOrderAmountItem item : listAmountItem) {
					if (item.isCouponItem()) {
						amount += Math.abs(item.getItemAmount());
					}
				}
			}
			amount = 0 - amount;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return amount;
	}
	public Map<String,Object> getOrderAmountApply(final Long orderId){
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("orderId", orderId);
		parameter.put("applyStatus", Constant.ORDER_AMOUNT_MODIFY_STATUS.PASS.name());
		List<OrdOrderAmountApply> orderAmountApplys = amountApplyDAO.selectByOrdOrderAmountApply(parameter);
		Long amountApply=0L;
		String remark = "";
		if(null!=orderAmountApplys && !orderAmountApplys.isEmpty()){
			for(OrdOrderAmountApply apply:orderAmountApplys){
				amountApply +=apply.getAmount();
				remark = apply.getApplyMemo();
			}
			parameter.put("amountApply", amountApply);
			parameter.put("remark", remark);
			return parameter;
		}
		return parameter;
	}
	
	public String getUpdateRemark(final Long orderItemMetaId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderItemMetaId", orderItemMetaId);
		List<OrdOrderItemMetaPrice> queryHistoryRecordList =  modifySettlementPriceService.queryHistoryRecordList(map);
		if(null!=queryHistoryRecordList && !queryHistoryRecordList.isEmpty()){
			for(OrdOrderItemMetaPrice price:queryHistoryRecordList){
				if(!StringUtil.isEmptyString(price.getRemark())){
					return price.getRemark();
				}
			}
		}
		return null;
	}
	private List<SetSettlementItem> initItem(OrdOrder order,Map countSettlePriceMap,final Long ordOrderItemMetaId) {
		Long orderId = order.getOrderId();
		List<SetSettlementItem> items = new ArrayList<SetSettlementItem>();
		List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(orderId);
		
		/*--------------------查询订单联系人--------------------------*/
		OrdPerson person = new OrdPerson();
		person.setObjectId(orderId);
		person.setObjectType(OBJECT_TYPE.ORD_ORDER.name());
		//person.setPersonType(ORD_PERSON_TYPE.CONTACT.name());
		List<OrdPerson> list = orderPersonDAO.getOrdPersons(person);
		if (list.size() == 0) {
			insertLog(orderId, "ORD_SETTLE_VILID_ERROR", orderId, "SETTLE_ERROR", "SYSTEM", "订单无联系人", "ERROR", "订单无联系人");
			throw new RuntimeException("orderId:" + orderId + ", Contact search results is empty!");
		}
		String contactName = list.get(0).getName();
		for(OrdPerson ordPeron:list){
			if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(ordPeron.getPersonType())){
				contactName = ordPeron.getName();
			}
		}
		/*--------------------查询订单联系人--------------------------*/
		
		/*--------------------查询订单的退款备注--------------------------*/
		String refundMemo = null;
		if (order.getRefundedAmount() != null && order.getRefundedAmount() > 0) {// 存在退款，查询退款明细
			StringBuffer refundMemoBuff = new StringBuffer();
			List<OrdRefundment> refundmentList = orderRefundmentDAO.findOrderRefundmentByOrderIdStatus(orderId, REFUNDMENT_STATUS.REFUNDED.name());
			for (OrdRefundment or : refundmentList) {
				refundMemoBuff.append("[").append(or.getMemo()).append("] ");
			}
			refundMemo = refundMemoBuff.toString();
		}
		/*--------------------查询订单的退款备注--------------------------*/
		Long couponAmount = this.getMarkCouponAmount(orderId);
		//订单结算总价
		Long countSettleAmount = 0L;
		if(null!=ordOrderItemMetaId){
			for(int i=0;i<metas.size();i++){
				if(metas.get(i).getOrderItemMetaId().longValue()!=ordOrderItemMetaId.longValue()){
					metas.remove(i);
					i--;
				}
			}
		 }
		for (OrdOrderItemMeta itemMeta : metas) {
			//结算价不为0（虚拟库存的结算价为0），团号为空或者是保险
			if(initItemValidate(order,itemMeta)){
				countSettleAmount +=itemMeta.getTotalSettlementPrice();
				countSettlePriceMap.put(itemMeta.getOrderId(), countSettleAmount);
				Long targetId = settlementTargetService.selectTargetIdByMetaProductId(itemMeta.getMetaProductId());
				if (targetId == null) {
					insertLog(orderId, "ORD_SETTLE_VILID_ERROR", orderId, "SETTLE_ERROR", "SYSTEM", "订单无联系人", "ERROR", "关联产品无结算对象"+itemMeta.getMetaProductId());
					throw new RuntimeException("orderId:" + orderId + ",metaProductId:" + itemMeta.getMetaProductId() + " TargetId search results is null!");
				}
				Long orderItemMetaId = itemMeta.getOrderItemMetaId();
				MetaProduct metaProduct = metaProductDAO.getMetaProduct(itemMeta.getMetaProductId(), itemMeta.getProductType());
				OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(itemMeta.getOrderItemId());
				ProdProduct product = prodProductDAO.selectByPrimaryKey(itemProd.getProductId());
				MetaProductBranch metaProductBranch = metaProductBranchDAO.selectBrachByPrimaryKey(itemMeta.getMetaBranchId());
				ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryProdBranchId(itemProd.getProdBranchId());
	
				SetSettlementItem item = new SetSettlementItem();
				item.setOrderId(orderId);
				item.setOrderStatus(order.getOrderStatus());
				item.setOrderPaymentTime(order.getPaymentTime());
				item.setOrderCreateTime(order.getCreateTime());
				item.setOrderPaymentStatus(order.getPaymentStatus());
				item.setOrderContactPerson(contactName);
				item.setOrderCouponAmount(couponAmount);
				item.setMetaFilialeName(itemMeta.getFilialeName());
				if (ORDER_STATUS.CANCEL.name().equals(order.getOrderStatus())) {
					item.setStatus(SET_SETTLEMENT_ITEM_STATUS.CANCEL.name());
				}
				List<OrdRefundMentItem> refundmentItemList = orderRefundmentDAO.findOrderRefundMentItemByOrderItemMetaId(orderItemMetaId);
				if (refundmentItemList != null && refundmentItemList.size() > 0) {
					item.setOrderRefund(true);
				} else {
					item.setOrderRefund(false);
				}
				item.setRefundMemo(refundMemo);
				item.setOrderItemProdId(itemProd.getOrderItemProdId());
				item.setProductId(itemProd.getProductId());
				item.setProductName(itemProd.getProductName());
				item.setProductType(itemProd.getProductType());
				item.setProductBranchId(itemProd.getProdBranchId());
				item.setProductBranchName(prodProductBranch.getBranchName());
				item.setProductPrice(itemProd.getPrice());
				item.setProductSubProductType(itemProd.getSubProductType());
				item.setFilialeName(product.getFilialeName());
				item.setOrderItemMetaId(orderItemMetaId);
				item.setOrderItemMetaPayedAmount(itemMeta.getPayedAmount());
				item.setMetaProductManager(metaProduct.getManagerName());
				item.setMetaProductId(itemMeta.getMetaProductId());
				item.setMetaProductName(itemMeta.getProductName());
				item.setMetaBranchId(itemMeta.getMetaBranchId());
				item.setMetaBranchName(metaProductBranch.getBranchName());
				item.setSettlementPrice(itemMeta.getSettlementPrice());
				item.setActualSettlementPrice(itemMeta.getActualSettlementPrice());
				item.setSupplierId(itemMeta.getSupplierId());
				item.setTargetId(targetId);
				item.setProductQuantity(itemMeta.getProductQuantity());
				item.setQuantity(itemMeta.getQuantity());
				item.setVisitTime(itemMeta.getVisitTime());
				item.setSubProductType(itemMeta.getSubProductType());
				Long totalSettlementPrice = itemMeta.getTotalSettlementPrice();
				if(totalSettlementPrice == null){
					totalSettlementPrice = itemMeta.getProductQuantity() * item.getQuantity() * item.getActualSettlementPrice();
				}
				item.setTotalSettlementPrice(totalSettlementPrice);
				if (!StringUtil.isEmptyString(order.getTravelGroupCode()) && !SUB_PRODUCT_TYPE.INSURANCE.name().equals( itemMeta.getSubProductType())) {
					item.setSettlementType(SETTLEMENT_TYPE.GROUP.name());
				} else {
					item.setSettlementType(SETTLEMENT_TYPE.ORDER.name());
				}

				Map<String,Object> apply = getOrderAmountApply(order.getOrderId());
				item.setAdjustmentAmount(null!=apply.get("amountApply")?(Long)apply.get("amountApply"):null);
				item.setAdjustmentRemark(null!=apply.get("remark")?(String)apply.get("remark"):null);
				item.setRefundedAmount(order.getRefundedAmount());
				item.setOughtPay(order.getOughtPay());
				item.setUpdateRemark(getUpdateRemark(item.getOrderItemMetaId()));
				item.setCountSettleAmount(countSettleAmount);
				items.add(item);
			}
		}
		return items;
	}

	private SetSettlementItem initItem(final Long orderItemMetaId,final Map countSettlePriceMap){
		OrdOrderItemMeta meta = orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
		OrdOrder order = orderDAO.selectByPrimaryKey(meta.getOrderId());
		List<SetSettlementItem> items = initItem(order,countSettlePriceMap,orderItemMetaId);
		for(SetSettlementItem item:items){
			if(orderItemMetaId.longValue() == item.getOrderItemMetaId().longValue()){
				return item;
			}
		}
		return null;
	}
	/**
	 * 财务结算系统处理完成之后，发送消息 收到消息后更新订单、订单子子项的结算状态
	 * 
	 * @param message
	 */
	private void orderSettle(Message message) {
		// Long objectId = message.getObjectId();
		String addition = message.getAddition();
		String[] strs = addition.split("\\|");
		if (strs.length != 3) {
			logger.error("message format error!!");
			return;
		}
		String[] orderItemMetaIds = strs[0].split(",");
		String status = strs[1];
		String operatorName = strs[2];
		List<Long> list = new ArrayList<Long>();
		for (String id : orderItemMetaIds) {
			if(!StringUtil.isEmptyString(id)){
				list.add(Long.parseLong(id));
			}else{
				logger.error("find an empty itemMetaId...............message:"+message);
			}
		}
		int size = list.size();
		if(size>0){
			int pagesize=500;
			int count = getTotalpages(size,pagesize);
			for(int i=0;i<count;i++){
				int begin = (i*pagesize)>0?((i*pagesize)>size?size:(i*pagesize)):0;
				int end = (begin+pagesize)>size?size:(begin+pagesize);
				List<Long> subList = list.subList(begin, end);
				this.orderUpdateService.updateOrderSettlementStatus(subList, SETTLEMENT_STATUS.valueOf(status), operatorName);
			}
		}
	}

	/**
	 * 财务结算系统处理完成之后，发送消息 收到消息后更新订单、订单子子项的结算状态
	 * 
	 * @param message
	 */
	private void modifySettlementPrice(Message message,Map countSettlePriceMap) {
		// Long objectId = message.getObjectId();
		String addition = message.getAddition();
		String[] strs = addition.split("\\|");
		if (strs.length != 2) {
			logger.error("message format error!!");
			return;
		}
		String[] orderItemMetaIds = strs[0].split(",");
		String operatorName = strs[1];
		List<SetSettlementItem> ssItems = new ArrayList<SetSettlementItem>();
		for (String id : orderItemMetaIds) {
			if(!StringUtil.isEmptyString(id)){
				logger.debug("id:"+id);
				Long ooimId = Long.parseLong(id);
				OrdOrderItemMeta ooim = orderItemMetaDAO.selectByPrimaryKey(ooimId);
				OrdOrder order = orderDAO.selectByPrimaryKey(ooim.getOrderId());
				
				if (order.isPaymentSucc() && order.isPayToLvmama() && order.isApprovePass()) {
					List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(ooim.getOrderId());
					Long countSettleAmount = 0L;
					for(OrdOrderItemMeta meta:metas){
						if(initItemValidate(order,meta)){
							countSettleAmount +=meta.getTotalSettlementPrice();
						}
					}
					SetSettlementItem item = new SetSettlementItem();
					item.setOrderId(ooim.getOrderId());
					item.setOrderItemMetaId(ooimId);
					item.setTotalSettlementPrice(ooim.getTotalSettlementPrice());
					item.setActualSettlementPrice(ooim.getActualSettlementPrice());
					item.setUpdateRemark(getUpdateRemark(ooim.getOrderItemMetaId()));
					item.setCountSettleAmount(countSettleAmount);
					ssItems.add(item);
					countSettlePriceMap.put(ooim.getOrderId(), countSettleAmount);
				}else{
					logger.info("message type:" + message.getEventType() + "order:" + order.getOrderId() + " isPayToLvmama:" + order.isPayToLvmama() + " fullpayed status:" + order.isFullyPayed() + " order status:" + order.getOrderStatus() + ", don't need to settlement!");
				}
			}else{
				logger.error("find an empty itemMetaId...............message:"+message);
			}
		}
		if(ssItems.size()>0){
			this.setSettlementItemService.updateSettlementPrice(ssItems, operatorName, EVENT_TYPE.valueOf(message.getEventType()));			
		}
	}
	
	public void updateItemSettleCountAmount(Map countSettlePriceMap){
		Iterator<Long> iterator = countSettlePriceMap.keySet().iterator();
		while(iterator.hasNext()){
			Long orderId = iterator.next();
			Long countSettleAmount =0L;
			List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(orderId);
			for(OrdOrderItemMeta meta:metas){
				countSettleAmount +=meta.getTotalSettlementPrice();
			}
			SetSettlementItem item =new SetSettlementItem();
			item.setOrderId(orderId);
			item.setCountSettleAmount(countSettleAmount);
			setSettlementItemService.updateSettlementItem(orderId, countSettleAmount);
		}
	}
	public static int getTotalpages(final int totalrecords,final int pagesize) {
		if (totalrecords < 0) {
			return -1;
		}
		int count = totalrecords / pagesize;
		if (totalrecords % pagesize > 0) {
			count++;
		}
		return count;
	}
	/**
	 * 解析消息
	 * @param message
	 * @return
	 */
	public static Message parseMessage(final Message message){
		String addition = message.getAddition();
		if(!StringUtil.isEmptyString(addition)){
			String[] additions = addition.split("=");
			if(additions.length==2){
				message.setEventType(additions[0]);
				message.setAddition(additions[1]);
				return message;
			}
		}
		return null;
		
	}
	private void insertLog(Long objectId, String objectType, Long parentId, String parentType, String operatorName, String logName, String logType, String content) {
		ComLog log = new ComLog();
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setOperatorName(operatorName);
		log.setLogName(logName);
		log.setLogType(logType);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setSetSettlementItemService(SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setOrderRefundmentDAO(OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setSettlementTargetService(SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setPassCodeDAO(PassCodeDAO passCodeDAO) {
		this.passCodeDAO = passCodeDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public void setOpTravelGroupDAO(OpTravelGroupDAO opTravelGroupDAO) {
		this.opTravelGroupDAO = opTravelGroupDAO;
	}
	public void setModifySettlementPriceService(
			ModifySettlementPriceService modifySettlementPriceService) {
		this.modifySettlementPriceService = modifySettlementPriceService;
	}
	public void setAmountApplyDAO(OrdOrderAmountApplyDAO amountApplyDAO) {
		this.amountApplyDAO = amountApplyDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	

}
