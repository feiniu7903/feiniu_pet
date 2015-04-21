package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 财付通对账下载数据JOB
 * @author zhangjie
 *
 */
public class TenpayDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService tenpayAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("TenpayDownPayDataJob starting");
		try {
			String result=tenpayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("TenpayDownPayDataJob completed");	
			}
			else{
				log.error("TenpayDownPayDataJob error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("TenpayDownPayDataJob error");
		}
	}


	public GatewayAccountService getTenpayAccountService() {
		return tenpayAccountService;
	}


	public void setTenpayAccountService(GatewayAccountService tenpayAccountService) {
		this.tenpayAccountService = tenpayAccountService;
	}


}
