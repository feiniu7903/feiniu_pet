package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 存款账户对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class CashAccountDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService cashAccountAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("CashAccountDownPayDataJob starting");
		try {
			String result=cashAccountAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("CashAccountDownPayDataJob completed");	
			}
			else{
				log.error("CashAccountDownPayDataJob error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("CashAccountDownPayDataJob error");
		}
	}


	public GatewayAccountService getCashAccountAccountService() {
		return cashAccountAccountService;
	}


	public void setCashAccountAccountService(GatewayAccountService cashAccountAccountService) {
		this.cashAccountAccountService = cashAccountAccountService;
	}	
}
