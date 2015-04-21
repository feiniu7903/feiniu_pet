package com.lvmama.pet.place.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.SpecialTopic;

public class SpecialTopicDAO extends BaseIbatisDAO {
	public SpecialTopic getSpecialTopicByIdCode(String idCode){
		return (SpecialTopic)super.queryForObject("SPECIAL_TOPIC.getSpecialTopicByIdCode",idCode);
	}
}
