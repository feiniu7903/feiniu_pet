/**
 * 
 */
package com.lvmama.pet.job.sms;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;

/**
 * 摧款短信上行处理
 * @author yangbin
 *
 */
public class ForPaymentReceiverLogic implements ReceiveLogic{
	
	
	/**
	 * 
	 */
	protected TopicMessageProducer orderMessageProducer;
	
	private final String SMS_CONTENT_PREFIX="Y";
	private final int SMS_CONTENT_LEN=4;

	@Override
	public int execute(String mobile, String content) {
		if(StringUtils.isNotEmpty(content)){
			String tmp=content.trim().toUpperCase();
			if(content.startsWith(SMS_CONTENT_PREFIX)&&content.length()==SMS_CONTENT_LEN){
				String code=tmp.substring(1);
				Message message=MessageFactory.newForPaymentReceiverMsg(mobile,code);
				orderMessageProducer.sendMsg(message);
				return SKIP_MESSAGE;
			}
		}
		return CONTINUE_MSSSAGE;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

}
