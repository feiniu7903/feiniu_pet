package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
/**
 * 支付宝对账下载数据JOB
 * @author ZHANG Nan
 *
 */
public class AlipayDownPayDataJob implements Runnable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService alipayAccountService;

	
	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("AlipayDownPayDataJob starting");
		try {
			String result=alipayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				log.info("AlipayDownPayDataJob completed");	
			}
			else{
				log.error("AlipayDownPayDataJob error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("AlipayDownPayDataJob error");
		}
	}


	public GatewayAccountService getAlipayAccountService() {
		return alipayAccountService;
	}


	public void setAlipayAccountService(GatewayAccountService alipayAccountService) {
		this.alipayAccountService = alipayAccountService;
	}
}
