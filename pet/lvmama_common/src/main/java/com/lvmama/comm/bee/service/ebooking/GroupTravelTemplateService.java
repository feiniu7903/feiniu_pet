package com.lvmama.comm.bee.service.ebooking;

import com.lvmama.comm.bee.po.ebooking.GroupTravelTemplate;

public interface GroupTravelTemplateService {

	public void insert(GroupTravelTemplate groupTravelTemplate);

	public GroupTravelTemplate selectByOrdId(Long orderId);

	public int update(GroupTravelTemplate groupTravelTemplate);
}
