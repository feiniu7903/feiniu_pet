package com.lvmama.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.service.UpdateHotelOrderStatusService;

/**
 * 定时更新监控里订单的状态
 */
public class UpdateHotelOrderStatusJob {
	private static final Log log = LogFactory.getLog(UpdateHotelOrderStatusJob.class);

	private List<UpdateHotelOrderStatusService> updateOrderStatusServices;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			for (UpdateHotelOrderStatusService updateOrderStatusService : updateOrderStatusServices) {
				try {
					updateOrderStatusService.updateOrderStatus();
				} catch (Exception e) {
					log.error("UpdateHotelOrderStatusJob " + updateOrderStatusService.getClass().getSimpleName(), e);
				}
			}
		}
	}

	public void setUpdateOrderStatusServices(List<UpdateHotelOrderStatusService> updateOrderStatusServices) {
		this.updateOrderStatusServices = updateOrderStatusServices;
	}
}
