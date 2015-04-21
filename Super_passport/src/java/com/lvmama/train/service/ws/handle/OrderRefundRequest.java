/**
 * 
 */
package com.lvmama.train.service.ws.handle;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketRefundNotifyVo;

/**
 * 退款通知接口
 * @author yangbin
 *
 */
public class OrderRefundRequest extends AbstractNotifyRequest{
	private OrderTrafficService orderTrafficService;
	private TopicMessageProducer orderMessageProducer;
	private OrderService orderServiceProxy;

	public OrderRefundRequest() {
		orderTrafficService = SpringBeanProxy.getBean(OrderTrafficService.class, "orderTrafficService");
		orderMessageProducer = SpringBeanProxy.getBean(TopicMessageProducer.class,"orderMessageProducer");
		orderServiceProxy = SpringBeanProxy.getBean(OrderService.class,"orderServiceProxy");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Rsp handle(ReqVo vo) throws RuntimeException{
		if(!(vo instanceof TicketRefundNotifyVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketRefundNotifyVo trnvo = (TicketRefundNotifyVo)vo;
		
		Rsp rsp = check(trnvo);
		if(rsp != null) 
			return rsp;
		
		String supplierOrderId = trnvo.getOrderId();
		
		OrdOrderTrafficRefund refund = new OrdOrderTrafficRefund();
		//精确到分
		refund.setAmount(PriceUtil.convertToFen(trnvo.getTicketFee()));
		refund.setBillNo(trnvo.getRefundId());
		if(trnvo.getTicketCharge() != null && !"".equals(trnvo.getTicketCharge().trim()))
			refund.setTicketCharge(PriceUtil.convertToFen(trnvo.getTicketCharge()));
		if(trnvo.getRefundType() != null && !"".equals(trnvo.getRefundType().trim()))
			refund.setRefundType(Integer.parseInt(trnvo.getRefundType()));
		if(trnvo.getTicketNum() != null && !"".equals(trnvo.getTicketNum().trim()))
			refund.setTicketNum(Integer.parseInt(trnvo.getTicketNum()));
		ResultHandleT<BaseVo> handle=orderTrafficService.addRefundInfo(supplierOrderId,refund);
		if(handle.isFail()){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
						Constant.HTTP_SERVER_ERROR_MSG, 
						handle.getReturnContent());
		}else if(handle.getReturnContent().getRet_code()==Constant.REPLY_CODE.REPEAT.getRetCode()){
			return new Rsp(handle.getReturnContent());
		}
		OrdOrderTraffic traffic = orderTrafficService.getTrafficBySupplierOrderId(supplierOrderId);
//		else{
			//如果退票成功，还需判断是否已经全部退票，如果全部退票，则退款退保
//			Map<String, OrdOrderTrafficTicketInfo> tickets = orderTrafficService.getAllTicketsByOrderId(traffic.getSupplierOrderId());
//			//首先判断是否订单仅一位乘客
//			if(tickets.size() == 1){
//				OrdOrderTrafficTicketInfo ticket = null;
//				Iterator it = tickets.entrySet().iterator();
//				while(it.hasNext()){
//					ticket = ((Map.Entry<String, OrdOrderTrafficTicketInfo>)it.next()).getValue();
//				}
//				if(ticket == null) throw new RuntimeException("异常，票信息未能正确获得");
//				long refundAmount = orderTrafficService.selectSumRefundByTraffic(traffic.getOrderTrafficId());
				
//				float per = ((float)refundAmount) / ticket.getPrice();
				//退款80%或退款只剩2元时 直接废单
				/*if(per >= 0.80f || ticket.getPrice()-refundAmount==200){//
					OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
					boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单已全部退票", "SYSTEM");
					if(flag){
						//orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
						orderServiceProxy.autoCreateOrdRefundmentBySupplier(order, traffic.getOrderItemMetaId(), refund.getAmount(), "System", "火车票退款");
					}
					return new Rsp(handle.getReturnContent());
				}*/
//			}
//			int ticketNoRefundNum = orderTrafficService.getTicketNoRefundNum(refund.getOrderTrafficId());
//			if(ticketNoRefundNum == 0){
//				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
//				boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单已全部退票", "SYSTEM");
//				if(flag){
//					orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
//				}
//				return new Rsp(handle.getReturnContent());
//			}
//		}
		orderMessageProducer.sendMsg(MessageFactory.newTrainCancelRefundMessage(traffic.getOrderItemMetaId(),refund.getBillNo()));
		return new Rsp(handle.getReturnContent());
	}
	
	/**
	 * 检查表单数据是否正确
	 * @param vo
	 * @return
	 */
	private Rsp check(TicketRefundNotifyVo vo){
		Rsp rsp = super.check(vo);
		if(rsp != null)
			return rsp;
		
		//先判断订单ID是否为空
		if(StringUtils.isEmpty(vo.getOrderId())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(), 
							Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
		}else if(StringUtils.isEmpty(vo.getRefundId())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_REFUND_ID.getRetCode(), 
							Constant.REPLY_CODE.MISS_REFUND_ID.getRetMsg()));
			//判断退款金额是否为空
		}else if(StringUtils.isEmpty(vo.getTicketFee())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_TICKET_FEE.getRetCode(), 
							Constant.REPLY_CODE.MISS_TICKET_FEE.getRetMsg()));
		}else return null;
	}

//	@Override
//	protected void parseBody(Element body) {
//		String supplierOrderId=XmlUtils.getChildElementContent(body, "OrderID");
//		if(StringUtils.isEmpty(supplierOrderId)){
//			setFail("订单号不存在");
//			return;
//		}
//		
//		Long amount=PriceUtil.convertToFen(XmlUtils.getChildElementContent(body, "Amount"));
//		if(amount>0){
//			setFail("退款金额错误");
//			return;
//		}
//		
//		String billNo=XmlUtils.getChildElementContent(body, "BillNo");
//		if(StringUtils.isEmpty(billNo)){
//			setFail("退款流水号不存在");
//			return;
//		}
//		
//		OrdOrderTrafficRefund refund = new OrdOrderTrafficRefund();
//		refund.setAmount(amount);
//		refund.setBillNo(billNo);
//		ResultHandleT<Boolean> handle=orderTrafficService.addRefundInfo(supplierOrderId,refund);
//		if(handle.isFail()){
//			setFail(handle.getMsg());
//			return;
//		}
//		if(handle.getReturnContent()){
//			setSuccess(true);
//			return;
//		}
//		
//		OrdOrderTraffic traffic = orderTrafficService.getTrafficBySupplierOrderId(supplierOrderId);
//		orderMessageProducer.sendMsg(MessageFactory.newTrainCancelRefundMessage(traffic.getOrderItemMetaId(),billNo));
//		setSuccess(false);
//	}

}
