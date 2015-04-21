//package com.lvmama.pet.recon.job;
//
//import java.util.Date;
//
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.pet.service.pay.FinReconStatementService;
//import com.lvmama.comm.utils.DateUtil;
//import com.lvmama.comm.vo.Constant;
//
//public class FinanceReconJob implements Runnable {
//	protected final Log log = LogFactory.getLog(this.getClass().getName());
//	private FinReconStatementService finReconStatementService;
//
//	@Override
//	public void run() {
//		if (!Constant.getInstance().isJobRunnable()) {
//			return;
//		}
//		log.info("FinanceReconJob starting");
//		Date currentInitDate = DateUtil.accurateToDay(new Date());
//		Date startDate = DateUtils.addDays(currentInitDate, -2);
//		finReconStatementService.updateReconStatementData(currentInitDate, startDate);
//		log.info("FinanceReconJob completed");
//	}
//
//	public FinReconStatementService getFinReconStatementService() {
//		return finReconStatementService;
//	}
//
//	public void setFinReconStatementService(FinReconStatementService finReconStatementService) {
//		this.finReconStatementService = finReconStatementService;
//	}
//
//}
