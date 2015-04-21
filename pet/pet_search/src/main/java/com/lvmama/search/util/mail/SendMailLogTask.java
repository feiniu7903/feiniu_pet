package com.lvmama.search.util.mail;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.lvmama.search.util.LuceneCommonDic;
/*
 * 线程类来调用hbasedata
 */
public class SendMailLogTask implements Callable {

	String message=null;
	public SendMailLogTask(){			
	}
	public SendMailLogTask(String message) {
		this.message=message;
	}
	@Override
	public Object call() throws Exception {
		
		
		try {
			MyAuthenticator tor=new MyAuthenticator();
			tor.sendmessage(message);
		} catch (Exception e) {
			return null;
		}
		return null;
		
		
	}

}
