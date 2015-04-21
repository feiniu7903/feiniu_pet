package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.TenpayDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 财付通对账下载数据调度
 * @author lvhao
 *
 */
public class TenpayDownPayDataJobServiceImpl implements
		TenpayDownPayDataJobService, Serializable {
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService tenpayAccountService;
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		tenpayAccountService = (GatewayAccountService) SpringBeanProxy.getBean("tenpayAccountService");
		
		log.info("TenpayDownPayDataJobService starting");
		try {
			String result=tenpayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info("TenpayDownPayDataJobService completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error("TenpayDownPayDataJobService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("TenpayDownPayDataJobService error");
		}
		return taskResult;
	}
}
