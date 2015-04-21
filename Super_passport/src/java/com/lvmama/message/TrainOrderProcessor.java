/**
 * 
 */
package com.lvmama.message;

import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.train.service.TrainOrderService;

/**
 * 默认只要是火车票类型的订单全部走对接接口
 * @author yangbin
 *
 */
public class TrainOrderProcessor implements MessageProcesser{
	private static final org.apache.commons.logging.Log logger=LogFactory.getLog(TrainOrderProcessor.class);
	private TrainOrderService trainOrderService;

	@Override
	public void process(Message message) {
		logger.info("Train message process:" + message.toString());
		//创建订单
		if(message.isOrderPaymentMsg()){
			trainOrderService.createTrainOrder(message.getObjectId());
		//取消订单
		}else if(message.isSupplierChannelOrderCancelMsg()){
			trainOrderService.cancelTrainOrder(message.getObjectId());
		//退票请求
		}else if(message.isTrainTicketDrawback()){
			trainOrderService.drawbackTrainTicket(message.getObjectId());
		//退款成功请求接口
		}else if(message.isTrainRefumentSuccessMsg()){
			trainOrderService.refundTrainTicketSuccess(message.getObjectId());
		}
	}
	
	public TrainOrderService getTrainOrderService() {
		return trainOrderService;
	}
	public void setTrainOrderService(TrainOrderService trainOrderService) {
		this.trainOrderService = trainOrderService;
	}
}
