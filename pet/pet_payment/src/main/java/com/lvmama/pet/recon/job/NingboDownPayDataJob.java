package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 宁波银行对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class NingboDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService ningboAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info(this.getClass().getName()+" starting");
		try {
			String result=ningboAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info(this.getClass().getName()+" completed");	
			}
			else{
				log.error(this.getClass().getName()+" error,result:"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(this.getClass().getName()+" error");
		}
	}


	public GatewayAccountService getNingboAccountService() {
		return ningboAccountService;
	}
	public void setNingboAccountService(GatewayAccountService ningboAccountService) {
		this.ningboAccountService = ningboAccountService;
	}
}
