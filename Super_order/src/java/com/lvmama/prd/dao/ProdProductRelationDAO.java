package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;

public class ProdProductRelationDAO extends BaseIbatisDAO {

    public ProdProductRelationDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long relationId) {
        ProdProductRelation key = new ProdProductRelation();
        key.setRelationId(relationId);
        int rows = super.delete("PROD_PRODUCT_RELATION.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdProductRelation record) {
        Long pk=(Long)super.insert("PROD_PRODUCT_RELATION.insert", record);
        return pk;
    }

    public void insertSelective(ProdProductRelation record) {
        super.insert("PROD_PRODUCT_RELATION.insertSelective", record);
    }

    public ProdProductRelation selectByPrimaryKey(Long relationId) {
        ProdProductRelation key = new ProdProductRelation();
        key.setRelationId(relationId);
        ProdProductRelation record = (ProdProductRelation) super.queryForObject("PROD_PRODUCT_RELATION.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdProductRelation record) {
        int rows = super.update("PROD_PRODUCT_RELATION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdProductRelation record) {
        int rows = super.update("PROD_PRODUCT_RELATION.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ProdProductRelation> getProductRelationByProductId(Long productId){
    	return super.queryForList("PROD_PRODUCT_RELATION.selectByProductId", productId);
    }
    
    public List<ProdProductRelation> selectProdRelationByParam(Map<String,Object> map){
    	return super.queryForList("PROD_PRODUCT_RELATION.selectProdRelationByParam", map);
    }
}