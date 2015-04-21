package com.lvmama.back.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.DownTmallDistributorOrderInterface;
import com.lvmama.comm.bee.service.tmall.OrdTmallDistributorMapService;
import com.lvmama.comm.vo.Constant;

public class DownOrderTmallDistributorJob {
	
	private static final Log log = LogFactory.getLog(DownOrderTmallDistributorJob.class);
	
	private DownTmallDistributorOrderInterface downTmallDistributorOrderInterface;
	private OrdTmallDistributorMapService ordTmallDistributorMapService;
	/***
	 * 由于淘宝订单会出现一对多的情况，即淘宝订单id可能会出现多个相同的，所以分批处理每次 只处理一条然后 根据状态进行过滤
	 */
	public void run() {
		if (Constant.getInstance().isJobRunnable() && Constant.getInstance().isSyncTmallDistributorOrder()) {
			log.info("DownOrderTmallDistributorJob begin run.");
			try{
				List<String> fenXiaoIdList=ordTmallDistributorMapService.selectOrdOfCreate();
				if(fenXiaoIdList!=null && fenXiaoIdList.size()>0){
					for(String fenXiaoId:fenXiaoIdList){
						downTmallDistributorOrderInterface.backDownDistributorOrder(Long.valueOf(fenXiaoId));
					}
				}
			}catch (Exception e){
				log.error(this.getClass(), e);
			}
			log.info("DownOrderTmallDistributorJob end run.");
		}
	}
	public void setDownTmallDistributorOrderInterface(
			DownTmallDistributorOrderInterface downTmallDistributorOrderInterface) {
		this.downTmallDistributorOrderInterface = downTmallDistributorOrderInterface;
	}
	public void setOrdTmallDistributorMapService(
			OrdTmallDistributorMapService ordTmallDistributorMapService) {
		this.ordTmallDistributorMapService = ordTmallDistributorMapService;
	}
	
}
