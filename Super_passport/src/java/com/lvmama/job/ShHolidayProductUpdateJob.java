package com.lvmama.job;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.shholiday.service.ShHolidayProductService;

public class ShHolidayProductUpdateJob {
	private static final Log log = LogFactory.getLog(ShHolidayProductUpdateJob.class);
	private ShHolidayProductService shholidayProductService;
	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("ShHolidayProductUpdateJob started");
			Date startDate = DateUtil.getTodayYMDDate();
			Date endDate = DateUtils.addMonths(startDate, 2);
			try {
				shholidayProductService.updateAllProductInfo(startDate, endDate);
			} catch (Exception e) {
				log.error("ShHolidayProductUpdateJob Exception:",e);
			}
			log.info("ShHolidayProductUpdateJob ended");
		}
	}

	public void setShholidayProductService(
			ShHolidayProductService shholidayProductService) {
		this.shholidayProductService = shholidayProductService;
	}
	
}
