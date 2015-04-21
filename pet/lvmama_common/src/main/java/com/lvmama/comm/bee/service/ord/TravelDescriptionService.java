package com.lvmama.comm.bee.service.ord;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderRouteTravel;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;

public interface TravelDescriptionService {
	public void initOrderTravel(final Long productId,final Long orderId,final String createUser);
	public Long viewOrderTravel(final Long orderId);
	
	public String queryContentTravelByOrderId(final Long orderId);
	/**
	 * 取得行程说明
	 * @return
	 */
	public String getTravelDesc(final Long productId, final Long multiJourneyId);
	public List<ViewJourneyPlace> getSelectedProdTarget(final Long journeyId);
	public List<ViewJourney> getViewJourneysById(final Long productId,final Long multiJourneyId) ;
}
