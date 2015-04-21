package com.lvmama.comm.abroad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TestThread {
	private final static Log log = LogFactory.getLog(TestThread.class);
	public static void main(String[] arg) throws Exception{
		for(int i=0;i<250;i++){
			log.info(i+"Start=======================================");
			System.out.println(i+"Start=======================================");
//			new Thread(new TestThread1()).start();
//			new Thread(new TestThread2()).start();
			log.info(i+"End=======================================");
			System.out.println(i+"End=======================================");
		}
	}
}
