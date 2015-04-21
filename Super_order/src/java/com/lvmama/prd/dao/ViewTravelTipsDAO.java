package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;

public class ViewTravelTipsDAO extends BaseIbatisDAO{
	
	public List<ViewTravelTips> selectByProductId(Long productId){
		return super.queryForList("VIEW_TRAVEL_TIPS.selectByProductId", productId);
	}
	
	public ViewTravelTips selectByViewtravelTipsId(Long viewTravelTipsId){
		return (ViewTravelTips)super.queryForObject("VIEW_TRAVEL_TIPS.selectByViewtravelTipsId", viewTravelTipsId);
	}
	
	public Long insert(ViewTravelTips viewTravelTips) {
        return (Long)super.insert("VIEW_TRAVEL_TIPS.insert", viewTravelTips);
    }
	
	public int deleteByPrimaryKey(Long viewTravelTipsId) {
		ViewTravelTips viewTravelTips = new ViewTravelTips();
		viewTravelTips.setViewTravelTipsId(viewTravelTipsId);
        int rows = super.delete("VIEW_TRAVEL_TIPS.deleteByPrimaryKey", viewTravelTips);
        return rows;
    }	
}
