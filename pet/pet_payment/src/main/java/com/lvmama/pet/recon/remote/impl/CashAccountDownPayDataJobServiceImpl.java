package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.CashAccountDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 存款账户对账下载数据调度
 * @author lvhao
 *
 */
public class CashAccountDownPayDataJobServiceImpl implements CashAccountDownPayDataJobService, Serializable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService cashAccountAccountService;

	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult  taskResult = new TaskResult();
		
		cashAccountAccountService = (GatewayAccountService) SpringBeanProxy.getBean("cashAccountAccountService");
		
		log.info("CashAccountDownPayDataJobService starting");
		try {
			String result=cashAccountAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info("CashAccountDownPayDataJobService completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error("CashAccountDownPayDataJobService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("CashAccountDownPayDataJobService error");
		}
		return taskResult;
	}

}
