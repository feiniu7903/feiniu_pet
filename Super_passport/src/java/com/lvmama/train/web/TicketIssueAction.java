package com.lvmama.train.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketIssueInfo;
import com.lvmama.comm.vo.train.notify.TicketIssueNotifyVo;
import com.lvmama.train.service.NotifyMessageService;

public class TicketIssueAction extends TrainNotifyAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 239471341934L;
	private NotifyMessageService notifyMessageService;
	private static final int CODE = 931;
	
	@SuppressWarnings("unchecked")
	@Action(value="/v1/notice/ticket_issued_notify")
	public String issue(){
		Rsp rsp = checkSign();
		if(rsp!=null){
			return output(rsp);
		}
		TicketIssueNotifyVo vo = new TicketIssueNotifyVo();
		Object merchantId = this.getRequest().getParameter("merchant_id");
		Object sign = this.getRequest().getParameter("sign");
		Object orderId = this.getRequest().getParameter("order_id");
		Object orderStatus = this.getRequest().getParameter("order_status");
		Object orderMsg = this.getRequest().getParameter("order_msg");
		Object issueInfos = this.getRequest().getParameter("json_param");
		if(merchantId != null) vo.setMerchantId((String)merchantId);
		if(sign != null) vo.setSign((String)sign);
		if(orderId != null) vo.setOrderId((String)orderId);
		if(orderStatus != null) vo.setOrderStatus((String)orderStatus);
		if(orderMsg != null) vo.setOrderMsg((String)orderMsg);
		log.info(vo);
		if(issueInfos != null){
			log.info("出票结果通知车票信息：" +(String)issueInfos);
			Map<String,Class> map = new HashMap<String, Class>();
			map.put("ticket_price", String.class);
			List<TicketIssueInfo> tiis = JsonUtil.getList4Json((String)issueInfos, TicketIssueInfo.class, map);
			
			log.info("出票结果乘客数量：" + tiis.size());
			vo.setIssueInfos(tiis);
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
