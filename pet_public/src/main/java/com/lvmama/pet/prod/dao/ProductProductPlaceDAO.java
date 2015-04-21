package com.lvmama.pet.prod.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProductProductPlace;

public class ProductProductPlaceDAO extends BaseIbatisDAO {
	public void insert(final ProductProductPlace productProductPlace) {
		if (null != productProductPlace) {
			super.insert("PRODUCT_PRODUCT_PLACE.insert",productProductPlace);
		}
	}
	
	/**
	 * 删除与产品的所有关联关系
	 * 
	 * @param productProductId
	 */
	public void deleteByProductId(final Long productId) {
		if (null != productId) {
			super.delete("PRODUCT_PRODUCT_PLACE.productProductPlaceDeleteByProductId", productId);
		}
	}
	
	/**
	 * @deprecated
	 * @param productId
	 * @param placeId
	 */
	public void update(Long productId,Long placeId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("productId", productId);
		param.put("placeId", placeId);
		super.update("PRODUCT_PRODUCT_PLACE.update",param);
	}
	
	public void updateById(String isOriginal,String isToPlace, Long id){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("isOriginal", isOriginal);
		param.put("isToPlace", isToPlace);
		param.put("id", id);
		super.update("PRODUCT_PRODUCT_PLACE.updateById",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductProductPlace> query(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (List<ProductProductPlace>) super.queryForList("PRODUCT_PRODUCT_PLACE.query", param);
		} 
		return null;
	}
	
	public List<ProductProductPlace> queryProductProductPlaceListByProductId(Long productId){
		return queryForList("PRODUCT_PRODUCT_PLACE.queryProductProductPlaceListByProductId",productId);
	}

}
