package com.lvmama.back.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.vo.Constant;

public class TaobaoProductSyncJob implements Runnable {
private static final Log log = LogFactory.getLog(TaobaoProductSyncJob.class);
	
	private TaobaoProductSyncService taobaoProductSyncService;
	
	@Override
	public void run(){
		if(Constant.getInstance().isJobRunnable() && Constant.getInstance().getTaobaoProductSyncEnabled()){
			log.info("TaobaoProductSyncJob start!");
			syncTaobaoProduct();
			log.info("TaobaoProductSyncJob end!");
		}
	}
	
	private void syncTaobaoProduct() {
		// 空的查询条件，可以获取所有的数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("not_null_productId", "1");
		params.put("not_null_prodBranchId", "1");
		params.put("isSync", "1");
		// 更新门票
		List<Long> ticketSkuIdList = taobaoProductSyncService.getTicketSkuId(params);
		for (Long ticketSkuId : ticketSkuIdList) {
            try {
                taobaoProductSyncService.updateTaobaoTicketSkuEffDates(ticketSkuId);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Auto update taobaoTicket sku error! ticketSkuId=" + ticketSkuId, e);
            }
		}
		// 更新线路
		List<Long> travelComboIds = taobaoProductSyncService.getTravelToTravelComboId(params);
		for (Long travelComboId : travelComboIds) {
			try {
				taobaoProductSyncService.updateTaobaoTravelComboCalendar(travelComboId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Auto update taobaoTravel combo error! travelComboId=" + travelComboId, e);
			}
		}
		
	}

	public TaobaoProductSyncService getTaobaoProductSyncService() {
		return taobaoProductSyncService;
	}

	public void setTaobaoProductSyncService(
			TaobaoProductSyncService taobaoProductSyncService) {
		this.taobaoProductSyncService = taobaoProductSyncService;
	}
}
