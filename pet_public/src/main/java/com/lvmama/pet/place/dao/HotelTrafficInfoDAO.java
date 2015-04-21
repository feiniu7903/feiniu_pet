package com.lvmama.pet.place.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.HotelTrafficInfo;

public class HotelTrafficInfoDAO extends BaseIbatisDAO {
	/**
	 * 新增酒店交通信息
	 * @param trafficInfo 酒店交通信息
	 */
	public void insert(final HotelTrafficInfo trafficInfo) {
		if (null != trafficInfo) {
			super.insert("HOTEL_TRAFFIC_INFO.insert", trafficInfo);
		}
	}
	
	/**
	 * 修改酒店交通信息
	 * @param trafficInfo
	 */
	public void update(final HotelTrafficInfo trafficInfo) {
		if (null != trafficInfo) {
			super.insert("HOTEL_TRAFFIC_INFO.update", trafficInfo);
		}
	}
	
	/**
	 * 查询酒店的交通信息
	 * @param placeId 酒店标识
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HotelTrafficInfo> queryByPlaceId(final Long placeId) {
		if (null != placeId) {
			return (List<HotelTrafficInfo>) super.queryForList("HOTEL_TRAFFIC_INFO.queryByPlaceId", placeId);
		} else {
			return null;
		}
	}
	
	/**
	 * 查询酒店的交通信息
	 * @param placeId 酒店标识
	 * @return
	 */
	public HotelTrafficInfo queryById(final Long id) {
		if (null != id) {
			return (HotelTrafficInfo) super.queryForObject("HOTEL_TRAFFIC_INFO.queryByPK", id);
		} else {
			return null;
		}
	}	
	
	/**
	 * 删除酒店的交通信息
	 * @param trafficInfoId 酒店交通信息标识
	 */
	public void delete(final Long trafficInfoId) {
		if (null != trafficInfoId) {
			super.delete("HOTEL_TRAFFIC_INFO.delete", trafficInfoId);
		}
	}
}
