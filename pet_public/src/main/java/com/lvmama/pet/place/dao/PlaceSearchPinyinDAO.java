package com.lvmama.pet.place.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;

public class PlaceSearchPinyinDAO extends BaseIbatisDAO {

	public PlaceSearchPinyin selectByPrimaryKey(Long placeSearchPinyinId) throws SQLException {
		PlaceSearchPinyin key = new PlaceSearchPinyin();
		key.setPlaceSearchPinyinId(placeSearchPinyinId);
		PlaceSearchPinyin record = (PlaceSearchPinyin) super.queryForObject("PLACE_SEARCH_PINYIN.selectByPrimaryKey", key);
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<PlaceSearchPinyin> queryPlacePinyinList(PlaceSearchPinyin placeSearchPinyin) {
		return super.queryForList("PLACE_SEARCH_PINYIN.queryByPlaceSearchPinyin", placeSearchPinyin);
	}
	
	/**
	 * delete
	 * @param placeSearchPinyin
	 */
	public void deleteHFKWPlaceSearchPinyin(PlaceSearchPinyin placeSearchPinyin) {
		int rows = super.delete("PLACE_SEARCH_PINYIN.deleteByPrimaryKey", placeSearchPinyin);
	}
	
	public void deletePlaceSearchPinyinByMap(Map<String, Object> params) {
		if (null == params || params.isEmpty()) {
			return;
		}
		super.delete("PLACE_SEARCH_PINYIN.deleteByParam", params);
	}
	
	/**
	 * save or update
	 * 
	 * @param placeSearchPinyin
	 */
	public void saveOrUpdate(PlaceSearchPinyin placeSearchPinyin) {
		if(placeSearchPinyin.getPlaceSearchPinyinId() == null) {
			super.insert("PLACE_SEARCH_PINYIN.insert", placeSearchPinyin);
		} else {
			super.update("PLACE_SEARCH_PINYIN.update", placeSearchPinyin);
		}
	}

	public void updatePlaceHFKW(PlaceSearchPinyin placeSearchPinyin) {
		super.update("PLACE_SEARCH_PINYIN.updatePlaceHFKW", placeSearchPinyin);
	}
}