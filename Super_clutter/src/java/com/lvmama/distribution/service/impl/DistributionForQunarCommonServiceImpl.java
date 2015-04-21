package com.lvmama.distribution.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.distribution.service.DistributionForQunarCommonService;

/**
 * 去哪儿线路对接公共服务实现
 * @author Alex.zhang
 *
 */
public class DistributionForQunarCommonServiceImpl extends DistributionCommonServiceImpl implements DistributionForQunarCommonService {
	
	
	private DistributionProductService distributionProductService ;
	
	@Override
	public List<DistributionProduct> getDistributionProductTimePriceList(Map<String, Object> params) {
		return distributionProductService.getDistributionProductTimePriceList(params);
	}

	public void setDistributionProductService(DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}
	
	public List<DistributionProduct> getDistributionProduct(Map<String, Object> pageMap) {
		super.setDistributionProductService(this.distributionProductService);
		return super.getDistributionProduct(pageMap);
	}

	@Override
	public List<ProdProductBranch> getDistributionProductBranchList(Map<String, Object> params, String operateType) {
		Long count = distributionProductService.getDistributionProductBranchCount(params);
		List<ProdProductBranch> results = new ArrayList<ProdProductBranch>();
		if(count!=null && count.intValue()>0){
			double pagesize = 1001d;//系统默认最大的每页数量,因底层的DAO已经限制每次最大返回值为1001。
			int pages = (int)Math.ceil(count.doubleValue()/pagesize);//当前数据量的总页数
			
			for(int i=0;i<pages;i++){
				params.put("start", (pagesize*i)+1);
				params.put("end", pagesize+(pagesize*i));
				
				results.addAll(this.distributionProductService.getDistributionProductBranchList(params, operateType));
			}
		}
		return results;
	}
}