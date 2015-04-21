/**
 * 
 */
package com.lvmama.back.message;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;

/**
 * 团状态变更后的消息接收处理.
 * 现在系统当中只对变更为取消的时候向团下订单处理人发消息 
 * @author yangbin
 *
 */
public class TravelGroupStatusMessageProcesser implements MessageProcesser {

	/**
	 * 订单服务
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 团服务
	 */
	private IOpTravelGroupService opTravelGroupService;
	
	/**
	 * 系统消息服务
	 */
	private ComMessageService comMessageService;
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.jms.MessageProcesser#process(com.lvmama.comm.jms.Message)
	 */
	@Override
	public void process(Message message) {
		if(message.isTravelGroupStatus()){
			if(LOG.isDebugEnabled()){
				LOG.debug("团状态更改消息");
			}
			String addition=message.getAddition();
			if(StringUtils.isNotEmpty(addition)){
				OpTravelGroup group=opTravelGroupService.getOpTravelGroup(message.getObjectId());
				if(group==null)
					return;
				if(addition.equalsIgnoreCase("CANCEL")){
					if(LOG.isDebugEnabled()){
						LOG.debug("取消发团状态");
					}
					handleCancelMessage(group);
				}
			}
		}else if(message.isTravelGroupWordAbled()){
			OpTravelGroup group=opTravelGroupService.getOpTravelGroup(message.getObjectId());
			if(group==null)
				return;
			
			handleTravelGroupWordAble(group);
		}
	}
	
	/**
	 * 按团号读取团的订单列表
	 * @param travelGroupCode
	 * @return
	 */
	private List<OrdOrder> getOrderList(String travelGroupCode){
		CompositeQuery compositeQuery=new CompositeQuery();
		OrderStatus orderStatusForQuery=new OrderStatus();
		//订单状态为正常
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		//订单资源已审核
		orderStatusForQuery.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		//订单已支付
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatusForQuery);
		
		compositeQuery.getContent().setTravelCode(travelGroupCode);		
		compositeQuery.getQueryFlag().setQuerySupplier(false);
		compositeQuery.getQueryFlag().setQueryUser(false);
		return orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
	}
	
	
	private void handleTravelGroupWordAble(OpTravelGroup group){
		List<OrdOrder> list=getOrderList(group.getTravelGroupCode());
		if(CollectionUtils.isNotEmpty(list)){
			if(LOG.isDebugEnabled()){
				LOG.debug("本次发出团通知书需要处理的订单数:"+list.size());
			}
			String content="订单号【ORDER_ID】对应的团通知可发出团通知书";
			Date date=new Date();
			for(OrdOrder order:list){	
				if(StringUtils.isEmpty(order.getTakenOperator())){
					continue;
				}
				ComMessage msg=new ComMessage();
				msg.setCreateTime(date);
				msg.setReceiver(order.getTakenOperator());
				msg.setStatus("CREATE");
				msg.setContent(content.replace("ORDER_ID", String.valueOf(order.getOrderId())));
				msg.setSender(Constant.SYSTEM_USER);
				try{
					comMessageService.insertComMessage(msg);
				}catch(Exception ex){
					LOG.warn("发送可发出团通知书系统消息失败，订单号是:"+order.getOrderId(),ex);
				}
			}
		}
	}

	/**
	 * 处理取消发团操作. 
	 * @param group 团
	 */
	private void handleCancelMessage(OpTravelGroup group){
		List<OrdOrder> list=getOrderList(group.getTravelGroupCode());
		if(CollectionUtils.isNotEmpty(list)){
			if(LOG.isDebugEnabled()){
				LOG.debug("本次取消的团需要处理的订单数:"+list.size());
			}
			String content="订单号【ORDER_ID】对应的团已经改为取消发团";
			Date date=new Date();
			for(OrdOrder order:list){	
				if(StringUtils.isEmpty(order.getTakenOperator())){
					continue;
				}
				ComMessage msg=new ComMessage();
				msg.setCreateTime(date);
				msg.setReceiver(order.getTakenOperator());
				msg.setStatus("CREATE");
				msg.setContent(content.replace("ORDER_ID", String.valueOf(order.getOrderId())));
				msg.setSender(Constant.SYSTEM_USER);
				try{
					comMessageService.insertComMessage(msg);
				}catch(Exception ex){
					LOG.warn("发送取消发团系统消息失败，订单号是:"+order.getOrderId(),ex);
				}
			}
		}
	}
	
	
	
	
	/**
	 * @param orderServiceProxy the orderServiceProxy to set
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * @param opTravelGroupService the opTravelGroupService to set
	 */
	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}

	/**
	 * @param comMessageService the comMessageService to set
	 */
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}




	/**
	 * 日志
	 */
	private static final Log LOG=LogFactory.getLog(TravelGroupStatusMessageProcesser.class);
}
