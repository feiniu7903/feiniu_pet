package com.lvmama.report.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.distribution.DistributionRakeBackService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.ReportOrderService;

public class ReportOrderRefreshJob implements Runnable {
	
	private static final Log log = LogFactory.getLog(ReportOrderRefreshJob.class);
	private ReportOrderService reportOrderService;
	private DistributionRakeBackService distributionRakeBackService;
	
	private Date theTime;
	
	public void run(){
		System.out.println("runnable:" + Constant.getInstance().isJobRunnable());
		if (Constant.getInstance().isJobRunnable()) {
			long begin = System.currentTimeMillis();
			Calendar cal = Calendar.getInstance();
			if (theTime!=null) {
				cal.setTime(theTime);
			}else{
				cal.add(Calendar.DAY_OF_MONTH, -1);
			}
			DateUtil.clearDateAfterDay(cal);
			Date theTime = DateUtil.clearDateAfterDay(cal);
			
			int count = reportOrderService.deleteAfterSpecTime(theTime);
			log.info("clean data: "+ count);
			
			List<Long> idList = reportOrderService.selectForNew(theTime);
			System.out.println("reoport create start******************");
			for (Long orderId : idList) {
				try{
					log.info("add new data: "+ orderId);
					reportOrderService.addSpecOrder(orderId);
					System.out.println("******************orderId:" + orderId);
					// add by hupeipei 2014-4-17  task 8054 分销商的返佣值设置要支持小数 
					// add by zhangwengang 2013-11-4 导出列表新增返佣点、返佣金额
					//Long rakeBackRate = distributionRakeBackService.getRakeBackRatebyOrderId(orderId);
					Float rakeBackRateF = distributionRakeBackService.getRakeBackRatebyOrderId(orderId);
					Long rakeBackRate = rakeBackRateF!=null?rakeBackRateF.longValue():null;
					System.out.println("******************rakeBackRate:" + rakeBackRate);
					if (null != rakeBackRate) {
						reportOrderService.updateSpecOrderByOrderId(rakeBackRate, orderId);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("reoport create end******************");
			idList = reportOrderService.selectForOld(theTime);
			for (Long orderId : idList) {
				try{
					log.info("update data: "+ orderId);
					reportOrderService.updateSpecOrder(orderId);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			log.info("Finished in "+ (System.currentTimeMillis()-begin));
		}
	}

	public void setTheTime(Date date) {
		this.theTime = date;
	}
	
	public void setReportOrderService(ReportOrderService reportOrderService) {
		this.reportOrderService = reportOrderService;
	}

	public void setDistributionRakeBackService(
			DistributionRakeBackService distributionRakeBackService) {
		this.distributionRakeBackService = distributionRakeBackService;
	}

	

}
