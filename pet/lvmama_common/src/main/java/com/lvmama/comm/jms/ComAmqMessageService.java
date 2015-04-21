package com.lvmama.comm.jms;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComAmqMessage;

/**
 * Service.
 * @author Alex Wang
 *
 */
public interface ComAmqMessageService {

	/**
	 * 查询未被接收的消息.
	 * @return 未被接收的消息
	 */
	List<ComAmqMessage> selectMessageUnReceived();
	
	/**
	 * 查询未被接收的消息数量.
	 * @return 未被接收的消息数量
	 */
	public Long countUnReceivedMessage();

	/**
	 * 把一条消息分发成多条消息.
	 * @param msg 消息
	 * @param compositeTopic topic
	 * @param queueArr queue数组
	 */
	void createMessages(Message msg, String compositeTopic, String[] queueArr);

	/**
	 * 把msg设置为已经收到.
	 * @param msg 消息
	 * @param destQueue 接收者
	 */
	void receivedMessage(Message msg, String destQueue);
	
	/**
	 * 把已经完成的消息清除
	 */
	void cleanFinished();

}
