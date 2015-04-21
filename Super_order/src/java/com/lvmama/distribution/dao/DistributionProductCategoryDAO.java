package com.lvmama.distribution.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;

public class DistributionProductCategoryDAO extends BaseIbatisDAO{
	
	/**
	 * 通过商户ID查分销类型
	 * @param serialNo
	 * @return IP信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DistributionProductCategory> selectByParams(Map<String,Object> params) {
		return (List<DistributionProductCategory>) super.queryForList("DISTRIBUTION_PROD_CATEGORY.selectByParams", params);
	}
	/**
	 * 查所有的分销类型
	 * @param serialNo
	 * @return IP信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DistributionProductCategory> selectAllDistributionProdCategory() {
		return (List<DistributionProductCategory>) super.queryForList("DISTRIBUTION_PROD_CATEGORY.selectByParams");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DistributionProductCategory> selectListDistributionProductCategory(Long distributorInfoId) {
		return (List<DistributionProductCategory>)super.queryForList("DISTRIBUTION_PROD_CATEGORY.selectListDistributionProductCategory",distributorInfoId);
	}
	
	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(DistributionProductCategory distributorCategory) {
		super.insert("DISTRIBUTION_PROD_CATEGORY.insert", distributorCategory);
	}
	
	/**
	 * 通过主键更新更新分销类型
	 */
	public void updateBydistributionProductCategoryId(DistributionProductCategory distributorCategory) {
		super.update("DISTRIBUTION_PROD_CATEGORY.updateByDistributorIpId", distributorCategory);
	}
	
	/**
	 * 通过主键删除分销类型
	 */
	public void deleteBydistributionProductCategoryId(Long distributionProductCategoryId) {
		super.delete("DISTRIBUTION_PROD_CATEGORY.deleteBydistributionProductCategoryId", distributionProductCategoryId);
	}
	
	/**
	 * 按条件查询是否存在分销返佣点 
	 */
	public Long selectPistributionProductCategoryConditionByCount(Map<String,Object> params){
		return (Long)super.queryForObject("DISTRIBUTION_PROD_CATEGORY.selectPistributionProductCategoryConditionByCount", params);
	}
}
