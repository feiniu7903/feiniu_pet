package com.lvmama.pet.job.mobile.push;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;
import javapns.notification.transmission.PushQueue;

import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;

public class IPADNotificationQueueManager extends Thread {

	private static IPADNotificationQueueManager instance;
	private final Logger logger = Logger.getLogger(this.getClass());
	private List<String> invalidTokenList = new ArrayList<String>();
	private PushQueue queue = null;
	String keystoreFileName = Constant.getInstance().getValue(
			"ipad.notification.push.keystore.filename");
	String password = Constant.getInstance().getValue(
			"ipad.notification.push.keystore.password");// 证书密码
	boolean production = Boolean.parseBoolean(Constant.getInstance().getValue(
			"ipad.notification.push.keystore.production")); // 设置true为正式服务地址，false为开发者地址

//	 String keystoreFileName = "D:\\document\\lvmamaPad_push_development.p12";
//	 String password = "111111";
//	 boolean production = false;
	File file = new File(keystoreFileName);
	String keystore = file.getPath();// 证书路径和证书名

	/**
	 * Returns the singleton instance of SessionManager.
	 * 
	 * @return the instance
	 */
	public static IPADNotificationQueueManager getInstance() {
		if (instance == null) {
			synchronized (IPADNotificationQueueManager.class) {
				instance = new IPADNotificationQueueManager();
				instance.start();
				FailureMessageHadler fmh = new FailureMessageHadler();
				fmh.setPns(instance.getFailedNotifications());
				fmh.start();
			}
		}
		return instance;
	}

	public void addQueue(List<PayloadPerDevice> list) {
		for (PayloadPerDevice payloadPerDevice : list) {
			queue.add(payloadPerDevice);
		}

	}

	public PushedNotifications getFailedNotifications() {
		PushedNotifications pns = queue.getPushedNotifications(false)
				.getFailedNotifications();
		logger.info("FailedNotifications size:" + pns.size());
		return pns;
	}

	public PushedNotifications getSuccessfulNotifications() {
		PushedNotifications pns = queue.getPushedNotifications(false)
				.getSuccessfulNotifications();
		logger.info("getSuccessfulNotifications size:" + pns.size());
		return pns;
	}

	public void addQueue(PayloadPerDevice payloadPerDevice) {
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
				List<Device> list = Push.feedback(keystore, password,
						production);
				logger.info("get invalid device size:" + list.size());
				for (Device device : list) {
					String token = device.getToken();
					logger.info("invalid token is :" + token);
					invalidTokenList.add(token);
				}
				try {
					/**
					 * 一小时执行一次
					 */
					Thread.sleep(60 * 1000 * 60);
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

	private IPADNotificationQueueManager() {
		try {
			logger.info("初始化 推送队列");
			queue = Push.queue(keystore, password, production, 10);
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// String keystoreFileName = Constant.getInstance().getValue(
		// "notification.push.keystore.filename");
		String keystoreFileName = "D:\\document\\lvmamaPad_push_development.p12";
		File file = new File(keystoreFileName);
		String keystore = file.getPath();// 证书路径和证书名
		String password = "111111";// 证书密码
		boolean production = false;
		// List<Device> list = Push.feedback(keystore, password, production);
		// PushedNotifications pn = Push.payloads(list, password, production,
		// null);
		// System.out.println("get invalid device size:"+list.size());
		// for (Device device : list) {
		// String token = device.getToken();
		// System.out.println(token);
		// }
		List<PayloadPerDevice> list = new ArrayList<PayloadPerDevice>();
		List<String> device = new ArrayList<String>();
		device.add("75e9ce0ac39bb49d1a5966bc89c72a67f78959a8a7e634afc829a0e314575e96");
		for (String string : device) {
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert("testfdfdsfdsfds");
			payload.addSound("default");// 声音
			payload.addCustomDictionary("title",
					"testtesttesttesttesttesttesttest");
			payload.addCustomDictionary("url",
					"http://m.lvmama.com/clutter/place/120044");
			payload.addCustomDictionary("type", "webBrower");

			PayloadPerDevice ppd = new PayloadPerDevice(payload, string);
			list.add(ppd);
		}

		IPADNotificationQueueManager.getInstance().addQueue(list);

	}

	public void removeInvlidTokenList() {
		this.invalidTokenList.clear();
	}

	public List<String> getInvalidTokenList() {
		return invalidTokenList;
	}

	public void setInvalidTokenList(List<String> invalidTokenList) {
		this.invalidTokenList = invalidTokenList;
	}

}
