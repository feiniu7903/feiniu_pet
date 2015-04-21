/**
 * 
 */
package com.lvmama.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdTrainFetchInfo;
import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class UpdateTrainJob implements Runnable{
	private final static Log logger = LogFactory.getLog(UpdateTrainJob.class);
	private ProdTrainService prodTrainService;
	private TrainDataSyncService trainDataSyncService;
	private ProdTrainCacheService prodTrainCacheService;
	private static final String REQUEST_TYPE = "update";

	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()&&Constant.getInstance().isTrainJobRunnable()){
			logger.info("start update train job");
			List<ProdTrainFetchInfo> list = prodTrainService.selectFetchInfoList();
			boolean isSuccess;
			if(!list.isEmpty()){
				logger.info("fetch info size:"+list.size());
				for(ProdTrainFetchInfo info:list){
					try{
						String requestDate = DateUtil.formatDate(info.getVisitTime(), "yyyy-MM-dd");
						isSuccess = false;
						if(info.getFetchKey().equals(Constant.TRAIN_INTERFACE.STATION_QUERY.getStringCode())){
							isSuccess = trainDataSyncService.syncStationInfo(REQUEST_TYPE, requestDate, null);
							prodTrainService.initTrainCacheMap(false);
						}else if(info.getFetchKey().equals(Constant.TRAIN_INTERFACE.CITY_STATION_QUERY.getStringCode())){
							isSuccess = trainDataSyncService.syncCityStationInfo(REQUEST_TYPE, requestDate, null);
							prodTrainService.initTrainCacheMap(false);
						}else if(info.getFetchKey().equals(Constant.TRAIN_INTERFACE.TRAIN_INFO_QUERY.getStringCode())){
							isSuccess = trainDataSyncService.syncLineInfo(REQUEST_TYPE, requestDate, null);
							prodTrainService.initTrainCacheMap(false);
						}else if(info.getFetchKey().equals(Constant.TRAIN_INTERFACE.TRAIN_PASS_QUERY.getStringCode())){
							isSuccess = trainDataSyncService.syncLineStopsInfo(REQUEST_TYPE, requestDate, null);
						}else if(info.getFetchKey().equals(Constant.TRAIN_INTERFACE.TRAIN_PRICE_QUERY.getStringCode())){
							isSuccess = trainDataSyncService.syncTicketPriceInfo(REQUEST_TYPE, requestDate, null, null, null);
						}
						if(!isSuccess) continue;
						
	//					if(prodTrainCacheService.existKey(info.getFetchKey(),info.getVisitTime())){//如果存在就更新
	//						if(STATION_2_STATION_UPDATE.equals(info.getFetchCatalog())){
	//							handleStation2StationUpdate(info.getFetchKey(), info.getVisitTime());
	//						}else if(LINE_INFO_UPDATE.equals(info.getFetchCatalog())){
	//							handleLineInfoUpdate(info.getFetchKey(), info.getVisitTime());
	//						}
	//						info.setFetchStatus(ProdTrainFetchInfo.STATUS.COMPLETE.name());
	//					}
						info.setFetchStatus(ProdTrainFetchInfo.STATUS.COMPLETE.name());
						prodTrainService.updateFetchInfoStatus(info);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
			logger.info("end update train job");
		}
	}
	
	public void runCopy(){
		if(Constant.getInstance().isJobRunnable()){
			if(Constant.getInstance().isTrainJobRunnable()){
				logger.info("begin copy train cache to new date.");
				Date today = DateUtil.getDayStart(new Date());
				Date date = DateUtils.addDays(today, 18);//只能显示最近的20天。该日期为第19天
				prodTrainCacheService.copyDataToNewDay(date);
				logger.info("end copy train cache to new date.");
			}
		}
	}

	
	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}
	public void setTrainDataSyncService(
			TrainDataSyncService trainDataSyncService) {
		this.trainDataSyncService = trainDataSyncService;
	}
	public ProdTrainCacheService getProdTrainCacheService() {
		return prodTrainCacheService;
	}
	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

}
