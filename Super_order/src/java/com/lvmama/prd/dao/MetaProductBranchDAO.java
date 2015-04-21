/**
 * 
 */
package com.lvmama.prd.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;

/**
 * @author yangbin
 *
 */
public class MetaProductBranchDAO extends BaseIbatisDAO {

	public MetaProductBranch selectBrachByPrimaryKey(Long pk) {
		MetaProductBranch branch = new MetaProductBranch();
		branch.setMetaBranchId(pk);
		return (MetaProductBranch) super.queryForObject(
				"META_PRODUCT_BRANCH.selectBrachByPrimaryKey", branch);
	}
	
	@SuppressWarnings("unchecked")
	public List<MetaProductBranch> selectBranchListByProductId(Long metaProductId){
		Assert.notNull(metaProductId);
		MetaProductBranch branch = new MetaProductBranch();
		branch.setMetaProductId(metaProductId);
		
		return super.queryForList("META_PRODUCT_BRANCH.selectBranchListByProductId",branch);
	}
	
	public List<MetaProductBranch> selectBranchListByParam(Map<String,Object> map){
		Assert.notEmpty(map);
		return super.queryForList("META_PRODUCT_BRANCH.selectBranchListByParam",map);
	}
	
	/**
	 * 查找销售类别关联的采购类别
	 * @param prodBranchId
	 * @return
	 */
	public List<MetaProductBranch> getMetaProductBranchByProdBranchId(Long prodBranchId){
		return super.queryForList(
				"META_PRODUCT_BRANCH.getMetaProductBranchByProdBranchId",
				prodBranchId);
	}
	
	public Long insert(MetaProductBranch branch){
		Long pk=(Long)super.insert("META_PRODUCT_BRANCH.insert",branch);
		return pk;
	}
	
	public void updateByPrimaryKeySelective(MetaProductBranch branch){
		super.update("META_PRODUCT_BRANCH.updateByPrimaryKeySelective", branch);
	}
	
	public void updateByPrimaryKey(MetaProductBranch branch){
		super.update("META_PRODUCT_BRANCH.updateByPrimaryKey", branch);
	}	
	
	/**
	 * 查找采购类别关联的销售产品及类别
	 * @param metaBranchId
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<MetaBranchRelateProdBranch> selectProdProductAndProdBranchByMetaBranchId(Long metaBranchId) {
		return super.queryForList("META_PRODUCT_BRANCH.selectRelativeProdProductBranchByMetaBranchId", metaBranchId);
	}
	
	/**
	 * 通过供应商ID查询供应下的采购产品的全部类别
	 * @param supplierId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MetaProductBranch>selectMetaProductBranchBySupplierId(Long supplierId){
		Map<String,Object> param= new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		return super.queryForList("META_PRODUCT_BRANCH.selectMetaProductBranchBySupplierId", param);
	}
	
	/**
	 * 通过供应商查询该供应商的SupplierType
	 * @param supplierId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectSupplierTypeBySupplierId(Long supplierId) {
		return super.queryForList("META_PRODUCT_BRANCH.selectSupplierTypeBySupplierId",supplierId);
	}
	
	
	/**
	 * 通过供应商Id和代理产品类型货代理产品编号查询采购产品类别
	 * @param param supplierId productIdSupplier productTypeSupplier
	 * @return
	 */
	public List<MetaProductBranch>selectMetaProductBranchBySupplierType(Map<String,Object> param){
		return super.queryForList("META_PRODUCT_BRANCH.selectMetaProductBranchBySupplierId", param);
	}
	
	public List<MetaProductBranch> selectMetaBranchByProductIds(List<Long> ids){
		if(CollectionUtils.isEmpty(ids)){
			return Collections.emptyList();
		}
		return super.queryForList("META_PRODUCT_BRANCH.selectMetaBranchByProductIds",ids);
	}

	public Long getEbkUserMetaBranchCount(Map<String, Object> params){
		return (Long)super.queryForObject("META_PRODUCT_BRANCH.getEbkMetaBranchCount",params);
	}
	public List<MetaProductBranch> getEbkUserMetaBranch(Map<String, Object> params){
		return super.queryForList("META_PRODUCT_BRANCH.getEbkMetaBranch",params);
	}
	public List<MetaProductBranch> getEbkMetaBranchParam(Map<String, Object> params){
		return super.queryForList("META_PRODUCT_BRANCH.getEbkMetaBranchParam",params);
	}
	public List<MetaProductBranch> getEbkMetaBranchByProductId(Long metaProductId){
		return super.queryForList("META_PRODUCT_BRANCH.getEbkMetaBranchByProductId",metaProductId);
	}
	public List<MetaProductBranch> getMetaBranch(Map<String, Object> params){
		return super.queryForList("META_PRODUCT_BRANCH.getMetaBranch",params);
	}
	/**
	 * EBK线路库存维护页详细查询
	 * @param params
	 * @return
	 */
	public List<EbkDayStockDetail> getEbkDayStockDetail(Map<String, Object> params){
		return super.queryForList("META_PRODUCT_BRANCH.selectEbkDayStockDetail",params);
	}
	/**
	 * EBK线路库存维护页查询结果总数，用于分页
	 * @param params
	 * @return
	 */
	public Long getEbkDayStockDetailPageCount(Map<String, Object> params){
		return (Long) super.queryForObject("META_PRODUCT_BRANCH.selectEbkDayStockPageCount",params);
	}
	
	public List<MetaProductBranch> selectMetaBranchsByStationStation(final Long stationStationId){
		MetaProductBranch mpb = new MetaProductBranch();
		mpb.setStationStationId(stationStationId);
		return super.queryForList("META_PRODUCT_BRANCH.selectMetaBranchsByStationStation",mpb);
	}
	
	public Long getProductIdByMetaBranchId(Long metaBranchId){
		return (Long)super.queryForObject("META_PRODUCT_BRANCH.getProductIdByMetaBranchId",metaBranchId);
	}

	public Double findPaymentAmountByBranchId(Long branchId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("branchId", branchId);
		return (Double) super.queryForObject("META_PRODUCT_BRANCH.selectPayMentAmountByBranchId", param);
	}
	
	/**
	 * 根据采购类别ID ,产品类别查询出采购类别的valid
	 * @param metaBranchId,productType
	 * @return valid
	 */
	public List<String> getMetaProductBranchValid(Map<String, Object> params){
		return super.queryForList("META_PRODUCT_BRANCH.getMetaProductBranchValid",params);
	}
}
