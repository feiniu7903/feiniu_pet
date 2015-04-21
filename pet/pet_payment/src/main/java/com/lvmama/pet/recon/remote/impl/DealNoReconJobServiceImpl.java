package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.DealNoReconJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 处理无需对账的数据调度
 * 
 * @author lvhao
 * 
 */
public class DealNoReconJobServiceImpl implements DealNoReconJobService,
		Serializable {
	
	private static final long serialVersionUID = 1L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private GatewayAccountService noReconService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		noReconService = (GatewayAccountService) SpringBeanProxy.getBean("noReconService");
		
		log.info("DealNoReconJobService starting");
		try {
			String result = noReconService.processAccount();
			if ("SUCCESS".equals(result)) {
				taskResult.setStatus(1);
				log.info("DealNoReconJobService completed");
			} else {
				taskResult.setStatus(3);
				log.error("DealNoReconJobService error,result:" + result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("DealNoReconJobService error");
		}
		return taskResult;
	}	
	
}
