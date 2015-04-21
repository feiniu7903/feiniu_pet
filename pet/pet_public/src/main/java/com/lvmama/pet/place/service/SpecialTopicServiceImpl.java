package com.lvmama.pet.place.service;

import com.lvmama.comm.pet.po.place.SpecialTopic;
import com.lvmama.comm.pet.service.place.SpecialTopicService;
import com.lvmama.pet.place.dao.SpecialTopicDAO;

public class SpecialTopicServiceImpl implements SpecialTopicService {

	private SpecialTopicDAO specialTopicDAO;
	public SpecialTopic getSpecialTopicByIdcode(String idcode) {
		return specialTopicDAO.getSpecialTopicByIdCode(idcode);
	}
	public void setSpecialTopicDAO(SpecialTopicDAO specialTopicDAO) {
		this.specialTopicDAO = specialTopicDAO;
	}
	
}
