package com.lvmama.back.job;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.vo.Constant;

public class SmsSendJob {
	private static final Log log = LogFactory.getLog(SmsSendJob.class);
	
	private OrderService orderServiceProxy;
	private SmsRemoteService smsRemoteService;
	private SmsService smsService;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			List<ComSms> smsList = smsService.getWaitSendSms();
			log.info("Run SmsSendJob");
			for (ComSms comSms : smsList) {
				if (comSms.isValid()) {
					OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(comSms.getObjectId());
					log.info("sms order:"+comSms.getObjectId());
					if (Constant.SMS_TEMPLATE.BEFORE_PERFORM.name().equalsIgnoreCase(comSms.getTemplateId()) 
							|| Constant.SMS_TEMPLATE.AFTER_PERFORM.name().equalsIgnoreCase(comSms.getTemplateId())
							|| Constant.SMS_TEMPLATE.AFTER_THREEMONTHS_PERFROM.name().equalsIgnoreCase(comSms.getTemplateId())
							|| (order.hasSelfPack() && Constant.SMS_TEMPLATE.DIEM_PAYMENT_SUCC.name().equalsIgnoreCase(comSms.getTemplateId()))) {
						sendSms(order, comSms);
					}else {
						send(order, comSms, null);
					}
				}else{
					log.info("Invalid sms, smsId:"+comSms.getSmsId());
					smsService.deleteByPrimaryKey(comSms.getSmsId());
				}
			}
		}
	}

	private void sendSms(final OrdOrder order, ComSms comSms) {
		if(order!=null){
			if (order.isCanceled()) {
				log.info("Order was canceled, delete BEFORE_PERFORM sms, smsId:"+comSms.getSmsId());
				this.smsService.deleteByPrimaryKey(comSms.getSmsId());
			}else{
				send(order, comSms, "QUNFA");
			}
		}
	}
	
	private void send(final OrdOrder order, ComSms comSms, final String type) {
		try {
			log.info("########## Begin send sms, smsId:"+comSms.getSmsId()+",mobile:"+comSms.getMobile()+",sendTime:"+sf.format(comSms.getSendTime()));
			if (null != order.getChannel() && Constant.QUNFACHANNELLIST.contains(order.getChannel())) {
				sendSmsWithType(comSms.getContent(), comSms.getMobile(), "QUNFA");
			} else {
				sendSmsWithType(comSms.getContent(), comSms.getMobile(), type);
			}
			this.smsService.update(comSms, Constant.SMS_STATUS.SEND, "发送成功");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendSmsWithType(final String content, final String mobile, final String type) throws Exception {
		if (StringUtils.isEmpty(type)) {
			smsRemoteService.sendSms(content, mobile);
		} else {
			smsRemoteService.sendSmsWithType(content, mobile, type);
		}
	}
	
 
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

}
