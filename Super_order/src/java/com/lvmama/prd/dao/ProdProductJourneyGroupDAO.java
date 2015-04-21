/**
 * 
 */
package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyGroup;

/**
 * @author yangbin
 *
 */
public class ProdProductJourneyGroupDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(Long prodJourenyId) {
        ProdProductJourneyGroup key = new ProdProductJourneyGroup();
        key.setProdJourneyId(prodJourenyId);
        int rows = super.delete("PROD_PRODUCT_JOURNEY_GROUP.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdProductJourneyGroup record) {    	
        Long pk=(Long)super.insert("PROD_PRODUCT_JOURNEY_GROUP.insert", record);
        return pk;
    }

    public void insertSelective(ProdProductJourneyGroup record) {
        super.insert("PROD_PRODUCT_JOURNEY_GROUP.insertSelective", record);
    }

    public ProdProductJourneyGroup selectByPrimaryKey(Long prodJourenyId) {
        ProdProductJourneyGroup key = new ProdProductJourneyGroup();
        key.setProdJourneyId(prodJourenyId);
        ProdProductJourneyGroup record = (ProdProductJourneyGroup) super.queryForObject("PROD_PRODUCT_JOURNEY_GROUP.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdProductJourneyGroup record) {
        int rows = super.update("PROD_PRODUCT_JOURNEY_GROUP.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdProductJourneyGroup record) {
        int rows = super.update("PROD_PRODUCT_JOURNEY_GROUP.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ProdProductJourneyGroup> selectJourneyGroupListByJourneyId(Long journeyId){
    	ProdProductJourneyGroup group=new ProdProductJourneyGroup();
    	group.setProdJourneyId(journeyId);
    	return super.queryForList("PROD_PRODUCT_JOURNEY_GROUP.selectJourneyGroupListByJourneyId", group);
    }
    
    public List<ProdProductJourneyGroup> selectByParam(Map<String,Object> map){
    	Assert.notEmpty(map);    	
    	return super.queryForList("PROD_PRODUCT_JOURNEY_GROUP.selectByParam",map);
    }
}
