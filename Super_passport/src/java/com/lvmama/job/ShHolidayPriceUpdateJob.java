package com.lvmama.job;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.shholiday.service.ShHolidayProductService;
/**
 * 产品价格更新接口
 * @author gaoxin
 *
 */
public class ShHolidayPriceUpdateJob {
	private static final Log log = LogFactory.getLog(ShHolidayPriceUpdateJob.class);
	private ShHolidayProductService shholidayProductService;
	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("ShHolidayPriceUpdateJob started");
			Date startDate = DateUtil.getTodayYMDDate();
			Date endDate = DateUtils.addMonths(startDate, 2);
			try {
				shholidayProductService.updateAllProductTimePrices(startDate, endDate);
			} catch (Exception e) {
				log.error("ShHolidayPriceUpdateJob Exception:",e);
			}
			log.info("ShHolidayPriceUpdateJob ended");
		}
	}

	public void setShholidayProductService(
			ShHolidayProductService shholidayProductService) {
		this.shholidayProductService = shholidayProductService;
	}
	
}
