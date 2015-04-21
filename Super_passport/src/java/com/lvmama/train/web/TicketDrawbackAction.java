package com.lvmama.train.web;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketDrawbackInfo;
import com.lvmama.comm.vo.train.notify.TicketDrawbackNotifyVo;
import com.lvmama.train.service.NotifyMessageService;

public class TicketDrawbackAction extends TrainNotifyAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 23941793471273L;
	
	private NotifyMessageService notifyMessageService;
	private static final int CODE = 932;
	
	@SuppressWarnings("unchecked")
	@Action(value="/v1/notice/ticket_drawback_notify")
	public String issue(){
		Rsp rsp = checkSign();
		if(rsp!=null){
			return output(rsp);
		}
		TicketDrawbackNotifyVo vo = new TicketDrawbackNotifyVo();
		Object merchantId = this.getRequest().getParameter("merchant_id");
		Object sign = this.getRequest().getParameter("sign");
		Object refundId = this.getRequest().getParameter("refund_id");
		Object orderId = this.getRequest().getParameter("order_id");
		Object ticketNum = this.getRequest().getParameter("ticket_num");
		Object infos = this.getRequest().getParameter("json_param");
		if(merchantId != null) vo.setMerchantId((String)merchantId);
		if(sign != null) vo.setSign((String)sign);
		if(refundId != null) vo.setRefundId((String)refundId);
		if(orderId != null) vo.setOrderId((String)orderId);
		if(ticketNum != null) vo.setTicketNum((String)ticketNum);
		log.info(vo);
		if(infos != null){
			log.info("退票结果通知退票详细信息：" +(String)infos);
			List<TicketDrawbackInfo> tdis = JsonUtil.getList4Json((String)infos, TicketDrawbackInfo.class, null);
			vo.setInfos(tdis);
		}
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
