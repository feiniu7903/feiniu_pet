package com.lvmama.report.jobs;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.ReportOrderService;

/**
 * 更新历史订单
 * @author zhaojindong
 */
public class UpdateHistoryOrderJob implements Runnable {
	private static final Log log = LogFactory.getLog(UpdateHistoryOrderJob.class);
	
	private ReportOrderService reportOrderService;
	private String startDate;
	private String endDate;
	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("更新历史订单，支付时间：" + startDate + "~" + endDate);
			Date start = StringUtil.isEmptyString(startDate)?null:DateUtil.toDate(startDate, "yyyy-MM-dd");
			Date end = StringUtil.isEmptyString(endDate)?null:DateUtil.toDate(endDate, "yyyy-MM-dd");
			List<Long> ids = reportOrderService.getHistoryOrderId(start, end);
			if(ids != null && ids.size() > 0){
				for(Long id : ids){
					try{
						reportOrderService.updateSpecOrder(id);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			log.info("更新历史订单完成，支付时间：" + startDate + "~" + endDate);
		}
	}

	public ReportOrderService getReportOrderService() {
		return reportOrderService;
	}

	public void setReportOrderService(ReportOrderService reportOrderService) {
		this.reportOrderService = reportOrderService;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
