package com.lvmama.comm.bee.service.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.comm.bee.po.prod.ViewMultiJourney;

/**
 * 行程明细
 * @author shihui
 *
 */
public interface ViewMultiJourneyService {

	public Long insert(ViewMultiJourney record, String operatorName);
	
	public Integer selectRowCount(Map<String, Object> params);
	
	public List<ViewMultiJourney> queryMultiJourneyByParams(Map<String, Object> params);
	
	public void update(ViewMultiJourney record, String operatorName);
	
	public ViewMultiJourney selectByPrimaryKey(Long multiJourneyId);
	
	public List<ViewMultiJourney> getAllMultiJourneyDetailByProductId(Long productId);
	
	public void deleteTimePriceByMultiJourneyId(Long productId, Long multiJourneyId);
	
	public List<Long> getBranchIdsByMultiJourneyId(Long productId, Long multiJourneyId);
	
	public int delete(Long multiJourneyId);
}
