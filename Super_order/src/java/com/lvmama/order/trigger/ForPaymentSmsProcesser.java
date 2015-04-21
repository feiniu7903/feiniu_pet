/**
 * 
 */
package com.lvmama.order.trigger;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.service.OrderForPaymentService;

/**
 * @author yangbin
 *
 */
public class ForPaymentSmsProcesser implements MessageProcesser{

	private OrderForPaymentService orderForPaymentService;
	
	@Override
	public void process(Message message) {
		if(message.isForPaymentMsg()){
			String addition=message.getAddition();
			if(StringUtils.isNotEmpty(addition)){
				String[] array=addition.split("_");
				if(array.length==2){
					orderForPaymentService.receiverForPayment(array[0],array[1]);
				}
			}
		}
	}

	public void setOrderForPaymentService(
			OrderForPaymentService orderForPaymentService) {
		this.orderForPaymentService = orderForPaymentService;
	}
	
	

}
