package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.pet.po.pub.CodeItem;

public class ViewJourneyPlaceDAO extends BaseIbatisDAO{

    
    public int deleteByPrimaryKey(Long journeyTargetId) {
        ViewJourneyPlace key = new ViewJourneyPlace();
        key.setJourneyPlaceId(journeyTargetId);
        int rows = super.delete("VIEW_JOURNEY_PLACE.deleteByPrimaryKey", key);
        return rows;
    }

    
    public void insert(ViewJourneyPlace record) {
        super.insert("VIEW_JOURNEY_PLACE.insert", record);
    }

    
    public ViewJourneyPlace selectByPrimaryKey(Long journeyTargetId) {
        ViewJourneyPlace key = new ViewJourneyPlace();
        key.setJourneyPlaceId(journeyTargetId);
        ViewJourneyPlace record = (ViewJourneyPlace) super.queryForObject("VIEW_JOURNEY_PLACE.selectByPrimaryKey", key);
        return record;
    }
    
    
    public int updateByPrimaryKey(ViewJourneyPlace record) {
        int rows = super.update("VIEW_JOURNEY_PLACE.updateByPrimaryKey", record);
        return rows;
    }

	public List<CodeItem> getProdTarget(Long pageId) {
		List<CodeItem> list = super.queryForList("VIEW_JOURNEY_PLACE.select_sup_prod_target", pageId);
		return list;
	}

	public void deleteByJourneyid(Long journeyId) {
		super.delete("VIEW_JOURNEY_PLACE.deleteByJourneyId",journeyId);
		
	}


	public List<ViewJourneyPlace> selectByJourneyId(Long journeyId) {
		return super.queryForList("VIEW_JOURNEY_PLACE.selectByJourneyId", journeyId);
	}

}