package com.lvmama.pet.place.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceAirline;

public class PlaceAirlineDAO extends BaseIbatisDAO {

    public PlaceAirlineDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long placeAirlineId) {
        PlaceAirline key = new PlaceAirline();
        key.setPlaceAirlineId(placeAirlineId);
        int rows = super.delete("PLACE_AIRLINE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(PlaceAirline record) {
        Object newKey = super.insert("PLACE_AIRLINE.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(PlaceAirline record) {
        Object newKey = super.insert("PLACE_AIRLINE.insertSelective", record);
        return (Long) newKey;
    }

    public PlaceAirline selectByPrimaryKey(Long placeAirlineId) {
        PlaceAirline key = new PlaceAirline();
        key.setPlaceAirlineId(placeAirlineId);
        PlaceAirline record = (PlaceAirline) super.queryForObject("PLACE_AIRLINE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(PlaceAirline record) {
        int rows = super.update("PLACE_AIRLINE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(PlaceAirline record) {
        int rows = super.update("PLACE_AIRLINE.updateByPrimaryKey", record);
        return rows;
    }
    
	public List<PlaceAirline> queryPlaceAirLineList(Map<String,Object> param){
		List<PlaceAirline> placeAirLineList=(List<PlaceAirline>)super.queryForList("PLACE_AIRLINE.queryPlaceAirlineList",param);
		return placeAirLineList;
	}

	public Long countPlaceAirLineList(Map<String,Object> param){
		Long totalResultSize = (Long) super.queryForObject("PLACE_AIRLINE.countPlaceAirlineList", param);
		return totalResultSize;
	}
	
	public Long countPlaceAirLineBycode(String airlineCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("airlineCode", airlineCode);
		Long totalResultSize = (Long) super.queryForObject("PLACE_AIRLINE.checkAirlineByairlineCode", param);
		return totalResultSize;
	}
}