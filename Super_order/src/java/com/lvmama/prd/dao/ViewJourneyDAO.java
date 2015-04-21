package com.lvmama.prd.dao;

import java.util.List;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.pet.po.place.Place;

public class ViewJourneyDAO extends BaseIbatisDAO{

	public int deleteByPrimaryKey(Long journeyId) {
        ViewJourney key = new ViewJourney();
        key.setJourneyId(journeyId);
        int rows = super.delete("VIEW_JOURNEY.deleteByPrimaryKey", key);
        return rows;
    }

    
    public void insert(ViewJourney record) {
        super.insert("VIEW_JOURNEY.insert", record);
    }
    
    public ViewJourney selectByPrimaryKey(Long journeyId) {
        ViewJourney key = new ViewJourney();
        key.setJourneyId(journeyId);
        ViewJourney record = (ViewJourney) super.queryForObject("VIEW_JOURNEY.selectByPrimaryKey", key);
        return record;
    }
    
    public int updateByPrimaryKey(ViewJourney record) {
        int rows = super.update("VIEW_JOURNEY.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * add by shihui
     * */
    public List<ViewJourney> getViewJourneysByProductId(Long productId) {
    	List<ViewJourney> viewJList = super.queryForList("VIEW_JOURNEY.getViewJourneysByProductId",productId);
    	if(viewJList != null) {
			for (int i = 0; i < viewJList.size(); i++) {
				ViewJourney viewJourney = viewJList.get(i);
				long journeyId = viewJourney.getJourneyId();
				List<Place> comPlaceList = super.queryForList("COM_PLACE.getComPlace", journeyId);
				viewJourney.setPlaceList(comPlaceList);
			}
    	}
		return viewJList;
	}
    
    public List<ViewJourney> getViewJourneyByMultiJourneyId(Long multiJourneyId) {
    	List<ViewJourney> viewJList = super.queryForList("VIEW_JOURNEY.getViewJourneyByMultiJourneyId", multiJourneyId);
    	if(viewJList != null) {
			for (int i = 0; i < viewJList.size(); i++) {
				ViewJourney viewJourney = viewJList.get(i);
				long journeyId = viewJourney.getJourneyId();
				List<Place> comPlaceList = super.queryForList("COM_PLACE.getComPlace", journeyId);
				viewJourney.setPlaceList(comPlaceList);
			}
		}
    	return viewJList;
    }
}