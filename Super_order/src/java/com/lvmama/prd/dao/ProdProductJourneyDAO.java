package com.lvmama.prd.dao;

import java.util.List;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;

public class ProdProductJourneyDAO extends BaseIbatisDAO {

    public ProdProductJourneyDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long prodJourenyId) {
        ProdProductJourney key = new ProdProductJourney();
        key.setProdJourenyId(prodJourenyId);
        int rows = super.delete("PROD_PRODUCT_JOURNEY.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdProductJourney record) {
    	Assert.notNull(record);    	
        return (Long)super.insert("PROD_PRODUCT_JOURNEY.insert", record);
    }

    public void insertSelective(ProdProductJourney record) {
        super.insert("PROD_PRODUCT_JOURNEY.insertSelective", record);
    }

    public ProdProductJourney selectByPrimaryKey(Long prodJourenyId) {
        ProdProductJourney key = new ProdProductJourney();
        key.setProdJourenyId(prodJourenyId);
        ProdProductJourney record = (ProdProductJourney) super.queryForObject("PROD_PRODUCT_JOURNEY.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdProductJourney record) {
        int rows = super.update("PROD_PRODUCT_JOURNEY.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdProductJourney record) {
        int rows = super.update("PROD_PRODUCT_JOURNEY.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ProdProductJourney> selectListByProductId(Long productId){
    	Assert.notNull(productId);
    	ProdProductJourney record=new ProdProductJourney();
    	record.setProductId(productId);
    	return super.queryForList("PROD_PRODUCT_JOURNEY.selectListByProductId", record);
    }
}