package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;

public class DistributionProductDAO extends BaseIbatisDAO{
	

	
	@SuppressWarnings("unchecked")
	public List<ProdProductBranch>	selectProdProductBranchByDistribution(Map<String,Object> params){
		return super.queryForList("DISTRIBUTION_PRODUCT.selectProdProductBranchByDistribution",params);
	}
	
	public List<ProdProductBranch>	selectProdProductBranchByParams(Map<String,Object> params){
		return super.queryForList("DISTRIBUTION_PRODUCT.selectProdProductBranchByParams",params);
	}
	 
	public Long	selectByDistributionCount(Map<String,Object> params){
		return (Long)super.queryForObject("DISTRIBUTION_PRODUCT.selectByDistributionCount",params);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean getProdBranchIdByCondition(Map<String,Object> params){
		Boolean count =Boolean.FALSE;
		List<Long> list = super.queryForList("DISTRIBUTION_PRODUCT.selectProdBranchIdByCondition", params);
		if(list!=null && !list.isEmpty()){
			count = Boolean.TRUE;
		}else{
			count = Boolean.FALSE;
		}
		return count;
	}
	
	/**
	 * 新增销售产品
	 * @param productId
	 * @param productBranchId
	 * @param distributionProductId
	 */
	public void insert(DistributionProduct distributionProduct){
		super.insert("DISTRIBUTION_PRODUCT.insert", distributionProduct);
	}
	public void updateVolid(DistributionProduct distributionProduct){
		super.insert("DISTRIBUTION_PRODUCT.updateVolid", distributionProduct);
	}
	/**
	 * 更新销售产品
	 * @param productId
	 * @param productBranchId
	 * @param distributorInfoId
	 * @param valid
	 */
	public void updateVolidByParams(Long productId, Long productBranchId, Long distributorInfoId, String valid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("productBranchId", productBranchId);
		params.put("distributorInfoId", distributorInfoId);
		params.put("valid", valid);
		super.update("DISTRIBUTION_PRODUCT.updateVolidByProductId", params);
	}

	@SuppressWarnings("unchecked")
	public List<ProdProduct> selectDistributionProductInfo(Map<String,Object> params){
		return (List<ProdProduct>)super.queryForList("DISTRIBUTION_PRODUCT.selectDistributionProductInfo",params);
	}
	
	public Long selectDistributionProductInfoCount(Map<String, Object> params) {
		return (Long) super.queryForObject("DISTRIBUTION_PRODUCT.selectDistributionProductInfoCount", params);
	}
	@SuppressWarnings("unchecked")
	public List<DistributionProduct> selectByParams(Map<String, Object> params) {
		return (List<DistributionProduct>)super.queryForList("DISTRIBUTION_PRODUCT.selectByParams",params);
	}
	public void updateCommentsCashback(DistributionProduct distributionProduct) {
		super.insert("DISTRIBUTION_PRODUCT.updateCommentsCashback", distributionProduct);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DistributionProduct> selectAllByParams(Map<String, Object> params) {
		return (List<DistributionProduct>)super.queryForListForReport("DISTRIBUTION_PRODUCT.selectByParams",params);
	}
}
