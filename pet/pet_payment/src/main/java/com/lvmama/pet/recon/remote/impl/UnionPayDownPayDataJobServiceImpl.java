package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.UnionPayDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 银联对账下载数据调度
 * @author lvhao
 *
 */
public class UnionPayDownPayDataJobServiceImpl implements
		UnionPayDownPayDataJobService, Serializable {
	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private GatewayAccountService unionPayAccountService;
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		unionPayAccountService = (GatewayAccountService) SpringBeanProxy.getBean("unionPayAccountService");
		
		log.info("UnionPayDownPayDataJobService starting");

		try {
			String result=unionPayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info("UnionPayDownPayDataJobService completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error("UnionPayDownPayDataJobService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("UnionPayDownPayDataJobService error");
		}
		return taskResult;
	}

}
