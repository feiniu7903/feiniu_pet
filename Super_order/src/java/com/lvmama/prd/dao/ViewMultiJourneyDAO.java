package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;

public class ViewMultiJourneyDAO extends BaseIbatisDAO{
    
    public int deleteByPrimaryKey(Long multiJourneyId) {
        ViewMultiJourney key = new ViewMultiJourney();
        key.setMultiJourneyId(multiJourneyId);
        int rows = super.delete("VIEW_MULTI_JOURNEY.deleteByPrimaryKey", key);
        return rows;
    }

    
    public Long insert(ViewMultiJourney record) {
       return (Long) super.insert("VIEW_MULTI_JOURNEY.insert", record);
    }
    
    public ViewMultiJourney selectByPrimaryKey(Long multiJourneyId) {
    	ViewMultiJourney key = new ViewMultiJourney();
    	key.setMultiJourneyId(multiJourneyId);
    	ViewMultiJourney record = (ViewMultiJourney) super.queryForObject("VIEW_MULTI_JOURNEY.selectByPrimaryKey", key);
        return record;
    }
    
    public int update(ViewMultiJourney record) {
        int rows = super.update("VIEW_MULTI_JOURNEY.update", record);
        return rows;
    }

    public Integer selectRowCount(Map<String, Object> params){
		Integer count = 0;
		count = (Integer) super.queryForObject("VIEW_MULTI_JOURNEY.selectRowCount", params);
		return count;
	}
    
    public List<ViewMultiJourney> queryMultiJourneyByParams(Map<String, Object> params) {
    	return super.queryForList("VIEW_MULTI_JOURNEY.queryMultiJourneyByParams", params);
	}
    
}