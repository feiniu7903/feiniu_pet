package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;

/**
 * 行程明细
 * 
 * @author haofeifei
 * 
 */
public interface EbkMultiJourneyService {

	public Long insert(EbkMultiJourney record);

	public List<EbkMultiJourney> queryMultiJourneyByParams(
			Map<String, Object> params);

	public void update(EbkMultiJourney record);

	public EbkMultiJourney selectByPrimaryKey(Long multiJourneyId);

	public List<EbkMultiJourney> getAllMultiJourneyDetailByProductId(
			Long productId);

	public void deleteTimePriceByMultiJourneyId(Long productId,
			Long multiJourneyId);

}
