package com.lvmama.pet.prod.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdContainerProduct;

public class ProdContainerProductDAO extends BaseIbatisDAO{
	/**
	 * 删除不可用的产品
	 */
	public void deleteInvalidContainerProducts() {
		super.delete("PROD_CONTAINER_PRODUCT.deleteInvalidContainerProducts");
	}
	/**
	 * 根据containnerId和tagId删除数据
	 * @param containerId
	 * @param tagId
	 */
	public void deleteContainerProductsByTagId(Long containerId,Long tagId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("containerId", containerId);
		param.put("tagId", tagId);
		super.delete("PROD_CONTAINER_PRODUCT.deleteContainerProductsByTagId",param);
	}
	
	public void deleteNoUsedContainerProducts(List<Long> usedContainerIdList, Long productId) {
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("usedContainerIdList", usedContainerIdList);
			params.put("productId", productId);
			super.delete("PROD_CONTAINER_PRODUCT.deleteNoUsedContainerProducts", params);
		
	}
	public Long insertContainerProduct(Long containerId, Long productId, Long seq) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerId", containerId);
		params.put("productId", productId);
		params.put("seq", seq);
		return (Long) super.insert("PROD_CONTAINER_PRODUCT.insertContainerProduct", params);
	}
	
	public int queryContainerProductCount(Long containerId, Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerId", containerId);
		params.put("productId", productId);
		return (Integer) super.queryForObject("PROD_CONTAINER_PRODUCT.queryContainerProductCount", params);
	}
	
	public int deleteContainerProduct(Long containerId, Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerId", containerId);
		params.put("productId", productId);
		return super.delete("PROD_CONTAINER_PRODUCT.deleteContainerProduct", params);
	}
	public List<Long> selectContainerIdListProductAlreadyExists(Long productId) {
		return super.queryForList("PROD_CONTAINER_PRODUCT.selectContainerIdListProductAlreadyExists", productId);
	}
	
	public Long getContainerProductListCount(Map<String,Object> params) {
		return (Long) super.queryForObject("PROD_CONTAINER_PRODUCT.getContainerProductListCount", params);
	}
	public List<ProdContainerProduct> getContainerProductList(Map<String,Object> params) {
		return super.queryForList("PROD_CONTAINER_PRODUCT.getContainerProductList", params);
	}
	
	public int setRecommendSeq(Long containerProductId, int recommendSeq) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerProductId", containerProductId);
		params.put("recommendSeq", recommendSeq);
		return super.update("PROD_CONTAINER_PRODUCT.setRecommendSeq", params);
	}
	
	public int containerProductShowOrHide(Long containerProductId, String isValid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerProductId", containerProductId);
		params.put("isValid", isValid);
		return super.update("PROD_CONTAINER_PRODUCT.restoreRecommendSeq", params);
	}
	
	public int deleteInvalidTagContainerProducts(Long containerId, String validProductIdStr) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerId", containerId);
		params.put("validProductIdStr", validProductIdStr);
		return super.delete("PROD_CONTAINER_PRODUCT.deleteInvalidTagContainerProducts", params);
	}
}
