package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.recon.remote.SandPosPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 杉德POS对账下载数据调度
 * @author lvhao
 *
 */
public class SandPosPayDataJobServiceImpl implements
		SandPosPayDataJobService, Serializable {

	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService sandPosAccountService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		sandPosAccountService = (GatewayAccountService) SpringBeanProxy.getBean("sandPosAccountService");
		
		log.info(this.getClass().getName()+" starting");
		try {
			String result=sandPosAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info(this.getClass().getName()+" completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error(this.getClass().getName()+" error,result:"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info(this.getClass().getName()+" error");
		}
		return taskResult;
	}	
	
}
