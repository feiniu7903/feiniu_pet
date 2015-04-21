package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.place.Place;

public interface ComPlaceService {
	/**
	 * 根据主键加载一条记录
	 */
	public Place load(Long placeId);
	/**
	 * 根据主键集合获取记录集合
	 * @author ZHANG Nan
	 * @param placeIds
	 * @return
	 */
	public List<Place> selectByPlaceIds(List<Long> placeIds);
}
