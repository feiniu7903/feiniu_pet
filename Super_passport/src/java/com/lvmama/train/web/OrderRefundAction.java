package com.lvmama.train.web;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketRefundNotifyVo;
import com.lvmama.train.service.NotifyMessageService;

public class OrderRefundAction extends TrainNotifyAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 32041235177329L;
	private NotifyMessageService notifyMessageService;
	private static final int CODE = 933;
	
	@Action(value="/v1/notice/ticket_refund_notify")
	public String refund(){
		Rsp rsp = checkSign();
		if(rsp!=null){
			return output(rsp);
		}
		TicketRefundNotifyVo vo = new TicketRefundNotifyVo();
		Object merchantId = this.getRequest().getParameter("merchant_id");
		Object sign = this.getRequest().getParameter("sign");
		Object refundId = this.getRequest().getParameter("refund_id");
		Object orderId = this.getRequest().getParameter("order_id");
		Object refundType = this.getRequest().getParameter("refund_type");
		Object ticketNum = this.getRequest().getParameter("ticket_num");
		Object ticketFee = this.getRequest().getParameter("ticket_fee");
		Object ticketCharge = this.getRequest().getParameter("ticket_charge");
		if(merchantId != null) vo.setMerchantId((String)merchantId);
		if(sign != null) vo.setSign((String)sign);
		if(refundId != null) vo.setRefundId((String)refundId);
		if(orderId != null) vo.setOrderId((String)orderId);
		if(refundType != null) vo.setRefundType((String)refundType);
		if(ticketNum != null) vo.setTicketNum((String)ticketNum);
		if(ticketFee != null) vo.setTicketFee((String)ticketFee);
		if(ticketCharge != null) vo.setTicketCharge((String)ticketCharge);
		log.info(vo);
		rsp = notifyMessageService.request(vo, CODE);
		return output(rsp);
	}
	
	public NotifyMessageService getNotifyMessageService() {
		return notifyMessageService;
	}
	public void setNotifyMessageService(NotifyMessageService notifyMessageService) {
		this.notifyMessageService = notifyMessageService;
	}
}
