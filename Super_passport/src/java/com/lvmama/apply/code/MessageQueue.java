package com.lvmama.apply.code;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.lvmama.comm.bee.po.pass.PassportMessage;

public class MessageQueue {

	private String processorKey;
	private int maxActivThreadquantity = 1;
	private BlockingQueue<PassportMessage> queueMessage = new LinkedBlockingDeque<PassportMessage>();
	public String getProcessorKey() {
		return processorKey;
	}
	public void setProcessorKey(String processorKey) {
		this.processorKey = processorKey;
	}
	public int getMaxActivThreadquantity() {
		return maxActivThreadquantity;
	}
	public void setMaxActivThreadquantity(int maxActivThreadquantity) {
		this.maxActivThreadquantity = maxActivThreadquantity;
	}
	public PassportMessage get(){
		return queueMessage.poll();
	}
	public void add(PassportMessage pm){
		queueMessage.add(pm);
	}
	public boolean isEmpty(){
		return queueMessage.isEmpty();
	}
}
