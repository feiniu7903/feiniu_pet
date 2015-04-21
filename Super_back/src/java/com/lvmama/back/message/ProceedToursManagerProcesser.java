package com.lvmama.back.message;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 旅行团监听器
 * @author Brian
 * 修改了之前的代码，现在的消息分不成的支付状态来更新团当中的信息
 * @author yangbin
 */
public class ProceedToursManagerProcesser implements MessageProcesser {

	/**
	 * 日志对象
	 */
	private static final Log LOG = LogFactory
			.getLog(ProceedToursManagerProcesser.class);

	/**
	 * 订单访问接口
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 支付记录的SERVICE.
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 团接口
	 */
	private IOpTravelGroupService opTravelGroupService;

	/**
	 * 消息接口
	 */
	private ComMessageService comMessageService;


	/**
	 * 消息处理.
	 * @param message
	 * @author yuzhibing
	 */
	@Override
	public void process(final Message message) {
		if(hasJMSMessageType(message)){
			OrdOrder order = getOrder(message.getObjectId());
			if(order==null){
				LOG.warn("处理的订单不存在:"+message.getObjectId());
				return;
			}

			if(!hasTravelGroupOrder(order)){//不是需要处理的订单直接退出
				return;
			}
			boolean LOG_ANBLE=LOG.isInfoEnabled();
			if(LOG_ANBLE){
				LOG.info("处理订单是否需要有团相关操作");
			}
			if (message.isOrderCreateMsg()) {
				opTravelGroupService.updateGroupPayNot(order);
				if(LOG_ANBLE){
					LOG.info("********创建订单******1");
				}
			} else if (message.isOrderPartpayPayment()) {
				//为第一次支付成功的消息就操作该地方人数
				Long countPaySuccess = payPaymentService.selectPaymentSuccessCount(order.getOrderId());
				if (countPaySuccess == 1) {
					opTravelGroupService.updateGroupPayPart(order);
				}
				if(LOG_ANBLE){
					LOG.info("********部分支付******2");
				}
			} else if (message.isOrderPaymentMsg()) {
				updateGroupPaySucc(message, order);
				if(LOG_ANBLE){
					LOG.info("********全额支付******3");
				}
				if(isSendTakenOrder(order)){
					String content=ORDER_PAYMENT_SUCCESS_MSG_TEMPLATE
							.replace("TRAVEL_GROUP", order.getTravelGroupCode())
							.replace("ORDER", String.valueOf(order.getOrderId()));
					sendMsgToToken(order, content);
				}
			} else if (message.isOrderCancelMsg()) {
				try{
					opTravelGroupService.rollbackGroupNum(order);
				}catch(Exception ex){
					LOG.warn("团订单取消时人数退回操作失败",ex);
				}
				//发处理人发消息
				if(isSendTakenOrder(order)){
					//废单原因当中如果是对应的CANCEL_TO_CREATE_NEW就替换一下，因为有地方需要使用该英文字符，而那地方不好修改
					String content = ORDER_CANCEL_MSG_TEMPLATE.replace("TRAVEL_GROUP",
							order.getTravelGroupCode())
							.replace("ORDER", String.valueOf(order.getOrderId())).replace("废单原因", StringUtils.equals(Constant.ORD_CANCEL_REASON.CANCEL_TO_CREATE_NEW.name(), order.getCancelReason())?"废单重下":order.getCancelReason());
					sendMsgToToken(order,content);
				}
				if(LOG_ANBLE){
					LOG.info("********取消订单******4");
				}
			}
		}
	}

	private void updateGroupPaySucc(final Message message, OrdOrder order) {
		long count = this.opTravelGroupService.getOrderQuantity(order);
		OpTravelGroup group = this.opTravelGroupService.getOptravelGroup(order.getTravelGroupCode());
		String oldPayStatus = message.getAddition();
		if (count > 0L && group != null) {
			if (StringUtils.isEmpty(oldPayStatus)) {
				long payCount = payPaymentService.selectPaymentSuccessCount(order.getOrderId());
				// 为全额支付
				if (payCount == 1L || (payCount > 1L && !order.isOpenPartPay())) {
					this.opTravelGroupService.updatePayNot2Success(group.getTravelGroupId(), count);
				} else if (payCount > 1L) {
					this.opTravelGroupService.updatePayPart2Success(group.getTravelGroupId(), count);
				}
			} else {
				if (StringUtils.equalsIgnoreCase(oldPayStatus, Constant.PAYMENT_STATUS.UNPAY.name())) {
					this.opTravelGroupService.updatePayNot2Success(group.getTravelGroupId(), count);
				} else if (StringUtils.equalsIgnoreCase(oldPayStatus, Constant.PAYMENT_STATUS.PARTPAY.name())) {
					this.opTravelGroupService.updatePayPart2Success(group.getTravelGroupId(), count);
				}
			}
		}
	}

