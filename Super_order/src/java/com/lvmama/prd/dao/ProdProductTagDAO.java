package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdProductTag;

public class ProdProductTagDAO extends BaseIbatisDAO {
	
	public void deleteProdProductTagTimeOut(){
		 super.delete("PROD_PRODUCT_TAG.deleteProdProductTagTimeOut");
	 }
    public int deleteByPrimaryKey(ProdProductTag prodProductTag) {
        int rows = super.delete("PROD_PRODUCT_TAG.deleteByPrimaryKey", prodProductTag);
        return rows;
    }
     
    public int deleteByTagId(Long tagId) {
        int rows = super.delete("PROD_PRODUCT_TAG.deleteByTagId", tagId);
        return rows;
    }
    
    public void deleteProductTags(ProdProductTag prodProductTag){
    	super.delete("PROD_PRODUCT_TAG.deleteProductTag",prodProductTag);
    }
    
    public ProdProductTag insertSelective(ProdProductTag record) {
    	super.insert("PROD_PRODUCT_TAG.insertSelective", record);
        return record;
    }

    public ProdProductTag selectByPrimaryKey(Long productTagId) {
        ProdProductTag key = new ProdProductTag();
        key.setProductTagId(productTagId);
        ProdProductTag record = (ProdProductTag) super.queryForObject("PROD_PRODUCT_TAG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdProductTag record) {
        int rows = super.update("PROD_PRODUCT_TAG.updateByPrimaryKeySelective", record);
        return rows;
    }

    @SuppressWarnings("unchecked")
	public List<ProdProductTag> selectByTagId(Long tagId) {
		return super.queryForList("PROD_PRODUCT_TAG.selectByTagId", tagId);
	}
    
    public int deleteByProductTagId(Long productTagId) {
		return super.delete("PROD_PRODUCT_TAG.deleteByProductTagId", productTagId);
	}
    
    @SuppressWarnings("unchecked")
	public List<ProdProductTag> selectByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PRODUCT_TAG.selectByParams", params);
	}
    
    public Integer selectRowCount(Map<String, Object> params) {
    	return (Integer) super.queryForObject("PROD_PRODUCT_TAG.selectByParamsCount", params);
    }

	public void deleteByProductIdAndTagId(Map<String, Object> params) {
		super.delete("PROD_PRODUCT_TAG.deleteByProductIdAndTagId", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProductTag> selectProdProductByParams(ProdProductTag prodProductTag){
		return super.queryForList("PROD_PRODUCT_TAG.selectProdProductByParams",prodProductTag);
	}
	@SuppressWarnings("unchecked")
	public List<ProdProductTag> selectProductTagByProductIdAndTagGroupId(Long productId,Long tagGroupId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("tagGroupId", tagGroupId);
		return super.queryForList("PROD_PRODUCT_TAG.selectProductTagByProductIdAndTagGroupId", params);
	}

	@SuppressWarnings("unchecked")
	public List<ProdProductTag> selectProductTagByProductIdAndTagId(ProdProductTag productTag) {
		return super.queryForList("PROD_PRODUCT_TAG.selectProductTagByProductIdAndTagId", productTag);
	}
	
	@SuppressWarnings("unchecked")
    public List<ProdProductTag> selectProductTagByProductId(Long productId) {
	    return super.queryForList("PROD_PRODUCT_TAG.selectProductTagByProductId", productId);
	}
	
	/**
	 * 根据多个产品Id和一个tagId做删除动作
	 * 
	 * @param params
	 */
	public void deleteByProductIdsAndTagId(Map<String, Object> params) {
		super.delete("PROD_PRODUCT_TAG.deleteByProductIdsAndTagId", params);
	}
}