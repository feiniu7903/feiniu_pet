package com.lvmama.pet.job.mobile.push;

import org.apache.log4j.Logger;

import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
/**
 * 失败消息重新执行。
 * @author dengcheng
 *
 */
public class FailureMessageHadler extends Thread {
	private final Logger logger = Logger.getLogger(this.getClass());
	private PushedNotifications pns;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true){
			logger.info("开始执行失败任务.......................");
			//PushedNotifications pns =  NotificationQueueManager.getInstance().getFailedNotifications();
			for (PushedNotification pushedNotification : pns) {
				PayloadPerDevice pay = null;
				try {
					pay = new PayloadPerDevice(pushedNotification.getPayload(), pushedNotification.getDevice().getToken());
					logger.info("failure task job:+"+pay.getDevice().getToken()+"++"+pay.getDevice().getDeviceId());
				} catch (InvalidDeviceTokenFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/**
				 * 暂时先注释掉
				 */
				//NotificationQueueManager.getInstance().addQueue(pay);
				
			}
			
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public PushedNotifications getPns() {
		return pns;
	}
	public void setPns(PushedNotifications pns) {
		this.pns = pns;
	}
	
}