	/**
	 * 判断订单类型
	 * @param ordOrder
	 * @return true/false
	 * @author yuzhibing
	 */
	private boolean hasTravelGroupOrder(final OrdOrder ordOrder) {
		return RouteUtil.hasTravelGroupProduct(ordOrder.getOrderType())&&StringUtils.isNotEmpty(ordOrder.getTravelGroupCode());
	}
	
	/**
	 * 判断需要处理的消息类型.
	 * @param message
	 * @return
	 */
	private boolean hasJMSMessageType(Message message){
		return (message.isOrderCreateMsg() || message.isOrderPartpayPayment()
				|| message.isOrderPaymentMsg() || message.isOrderCancelMsg());
	}
	
	/**
	 * 发送弹出消息模版
	 */
	private static final String ORDER_CANCEL_MSG_TEMPLATE="订单ORDER已经废单,原因[废单原因],团号[TRAVEL_GROUP]";
	private static final String ORDER_PAYMENT_SUCCESS_MSG_TEMPLATE="订单ORDER已经付款成功,团号[TRAVEL_GROUP]";
	/**
	 * 向订单处理人发送订单消息.
	 * @param order 订单
	 * @param msg 消息内容
	 */
	private void sendMsgToToken(final OrdOrder order,final String msg){		
		Set<String> operators=new HashSet<String>();
		//发订单处理人发消息		
		if(Constant.AUDIT_TAKEN_STATUS.TAKEN.name().equals(order.getTaken())){
			operators.add(order.getTakenOperator());
		}
		
		//向资源审核人发消息
		for(OrdOrderItemMeta meta:order.getAllOrdOrderItemMetas()){
			if(Constant.AUDIT_TAKEN_STATUS.TAKEN.name().equals(meta.getTaken())){
				ComAudit comAudit=getMetaAudit(meta);
				if(comAudit==null){//如果不存在审核人就跳过
					comAudit=getMetaAudit(meta);
					continue;
				}
				if(operators.contains(comAudit.getOperatorName()))
					continue;
				operators.add(comAudit.getOperatorName());
			}
		}
		for(String operator:operators){
			insertMsg(operator, msg);
		}
	}

	/**
	 * 最审核信息，首选取已经审核通过的，如果不存在再取未审核的状态的记录.
	 * @param meta
	 * @return
	 */
	private ComAudit getMetaAudit(OrdOrderItemMeta meta){
		Map<String,String> map=new HashMap<String, String>();
		map.put("objectId", meta.getOrderItemMetaId().toString());
		map.put("objectType", Constant.AUDIT_OBJECT_TYPE.ORD_ORDER_ITEM_META.name());
		map.put("auditStatus",Constant.AUDIT_STATUS.AUDIT_COMPLETED.name());
		List<ComAudit> list=orderServiceProxy.selectComAuditList(map);
		if(CollectionUtils.isEmpty(list)){
			map.put("auditStatus", Constant.AUDIT_STATUS.AUDIT_GOING.name());
			list=orderServiceProxy.selectComAuditList(map);
			if(CollectionUtils.isEmpty(list)){
				return null;
			}
		}
		return list.get(0);
	}
	
	
	/**
	 * 写入消息
	 * @param receiver
	 * @param content
	 * @param date
	 */
	private void insertMsg(String receiver,String content){
		ComMessage msg=new ComMessage();
		msg.setCreateTime(new Date());
		
		msg.setContent(content);
		msg.setReceiver(receiver);
		msg.setSender(Constant.SYSTEM_USER);
		msg.setStatus("CREATE");
		
		try{
			comMessageService.insertComMessage(msg);
		}catch(Exception ex){
			LOG.warn("消息写入时异常",ex);
		}
	}

	/**
	 * 判断订单是否需要给操作人发送消息.
	 * @param order
	 * @return
	 */
	private boolean isSendTakenOrder(final OrdOrder order){
		return (StringUtils.isNotEmpty(order.getTravelGroupCode()));
	}

	/**
	 * 获取订单
	 * @param orderId 订单号
	 * @return 订单实体
	 */
	private OrdOrder getOrder(final Long orderId) {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		return order;
	}

	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * @param comMessageService the comMessageService to set
	 */
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
	
}
