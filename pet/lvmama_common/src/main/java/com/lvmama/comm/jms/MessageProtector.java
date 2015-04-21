package com.lvmama.comm.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 对消息服务器可能存在的丢消息的保护，发送之前记录，接收到之后先设置收到。
 * 另外一个检测线程当发现某条消息长时间（5分钟）未被设置为收到后
 * @author Alex Wang
 */
public class MessageProtector {
	private static final Log log = LogFactory.getLog(MessageProtector.class);
	private static MessageProtector instance;
	private Map<String, String[]> topicMap = new HashMap<String, String[]>();
	
	/**
	 * MAP XML.
        <virtualDestinations>
          <compositeTopic name="ActiveMQ.ORDER">
            <forwardTo>
                <queue physicalName="ActiveMQ.ORDER.back" />
                <!--queue physicalName="ActiveMQ.ORDER.cash" /-->
                <queue physicalName="ActiveMQ.ORDER.order" />
                <!--queue physicalName="ActiveMQ.ORDER.passport" /-->
            </forwardTo>
          </compositeTopic>
          <compositeTopic name="ActiveMQ.PASSPORT">
            <forwardTo>
                <queue physicalName="ActiveMQ.PASSPORT.back" />
                <queue physicalName="ActiveMQ.PASSPORT.order" />
                <queue physicalName="ActiveMQ.PASSPORT.passport" />
            </forwardTo>
          </compositeTopic>
          <compositeTopic name="ActiveMQ.PRODUCT">
            <forwardTo>
                <queue physicalName="ActiveMQ.PRODUCT.back" />
                <!--queue physicalName="ActiveMQ.PRODUCT.order" /-->
            </forwardTo>
          </compositeTopic>
          <compositeTopic name="ActiveMQ.POLICY">
            <forwardTo>
                <queue physicalName="ActiveMQ.POLICY.back" />
                <!--queue physicalName="ActiveMQ.POLICY.order" /-->
            </forwardTo>
          </compositeTopic>
          <!--compositeTopic name="ActiveMQ.SMS">
            <forwardTo>
              <queue physicalName="ActiveMQ.PASSPORT.back" />
              <queue physicalName="ActiveMQ.PASSPORT.order" />
            </forwardTo>
          </compositeTopic-->
          <compositeTopic name="ActiveMQ.SSO">
            <forwardTo>
              <queue physicalName="ActiveMQ.SSO.sso" />
              <queue physicalName="ActiveMQ.SSO.clutter" />
            </forwardTo>
          </compositeTopic>
        </virtualDestinations>
	 */
	private void init() {
		String[] activeMqOrder = new String[] { "ActiveMQ.ORDER.back",
				"ActiveMQ.ORDER.order", "ActiveMQ.ORDER.payment", "ActiveMQ.ORDER.clutter", "ActiveMQ.ORDER.passport","ActiveMQ.ORDER.client_clutter","ActiveMQ.ORDER.tntBack"};
		topicMap.put("ActiveMQ.ORDER", activeMqOrder);
		
		String[] activeMqPassport = new String[] { "ActiveMQ.PASSPORT.back",
				"ActiveMQ.PASSPORT.order", "ActiveMQ.PASSPORT.passport", "ActiveMQ.PASSPORT.ebk_push"};
		topicMap.put("ActiveMQ.PASSPORT", activeMqPassport);
		
		String[] activeMqPassportChimelong = new String[] { "ActiveMQ.PASSPORT.CHIMELONG.passport"};
		topicMap.put("ActiveMQ.PASSPORT.CHIMELONG", activeMqPassportChimelong);
        
		String[] activeMqProduct = new String[] { "ActiveMQ.PRODUCT.back","ActiveMQ.PRODUCT.clutter","ActiveMQ.PRODUCT.client_clutter","ActiveMQ.PRODUCT.tntBack"};

		topicMap.put("ActiveMQ.PRODUCT", activeMqProduct);
		
		String[] activeMqPolicy = new String[] { "ActiveMQ.POLICY.back"};
		topicMap.put("ActiveMQ.POLICY", activeMqPolicy);
		
		String[] activeMqSso = new String[] { "ActiveMQ.SSO.sso"};
		topicMap.put("ActiveMQ.SSO", activeMqSso);
		
		//TODO pet_search下线时需要将老的消息节点去掉
		String[] activeMqResource = new String[] { "ActiveMQ.RESOURCE.job",
				"ActiveMQ.RESOURCE.payment","ActiveMQ.RESOURCE.vstSearch1","ActiveMQ.RESOURCE.vstSearch2","ActiveMQ.RESOURCE.vstSearch3","ActiveMQ.RESOURCE.vstSearch4","ActiveMQ.RESOURCE.vstSearch5","ActiveMQ.RESOURCE.vstSearch6","ActiveMQ.ORDER.tntBack"};
		topicMap.put("ActiveMQ.RESOURCE", activeMqResource);
	}
	
	public static MessageProtector getInstance() {
		if (instance==null) {
			MessageProtector protector = new MessageProtector();
			protector.init();
			instance = protector;
		}
		return instance;
	}
	
	public String[] getQueues(String topic) {
		return topicMap.get(topic);
	}
	
	/**
	 * 记录.
	 * @param message
	 * @param compositeTopic
	 */
	public void record(Message message, String compositeTopic) {
		Object obj = SpringBeanProxy.getBean("comAmqMessageService");
		if (obj!=null) {
			ComAmqMessageService comAmqMessageService = (ComAmqMessageService)obj;
			String[] strArr = topicMap.get(compositeTopic);
			if (strArr!=null && strArr.length>0) {
				comAmqMessageService.createMessages(message,compositeTopic,strArr);
			}
		}
	}
	
	/**
	 * 接收.
	 * @param message
	 * @param destQueue
	 */
	public void receive(Message message, String destQueue) {
		Object obj = SpringBeanProxy.getBean("comAmqMessageService");
		if (obj!=null) {
			ComAmqMessageService comAmqMessageService = (ComAmqMessageService)obj;
			comAmqMessageService.receivedMessage(message, destQueue);
		}else{
			log.info("obj is null.");
		}
	}

}
