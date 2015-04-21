package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.NingboDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 宁波银行对账下载数据调度
 * @author lvhao
 *
 */
public class NingboDownPayDataJobServiceImpl implements
		NingboDownPayDataJobService, Serializable {

	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService ningboAccountService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		ningboAccountService = (GatewayAccountService) SpringBeanProxy.getBean("ningboAccountService");
		
		log.info(this.getClass().getName()+" starting");
		try {
			String result=ningboAccountService.processAccount();
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
