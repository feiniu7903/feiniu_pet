package com.lvmama.comm.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import com.lvmama.comm.utils.MemcachedUtil;

public class TopicMessageProducer {

	private static final Log log = LogFactory
			.getLog(TopicMessageProducer.class);

	private JmsTemplate template;

	private String destination;

	/**
	 * 向一个 Queue 组发送JMS，达到 Topic 效果
	 * 
	 * <pre>
	 * 从 Memcached 获取 JMS 控制参数，对消息进行 复制，移动（升级）操作
	 * 控制参数格式：[queue name / group name]=[operation 1.copy 2. move]|[JMS event type]|[new q destination]
	 * 对于 group name 的情况，operation 只能为 1
	 * 例子：
	 * 	1. 把order所有消息复制到 ActiveMQ.ORDER.monitor，ActiveMQ.ORDER.monitor2 {ActiveMQ.ORDER=1|*|ActiveMQ.ORDER.monitor,ActiveMQ.ORDER.monitor2}
	 * </pre>
	 * 
	 * @param msg
	 */
	public void sendMsg(Message msg) {
		log.info(msg);

		MemcachedUtil memcachedUtil = MemcachedUtil.getInstance();
		boolean isEnableJMSMgmt = "Y".equals(memcachedUtil.get("JMS_MANAGEMENT_ON"));

		String[] queues = MessageProtector.getInstance().getQueues(destination);
		for (String queue : queues) {
			// JMS management for queue
			if (memcachedUtil != null) {
				if (isEnableJMSMgmt) {
					Object ruleObj = memcachedUtil.get("JMS_MANAGEMENT_RULE_"
							+ queue);
					if (ruleObj != null && ruleObj instanceof String) {
						String rule = (String) ruleObj;
						String[] rules = rule.split("\\|");
						if (rules.length == 3) {
							// copy
							if ("1".equals(rules[0])) {
								if ("*".equals(rules[1])
										|| msg.getEventType().equals(rules[1])) {
									String[] newDestinationQs = rules[2]
											.split(",");
									for (String newDestinationQ : newDestinationQs) {
										template.convertAndSend(
												newDestinationQ, msg);
									}
								}
							}
							// move
							else if ("2".equals(rules[0])) {
								if ("*".equals(rules[1])
										|| msg.getEventType().equals(rules[1])) {
									String[] newDestinationQs = rules[2]
											.split(",");
									for (String newDestinationQ : newDestinationQs) {
										template.convertAndSend(
												newDestinationQ, msg);
									}

									continue;
								}
							}
						}
					}
				}
			}

			template.convertAndSend(queue, msg);
		}

		// JMS management for group
		if (memcachedUtil != null) {
			if (isEnableJMSMgmt) {
				Object ruleObj = memcachedUtil.get("JMS_MANAGEMENT_RULE_"
						+ destination);
				if (ruleObj != null && ruleObj instanceof String) {
					String rule = (String) ruleObj;
					String[] rules = rule.split("\\|");
					if (rules.length == 3) {
						if ("1".equals(rules[0])) {
							if ("*".equals(rules[1])
									|| msg.getEventType().equals(rules[1])) {
								String[] newDestinationQs = rules[2].split(",");
								for (String newDestinationQ : newDestinationQs) {
									template.convertAndSend(newDestinationQ,
											msg);
								}
							}
						}
					}
				}
			}
		}
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
