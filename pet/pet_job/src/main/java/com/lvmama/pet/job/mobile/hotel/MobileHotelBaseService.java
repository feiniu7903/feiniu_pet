package com.lvmama.pet.job.mobile.hotel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.vo.Constant;

public class MobileHotelBaseService {
	@Autowired
	protected PlacePlaceDestService placePlaceDestService;
	/**
	 * 目的地服务
	 */
	@Autowired
	protected PlaceService placeService;
	
	public Map<String, Object> getNameByLocation(Map<String, Object> param) {
		// 这里需处理 根据找到的城市做一次有门票的搜索 
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String keyword = String.valueOf(param.get("name"));
			if (keyword.contains("市")) {
				keyword = keyword.replace("市", "");
			}
			
			Place city = queryPlaceByName(keyword);
			if (null == city) {
				m.put("id", "");
				m.put("pinyin", "");
				m.put("name", "");
				return m;
			}
/*			if (!Constant.PLACE_TYPE.PROVINCE.name().equals(
							city.getPlaceType())) {
				if (null != city) {
					m.put("id", city.getPlaceId());
					m.put("name", city.getName());
					m.put("pinyin", city.getPinYin());
					m.put("type", Constant.PLACE_TYPE.CITY.name());
				}
			} else {
				List<PlacePlaceDest> ppdList = placePlaceDestService.queryParentPlaceList(city.getPlaceId());
				if (ppdList != null && !ppdList.isEmpty()) {
					m.put("id", ppdList.get(0).getParentPlaceId());
					m.put("name", ppdList.get(0).getParentPlaceName());
					m.put("type", Constant.PLACE_TYPE.PROVINCE.name());
				}

			}*/
			m.put("id", city.getPlaceId());
			m.put("name", city.getName());
			m.put("pinyin", city.getPinYin());
			m.put("type", Constant.PLACE_TYPE.CITY.name());
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
	
	public Place queryPlaceByName(String keyword){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("stage", "1");
		param.put("name", keyword);
		param.put("isValid", "Y");
		List<Place> list = placeService.queryPlaceListByParam(param);
		if(!list.isEmpty()){
			Place p  = list.get(0);
			return p;
		}
		return null;
	}
}
