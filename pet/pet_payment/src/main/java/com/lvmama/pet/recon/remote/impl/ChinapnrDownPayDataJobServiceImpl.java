package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.remote.ChinapnrDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 汇付天下对账下载数据调度
 * @author lvhao
 *
 */
public class ChinapnrDownPayDataJobServiceImpl implements
		ChinapnrDownPayDataJobService, Serializable {

	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService chinapnrAccountService;
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult taskResult = new TaskResult();
		
		chinapnrAccountService = (GatewayAccountService) SpringBeanProxy.getBean("chinapnrAccountService");
		
		log.info("ChinapnrDownPayDataJobService starting");
		try {
			String result=chinapnrAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);
				log.info("ChinapnrDownPayDataJobService completed");	
			}
			else{
				taskResult.setStatus(3);
				log.error("ChinapnrDownPayDataJobService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);
			log.info("AlipayDownPayDataJobService error");
		}
		return taskResult;
	}

}
