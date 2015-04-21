package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 奖金账户对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class CashBonusDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService cashBonusAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("cashBonusAccountService starting");
		try {
			String result=cashBonusAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("cashBonusAccountService completed");	
			}
			else{
				log.error("cashBonusAccountService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("cashBonusAccountService error");
		}
	}


	public GatewayAccountService getCashBonusAccountService() {
		return cashBonusAccountService;
	}


	public void setCashBonusAccountService(GatewayAccountService cashBonusAccountService) {
		this.cashBonusAccountService = cashBonusAccountService;
	}	
}
