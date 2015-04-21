package com.lvmama.report.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.CashBonusReportService;

public class CashBonusReportJob implements Runnable {
	
	private final Log log = LogFactory.getLog(getClass());
	
	private CashBonusReportService cashBonusReportService;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("CashBonusReportJob start.");
			cashBonusReportService.execSPFinancialReport();
			log.info("CashBonusReportJob end.");
		}
	}

	public CashBonusReportService getCashBonusReportService() {
		return cashBonusReportService;
	}

	public void setCashBonusReportService(
			CashBonusReportService cashBonusReportService) {
		this.cashBonusReportService = cashBonusReportService;
	}
	
	

}
