package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 汇付天下对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class ChinapnrDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService chinapnrAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("ChinapnrDownPayDataJob starting");
		try {
			String result=chinapnrAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("ChinapnrDownPayDataJob completed");	
			}
			else{
				log.error("ChinapnrDownPayDataJob error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("AlipayDownPayDataJob error");
		}
	}


	public GatewayAccountService getChinapnrAccountService() {
		return chinapnrAccountService;
	}


	public void setChinapnrAccountService(GatewayAccountService chinapnrAccountService) {
		this.chinapnrAccountService = chinapnrAccountService;
	}
}
