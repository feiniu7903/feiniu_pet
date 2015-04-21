package com.lvmama.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.shholiday.service.SHHolidayOrderService;

public class SHHolidayOrderProcessor implements MessageProcesser {

	private static final Log log = LogFactory.getLog(SHHolidayOrderProcessor.class);
	
	private SHHolidayOrderService sHHolidayOrderService;
	
	@Override
	public void process(Message message) {
		if(message.isOrderCreateMsg()){
			String result = sHHolidayOrderService.submitOrder(message.getObjectId());
			log.info("SHHolidayOrderProcessor submitOrder result: " + result);
		}
		if(message.isSupplierChannelReSubmit()){
			String result = sHHolidayOrderService.reSubmitOrder(message.getObjectId());
			log.info("SHHolidayOrderProcessor submitOrder result: " + result);
		}
		if(message.isSupplierChannelOrderCancelMsg()){
			String result = sHHolidayOrderService.cancelOrder(message.getObjectId());
			log.info("SHHolidayOrderProcessor cancelOrder result: " + result);
		}
		if(message.isOrderModifyPerson()){
			String result = sHHolidayOrderService.updateOrder(message.getObjectId());
			log.info("SHHolidayOrderProcessor modifyPersonOrder result: " + result);
		}
		if(message.isOrderPaymentMsg()){
			String result = sHHolidayOrderService.payOrder(message.getObjectId());
			log.info("SHHolidayOrderProcessor payOrder result: " + result);
		}
	}
	
	public void setsHHolidayOrderService(SHHolidayOrderService sHHolidayOrderService) {
		this.sHHolidayOrderService = sHHolidayOrderService;
	}

	
}
