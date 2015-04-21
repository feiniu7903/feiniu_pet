package com.lvmama.order.logic;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderAmountItemDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.service.OrderItemMetaSaleAmountServie;

public class TransactionLogic {

	private Logger log = Logger.getLogger(this.getClass());

	private OrderDAO orderDAO;
	
	/**
	 * 订单金额明细DAO.
	 */
	private OrderAmountItemDAO orderAmountItemDAO;
	
	protected TopicMessageProducer orderMessageProducer;

	protected PayPaymentService payPaymentService;
	
	protected OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie;

	public OrdOrder newPaymentSuccess(PayPayment payPayment) {
		//支付成功处理
		String serial = payPayment.getSerial();
		Long orderId = payPayment.getObjectId();
		OrdOrder ordOrder = orderDAO.selectByPrimaryKeyForUpdate(orderId);
		log.info("SERIAL: " + serial);
		log.info("ORDER_ID: " + orderId);
		log.info("paymentStatus: " + ordOrder.getPaymentStatus());
		log.info("ordOrder orderStatus: " + ordOrder.getOrderStatus());
		
		//记录订单奖金支付金额
		if(Constant.PAYMENT_GATEWAY.CASH_BONUS.name().equals(payPayment.getPaymentGateway())){
			ordOrder.setBonusPaidAmount(payPayment.getAmount());
		}
		
		//如果订单已经支付成功，则只更新订单应付金额，不发消息
		if(ordOrder.isPaymentSucc()) {
			ordOrder.setActualPay(ordOrder.getActualPay() + payPayment.getAmount());
			ordOrder.setPaymentFinishTime(payPayment.getCreateTime());
			orderDAO.updateByPrimaryKey(ordOrder);
		}
		//如果订单未支付成功，则更新实付金额，并且更新状态，发消息
		else if(!ordOrder.isPaymentSucc()) {
			ordOrder.setActualPay(ordOrder.getActualPay() + payPayment.getAmount());
			// 全额支付时修改payment_status
			if (ordOrder.isFullyPayed()) {
				ordOrder.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
				ordOrder.setPaymentFinishTime(payPayment.getCallbackTime());
				if(ordOrder.isNormal()){
					 ordOrder.setOrderViewStatus(Constant.ORDER_VIEW_STATUS.PAYED.name());
				}
			} else {
				ordOrder.setPaymentStatus(Constant.PAYMENT_STATUS.PARTPAY.name());
				if(ordOrder.isNormal()){
					 ordOrder.setOrderViewStatus(Constant.ORDER_VIEW_STATUS.PARTPAY.name());
				}
			}
			
			ordOrder.setPaymentTime(payPayment.getCallbackTime());
			orderDAO.updateByPrimaryKey(ordOrder);
			ordOrder.setUpdateInCurrent(true);
		}
		//进行支付金额拆分,如果支付成功或实付大于应付
		if(ordOrder.isPaymentSucc() || ordOrder.getActualPay()>ordOrder.getOughtPay()){
			orderItemMetaSaleAmountServie.updateOrderItemMetaSaleAmount(orderId); 
		}else{
			orderItemMetaSaleAmountServie.updateOrderItemSaleAmount(orderId);
		}
		return ordOrder;
	}
	
	public OrdOrder newTransferPaymentSuccess(final List<PayPayment> payPayments) {
		//目标订单，即接受支付转移的订单
		OrdOrder targetOrder = null;
		
		if (null == payPayments || payPayments.size() == 0) {
			return null;
		}
		
		targetOrder = orderDAO.selectByPrimaryKey(payPayments.get(0).getObjectId());
		
		if (null == targetOrder) {
			return null;
		}
		
		//原始订单，即转移出支付记录的订单
		OrdOrder orgOrder = null == targetOrder.getOrgId() ? null : orderDAO.selectByPrimaryKey(targetOrder.getOriginalOrderId());
		Long bonusPaidAmount = 0L;
		if (null != orgOrder) {
			orgOrder.setPaymentStatus(Constant.PAYMENT_STATUS.TRANSFERRED.name());
			//orgOrder.setOrderViewStatus(Constant.PAYMENT_STATUS.TRANSFERRED.name());
			orderDAO.updateByPrimaryKey(orgOrder);
			bonusPaidAmount = orgOrder.getBonusPaidAmount();
		}
		
		for (PayPayment payPayment : payPayments) {
			targetOrder.setActualPay(targetOrder.getActualPay() + payPayment.getAmount());
		}
		if(bonusPaidAmount>targetOrder.getActualPay()) {
			bonusPaidAmount=targetOrder.getActualPay();
		}
		targetOrder.setBonusPaidAmount(bonusPaidAmount);

		// 全额支付时修改payment_status
		if (targetOrder.isFullyPayed()) {
			targetOrder.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
			targetOrder.setOrderViewStatus(Constant.PAYMENT_STATUS.PAYED.name());
			targetOrder.setPaymentFinishTime(new Date());
		} else {
			targetOrder.setPaymentStatus(Constant.PAYMENT_STATUS.PARTPAY.name());
			targetOrder.setOrderViewStatus(Constant.PAYMENT_STATUS.PARTPAY.name());
		}
		targetOrder.setPaymentTime(new Date());
		orderDAO.updateByPrimaryKey(targetOrder);
		return targetOrder;
	}	

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setOrderAmountItemDAO(OrderAmountItemDAO orderAmountItemDAO) {
		this.orderAmountItemDAO = orderAmountItemDAO;
	}

	public void setOrderItemMetaSaleAmountServie(
			OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie) {
		this.orderItemMetaSaleAmountServie = orderItemMetaSaleAmountServie;
	}
	
	

}
