package com.lvmama.back.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

public class AutoUpdateCommentsCashbackJob {

	private static final Log log = LogFactory.getLog(AutoUpdateCommentsCashbackJob.class);
	
	private DistributionService distributionService;
	private DistributionProductService distributionProductService;
	private ComLogService comLogRemoteService;
	
	public void run(){
		if(!Constant.getInstance().isJobRunnable()){
			return ;
		}
		log.info("自动更新评论返现值任务开始");
		DistributorInfo distributorInfo = distributionService.selectByDistributorCode(Constant.DISTRIBUTOR.QUNA_TICKET.getCode());
		if(distributorInfo!=null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("volid", "true");
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> list = distributionProductService.selectAllByParams(params);
			for(DistributionProduct distributionProduct:list){
				if(!"false".equalsIgnoreCase(distributionProduct.getNeedAutoUpdateCashBack())){
					try {
						distributionProduct.setNeedAutoUpdateCashBack("true");
						distributionProductService.autoUpdateCommentsCashback(distributionProduct);
						comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "WHITE_LIST", "修改分销产品", "系统自动更新评论返现值", null);
					} catch (Exception e) {
						log.error("AutoUpdateCommentsCashbackJob Exception",e);
					}
				}
			}
		}
		log.info("自动更新评论返现值任务结束");
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public void setDistributionProductService(
			DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
	
	
}
