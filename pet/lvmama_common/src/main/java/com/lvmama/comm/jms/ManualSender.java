package com.lvmama.comm.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.vo.Constant;

public class ManualSender {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-lvmama-comm-jms.xml");
		// TopicMessageProducer
		// orderMessageProducer=(TopicMessageProducer)context.getBean("orderMessageProducer");
		// Message msg = MessageFactory.newOrderApproveMessage(1307988l);
		// msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
		// orderMessageProducer.sendMsg(msg);

		// TopicMessageProducer
		// resourceMessageProducer=(TopicMessageProducer)context.getBean("resourceMessageProducer");
		// Message msg = MessageFactory.newPaymentSuccessCallMessage(2000093l);
		// resourceMessageProducer.sendMsg(msg);

		long drawId[] = { 43166, 43165, 43164, 43163, 43162, 43161, 43160, 43159, 43158, 43157,
				43156, 43155, 43154, 43153, 43152, 43151, 43150, 43149, 43148, 43147, 43146, 43145,
				43144, 43143, 43142, 43141, 43140, 43139, 43138, 43137, 43136, 43135, 43134, 43133,
				43132, 43131, 43130, 43129, 43128, 43127, 43126, 43125, 43124, 43123, 43122, 43121,
				43120, 43119, 43118, 43117, 43116, 43115, 43114, 43113, 43112, 43111, 43110, 43109,
				43108, 43107, 43090, 43089 };
		
		for(int i=0;i<drawId.length;i++){
			TopicMessageProducer resourceMessageProducer = (TopicMessageProducer) context
					.getBean("resourceMessageProducer");
			Message msg = MessageFactory.newPaymentRefundmentMessage(drawId[i]);
			msg.setAddition(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name());
			resourceMessageProducer.sendMsg(msg);
			System.out.println(msg.getObjectId());
		}
		
		System.out.println("finished");


	}
}
