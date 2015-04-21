package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;

/**
 * 去哪儿 Service
 * 
 * @author Alex.zhang
 */
public interface DistributionForQunarCommonService {

	/**
	 * 根据条件取分销产品
	 * 
	 * @param pageMap
	 * @return
	 */
	public List<DistributionProduct> getDistributionProduct(Map<String, Object> pageMap);

	/**
	 * 取分销产品总数
	 * 
	 * @param objectMap
	 * @return
	 */
	public Long getDistributionProductCount(Map<String, Object> pageMap);


	/**
	 * 根据分销商帐号查询分销商
	 * 
	 * @param partnerCode
	 * @return
	 */
	public DistributorInfo getDistributorByCode(String partnerCode);

	/**
	 * 根据分销商Id查询分销商Ip白名单
	 * 
	 * @param distributorInfoId
	 * @return
	 */
	public List<String> getDistributorIps(Long distributorInfoId);
	
	public List<DistributionProduct> getDistributionProductTimePriceList(Map<String, Object> params);
	
	List<ProdProductBranch> getDistributionProductBranchList(Map<String, Object> params, String type);
}
