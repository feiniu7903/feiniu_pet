package com.lvmama.comm.bee.service.view;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.TravelTips;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;

/**
 * 产品的旅行须知关联
 * @author yanzhirong
 *
 */
public interface ViewTravelTipsService {
	
	public Long insertViewTravelTips(ViewTravelTips viewTravelTips);
	
	public List<ViewTravelTips> selectByProductId(Long productId);
	
	public ViewTravelTips selectByViewtravelTipsId(Long viewtravelTipsId);
	
	public int deleteViewTravelTips(Long viewTravelTipsId);
		
	public Long insertTravelTips(TravelTips travelTips);
	
	public List<TravelTips> selectByParam(Map paramMap);
	
	public TravelTips selectByTravelTipsId(Long travelTipsId);
	
	public Long selectByParamCount(Map paramMap);
	
	public void updateTravelTips(TravelTips travelTips);
	
	public int deleteTravelTips(Long travelTipsId);
	
	
}
