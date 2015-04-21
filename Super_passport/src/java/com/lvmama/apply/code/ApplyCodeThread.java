package com.lvmama.apply.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lvmama.comm.bee.po.pass.PassportMessage;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassportMessageService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.passport.service.PassBusinessService;

/**
 * 景区多线程申码：1.可配置线程数据。
 * 			  2.一个供应商只有一个队列，同一个供应商线程池中只有一个线程在申码。
 * 			  3.
 * @author yuzhibing
 *
 */
public class ApplyCodeThread implements Runnable{
	private static final Log log = LogFactory.getLog(ApplyCodeThread.class);
 
	private ThreadPoolTaskExecutor threadPool;
	/**
	 * 最大线程数,默认10
	 */
	private int maxPoolSize = 10;
	private int messageSize=20;
	private String hostname;//对应的主机
	private PassportMessageService passportMessageService;
	private PassBusinessService passBusinessService;
	private TopicMessageProducer passportMessageProducer;
	private PassCodeService passCodeService;
	private Long threadKey;
	/**
	 * 线程池中运行的供应商key
	 */
	private Map<String, Boolean> processorKey = new Hashtable<String, Boolean>();
	/**
	 * 供应商队列，和线程数据一样大
	 */
	private Map<String,MessageQueue> messageQueueMap = new HashMap<String,MessageQueue>();
	private int messageCount=0;
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		this.threadPool = threadPool;
	}

	@Override
	public void run() {
		threadKey = System.currentTimeMillis();
		try {
			debug("------------------------:run-----------------------------------------------");
			messageCount=0;
			initPassportMessageQueue();
			this.debug("run supplier messageQueueMap:"+this.messageQueueMap.size());
			runThread();
			debug("------------------------end------------------------------------message count:"+messageCount);
		} catch (EmptyMessageException ex) {
			this.debug("current thread end. message count:"+messageCount);
		}
	}
 
	private boolean checkQueueSize(){
		return this.messageQueueMap.size()<maxPoolSize?true:false;
	}
	
	private void initPassportMessageQueue() {
		List<String> noSelectProcessor = queryRunSupplier();
		List<PassportMessage> list = passportMessageService.selectList(hostname, noSelectProcessor,messageSize);
		if (list.isEmpty()) {// 如果没有拿到数据，检查是否还有已经拉下来的数据，如果没有就直接退出
			if (messageQueueMap.isEmpty() && threadPool.getActiveCount() <= 0 && processorKey.isEmpty()) {// 并且没有活动线程的存在
				throw new EmptyMessageException();
			}
		} else {
			for (PassportMessage pm:list) {
				MessageQueue mq = messageQueueMap.get(pm.getProcessor());
				if (mq != null) {
					mq.add(pm);
					messageQueueMap.put(pm.getProcessor(), mq);
				} else {
					mq = new MessageQueue();
					mq.add(pm);
					messageQueueMap.put(pm.getProcessor(), mq);
				}
			}
		}
	}

	/**
	 * 查询已在队列中的供应商
	 * @return
	 */
	private List<String> queryRunSupplier() {
		List<String> noSelectProcessor = new Vector<String>();
		if(!messageQueueMap.isEmpty()){
			for(String key:messageQueueMap.keySet()){
				noSelectProcessor.add(key);
			}
		}
		return noSelectProcessor;
	}
	
	
	public synchronized void removeKey(final String key){
		processorKey.remove(key);
	}
	
	private void runThread(){
		while (true) {
			int activeCount =  threadPool.getActiveCount();
			//创建线程
			try {
				int newPoolSize = (maxPoolSize -activeCount);
				this.debug("activeCount:"+activeCount+",newPoolSize:"+newPoolSize+",run Thread Supplier:"+this.processorKey.keySet().toString());
				if(!messageQueueMap.isEmpty()){
					List<String> clearKey=new ArrayList<String>();//记录消息已处理完，可以从messageQueueMap中清除的队列
					for(String key:messageQueueMap.keySet()){
						synchronized (this) {
							if(processorKey.containsKey(key)){
								continue;
							}
						}
						MessageQueue queue = messageQueueMap.get(key);
						PassportMessage pm = queue.get();
						if(pm==null){
							clearKey.add(key);
						}else{
							threadPool.execute(new ApplyCodeTask(this,pm));
							synchronized (this) {
								processorKey.put(key, true);
							}
							messageCount++;
							newPoolSize--;
							if(queue.isEmpty()){
								clearKey.add(key);
							}
						}
						if(newPoolSize<=0){//活动线程用完了，本次循环退出
							break;
						}
					}
					clearMessageQueue(clearKey);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if(this.checkQueueSize()){
				initPassportMessageQueue();
			}
		}
	}

	private void clearMessageQueue(List<String> clearKey) {
		if(!clearKey.isEmpty()){//删除已经不存在消息的key
			for(String key:clearKey){
				messageQueueMap.remove(key);
			}
		}
	}
	
	private void debug(String str){
		if(log.isDebugEnabled()){
			log.debug(threadKey+":"+str);
		}
	}
 

	public String getHostname() {
		return hostname;
	}

	public PassportMessageService getPassportMessageService() {
		return passportMessageService;
	}

	public PassBusinessService getPassBusinessService() {
		return passBusinessService;
	}

	public TopicMessageProducer getPassportMessageProducer() {
		return passportMessageProducer;
	}

	public PassCodeService getPassCodeService() {
		return passCodeService;
	}

	public void setMessageSize(int messageSize) {
		this.messageSize = messageSize;
	}

	public void setPassportMessageService(
			PassportMessageService passportMessageService) {
		this.passportMessageService = passportMessageService;
	}

	public void setPassBusinessService(PassBusinessService passBusinessService) {
		this.passBusinessService = passBusinessService;
	}

	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	/**
	 * 消息用完退出
	 * @author yangbin
	 *
	 */
	private static class EmptyMessageException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1285371320974522616L;
		
	}
	
}
