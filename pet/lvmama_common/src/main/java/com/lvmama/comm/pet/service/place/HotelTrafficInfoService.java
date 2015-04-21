package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.HotelTrafficInfo;

public interface HotelTrafficInfoService {
	/**
	 * 插入酒店交通信息
	 * @param trafficInfo
	 * @param operatorName
	 */
	void insert(HotelTrafficInfo trafficInfo, String operatorName);
	/**
	 * 更新酒店交通信息
	 * @param trafficInfo
	 * @param operatorName
	 */
	void update(HotelTrafficInfo trafficInfo, String operatorName);
	/**
	 * 删除酒店交通信息
	 * @param trafficInfoId
	 * @param operatorName
	 */
	void delete(Long trafficInfoId, String operatorName);
	/**
	 * 根据酒店标识查询酒店交通信息
	 * @param placeId
	 * @return
	 */
	List<HotelTrafficInfo> queryByPlaceId(Long placeId);
	
	/**
	 * 根据主键查询酒店交通信息
	 * @param id
	 * @return
	 */
	HotelTrafficInfo queryByPlacePK(Long id);
}
