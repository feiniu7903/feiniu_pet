package com.lvmama.ebk.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.GroupTravelTemplate;

public class GroupTravelTemplateDAO extends BaseIbatisDAO {

	public void insert(GroupTravelTemplate groupTravelTemplate) {
		super.insert("GROUP_TRAVEL_TEMPLATE.insert", groupTravelTemplate);
	}

	public int deleteByPrimaryKey(Long routeTravelId) {
		int rows = super.delete("GROUP_TRAVEL_TEMPLATE.deleteByPrimaryKey",
				routeTravelId);
		return rows;
	}

	public GroupTravelTemplate selectByOrdId(Long orderId) {
		GroupTravelTemplate groupTravelTemplate = (GroupTravelTemplate) super
				.queryForObject("GROUP_TRAVEL_TEMPLATE.selectByOrdId", orderId);
		return groupTravelTemplate;
	}

	public int update(GroupTravelTemplate groupTravelTemplate) {
		int rows = super.update("GROUP_TRAVEL_TEMPLATE.update",
				groupTravelTemplate);
		return rows;
	}
}
