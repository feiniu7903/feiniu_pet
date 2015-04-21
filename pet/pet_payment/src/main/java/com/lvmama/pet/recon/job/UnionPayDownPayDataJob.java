package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 银联对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class UnionPayDownPayDataJob implements Runnable {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private GatewayAccountService unionPayAccountService;

	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("UnionPayDownPayDataJob starting");

		try {
			String result=unionPayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("UnionPayDownPayDataJob completed");	
			}
			else{
				log.error("UnionPayDownPayDataJob error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("UnionPayDownPayDataJob error");
		}
	}

	public GatewayAccountService getUnionPayAccountService() {
		return unionPayAccountService;
	}

	public void setUnionPayAccountService(GatewayAccountService unionPayAccountService) {
		this.unionPayAccountService = unionPayAccountService;
	}
}
