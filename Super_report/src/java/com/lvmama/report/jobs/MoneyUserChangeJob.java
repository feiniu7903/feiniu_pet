package com.lvmama.report.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.MoneyUserService;

/**
 * 
 * 现金账户日报表的数据生成任务
 * 
 * @author yanggan
 * 
 */
public class MoneyUserChangeJob implements Runnable {

	private static final Log log = LogFactory.getLog(MoneyUserChangeJob.class);
	private MoneyUserService moneyUserService;
	
	/**
	 * 数据生成任务
	 */
	public void run() {
		boolean jobRunnable = Constant.getInstance().isJobRunnable();
		System.out.println("!runnable:" + jobRunnable);
		if (jobRunnable) {
			log.info(this.getClass().getName() + "  begin!");
			long begin = System.currentTimeMillis();
			moneyUserService.dataMaker();
			log.info("!Finished in " + (System.currentTimeMillis() - begin));
		}
	}

	public void setMoneyUserService(MoneyUserService moneyUserService) {
		this.moneyUserService = moneyUserService;
	}



}
