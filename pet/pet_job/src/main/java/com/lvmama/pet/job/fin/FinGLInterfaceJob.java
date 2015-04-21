package com.lvmama.pet.job.fin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.fin.FinGLBizService;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.vo.Constant;

/**
 * 总账接口 此接口每天会向用友推送数据
 */

public class FinGLInterfaceJob implements Runnable {
	private static Log log = LogFactory.getLog(FinGLInterfaceJob.class);

	/**
	 * 对账接口代理
	 */
	private FinGLBizService finGLServiceProxy;
	/**
	 * 对账接口服务
	 */
	private FinGLService finGLService;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("FinGLInterfaceJob Running!");
			// 发送对账结果
			finGLServiceProxy.send();
		}
	}

	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public FinGLBizService getFinGLServiceProxy() {
		return finGLServiceProxy;
	}

	public void setFinGLServiceProxy(FinGLBizService finGLServiceProxy) {
		this.finGLServiceProxy = finGLServiceProxy;
	}

}
