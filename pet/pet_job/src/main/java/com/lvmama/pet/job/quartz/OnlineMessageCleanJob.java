package com.lvmama.pet.job.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.service.onlineLetter.OnlineLetterService;
import com.lvmama.comm.vo.Constant;

public class OnlineMessageCleanJob  implements Runnable {
	
	private static final Logger LOG =Logger.getLogger(OnlineMessageCleanJob.class);
	private OnlineLetterService onlineLetterService;
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable() && Constant.getInstance().isJobRunnable("autoCleanOnlineMessageJob")){
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("cleanDate", new Date());
			parameters.put("rn",10000);
			try{
				int cleanCount=1;
				while(cleanCount>0){
					cleanCount=onlineLetterService.batchDeleteUserLetter(parameters);
				}
			}catch(Exception e){
				LOG.warn(" clean online message is error:\r\n"+e);
			}
		}
	}
	public OnlineLetterService getOnlineLetterService() {
		return onlineLetterService;
	}
	public void setOnlineLetterService(OnlineLetterService onlineLetterService) {
		this.onlineLetterService = onlineLetterService;
	}

}
