package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.CashBonusDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 奖金账户对账下载数据调度
 * @author lvhao
 *
 */
public class CashBonusDownPayDataJobServiceImpl implements
		CashBonusDownPayDataJobService, Serializable {

	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService cashBonusAccountService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		cashBonusAccountService = (GatewayAccountService) SpringBeanProxy.getBean("cashBonusAccountService");
		
		log.info("cashBonusAccountService starting");
		try {
			String result=cashBonusAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info("cashBonusAccountService completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error("cashBonusAccountService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("cashBonusAccountService error");
		}
		return taskResult;
	}	
	
}
