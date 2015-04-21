package com.lvmama.back.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.vo.Constant;

/**
 * 业务系统申请码请求
 * 
 * @author chenlinjun
 * 
 */
public class OrderPassportProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(OrderPassportProcesser.class);
	private OrderService orderServiceProxy;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private TopicMessageProducer passportChimelongMessageProducer;
	private PerformTargetService performTargetService;
	private ComLogService comLogService;
	
	public void process(Message message) {
		log.info(message);
		if (message.isOrderPaymentMsg() || message.isOrderApproveMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			log.info("PaymentMsg, orderId:"+message.getObjectId());
			if (order.isPaymentSucc() && order.isPassportOrder() && order.isPayToLvmama() && order.isApprovePass() && !order.isCanceled()) {
				log.info("Payment success, orderId:"+order.getOrderId());
				//订单是否已经做过申请
				if(!passCodeService.hasApply(order.getOrderId())){
					log.info(" orderId:"+order.getOrderId()+",applying code");
					//发送申码请求处理消息
					List<PassCode> list = passCodeService.applyCodeForOrder(order,initSupPerformTarget(order));
					if (list != null && list.size() > 0) { 
						for (PassCode passCode : list) {
							
							List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
							PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
							String providerName = passPortCode!=null? passPortCode.getProviderName():"";
							
							if(providerName.equals("长隆")){
								passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(passCode.getCodeId()));
							}else{
								passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(passCode.getCodeId()));
							}
						}
					} else {
						addComLog(order.getOrderId());
					}
				}
			}
		}
		if (message.isOrderApproveMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			log.info("ApproveMsg, orderId:"+message.getObjectId());
			if (order.isApprovePass() && order.isPassportOrder() && order.isPayToSupplier() && !order.isCanceled()) {
				log.info("Approve pass, orderId: "+order.getOrderId());
				//订单是否已经做过申请
				if(!passCodeService.hasApply(order.getOrderId())){
					log.info(" orderId:"+order.getOrderId()+",applying code");
					List<PassCode> list = passCodeService.applyCodeForOrder(order,initSupPerformTarget(order));
					if (list != null && list.size() > 0) { 
						for (PassCode passCode : list) {
							
							List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
							PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
							String providerName = passPortCode!=null? passPortCode.getProviderName():"";
							
							if(providerName.equals("长隆")){
								passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(passCode.getCodeId()));
							}else{
								passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(passCode.getCodeId()));
							}
							
						}
					} else {
						addComLog(order.getOrderId());
					}
				}
			}
		}
		if (message.isOrderCancelMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if (order.isCanceled() && order.isPassportOrder()) {
				log.info("Order cancel, orderId: "+order.getOrderId());
				List<PassEvent> list = passCodeService.destroyCode( order);
				//发送申码请求处理消息
				for (PassEvent passEvent : list) {
					passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(passEvent.getEventId()));
				}
			}
		}
	}
	
	private Map<Long,List<SupPerformTarget>> initSupPerformTarget(OrdOrder order) {
		List<OrdOrderItemMeta> ordOrderItemMetaList = order.getAllOrdOrderItemMetas();
		Map<Long,List<SupPerformTarget>> supPerformTargetMap = new HashMap<Long, List<SupPerformTarget>>();
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
			List<SupPerformTarget> supPerformTargetList = this.performTargetService.findSuperSupPerformTargetByMetaProductId(ordOrderItemMeta.getMetaProductId());
			supPerformTargetMap.put(ordOrderItemMeta.getMetaProductId(), supPerformTargetList);
		}
		return supPerformTargetMap;
	}
	
	private void addComLog(Long orderId) {
		ComLog log = new ComLog();
		log.setObjectId(orderId);
		log.setObjectType("ORD_ORDER");
		log.setOperatorName("SYSTEM");
		log.setLogName("申请通关码失败");
		log.setLogType("noPassDeviceBound");
		log.setContent("没有绑定通关设备");
		log.setParentType("ORD_ORDER");
		comLogService.addComLog(log);
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setPassportChimelongMessageProducer(TopicMessageProducer passportChimelongMessageProducer) {
		this.passportChimelongMessageProducer = passportChimelongMessageProducer;
	}
}
