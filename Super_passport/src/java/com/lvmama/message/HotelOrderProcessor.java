package com.lvmama.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.hotel.service.HotelOrderService;

/**
 * 酒店预订、取消
 */
public class HotelOrderProcessor implements MessageProcesser {
	private static final Log log = LogFactory.getLog(HotelOrderProcessor.class);

	private HotelOrderService hotelOrderService;

	public void process(Message message) {
		log.info("HotelOrderProcessor message: " + message);
		if (message.isOrderPaymentMsg()) {
			String result = hotelOrderService.submitOrder(message.getObjectId());
			log.info("HotelOrderProcessor submitOrder result: " + result);
		}
		if (message.isOrderCancelMsg()) {
			String result = hotelOrderService.cancelOrder(message.getObjectId());
			log.info("HotelOrderProcessor cancelOrder result: " + result);
		}
	}

	public void setHotelOrderService(HotelOrderService hotelOrderService) {
		this.hotelOrderService = hotelOrderService;
	}
}
