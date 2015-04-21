package com.lvmama.ebk.service;

import com.lvmama.comm.bee.po.ebooking.GroupTravelTemplate;
import com.lvmama.comm.bee.service.ebooking.GroupTravelTemplateService;
import com.lvmama.ebk.dao.GroupTravelTemplateDAO;

/**
 * 出团通知书模板
 * 
 * @author zhangwengang
 */
public class GroupTravelTemplateServiceImpl implements
		GroupTravelTemplateService {

	private GroupTravelTemplateDAO groupTravelTemplateDAO;

	@Override
	public void insert(GroupTravelTemplate groupTravelTemplate) {
		groupTravelTemplateDAO.insert(groupTravelTemplate);
	}

	@Override
	public GroupTravelTemplate selectByOrdId(Long orderId) {
		return groupTravelTemplateDAO.selectByOrdId(orderId);
	}

	@Override
	public int update(GroupTravelTemplate groupTravelTemplate) {
		return groupTravelTemplateDAO.update(groupTravelTemplate);
	}

	public GroupTravelTemplateDAO getGroupTravelTemplateDAO() {
		return groupTravelTemplateDAO;
	}

	public void setGroupTravelTemplateDAO(
			GroupTravelTemplateDAO groupTravelTemplateDAO) {
		this.groupTravelTemplateDAO = groupTravelTemplateDAO;
	}

}
