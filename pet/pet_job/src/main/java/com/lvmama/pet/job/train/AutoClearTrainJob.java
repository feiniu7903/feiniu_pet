/**
 * 
 */
package com.lvmama.pet.job.train;

import java.util.Date;

import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.vo.Constant;

/**
 * 清除已经过期的车票信息,规则以当前日期删除当天之前的数据
 * @author yangbin
 *
 */
public class AutoClearTrainJob implements Runnable{

	private ProdTrainCacheService prodTrainCacheService;
	
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			prodTrainCacheService.removeNotValidTrains(new Date());
		}
	}

	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

	
}
