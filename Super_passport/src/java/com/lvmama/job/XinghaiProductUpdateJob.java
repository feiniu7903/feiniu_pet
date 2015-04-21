package com.lvmama.job;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayProductService;

public class XinghaiProductUpdateJob {
	private static final Log log = LogFactory.getLog(XinghaiProductUpdateJob.class);
	private XinghaiHolidayProductService xinghaiHolidayProductService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("XinghaiProductUpdateJob started");
			Date startDate = DateUtil.getTodayYMDDate();
			Date endDate = DateUtils.addDays(startDate, 30);
			try {
				xinghaiHolidayProductService.onOffLineHotels();
			} catch (Exception e) {
				log.error("onOffLineHotelProducts Exception:", e);
			}
			try {
				xinghaiHolidayProductService.onOffLineRoomTypes();
			} catch (Exception e) {
				log.error("onOffLineRomTypeProducts Exception:", e);
			}
			try {
				xinghaiHolidayProductService.updateAdditionalTimePrice(startDate, endDate);
			} catch (Exception e) {
				log.error("updateAdditionalProducts Exception:", e);
			}
		}
	}

	public void setXinghaiHolidayProductService(XinghaiHolidayProductService xinghaiHolidayProductService) {
		this.xinghaiHolidayProductService = xinghaiHolidayProductService;
	}
}
