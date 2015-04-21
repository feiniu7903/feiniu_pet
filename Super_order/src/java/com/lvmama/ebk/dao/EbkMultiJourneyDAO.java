package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;

public class EbkMultiJourneyDAO extends BaseIbatisDAO{
    
    public int deleteByPrimaryKey(Long multiJourneyId) {
    	EbkMultiJourney key = new EbkMultiJourney();
        key.setMultiJourneyId(multiJourneyId);
        int rows = super.delete("EBK_MULTI_JOURNEY.deleteByPrimaryKey", key);
        return rows;
    }

    
    public Long insert(EbkMultiJourney record) {
       return (Long) super.insert("EBK_MULTI_JOURNEY.insert", record);
    }
    
    public EbkMultiJourney selectByPrimaryKey(Long multiJourneyId) {
    	EbkMultiJourney key = new EbkMultiJourney();
    	key.setMultiJourneyId(multiJourneyId);
    	EbkMultiJourney record = (EbkMultiJourney) super.queryForObject("EBK_MULTI_JOURNEY.selectByPrimaryKey", key);
        return record;
    }
    
    public int update(EbkMultiJourney record) {
        int rows = super.update("EBK_MULTI_JOURNEY.update", record);
        return rows;
    }

    public Integer selectRowCount(Map<String, Object> params){
		Integer count = 0;
		count = (Integer) super.queryForObject("EBK_MULTI_JOURNEY.selectRowCount", params);
		return count;
	}
    
    public List<EbkMultiJourney> queryMultiJourneyByParams(Map<String, Object> params) {
    	return super.queryForList("EBK_MULTI_JOURNEY.queryMultiJourneyByParams", params);
	}
    
}