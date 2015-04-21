package com.lvmama.pet.recon.remote.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.recon.remote.AlipayDownPayDataJobService;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 支付宝对账下载数据调度
 * @author lvhao
 *
 */
public class AlipayDownPayDataJobServiceImpl implements AlipayDownPayDataJobService, Serializable {
	private static final long serialVersionUID = 3447543756084801777L;
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	private GatewayAccountService alipayAccountService;

	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		TaskResult  taskResult = new TaskResult();

		log.info("AlipayDownPayDataJobService starting");
		
		alipayAccountService = (GatewayAccountService) SpringBeanProxy.getBean("alipayAccountService");
		
		try {
			String result=alipayAccountService.processAccount();
			if("SUCCESS".equals(result)){
				taskResult.setStatus(1);//完成
				log.info("AlipayDownPayDataJobService completed");
			}
			else{
				taskResult.setStatus(3);//未完成
				log.error("AlipayDownPayDataJobService error,result:"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			taskResult.setStatus(3);//未完成
			log.info("AlipayDownPayDataJobService error");
		}
		return taskResult;
	}	

}
