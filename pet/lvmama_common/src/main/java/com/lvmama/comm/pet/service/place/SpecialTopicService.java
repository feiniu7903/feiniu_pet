package com.lvmama.comm.pet.service.place;

import com.lvmama.comm.pet.po.place.SpecialTopic;

public interface SpecialTopicService {
	/**
	 * 根据编号获取专题对象
	 * @param idcode
	 * @return
	 */
	public SpecialTopic getSpecialTopicByIdcode(String idcode);
}
