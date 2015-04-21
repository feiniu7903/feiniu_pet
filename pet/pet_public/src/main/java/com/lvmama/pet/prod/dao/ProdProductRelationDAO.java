package com.lvmama.pet.prod.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;

public class ProdProductRelationDAO extends BaseIbatisDAO {
	/**
	 * 新增产品的关联关系
	 * @param relation 关联关系
	 * @return 新增的关联关系
	 */
	public ProdProductRelation insert(ProdProductRelation relation) {
		if (null != relation) {
			super.insert("PROD_PRODUCT_RELATION.insert", relation);
			return relation;
		} else {
			return null;
		}	 
	}
	
	/**
	 * 根据查询条件查询相关的关联关系
	 * @param param 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProductRelation> queryByMap(Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return null;
		} else {
			return (List<ProdProductRelation>) super.queryForList("PROD_PRODUCT_RELATION.query", param); 
		}
	}
	
	/**
	 * 根据查询条件计算符合条件的记录数
	 * @param param
	 * @return
	 */
	public Long count(Map<String, Object> param) {
		return (Long) super.queryForObject("PROD_PRODUCT_RELATION.count", param);
	}
	
	/**
	 * 根据关联标识查找关联关系
	 * @param relationId 关联标识
	 * @return
	 */
	public ProdProductRelation queryProdProductRelationByPK(Long relationId) {
		if (null == relationId) {
			return null;
		}
		return (ProdProductRelation) super.queryForObject("PROD_PRODUCT_RELATION.selectByPrimaryKey", relationId);
	}
}
