package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.pet.po.place.Place;

public interface DistributionProductService {

	/**
	 * 查询指定产品类别级分销商信息
	 * 
	 * @param productId
	 * @param type
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId, String type);
	/**
	 * 查询指定产品类别级分销商信息
	 * 
	 * @param productId
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId);
	
	/**
	 * 根据条件查询分销商产品总数
	 */
	Long getDistributionProductBranchCount(Map<String, Object> params);
	/**
	 * 根据条件查询分销商产品信息
	 */
	List<ProdProductBranch> getDistributionProductBranchList(Map<String, Object> params, String type);
	
	/**
	 * 根据条件查询分销商产品信息
	 */
	List<ProdProductBranch> getProductBranchList(Map<String, Object> params, String type);
	/**
	 * 根据条件查询分销商产品信息
	 * @param branchId 
	 * @param distributorInfoId 
	 */
	DistributionProduct getDistributionProductByBranchId(Long branchId, Long distributorInfoId);

	/**
	 * 查询所有可分销的产品类别
	 */
	public List<ProdProductBranch> selectProdCanDistributeByProdType(Map<String, Object> params);

	/**
	 * 根据条件查询分销商产品数目
	 */

	Long getDistributionProductListCount(Map<String, Object> params);

	/**
	 * 根据分销商查询分销产品类别
	 */
	public Boolean selectDistributionProductBranchList(Map<String, Object> params);
	
	Long getDistributionProductCount(Map<String, Object> params);
	
	List<DistributionProduct> getDistributionProductList(Map<String, Object> params);

	List<DistributionProduct> getDistributionOnline(Map<String, Object> params);

	List<DistributionProduct> getDistributionProductTimePriceList(Map<String, Object> params);
	
	/**
	 * 判断分销产品类别利润是否为0，来决定是否可售
	 * @param params
	 * @param timeprice
	 * @return
	 */
	public boolean isSellableDistributionProductTimePrice(Map<String,Object> params,TimePrice timeprice);

	void saveDistributionProduct(Long distributorId, Long branchId, Long productId);

	/**
	 * 增加分销商产品
	 * 
	 * @author: ranlongfei 2013-6-25
	 * @param distributorIds
	 * @param productId
	 * @param branchId
	 */
	void saveDistributionProduct(List<Long> distributorIds, Long productId, Long branchId);
	/**
	 * 修改分销商产品的有效性（true白名单，false黑名单）
	 * 
	 * @author: ranlongfei 2013-6-25
	 * @param branchId
	 * @param distributorIds
	 * @param volid
	 */
	void updateDistributionProductVolid(Long branchId, List<Long> distributorIds, String volid);
	/**
	 * 根据产品id修改分销商产品的有效性（true白名单，false黑名单）
	 * 
	 * @param productId
	 * @param valid
	 */
	void updateDistributionProductVolid(Long productId, String valid);
	
	void checkCancel(Long productId, String payTarget);

	public void autoUpdateCommentsCashback(DistributionProduct distributionProduct);
	
	public void updateCommentsCashback(DistributionProduct distributionProduct);
	
	public List<DistributionProduct> selectAllByParams(Map<String, Object> params);

}
