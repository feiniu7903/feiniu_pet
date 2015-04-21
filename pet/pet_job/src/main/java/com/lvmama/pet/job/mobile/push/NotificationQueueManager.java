package com.lvmama.pet.job.mobile.push;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushedNotifications;
import javapns.notification.transmission.PushQueue;

import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;


public class NotificationQueueManager extends Thread{
	
private static NotificationQueueManager instance;
	private final Logger logger = Logger.getLogger(this.getClass());
	//private List<String> invalidTokenList  = new ArrayList<String>();
	private Map<String,String> inavalidTokenMap = new LinkedHashMap<String,String>();
	private  PushQueue queue = null;
	String keystoreFileName = Constant.getInstance().getValue(
			"notification.push.keystore.filename");
	String password = Constant.getInstance().getValue(
			"notification.push.keystore.password");// 证书密码
	boolean production = Boolean.parseBoolean(Constant.getInstance()
			.getValue("notification.push.keystore.production")); // 设置true为正式服务地址，false为开发者地址
	
//	String keystoreFileName ="D:\\document\\lvmama_push_development.p12";
//	String password = "111111";
//	boolean production = false;
	
	File file = new File(keystoreFileName);
	String keystore = file.getPath();// 证书路径和证书名
	
	 /**
     * Returns the singleton instance of SessionManager.
     * 
     * @return the instance
     */
    public static NotificationQueueManager getInstance() {
        if (instance == null) {
            synchronized (NotificationQueueManager.class) {
                instance = new NotificationQueueManager();
                instance.start();
                FailureMessageHadler fmh =  new FailureMessageHadler();
                fmh.setPns(instance.getFailedNotifications());
                fmh.start();
            }
        }
        return instance;
    }
    
    public void addQueue (List<PayloadPerDevice> list){
    	for (PayloadPerDevice payloadPerDevice : list) {
				queue.add(payloadPerDevice);	
		}
    	 
    }
    
    public PushedNotifications getFailedNotifications(){
    	PushedNotifications pns = queue.getPushedNotifications(false).getFailedNotifications();
    	logger.info("FailedNotifications size:"+pns.size());
    	return pns;
    }
    
    public PushedNotifications getSuccessfulNotifications(){
    	PushedNotifications pns = queue.getPushedNotifications(false).getSuccessfulNotifications();
    	logger.info("getSuccessfulNotifications size:"+pns.size());
    	return pns;
    }
    
    
    
    public void addQueue (PayloadPerDevice payloadPerDevice){
    	queue.add(payloadPerDevice);
    }
    
    
    
  
    @Override
    	public void run() {
    		// TODO Auto-generated method stub
    		super.run();
    		
    		while (true) {
    			try {
    				/**
    				 * 处理失效的token
    				 */
    				logger.info("begin feedBack ");
    				List<Device>   list = Push.feedback(keystore, password, production);
    				logger.info("get invalid device size:"+list.size());
    				for (Device device : list) {
						String token  = device.getToken();
						logger.info("invalid token is :"+token);
						inavalidTokenMap.put(token, token);
					}
    				try {
    					/**
    					 * 6小时执行一次
    					 */
						Thread.sleep(60*1000*60*6);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (CommunicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeystoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
    		
    	}
    
 
    
    private NotificationQueueManager(){
    	try {
    		logger.info("初始化 推送队列");
    		queue =  Push.queue(keystore, password, production, 10);
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
	

	public void removeInvlidTokenList() {
		this.inavalidTokenMap.clear();
	}

	public Map<String, String> getInavalidTokenMap() {
		return inavalidTokenMap;
	}

	public void setInavalidTokenMap(Map<String, String> inavalidTokenMap) {
		this.inavalidTokenMap = inavalidTokenMap;
	}
	
     
    
}
