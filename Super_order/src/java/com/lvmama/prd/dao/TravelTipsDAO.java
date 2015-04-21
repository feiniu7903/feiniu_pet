package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.TravelTips;

public class TravelTipsDAO extends BaseIbatisDAO{

	public Long selectByParamCount(Map paramMap){
		return (Long)super.queryForObject("TRAVEL_TIPS.selectTravelTipsByParamCount", paramMap);
	}
	
	public List<TravelTips> selectByParam(Map paramMap){
		return super.queryForList("TRAVEL_TIPS.selectTravelTipsByParam", paramMap);
	}
	
	public TravelTips selectByTravelTipsId(Long travelTipsId){
		return (TravelTips)super.queryForObject("TRAVEL_TIPS.selectByTravelTipsId", travelTipsId);
	}
	
	public void update(TravelTips travelTips){
		super.update("TRAVEL_TIPS.update", travelTips);
	}
	
	public Long insert(TravelTips travelTips){
		return (Long)super.insert("TRAVEL_TIPS.insert", travelTips);
	}
	
	public int deleteByPrimaryKey(Long travelTipsId){
		TravelTips travelTips = new TravelTips();
		travelTips.setTravelTipsId(travelTipsId);
		int rows = super.delete("TRAVEL_TIPS.deleteByPrimarKey", travelTips);
		return rows;
	}
}
