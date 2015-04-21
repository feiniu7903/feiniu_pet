package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.TravelTips;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;
import com.lvmama.comm.bee.service.view.ViewTravelTipsService;
import com.lvmama.prd.dao.TravelTipsDAO;
import com.lvmama.prd.dao.ViewTravelTipsDAO;

public class ViewTravelTipsServiceImpl implements ViewTravelTipsService{
	
	private ViewTravelTipsDAO viewTravelTipsDAO;
	
	private TravelTipsDAO travelTipsDAO;	
	
	@Override
	public Long selectByParamCount(Map paramMap) {
		return this.travelTipsDAO.selectByParamCount(paramMap);
	}
	
	@Override
	public Long insertViewTravelTips(ViewTravelTips viewTravelTips) {
		return viewTravelTipsDAO.insert(viewTravelTips);
	}

	@Override
	public List<ViewTravelTips> selectByProductId(Long productId) {
		return viewTravelTipsDAO.selectByProductId(productId);
	}

	@Override
	public int deleteViewTravelTips(Long viewTravelTipsId) {
		return this.viewTravelTipsDAO.deleteByPrimaryKey(viewTravelTipsId);
	}

	@Override
	public Long insertTravelTips(TravelTips travelTips) {
		return this.travelTipsDAO.insert(travelTips);
	}

	@Override
	public List<TravelTips> selectByParam(Map paramMap) {
		paramMap.put("orderby", "TT.ID");
		paramMap.put("order", "desc");		
		return this.travelTipsDAO.selectByParam(paramMap);
	}

	@Override
	public void updateTravelTips(TravelTips travelTips) {
		this.travelTipsDAO.update(travelTips);
	}

	@Override
	public int deleteTravelTips(Long travelTipsId) {
		return this.travelTipsDAO.deleteByPrimaryKey(travelTipsId);
	}
	
	@Override
	public TravelTips selectByTravelTipsId(Long travelTipsId) {
		return this.travelTipsDAO.selectByTravelTipsId(travelTipsId);
	}
	
	@Override
	public ViewTravelTips selectByViewtravelTipsId(Long viewtravelTipsId) {
		return this.viewTravelTipsDAO.selectByViewtravelTipsId(viewtravelTipsId);
	}

	public void setViewTravelTipsDAO(ViewTravelTipsDAO viewTravelTipsDAO) {
		this.viewTravelTipsDAO = viewTravelTipsDAO;
	}

	public void setTravelTipsDAO(TravelTipsDAO travelTipsDAO) {
		this.travelTipsDAO = travelTipsDAO;
	}

	

	
	
}
