package com.lvmama.order.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class CommentPointSmsCreator extends AbstractOrderSmsCreator implements MultiSmsCreator {
	
	private String content;
	private OrdOrder order;
	
	public CommentPointSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		order = orderDAO.selectByPrimaryKey(objectId);
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		data.put("content", content);
		data.put("orderChannel", orderChannel);
		return data;
	}
	
	public List<ComSms> createSmsList() {
		List<ComSms> smsList = new ArrayList<ComSms>();
		ComSms comSms = super.createSingleSms();
		if(comSms!=null){
			comSms.setSendTime(getSendDate(order.getVisitTime()));
			smsList.add(comSms);
		}
		return smsList;
	}
	@Override
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.AFTER_PERFORM.name());
	}

	public Date getSendDate(Date visiteTime){
		//订购游玩日后+2天的中午10点发送
		String temp = DateUtil.getDateTime("yyyy-MM-dd",DateUtil.getDateAfterDays(visiteTime,2));
		Date sendDate = DateUtil.toDate(temp+" 10:00:00","yyyy-MM-dd HH:mm:ss");
 		return sendDate;
	}
 
}
